package com.android.sample.broadcastreceiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class WakefulReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = WakefulReceiver.class.getSimpleName();

    public WakefulReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "onReceive");
        Intent serviceIntent = new Intent(context, WakefulIntentService.class);
        startWakefulService(context, serviceIntent);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
