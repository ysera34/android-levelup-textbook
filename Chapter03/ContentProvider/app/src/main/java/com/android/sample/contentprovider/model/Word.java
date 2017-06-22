package com.android.sample.contentprovider.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoon on 2017. 6. 21..
 */

public class Word implements Parcelable {

    private long mId;
    private String mName;
    private String mWords;
    private int mDate;

    public Word() {
    }

    public Word(long id, String name, String words, int date) {
        mId = id;
        mName = name;
        mWords = words;
        mDate = date;
    }

    protected Word(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mWords = in.readString();
        mDate = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mWords);
        dest.writeInt(mDate);
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getWords() {
        return mWords;
    }

    public void setWords(String words) {
        mWords = words;
    }

    public int getDate() {
        return mDate;
    }

    public void setDate(int date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mWords='" + mWords + '\'' +
                ", mDate=" + mDate +
                '}';
    }
}
