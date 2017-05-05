package com.sheldon.mediashare;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.musicshare.sheldon.mediashare.user.UserInfo;

/**
 * Created by archermind on 17-4-12.
 */

public class UserInfoView extends LinearLayout {

    private ImageView mImageViewUserHead;
    private TextView mTextViewUserName;
    private TextView mTextViewLevel;
    private TextView mTextViewFans;
    private TextView mTextViewContribute;

    public UserInfoView(Context context) {
        this(context, null);
    }

    public UserInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.user_info_layout, this, true);
        mImageViewUserHead = (ImageView) findViewById(R.id.iv_user_head);
        mTextViewUserName = (TextView) findViewById(R.id.tv_user_name);
        mTextViewLevel = (TextView) findViewById(R.id.tv_level);
        mTextViewFans = (TextView) findViewById(R.id.tv_fans);
        mTextViewContribute = (TextView) findViewById(R.id.tv_contribute);
    }

    public void setData(UserInfo ui) {
        mImageViewUserHead.setImageDrawable(ui.headImage);
        mTextViewUserName.setText(ui.displayName);
        mTextViewLevel.setText(""+ui.level);
        mTextViewFans.setText(""+ui.fans);
        mTextViewContribute.setText(""+ui.contribute);
        requestLayout();
    }
}
