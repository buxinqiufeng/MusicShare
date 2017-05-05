package com.sheldon.mediashare;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sheldon.mediashare.music.ILocalMusicChangedCallback;
import com.sheldon.mediashare.music.LocalMusicInfo;
import com.sheldon.mediashare.music.LocalMusicManager;
import com.sheldon.mediashare.music.MusicInfoBase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 17-4-13.
 */

public class LocalMusicFragment extends BaseFragment {
    private static final String TAG = "LocalMusicFragment";
    private LocalMusicManager mLocalMusicManager;
    private ArrayList<LocalMusicInfo> mMusicList = new ArrayList<LocalMusicInfo>() {};
    private Context mContext;
    ListView mListViewMusic;
    MusicListAdapter mMusicListAdapter;

    OnPlayMusicChangedListener mPlayMusicChangedListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mPlayMusicChangedListener = (OnPlayMusicChangedListener)activity;
        } catch(ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPlayMusicChangedListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_local_music_fragment, null, false);

        mListViewMusic = (ListView)view.findViewById(R.id.lv_music);
        mMusicListAdapter = new MusicListAdapter();
        mListViewMusic.setAdapter(mMusicListAdapter);
        mListViewMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<MusicInfoBase> playList = new ArrayList<MusicInfoBase>();
                for(int pos=i; pos <mMusicList.size(); pos++) {
                    playList.add(mMusicList.get(pos));
                }
                for(int pos=0; pos<i; pos++) {
                    playList.add(mMusicList.get(pos));
                }
                mPlayMusicChangedListener.onPlayStatusChanged(playList, PlayStatus_Play);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

        mContext.bindService(Common.generateIntentForService(mContext, Common.ACTION_START_ALLINONE), mAllInOneConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mContext.unbindService(mAllInOneConn);
        mContext = null;
    }

    private IAllInOneService mAllinOneService;
    private ServiceConnection mAllInOneConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAllinOneService = IAllInOneService.Stub.asInterface(iBinder);
            try {
                Log.i(TAG, "registLocalMusicChangedListener");
                mAllinOneService.registLocalMusicChangedListener(mLocalMusicChangedCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mAllinOneService = null;
        }
    };
    private ILocalMusicChangedCallback mLocalMusicChangedCallback = new ILocalMusicChangedCallback() {
        @Override
        public void onChanged(int type, List<LocalMusicInfo> list) throws RemoteException {

            Log.i(TAG, "ILocalMusicChangedCallback-onChanged,type="+type);
            switch (type) {
                case LocalMusicManager.LocalMusicChanged_REFRASH:
                    mMusicList.clear();
                    mMusicList.addAll(list);
                    mMusicListAdapter.notifyDataSetChanged();
                    break;
                case LocalMusicManager.LocalMusicChanged_ADD:
                    break;
                case LocalMusicManager.LocalMusicChanged_REMOVE:
                    break;
                case LocalMusicManager.LocalMusicInfoChanged_CHANGED:
                    break;
                default:
            }
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    private class MusicListAdapter extends BaseAdapter {

        private LayoutInflater mListContainer;
        public MusicListAdapter() {
            mListContainer = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mMusicList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MusicListItemHolder holder = null;
            if(null == view) {
                view = mListContainer.inflate(R.layout.local_music_list_item, null);
                holder = new MusicListItemHolder();
                holder.tvDisplayName = (TextView) view.findViewById(R.id.tv_name);
                holder.tvQuality = (TextView) view.findViewById(R.id.tv_quality);
                holder.tvFormat = (TextView) view.findViewById(R.id.tv_format);
                holder.tvArtist = (TextView) view.findViewById(R.id.tv_artist);
                holder.tvSize = (TextView) view.findViewById(R.id.tv_size);
                view.setTag(holder);
            } else {
                holder = (MusicListItemHolder) view.getTag();
            }

            LocalMusicInfo musicInfo = mMusicList.get(i);
            //Log.d(TAG, ""+musicInfo);
            holder.tvDisplayName.setText(musicInfo.displayName);
            holder.tvQuality.setText("320K");
            holder.tvFormat.setText("mp3");
            holder.tvArtist.setText(musicInfo.artist);
            holder.tvSize.setText(musicSizeToString(musicInfo.size));

            return view;
        }
    }

    private String musicSizeToString(int size) {
        DecimalFormat dnf=new DecimalFormat ("##0.0");
        String strSize=dnf.format(size/1000000.0);
        return strSize+"M";
    }

    private class MusicListItemHolder {
        public TextView tvDisplayName;
        public TextView tvQuality;
        public TextView tvFormat;
        public TextView tvArtist;
        public TextView tvSize;
    }
}
