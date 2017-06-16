package com.android.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yoon on 2017. 6. 16..
 */

public class HomeActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private int mNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFragmentManager = getSupportFragmentManager();

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
}
