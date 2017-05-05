// ILocalMusicChangedCallback.aidl
package com.sheldon.mediashare.music;

import com.sheldon.mediashare.music.LocalMusicInfo;

interface ILocalMusicChangedCallback {
    void onChanged(int type, out List<LocalMusicInfo> list);
}
