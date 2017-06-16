package com.android.sample.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static com.android.sample.fragment.NetworkCheckFragment.ACTION_CHECK_INTERNET;
import static com.android.sample.fragment.NetworkCheckFragment.EXTRA_CHECK_INTERNET;

/**
 * Created by yoon on 2017. 6. 17..
 */

public class NetworkCheckReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkCheckReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive");
        Intent checkInternetIntent = new Intent(ACTION_CHECK_INTERNET);
        checkInternetIntent.putExtra(EXTRA_CHECK_INTERNET, isInternetConnected(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(checkInternetIntent);
    }

    public boolean isInternetConnected(Context context) {
        return isWifiConnected(context) || isMobileConnected(context);
    }

    public boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public boolean isMobileConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
