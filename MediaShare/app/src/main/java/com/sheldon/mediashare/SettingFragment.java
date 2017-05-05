package com.sheldon.mediashare;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by archermind on 17-4-13.
 */

public class SettingFragment extends BaseFragment {

    private UserInfoView mUserInfoView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_setting_fragment, null, false);

        UserInfoView mUserInfoView = (UserInfoView) view.findViewById(R.id.user_info_view);
        return view;
    }
}
