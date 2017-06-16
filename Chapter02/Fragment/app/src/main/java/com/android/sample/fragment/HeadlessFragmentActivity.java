package com.android.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yoon on 2017. 6. 17..
 */

public class HeadlessFragmentActivity extends AppCompatActivity {

    private static final String TAG = HeadlessFragmentActivity.class.getSimpleName();
    private static final String TAG_HEADLESS_FRAGMENT = "tag_headless_fragment";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG_HEADLESS_FRAGMENT);

        if (fragment == null) {
            mFragmentManager.beginTransaction()
                    .add(NetworkCheckFragment.newInstance(), TAG_HEADLESS_FRAGMENT)
                    .commit();
        }
    }
}
