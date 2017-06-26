package com.android.sample.broadcastreceiver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class WakeActivity extends AppCompatActivity {

    private static final String TAG = WakeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        setContentView(R.layout.activity_wake);
        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(30 * 1000);
                    sendBroadcast(new Intent("com.android.sample.broadcastreceiver.TEST_ACTION"));
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        })).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString("KEY", null);
        TextView textView = (TextView) findViewById(R.id.text_view);
        if (result == null) {
            textView.setText("Keep it in the sleep state.");
        } else {
            textView.setText("intent service processing completed.");
        }
    }
}
