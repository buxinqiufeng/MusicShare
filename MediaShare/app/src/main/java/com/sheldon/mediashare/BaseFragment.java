package com.sheldon.mediashare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheldon.mediashare.R;
import com.sheldon.mediashare.music.MusicInfoBase;

import java.util.List;

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-08-27
 * Time: 09:01
 * FIXME
 */
public class BaseFragment extends Fragment {
    private String title;
    private int iconId;
    private View mView;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public static final int PlayStatus_Play = 1;
    public static final int PlayStatus_Pause = 2;
    public static final int PlayStatus_Stop = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment, null, false);
        TextView textView = (TextView) mView.findViewById(R.id.text);
        textView.setText(getTitle());

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public interface OnPlayMusicChangedListener {
        public void onPlayStatusChanged(List<MusicInfoBase> list, int status);
    }
}