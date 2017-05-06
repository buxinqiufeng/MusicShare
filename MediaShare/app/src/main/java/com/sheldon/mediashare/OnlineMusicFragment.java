package com.sheldon.mediashare;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sheldon.mediashare.music.IOnlineMusicChangedCallback;
import com.sheldon.mediashare.music.LocalMusicInfo;
import com.sheldon.mediashare.music.LocalMusicManager;
import com.sheldon.mediashare.music.OnlineMusicInfo;
import com.sheldon.mediashare.music.OnlineMusicManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 17-4-13.
 */

public class OnlineMusicFragment extends BaseFragment {
    private static final String TAG = "OnlineMusicFragment";

    private EditText mEditTextSearch;
    private ImageView mBtnSearch;
    private ListView mListview;
    private ListAdapter mListAdapter;
    private List<TopBoard> mTopboardList = new ArrayList<TopBoard>();

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_online_music_fragment, null, false);

        mContext = getContext();

        mEditTextSearch = (EditText) view.findViewById(R.id.tv_online_search);
        mBtnSearch = (ImageView) view.findViewById(R.id.iv_online_search);
        mListview = (ListView) view.findViewById(R.id.lv_online_topboard);

        mListAdapter = new ListAdapter();
        mListview.setAdapter(mListAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mContext.bindService(Common.generateIntentForService(mContext, Common.ACTION_START_ALLINONE), mAllInOneConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mContext.unbindService(mAllInOneConn);
    }

    private IAllInOneService mAllinOneService;
    private ServiceConnection mAllInOneConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAllinOneService = IAllInOneService.Stub.asInterface(iBinder);
            try {
                Log.i(TAG, "registOnlineMusicChangedListener");
                mAllinOneService.registOnlineMusicChangedListener(mOnlineMusicChangedCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mAllinOneService = null;
        }
    };

    private IOnlineMusicChangedCallback mOnlineMusicChangedCallback = new IOnlineMusicChangedCallback() {

        @Override
        public void onChanged(int grouptype, String groupname, int changetype, List<OnlineMusicInfo> list) throws RemoteException {
            Log.i(TAG, "ILocalMusicChangedCallback-onChanged,grouptype="+grouptype+", groupname="+groupname+", changetype="+changetype);
            switch (grouptype) {
                case OnlineMusicManager.TOP_BOARD_GROUP://top board中音乐有变化
                    TopBoard topBoard = getTopboardByName(groupname);
                    if(null == topBoard) {
                        topBoard = new TopBoard();
                        topBoard.title = groupname;
                        mTopboardList.add(topBoard);
                    }
                    if(OnlineMusicManager.MUSIC_CHANGE_REFRESH == changetype) {
                        topBoard.mMusicList.clear();
                    }

                    topBoard.mMusicList.addAll(list);
                    mListAdapter.notifyDataSetChanged();
                    break;
                default:
            }
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };

    private TopBoard getTopboardByName(String name) {
        for(TopBoard board : mTopboardList) {
            if(board.title.equals(name)) {
                return board;
            }
        }
        return null;
    }

    private class ListAdapter extends BaseAdapter {

        private LayoutInflater mListContainer;
        public ListAdapter() {
            mListContainer = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mTopboardList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        private int colorPicker[] = {0xcce60707, 0xccFF4081, 0xcc3F51B5};
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListItemHolder holder = null;
            if(null == view) {
                view = mListContainer.inflate(R.layout.music_top_board_list_item, null);
                LinearLayout layout_bg = (LinearLayout)view.findViewById(R.id.layout_bg);
                layout_bg.setBackgroundColor(colorPicker[i%colorPicker.length]);
                holder = new ListItemHolder();
                holder.tvTitle = (TextView) view.findViewById(R.id.tv_top_board_title);
                holder.tvUpdatetime = (TextView) view.findViewById(R.id.tv_top_board_updatetime);
                holder.tvTop1 = (TextView) view.findViewById(R.id.tv_top_board_item1);
                holder.tvTop2 = (TextView) view.findViewById(R.id.tv_top_board_item2);
                holder.tvTop3 = (TextView) view.findViewById(R.id.tv_top_board_item3);
                holder.tvTop4 = (TextView) view.findViewById(R.id.tv_top_board_item4);
                holder.tvTop5 = (TextView) view.findViewById(R.id.tv_top_board_item5);
                view.setTag(holder);
            } else {
                holder = (ListItemHolder) view.getTag();
            }

            TopBoard topBoard = mTopboardList.get(i);
            holder.tvTitle.setText(topBoard.title);
            holder.tvUpdatetime.setText(topBoard.updateTime);
            List<OnlineMusicInfo> musicList = topBoard.mMusicList;
            holder.tvTop1.setText(getItemString(0, musicList));
            holder.tvTop2.setText(getItemString(1, musicList));
            holder.tvTop3.setText(getItemString(2, musicList));
            holder.tvTop4.setText(getItemString(3, musicList));
            holder.tvTop5.setText(getItemString(4, musicList));

            return view;
        }
    }

    private String getItemString(int index, List<OnlineMusicInfo> list) {
        if(null == list || list.size()-1 < index) return "";
Log.d(TAG, "getItemString-musicinfo="+list.get(index));
        return String.format("%d.%s", index+1, generateShotItem(list.get(index)));
    }

    private String generateShotItem(OnlineMusicInfo musicInfo) {
        String str = musicInfo.displayName;
        if(null != musicInfo.title && musicInfo.title.length() > 0
                && null != musicInfo.artist && musicInfo.artist.length() > 0) {
            str = musicInfo.title+"("+musicInfo.artist+")";
        } else {
            return str;
        }
        if(null != musicInfo.album && musicInfo.album.length() > 0) {
            str += " - "+musicInfo.album;
        }
        return str;
    }

    private class ListItemHolder {
        public TextView tvTitle;
        public TextView tvUpdatetime;
        public TextView tvTop1;
        public TextView tvTop2;
        public TextView tvTop3;
        public TextView tvTop4;
        public TextView tvTop5;
    }
}
