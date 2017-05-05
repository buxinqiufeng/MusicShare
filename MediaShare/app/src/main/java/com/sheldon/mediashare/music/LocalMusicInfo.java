package com.sheldon.mediashare.music;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by archermind on 17-4-13.
 */

public class LocalMusicInfo extends MusicInfoBase implements Parcelable {
    public LocalMusicInfo(String displayName, String title, String mineType, String uri, String artist, String album, int duration, int size, String publishTime) {
        super(displayName, title, mineType, uri, artist, album, duration, size, publishTime);
    }

    public LocalMusicInfo() {

    }

    protected LocalMusicInfo(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<LocalMusicInfo> CREATOR = new Creator<LocalMusicInfo>() {
        @Override
        public LocalMusicInfo createFromParcel(Parcel in) {
            return new LocalMusicInfo(in);
        }

        @Override
        public LocalMusicInfo[] newArray(int size) {
            return new LocalMusicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(displayName);
        parcel.writeString(title);
        parcel.writeString(mineType);
        parcel.writeString(uri);
        parcel.writeString(artist);
        parcel.writeString(album);
        parcel.writeInt(duration);
        parcel.writeInt(size);
        parcel.writeString(publishTime);
    }

    public void readFromParcel(Parcel in) {
        displayName = in.readString();
        title = in.readString();
        mineType = in.readString();
        uri = in.readString();
        artist = in.readString();
        album = in.readString();
        duration = in.readInt();
        size = in.readInt();
        publishTime = in.readString();
    }
}
