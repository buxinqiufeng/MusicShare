package com.sheldon.mediashare.music;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

import com.sheldon.mediashare.Common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by archermind on 17-4-13.
 */

public class LocalMusicManager {
    private static final String TAG = "LocalMusicManager";

    private Context mContext;
    private ArrayList<ILocalMusicChangedCallback> mCallbacks = new ArrayList<ILocalMusicChangedCallback>();
    private Handler mHandler = new Handler();

    public static final int LocalMusicChanged_REFRASH = 1;
    public static final int LocalMusicChanged_ADD = 2;
    public static final int LocalMusicChanged_REMOVE = 3;
    public static final int LocalMusicInfoChanged_CHANGED = 4;

    private ArrayList<LocalMusicInfo> mMusicList = new ArrayList<LocalMusicInfo>();

    public LocalMusicManager(Context context) {
        mContext = context;
    }

    public void startScan() {
        mHandler.post(mScanMusicRunnable);
    }

    public void registListener(ILocalMusicChangedCallback cb) {
        mCallbacks.add(cb);
        try {
            if(mMusicList.size() > 0) {
                cb.onChanged(LocalMusicChanged_REFRASH, mMusicList);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregistListener(ILocalMusicChangedCallback cb) {
        mCallbacks.remove(cb);
    }

    private Runnable mScanMusicRunnable = new Runnable() {
        @Override
        public void run() {
            if(null == mContext) return;

            Log.d(TAG, "begin scan local music.");
            Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            if(null == cursor) {
                Log.e(TAG, "query failed.");
            } else if(!cursor.moveToFirst()) {
                Log.e(TAG, "cursor move to first failed.");
            } else {
                do{
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                    String mineType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                    String uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String year = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR));//publish time.
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    LocalMusicInfo localMusicInfo = new LocalMusicInfo(displayName, title, mineType, uri, artist, album, duration, size, year);
                    mMusicList.add(localMusicInfo);
                    Log.d(TAG, "find new song,"+localMusicInfo);
                } while(cursor.moveToNext());
            }
            cursor.close();

            //从歌曲的显示名字中重新解析信息
            //因为循环中有删除操作，所以不能使用for循环遍历list
            Iterator<LocalMusicInfo> it = mMusicList.iterator();
            while(it.hasNext()) {
                LocalMusicInfo music = it.next();
                if(music.size < Common.MIN_MUSIC_SIZE) {
                    it.remove();
                    continue;
                }
                reparseMusic(music);
            }

            Log.d(TAG, "finish scan local music.total songs:"+mMusicList.size());
            notifyListeners(LocalMusicChanged_REFRASH, mMusicList);
        }
    };

    private void reparseMusic(LocalMusicInfo music) {
        String displayName = removeSuffix(music.displayName);
        String seperapter = " - ";//分隔符
        String parseName = null;
        String parseArtist = null;
        boolean parseSucess = false;
        int pos;
        if((pos = displayName.indexOf(seperapter)) != -1) {
            parseArtist = displayName.substring(0, pos);
            parseName = displayName.substring(pos+seperapter.length());
            parseSucess = true;
            Log.d(TAG, "parseArtist="+parseArtist+",parseName="+parseName);
            if(parseArtist.contains(seperapter) || parseName.contains(seperapter)) {//如果解析后的字符串中含有分隔符，那认为解析失败
                parseSucess = false;
            }
        }
        if(parseSucess) {
            music.displayName = parseName;
            music.artist = parseArtist;
        } else {
            music.displayName = displayName;//这里的displayName去除了后缀名
        }
    }

    private String removeSuffix(String str) {
        if(null == str) return null;

        int pos = str.lastIndexOf(".");
        if(pos > 0) {
            return str.substring(0, pos);
        } else {
            return str;
        }
    }

    private void notifyListeners(int type, List<LocalMusicInfo> musicList) {
        Log.d(TAG, "notifyListeners");
        for(ILocalMusicChangedCallback cb : mCallbacks) {
            Log.d(TAG, "notifyListeners-1");
            try {
                cb.onChanged(type, musicList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

//    public static abstract class MusicChangedListener {
//        public abstract void onLocalMusicChanged(LocalMusicChangedType changedType, List<LocalMusicInfo> musicList);
//    }
}
