package com.sheldon.mediashare;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicshare.sheldon.mediashare.user.UserInfo;

/**
 * Created by archermind on 17-4-13.
 */

public class MyShareFragment extends BaseFragment {

    private UserInfoView mUserInfoView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_shared_music_fragment, null, false);
        mUserInfoView = (UserInfoView)view.findViewById(R.id.user_info_view);

        mUserInfoView = (UserInfoView) view.findViewById(R.id.user_info_view);
        //if(null != mUserInfoView) {
        UserInfo ui = new UserInfo();
        ui.headImage = this.getContext().getDrawable(R.drawable.icon_record_normal);
        ui.displayName = "这是一个测试用户";
        ui.level = 11;
        ui.fans = 100001;
        ui.contribute = 3987;
        mUserInfoView.setData(ui);
        //}

        return view;
    }
}
