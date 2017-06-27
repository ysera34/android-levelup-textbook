package com.android.sample.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();

    private TextView mTextView;

    private LocalBroadcastManager mLocalBroadcastManager;
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (FibonacciService.ACTION_CALC_DONE.equals(action)) {
                int result = intent.getIntExtra(FibonacciService.KEY_CALC_RESULT, -1);
                long mesc = intent.getLongExtra(FibonacciService.KEY_CALC_MILLISECONDS, -2);

                mTextView.setText("fibonacci (" + FibonacciService.N +
                        ") = " + result + " (" + mesc + ")millisecond");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTextView = (TextView) findViewById(R.id.text_view);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        mIntentFilter = new IntentFilter(FibonacciService.ACTION_CALC_DONE);
        Intent serviceIntent = new Intent(FibonacciService.ACTION_CALC);
        serviceIntent.setClass(getApplicationContext(), FibonacciService.class);
        startService(serviceIntent);

        mTextView.setText("fibonacci (" + FibonacciService.N + ") calculating...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }
}
