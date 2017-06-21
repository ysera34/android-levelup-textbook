package com.android.sample.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.android.sample.contentprovider.WordContract.AUTHORITY;
import static com.android.sample.contentprovider.WordContract.MIME_TYPE_DIR;
import static com.android.sample.contentprovider.WordContract.MIME_TYPE_ITEM;
import static com.android.sample.contentprovider.WordContract.TABLE_NAME;
import static com.android.sample.contentprovider.WordDatabaseHelper.DB_NAME;
import static com.android.sample.contentprovider.WordDatabaseHelper.DB_VERSION;

public class WordContentProvider extends ContentProvider {

    private static final String TAG = WordContentProvider.class.getSimpleName();

    private static final UriMatcher URI_MATCHER;

    private static final int ROW_DIR = 1;
    private static final int ROW_ITEM = 2;

    private static final int sLastId = 0;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, ROW_DIR);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", ROW_ITEM);
    }

    private WordDatabaseHelper mDatabaseHelper;

    public WordContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        Log.d(TAG, "onCreate");
        mDatabaseHelper = new WordDatabaseHelper(getContext(), DB_NAME, null, DB_VERSION, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                Log.d(TAG, "onCorruption");
                String path = dbObj.getPath();
                getContext().deleteFile(path);
            }
        });
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.

        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                return MIME_TYPE_DIR;
            case ROW_ITEM:
                return MIME_TYPE_ITEM;
            default:
                return null;
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        Uri resultUri = null;
        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                synchronized (mDatabaseHelper) {
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    long lastId = db.insert(TABLE_NAME, null, values);
                    resultUri = ContentUris.withAppendedId(uri, lastId);
                    Log.d(TAG, "word insert : " + values);
                    getContext().getContentResolver().notifyChange(resultUri, null);
                }
                break;
            case ROW_ITEM:
                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return resultUri;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                synchronized (mDatabaseHelper) {
                    Log.d(TAG, "query(dir) uri: " + uri.toString());
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                }
                return cursor;
            case ROW_ITEM:
                synchronized (mDatabaseHelper) {
                    Log.d(TAG, "query(item) uri: " + uri.toString());
                    long id = ContentUris.parseId(uri);
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    cursor = db.query(TABLE_NAME, projection, _ID, new String[]{Long.toString(id)}, null, null, null);
                }
                return cursor;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.

        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                synchronized (mDatabaseHelper) {
                    Log.d(TAG, "update(dir) values: " + values);
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    count = db.update(TABLE_NAME, values, selection, selectionArgs);
                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                break;
            case ROW_ITEM:
                int id = (int) ContentUris.parseId(uri);
                synchronized (mDatabaseHelper) {
                    Log.d(TAG, "update(item) values: " + values);
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    count = db.update(TABLE_NAME, values, _ID + "=?", new String[]{Long.toString(id)});
                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return count;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                synchronized (mDatabaseHelper) {
                    Log.d(TAG, "delete(dir)");
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    count = db.delete(TABLE_NAME, selection, selectionArgs);
                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                break;
            case ROW_ITEM:
                int id = (int) ContentUris.parseId(uri);
                synchronized (mDatabaseHelper) {
                    Log.d(TAG, "delete(item) id:" + id);
                    SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                    count = db.delete(TABLE_NAME, _ID + "=?", new String[]{Long.toString(id)});
                    if (count > 0) {
                        getContext().getContentResolver().notifyChange(uri, null);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return count;
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
