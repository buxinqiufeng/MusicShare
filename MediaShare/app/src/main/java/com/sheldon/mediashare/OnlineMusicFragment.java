package com.sheldon.mediashare;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sheldon.mediashare.music.LocalMusicInfo;
import com.sheldon.mediashare.music.OnlineMusicInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 17-4-13.
 */

public class OnlineMusicFragment extends BaseFragment {

    private TextView mTextviewSearch;
    private Button mBtnSearch;
    private ListView mListview;
    private List<TopBoard> mTopboardList = new ArrayList<TopBoard>();

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_online_music_fragment, null, false);

        mContext = getContext();

        mTextviewSearch = (TextView) view.findViewById(R.id.tv_online_search);
        mBtnSearch = (Button) view.findViewById(R.id.bt_online_search);
        mListview = (ListView) view.findViewById(R.id.lv_online_topboard);

        ListAdapter adapter = new ListAdapter();
        mListview.setAdapter(adapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        return view;
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

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ListItemHolder holder = null;
            if(null == view) {
                view = mListContainer.inflate(R.layout.local_music_list_item, null);
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

        return String.format("%d.%s", index+1, generateShotItem(list.get(index)));
    }

    private String generateShotItem(OnlineMusicInfo musicInfo) {
        String str = musicInfo.displayName;
        if(null != musicInfo.title && null != musicInfo.artist) {
            str = musicInfo.title+"("+musicInfo.artist+")";
        } else {
            return str;
        }
        if(null != musicInfo.album) {
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
