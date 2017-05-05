// IAllInOneInterface.aidl
package com.sheldon.mediashare;

import com.sheldon.mediashare.music.ILocalMusicChangedCallback;
import com.sheldon.mediashare.music.IOnlineMusicChangedCallback;

interface IAllInOneService {
    //void registLocalMusicChangedListener(int type, out List<LocalMusicInfo> list);
    void registLocalMusicChangedListener(ILocalMusicChangedCallback cb);
    void registOnlineMusicChangedListener(IOnlineMusicChangedCallback cb);
}
