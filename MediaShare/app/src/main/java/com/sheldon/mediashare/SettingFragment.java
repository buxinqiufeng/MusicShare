package com.sheldon.mediashare;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sheldon.mediashare.music.LocalMusicInfo;

/**
 * Created by archermind on 17-4-13.
 */

public class SettingFragment extends BaseFragment {

    private UserInfoView mUserInfoView;
    private ListView mListviewSettings;

    private static final int[] LIST_SETTING_TITLE = new int[]{
            R.string.setting_friend,
            R.string.setting_fans,
            R.string.setting_feedback,
            R.string.setting_about
    };

    private static final int[] LIST_SETTING_SUMMARY = new int[]{
            R.string.setting_friend_summary,
            R.string.setting_fans_summary,
            R.string.setting_feedback_summary,
            R.string.setting_about_summary,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_setting_fragment, null, false);

        mUserInfoView = (UserInfoView) view.findViewById(R.id.user_info_view);
        mListviewSettings = (ListView) view.findViewById(R.id.lv_settings);
        mListviewSettings.setAdapter(new ListAdapter());
        return view;
    }

    private class ListAdapter extends BaseAdapter {

        private LayoutInflater mListContainer;
        public ListAdapter() {
            mListContainer = LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return LIST_SETTING_TITLE.length;
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
            SettingHolder holder;
            if(null == view) {
                view = mListContainer.inflate(R.layout.user_setting_list_item, null);
                holder = new SettingHolder();
                holder.title = (TextView) view.findViewById(R.id.tv_setting_title);
                holder.summary = (TextView) view.findViewById(R.id.tv_setting_summary);
                view.setTag(holder);
            } else {
                holder = (SettingHolder) view.getTag();
            }

            holder.title.setText(getContext().getText(LIST_SETTING_TITLE[i]));
            holder.summary.setText(getContext().getText(LIST_SETTING_SUMMARY[i]));

            return view;
        }

        private class SettingHolder {
            int id;
            TextView title;
            TextView summary;
        }
    }
}
