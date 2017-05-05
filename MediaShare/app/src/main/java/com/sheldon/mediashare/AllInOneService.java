package com.sheldon.mediashare;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sheldon.mediashare.music.ILocalMusicChangedCallback;
import com.sheldon.mediashare.music.LocalMusicInfo;
import com.sheldon.mediashare.music.LocalMusicManager;

import java.util.ArrayList;

public class AllInOneService extends Service {
    private static final String TAG = "AllInOneService";
    private LocalMusicManager mLocalMusicManager;

    public AllInOneService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        mLocalMusicManager = new LocalMusicManager(this.getApplicationContext());
        mLocalMusicManager.startScan();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return stub;
    }

    IAllInOneService.Stub stub = new IAllInOneService.Stub() {
        @Override
        public void registLocalMusicChangedListener(ILocalMusicChangedCallback cb) throws RemoteException {
            Log.i(TAG, "registLocalMusicChangedListener");
            mLocalMusicManager.registListener(cb);
        }
    };

}
