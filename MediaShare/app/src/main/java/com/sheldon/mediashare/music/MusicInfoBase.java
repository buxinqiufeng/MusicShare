package com.sheldon.mediashare.music;

import android.graphics.drawable.Drawable;

/**
 * Created by archermind on 17-4-13.
 */

public class MusicInfoBase {
    public String displayName;
    public String artist;
    public String album;
    public int duration;
    public int size;
    public String publishTime;
    public String title;
    public String mineType;
    public String uri;
    public int numLove;

    public MusicInfoBase(String displayName, String title, String mineType, String uri, String artist, String album, int duration, int size, String publishTime) {
        this(displayName, title, mineType, uri, artist, album, duration, size, publishTime, -1);
    }

    public MusicInfoBase(String displayName, String title, String mineType, String uri, String artist, String album, int duration, int size, String publishTime, int love) {
        this.displayName = displayName;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.size = size;
        this.publishTime = publishTime;
        this.title = title;
        this.mineType = mineType;
        this.uri = uri;
        this.numLove = love;
    }

    public MusicInfoBase() {
    }

    public String toString() {
        return "Music info: displayname="+displayName
                +",title="+title
                +",minetype="+mineType
                +",uri="+uri
                +",artist="+artist
                +"album="+album
                +"duration="+duration
                +"publishtime="+publishTime
                +"size="+size;
    }
}
