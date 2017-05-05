// IAllInOneInterface.aidl
package com.sheldon.mediashare;

import com.sheldon.mediashare.music.ILocalMusicChangedCallback;

interface IAllInOneService {
    //void registLocalMusicChangedListener(int type, out List<LocalMusicInfo> list);
    void registLocalMusicChangedListener(ILocalMusicChangedCallback cb);
}
