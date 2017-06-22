package com.android.sample.contentprovider.onmemory;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.sample.contentprovider.R;
import com.android.sample.contentprovider.contract.WordContract;

import static com.android.sample.contentprovider.contract.WordContract.CONTENT_URI;
import static com.android.sample.contentprovider.contract.WordContract.WordColumns.WORDS;

/**
 * Created by yoon on 2017. 6. 23..
 */

public class WordActivity extends AppCompatActivity {

    private static final String TAG = WordActivity.class.getSimpleName();

    private static final String[] PROJECTIONS = new String[]{
            WordContract.WordColumns._ID,
            WordContract.WordColumns.NAME,
            WORDS,
            WordContract.WordColumns.DATE,
    };
    private ContentResolver mContentResolver;
    private ContentObserver mContentObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        mContentResolver = getContentResolver();

        dump();
        mContentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.d(TAG, "Content Observer: Content Provider Changed");
                dump();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        mContentResolver.registerContentObserver(CONTENT_URI, true, mContentObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        mContentResolver.unregisterContentObserver(mContentObserver);
    }

    private void dump() {
        Cursor cursor = mContentResolver.query(CONTENT_URI, PROJECTIONS, null, null, null);
        if (cursor.moveToFirst()) {
            int wordsColumn = cursor.getColumnIndexOrThrow(WORDS);
            do {
                Log.d(TAG, "words:" + cursor.getString(wordsColumn));
            }
            while (cursor.moveToNext());
        }
    }
}
