package com.android.sample.contentprovider;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import static android.provider.BaseColumns._ID;
import static com.android.sample.contentprovider.WordContract.WordColumns.DATE;
import static com.android.sample.contentprovider.WordContract.WordColumns.NAME;
import static com.android.sample.contentprovider.WordContract.WordColumns.WORDS;

public class WordActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = WordActivity.class.getSimpleName();
    private static final int LOADER_ID = 1;
    private static final String[] PROJECTIONS = new String[]{
            _ID, NAME, WORDS, DATE,
    };
    private CursorAdapter mCursorAdapter;
    private ContentObserver mContentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Context context = getApplicationContext();

//            Stetho.initializeWithDefaults(this);
        }
        setContentView(R.layout.activity_word);

        mCursorAdapter = new CursorAdapter(getApplicationContext(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                Log.d(TAG, "newView");
                return View.inflate(context, R.layout.list_item_word, null);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
                String words = cursor.getString(cursor.getColumnIndexOrThrow(WORDS));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE));

                TextView nameTextView = (TextView) view.findViewById(R.id.list_item_name_text_view);
                TextView wordTextView = (TextView) view.findViewById(R.id.list_item_word_text_view);
                TextView dateTextView = (TextView) view.findViewById(R.id.list_item_date_text_view);

                date = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6);
                Log.d(TAG, "bindView name: " + name + ", dateText: " + date);

                nameTextView.setText(name);
                wordTextView.setText(words);
                dateTextView.setText(date);
            }
        };

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mCursorAdapter);
        mCursorAdapter.notifyDataSetChanged();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(
                WordActivity.this, WordContract.CONTENT_URI, PROJECTIONS, null, null, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished");
        data.setNotificationUri(getContentResolver(), WordContract.CONTENT_URI);
        mCursorAdapter.swapCursor(data);
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset");
        mCursorAdapter.swapCursor(null);
    }
}
