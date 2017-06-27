package com.android.sample.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yoon on 2017. 6. 27..
 */

public class BackgroundMusicService extends Service {

    private static final String TAG = BackgroundMusicService.class.getSimpleName();

    private final IBinder mBinder = new MusicServiceBinder();
    private MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    public class MusicServiceBinder extends Binder {
        BackgroundMusicService getService() {
            return BackgroundMusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
//        return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    public boolean isPlaying() {
        boolean isPlaying = false;
        if (mPlayer != null) {
            isPlaying = mPlayer.isPlaying();
        }
        return isPlaying;
    }

    public void play() {
        Log.d(TAG, "play");
        mPlayer = MediaPlayer.create(this, R.raw.sample_track);
        mPlayer.setLooping(true);
        mPlayer.setVolume(100, 100);
        mPlayer.start();
    }

    public void stop() {
        Log.d(TAG, "stop");
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
