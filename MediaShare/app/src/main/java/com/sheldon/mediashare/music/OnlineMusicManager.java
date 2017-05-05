package com.sheldon.mediashare.music;

import android.content.Context;
import android.os.RemoteException;

import com.sheldon.mediashare.Common;
import com.sheldon.mediashare.TopBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 17-5-5.
 */

public class OnlineMusicManager {

    private Context mContext;
    private List<IOnlineMusicChangedCallback> mCallbacks = new ArrayList<IOnlineMusicChangedCallback>();

    public static final int TOP_BOARD_GROUP = 1001;

    public static final int MUSIC_CHANGE_REFRESH = 1;

    private List<TopBoard> mListTopboard = new ArrayList<TopBoard>();

    private void mock_data() {
        if(!Common.MOCK_MODE) return;
        TopBoard board = new TopBoard();
        board.title = "影视热歌榜";
        board.updateTime = "2016-11-23";
        board.mMusicList.add(new OnlineMusicInfo("三生三世", "三生三世", "", "", "张丽颖", "<<桃花源>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("逆战-张杰", "逆战", "", "", "张杰", "<<我们的未来>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("春风实力", "春风实力", "", "", "佚名", "<<十里春风>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("思美人", "思美人", "", "", "王杰", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("", "漂洋过海来看你", "", "", "娃娃", "<<思念>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        mListTopboard.add(board);
        board = new TopBoard();
        board.title = "亚洲流行榜";
        board.updateTime = "2016-11-2";
        board.mMusicList.add(new OnlineMusicInfo("刚好遇见你", "刚好遇见你", "", "", "李玉刚", "<<刚好>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("演员-薛之谦", "演员", "", "", "薛之谦", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("你还要我怎样-薛之谦", "你还要我怎样", "", "", "薛之谦", "<<暧昧>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        mListTopboard.add(board);
        board = new TopBoard();
        board.title = "动漫热歌榜";
        board.updateTime = "2016-11-23";
        board.mMusicList.add(new OnlineMusicInfo("三生三世", "三生三世", "", "", "张丽颖", "<<桃花源>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("逆战-张杰", "逆战", "", "", "张杰", "<<我们的未来>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("春风实力", "春风实力", "", "", "佚名", "<<十里春风>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("思美人", "思美人", "", "", "王杰", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("", "漂洋过海来看你", "", "", "娃娃", "<<思念>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        mListTopboard.add(board);
        board = new TopBoard();
        board.title = "欧洲流行榜";
        board.updateTime = "2016-11-2";
        board.mMusicList.add(new OnlineMusicInfo("刚好遇见你", "刚好遇见你", "", "", "李玉刚", "<<刚好>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("演员-薛之谦", "演员", "", "", "薛之谦", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("你还要我怎样-薛之谦", "你还要我怎样", "", "", "薛之谦", "<<暧昧>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        mListTopboard.add(board);
        board = new TopBoard();
        board.title = "音乐热歌榜";
        board.updateTime = "2016-11-23";
        board.mMusicList.add(new OnlineMusicInfo("三生三世", "三生三世", "", "", "张丽颖", "<<桃花源>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("逆战-张杰", "逆战", "", "", "张杰", "<<我们的未来>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("春风实力", "春风实力", "", "", "佚名", "<<十里春风>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("思美人", "思美人", "", "", "王杰", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("", "漂洋过海来看你", "", "", "娃娃", "<<思念>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        mListTopboard.add(board);
        board = new TopBoard();
        board.title = "非洲流行榜";
        board.updateTime = "2016-11-2";
        board.mMusicList.add(new OnlineMusicInfo("刚好遇见你", "刚好遇见你", "", "", "李玉刚", "<<刚好>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("演员-薛之谦", "演员", "", "", "薛之谦", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("你还要我怎样-薛之谦", "你还要我怎样", "", "", "薛之谦", "<<暧昧>>",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        board.mMusicList.add(new OnlineMusicInfo("Tommow will be OK", "Tomorrow will be OK", "", "", "Adl", "",100, 100000, ""));
        mListTopboard.add(board);
    }

    public OnlineMusicManager(Context context) {
        mock_data();
    }

    public void registListener(IOnlineMusicChangedCallback cb) {
        if(!mCallbacks.contains(cb)) {
            mCallbacks.add(cb);
        }
        syncData2CbWhenRegisted(cb);
    }

    public void unregistListener(ILocalMusicChangedCallback cb) {
        mCallbacks.remove(cb);
    }

    private void syncData2CbWhenRegisted(IOnlineMusicChangedCallback cb) {
        for(TopBoard board : mListTopboard) {
            try {
                cb.onChanged(TOP_BOARD_GROUP, board.title, MUSIC_CHANGE_REFRESH, board.mMusicList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
