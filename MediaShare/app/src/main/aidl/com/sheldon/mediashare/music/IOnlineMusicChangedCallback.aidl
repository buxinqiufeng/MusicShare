// ILocalMusicChangedCallback.aidl
package com.sheldon.mediashare.music;

import com.sheldon.mediashare.music.OnlineMusicInfo;

interface IOnlineMusicChangedCallback {
    void onChanged(int grouptype, String groupname, int changetype, out List<OnlineMusicInfo> list);
}
