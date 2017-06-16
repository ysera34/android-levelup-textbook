package com.android.sample.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by yoon on 2017. 6. 17..
 */

public class NetworkCheckFragment extends Fragment {

    private static final String TAG = NetworkCheckFragment.class.getSimpleName();
    public static final String ACTION_CHECK_INTERNET = "action_check_internet";
    public static final String EXTRA_CHECK_INTERNET = "extra_check_internet";

    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver;

    public static NetworkCheckFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NetworkCheckFragment fragment = new NetworkCheckFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* prevent reCreate Fragment */
        setRetainInstance(true);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (ACTION_CHECK_INTERNET.equals(action)) {
                    boolean isConnected = intent.getBooleanExtra(EXTRA_CHECK_INTERNET, false);
                    if (isConnected) {
                        Toast.makeText(getActivity(), "internet connected", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "internet connected");
                    } else {
                        Toast.makeText(getActivity(), "internet disabled", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "internet disabled");
                    }
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, 
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIntentFilter == null) {
            mIntentFilter = new IntentFilter(ACTION_CHECK_INTERNET);
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }
}
