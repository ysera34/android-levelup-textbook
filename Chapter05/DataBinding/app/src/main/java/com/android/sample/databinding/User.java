package com.android.sample.databinding;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Created by yoon on 2017. 7. 31..
 */

public class User {

    public ObservableField<String> mName = new ObservableField<>();
    public ObservableInt mAge = new ObservableInt();
    public ObservableInt mLikes = new ObservableInt();

    public User(ObservableField<String> name, ObservableInt age) {
        mName = name;
        mAge = age;
        mLikes.set(0);
    }

    public User(String name, int age) {
        mName.set(name);
        mAge.set(age);
        mLikes.set(0);
    }

    public void onClickLike(View view) {
        mLikes.set(mLikes.get() + 1);
    }
}
