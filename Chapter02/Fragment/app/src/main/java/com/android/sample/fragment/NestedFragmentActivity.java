package com.android.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by yoon on 2017. 6. 16..
 */

public class NestedFragmentActivity extends AppCompatActivity {

    private static final String TAG = NestedFragmentActivity.class.getSimpleName();
    private static final String TAG_PARENT = "tag_parent";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_fragment);
        mFragmentManager = getSupportFragmentManager();
        Fragment parentFragment = mFragmentManager.findFragmentByTag(TAG_PARENT);
        Log.d(TAG, "onCreate parentFragment: " + parentFragment);
        if (parentFragment == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, ParentFragment.newInstance(), TAG_PARENT)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment parentFragment = mFragmentManager.findFragmentByTag(TAG_PARENT);
        if (parentFragment != null
                && parentFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            parentFragment.getChildFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
