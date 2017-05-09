package com.sheldon.mediashare;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.githang.viewpagerindicator.IconPagerAdapter;
import com.githang.viewpagerindicator.IconTabPageIndicator;
import com.sheldon.mediashare.music.ILocalMusicChangedCallback;
import com.sheldon.mediashare.music.LocalMusicInfo;
import com.sheldon.mediashare.music.MusicInfoBase;
import com.sheldon.mediashare.nowplaying.NowPlayingView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements BaseFragment.OnPlayMusicChangedListener{

    private ViewPager mViewPager;
    private IconTabPageIndicator mIndicator;
    private NowPlayingView mPlayingView;

    List<BaseFragment> mFragments;

    private Context mContext;

    private AllInOneService mAllInOneService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        initViews();
        initService();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicator = (IconTabPageIndicator) findViewById(R.id.indicator);
        mPlayingView = (NowPlayingView) findViewById(R.id.nowplaying);
        mPlayingView.setPlayStatusListener(this);
        mPlayingView.setVisibility(View.GONE);
        mFragments = initFragments();
        FragmentAdapter adapter = new FragmentAdapter(mFragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                BaseFragment selectFragment = mFragments.get(i);
                if(selectFragment instanceof LocalMusicFragment
                        && mPlayingView.isPlaying()) {
                    mPlayingView.setVisibility(View.VISIBLE);
                } else {
                    mPlayingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initService() {
        startService(Common.generateIntentForService(mContext, Common.ACTION_START_ALLINONE));
    }



    private List<BaseFragment> initFragments() {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();

        BaseFragment LocalMusicFragment = new LocalMusicFragment();
        LocalMusicFragment.setTitle(mContext.getString(R.string.main_local_music));
        LocalMusicFragment.setIconId(R.drawable.tab_local_music_selector);
        fragments.add(LocalMusicFragment);

        BaseFragment noteFragment = new OnlineMusicFragment();
        noteFragment.setTitle(mContext.getString(R.string.main_online_music));
        noteFragment.setIconId(R.drawable.tab_online_music_selector);
        fragments.add(noteFragment);

        BaseFragment contactFragment = new NotificationFragment();
        contactFragment.setTitle(mContext.getString(R.string.main_notification));
        contactFragment.setIconId(R.drawable.tab_shared_selector);
        fragments.add(contactFragment);

        BaseFragment settingFragment = new SettingFragment();
        settingFragment.setTitle(mContext.getString(R.string.main_setting));
        settingFragment.setIconId(R.drawable.tab_setting_selector);
        fragments.add(settingFragment);

        return fragments;
    }

    @Override
    public void onPlayStatusChanged(List<MusicInfoBase> list, int status) {
        switch (status) {
            case BaseFragment.PlayStatus_Play:
                mPlayingView.setVisibility(View.VISIBLE);
                mPlayingView.setPlayList(list);
                break;
            case BaseFragment.PlayStatus_Pause:

                break;
            case BaseFragment.PlayStatus_Stop:
                mPlayingView.setVisibility(View.GONE);
                break;

        }
    }

    class FragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
        private List<BaseFragment> mFragments;

        public FragmentAdapter(List<BaseFragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getIconResId(int index) {
            return mFragments.get(index).getIconId();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragments.get(position).getTitle();
        }
    }

}