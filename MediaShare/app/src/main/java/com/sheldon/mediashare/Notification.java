package com.sheldon.mediashare;

/**
 * Created by archermind on 17-5-9.
 */

public class Notification {
    public static final int TYPE_SYSTEM = 1;
    public static final int TYPE_GROUP = 2;
    public static final int TYPE_FRIEND = 3;

    public String content;
    public String time;
    public int type;
    public boolean haveRead;

    Notification(String content, String time, int type) {
        this.content = content;
        this.time = time;
        this.type = type;
        haveRead = false;
    }

    public String toString() {
        return "Notification:type="+type+",haveRead="+haveRead+",content="+content+",time="+time;
    }
}
