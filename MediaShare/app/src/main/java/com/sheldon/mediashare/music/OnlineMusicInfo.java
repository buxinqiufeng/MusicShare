package com.sheldon.mediashare.music;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by archermind on 17-4-13.
 */

public class OnlineMusicInfo extends MusicInfoBase implements Parcelable {
    public OnlineMusicInfo(String displayName, String title, String mineType, String uri, String artist, String album, int duration, int size, String publishTime) {
        super(displayName, title, mineType, uri, artist, album, duration, size, publishTime);
    }

    public OnlineMusicInfo() {

    }

    protected OnlineMusicInfo(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<OnlineMusicInfo> CREATOR = new Creator<OnlineMusicInfo>() {
        @Override
        public OnlineMusicInfo createFromParcel(Parcel in) {
            return new OnlineMusicInfo(in);
        }

        @Override
        public OnlineMusicInfo[] newArray(int size) {
            return new OnlineMusicInfo[size];
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
