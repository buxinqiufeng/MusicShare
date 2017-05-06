package com.sheldon.mediashare.nowplaying;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sheldon.mediashare.BaseFragment;
import com.sheldon.mediashare.R;
import com.sheldon.mediashare.music.MusicInfoBase;

import java.io.IOException;
import java.util.List;

/**
 * Created by archermind on 17-5-2.
 */

public class NowPlayingView extends LinearLayout {
    private static final String TAG = "NowPlayingView";

    private static final int MSG_PLAYING_TICK = 1001;

    private ImageView mImageviewPlay;
    private TextView mTextviewSongName;
    private TextView mTextviewArtist;
    private TextView mTextviewSongTime;
    private ImageView mImageviewPrise;
    private SeekBar mSeekBar;
    private MediaPlayer mMediaPlayer;

    private List<MusicInfoBase> mPlayList;
    private MusicInfoBase mCurPlaying;
    private Handler mHandler;

    private BaseFragment.OnPlayMusicChangedListener mPlayChangedListener;

    public NowPlayingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(LinearLayout.HORIZONTAL);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_nowplaying, this, true);
        setBackgroundColor(0x00494949);//浅灰色

        mImageviewPlay = (ImageView) view.findViewById(R.id.iv_play_status);
        mTextviewSongName = (TextView) view.findViewById(R.id.tv_playing_song);
        mTextviewArtist = (TextView) view.findViewById(R.id.tv_playing_artist);
        mTextviewSongTime = (TextView) view.findViewById(R.id.tv_playing_time);
        mImageviewPrise = (ImageView) view.findViewById(R.id.iv_prise);
        mSeekBar = (SeekBar) view.findViewById(R.id.pb_playing_progress);

        mImageviewPlay.setOnClickListener(mOnPlayBtnClickListener);
        mSeekBar.setOnSeekBarChangeListener(mOnSeekbarChangedLister);

        mImageviewPlay = (ImageView) view.findViewById(R.id.iv_play_status);
        //mImageviewPlay.setScaleType(ImageView.ScaleType.FIT_XY);
        //mImageviewPrise.setScaleType(ImageView.ScaleType.FIT_XY);
        int screenWidth = getWindowWidth(getContext());
        if(0 != screenWidth) {
            mImageviewPlay.setMaxWidth(screenWidth/7);
            //mImageviewPrise.setMaxWidth(screenWidth/12);
        } else {
            mImageviewPlay.setMaxWidth(64);
        }

        mImageviewPrise.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playNextSong();
                palyStatusChanged();
                updateView();
            }
        });

        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {

                switch (msg.what) {
                    case MSG_PLAYING_TICK:
                        updateView();
                        palyStatusChanged();
                        sendEmptyMessageDelayed(MSG_PLAYING_TICK, 1000);
                        break;
                }
            }
        };
    }

    public void setPlayingSong(MusicInfoBase music) {
        mCurPlaying = music;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(music.uri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mHandler.sendEmptyMessage(MSG_PLAYING_TICK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateView();
        palyStatusChanged();
    }

    public void setPlayList(List<MusicInfoBase> list) {
        mPlayList = list;
        if(null != mPlayList && mPlayList.size()>0) {
            setPlayingSong(mPlayList.get(0));
        }
    }

    public void setPlayStatusListener(BaseFragment.OnPlayMusicChangedListener listener) {
        mPlayChangedListener = listener;
    }

    public boolean isPlaying() {
        if(null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            return true;
        } else {
            return false;
        }
    }

    private void playNextSong() {
        mMediaPlayer.reset();
        if(null != mPlayList && mPlayList.size() > 0) {
            int pos = mPlayList.indexOf(mCurPlaying);
            if(pos < mPlayList.size()-1) {
                setPlayingSong(mPlayList.get(pos+1));
            } else {
                setPlayingSong(mPlayList.get(0));
            }
        } else {
            if(null != mPlayChangedListener) {
                mPlayChangedListener.onPlayStatusChanged(null, BaseFragment.PlayStatus_Stop);
            }
            mCurPlaying = null;
        }

    }

    private MusicInfoBase lastPlaying;
    private void updateView() {
        if(null != mCurPlaying) {
            if(lastPlaying != mCurPlaying) {
                mSeekBar.setProgress(0);
                mSeekBar.setEnabled(true);
                mTextviewSongName.setText(mCurPlaying.displayName);
                mTextviewArtist.setText(mCurPlaying.artist);
                mSeekBar.setMax(mCurPlaying.duration);

            }
            int curPos = mMediaPlayer.getCurrentPosition();
            mTextviewSongTime.setText(generatePlayTime(mMediaPlayer.getDuration(), curPos));
            mSeekBar.setProgress(curPos);
        } else {
            mSeekBar.setEnabled(false);
            mTextviewSongName.setText("--");
            mTextviewArtist.setText("--");
            mTextviewSongTime.setText("00:00");

        }
        lastPlaying = mCurPlaying;
    }

    private void palyStatusChanged() {
        if(null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            mImageviewPlay.setImageDrawable(getContext().getDrawable(R.drawable.pausepressed));
        } else {
            mImageviewPlay.setImageDrawable(getContext().getDrawable(R.drawable.play1pressed));
        }
    }

    private String generatePlayTime(int total, int cur) {
        return formatTime(cur)+"/"+formatTime(total);
    }

    private String formatTime(int t) {
        int seconds = t/1000;
        return String.format("%02d:%02d",seconds/60, seconds%60);
    }

    private SeekBar.OnSeekBarChangeListener mOnSeekbarChangedLister = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(null != mMediaPlayer) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        }
    };

    private OnClickListener mOnPlayBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
            palyStatusChanged();
        }
    };

    private int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;
        if(null != wm) {
            width = wm.getDefaultDisplay().getWidth();
        }
        return width;
    }
}

