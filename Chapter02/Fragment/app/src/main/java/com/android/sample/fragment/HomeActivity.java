package com.android.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by yoon on 2017. 6. 16..
 */

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final String KEY_NUMBER = "key_number";

    private FragmentManager mFragmentManager;
    private FragmentManager.OnBackStackChangedListener mBackStackChangedListener;
    private int mNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFragmentManager = getSupportFragmentManager();
        mBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int count = 0;
                if (mFragmentManager.getFragments() != null) {
                    for (Fragment fragment : mFragmentManager.getFragments()) {
                        if (fragment != null) {
                            count++;
                        }
                    }
                    mNumber = count;
                    Log.d(TAG, "onBackStackChanged mNumber: " + mNumber);
                }
            }
        };

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, HomeFragment.newInstance(mNumber++))
                        .addToBackStack(null)
                        .commit();
            }
        });
        findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumber == 0) {
                    return;
                }
                mFragmentManager.popBackStack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFragmentManager.addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        Log.d(TAG, "onCreate fragment: " + fragment + ", mNumber: " + mNumber);
        if (fragment == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, HomeFragment.newInstance(mNumber), FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFragmentManager.removeOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NUMBER, mNumber);
        Log.d(TAG, "onSaveInstanceState: mNumber: " + mNumber);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mNumber = savedInstanceState.getInt(KEY_NUMBER);
        Log.d(TAG, "onRestoreInstanceState: mNumber: " + mNumber);
    }
}
