package com.android.sample.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private boolean mIsPlaying;
    private Button mPlayButton;
    private Button mStopButton;
    private BackgroundMusicService mMusicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(this);
        mStopButton = (Button) findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(this);

        (findViewById(R.id.main2_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                if (mMusicService != null) {
                    mMusicService.play();
                }
                updateButtonEnable();
                break;
            case R.id.stop_button:
                if (mMusicService != null) {
                    mMusicService.stop();
                }
                updateButtonEnable();
                break;
            case R.id.main2_button:
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                break;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = ((BackgroundMusicService.MusicServiceBinder) service).getService();
            Log.d("ServiceConnection", "onServiceConnected");
            updateButtonEnable();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
            Log.d("ServiceConnection", "onServiceDisconnected");
        }
    };

    private void updateButtonEnable() {
        if (mMusicService != null) {
            if (mMusicService.isPlaying()) {
                mPlayButton.setEnabled(false);
                mStopButton.setEnabled(true);
            } else {
                mPlayButton.setEnabled(true);
                mStopButton.setEnabled(false);
            }
        } else {
            mPlayButton.setEnabled(false);
            mStopButton.setEnabled(false);
        }
    }

    public void doBindService() {
        Intent intent = null;
        intent = new Intent(this, BackgroundMusicService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mMusicService == null) {
            doBindService();
        }
        startService(new Intent(getApplicationContext(), BackgroundMusicService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (mMusicService != null) {
            mIsPlaying = mMusicService.isPlaying();
            if (!mIsPlaying) {
                mMusicService.stopSelf();
            }
            unbindService(mServiceConnection);
            mMusicService = null;
        }
    }
}
