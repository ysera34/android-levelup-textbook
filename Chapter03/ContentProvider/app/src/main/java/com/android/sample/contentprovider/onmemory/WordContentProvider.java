package com.android.sample.contentprovider.onmemory;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.android.sample.contentprovider.model.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static android.provider.UserDictionary.Words._ID;
import static com.android.sample.contentprovider.contract.WordContract.AUTHORITY;
import static com.android.sample.contentprovider.contract.WordContract.MIME_TYPE_DIR;
import static com.android.sample.contentprovider.contract.WordContract.MIME_TYPE_ITEM;
import static com.android.sample.contentprovider.contract.WordContract.TABLE_NAME;
import static com.android.sample.contentprovider.contract.WordContract.WordColumns.DATE;
import static com.android.sample.contentprovider.contract.WordContract.WordColumns.NAME;
import static com.android.sample.contentprovider.contract.WordContract.WordColumns.WORDS;

/**
 * Created by yoon on 2017. 6. 23..
 */

public class WordContentProvider extends ContentProvider {

    private static final String TAG = WordContentProvider.class.getSimpleName();

    private static final UriMatcher URI_MATCHER;

    private static final SparseArray<Word> WORD_SPARSE_ARRAY = new SparseArray<>();
    private static final int ROW_DIR = 1;
    private static final int ROW_ITEM = 2;

    private static int sLastId = 0;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, ROW_DIR);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", ROW_ITEM);

        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "I do not feel well today.", 20170623)); sLastId++;
        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "Tomorrow is a good day.", 20170624)); sLastId++;
        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "There are days like this and days like that.", 20170625)); sLastId++;
        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "Listening to good songs is fun.", 20170626)); sLastId++;
        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "I want to live by dreaming what I want to do.", 20170627)); sLastId++;
        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "What does life really want?", 20170628)); sLastId++;
        WORD_SPARSE_ARRAY.append(sLastId, new Word(sLastId, "ABCD", "Will tomorrow be a good thing?", 20170629)); sLastId++;
    }

    public WordContentProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                return MIME_TYPE_DIR;
            case ROW_ITEM:
                return MIME_TYPE_ITEM;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri = null;
        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                Word row = new Word();
                if (values.containsKey(NAME)) {
                    row.setName(values.getAsString(NAME));
                }
                if (values.containsKey(WORDS)) {
                    row.setWords(values.getAsString(WORDS));
                }
                if (values.containsKey(DATE)) {
                    row.setDate(values.getAsInteger(DATE));
                }
                synchronized (WORD_SPARSE_ARRAY) {
                    sLastId++;
                    row.setId(sLastId);
                    WORD_SPARSE_ARRAY.append(sLastId, row);
                    resultUri = ContentUris.withAppendedId(uri, sLastId);
                    Log.d(TAG, "word provider insert " + row);

                    getContext().getContentResolver().notifyChange(resultUri, null);
                }
                break;
//            case ROW_ITEM:
//                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return resultUri;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query uri: " + uri.toString());

        HashSet<String> set = new HashSet<>(Arrays.asList(projection));
        Log.d(TAG, "query projection: " + set.toString());

        String[] columns = set.toArray(new String[set.size()]);
        MatrixCursor cursor = new MatrixCursor(columns);

        switch (URI_MATCHER.match(uri)) {
            case ROW_DIR:
                synchronized (WORD_SPARSE_ARRAY) {
                    String name = null;
                    if (selection != null) {
                        Log.d(TAG, "query where: " + selection);
                        name = selection.split("=")[1];
                    }
                    int size = WORD_SPARSE_ARRAY.size();
                    for (int i = 0; i < size; i++) {
                        Word obj = WORD_SPARSE_ARRAY.valueAt(i);
                        if (name == null || TextUtils.equals(obj.getName(), name)) {
                            Object[] row = getRow(obj.getId(), columns);
                            cursor.addRow(row);
                        }
                    }
                }
                return cursor;
            case ROW_ITEM:
                synchronized (WORD_SPARSE_ARRAY) {
                    long id = ContentUris.parseId(uri);
                    Object[] row = getRow(id, columns);
                    cursor.addRow(row);
                }
                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return cursor;
    }

    private Object[] getRow(long id, String[] columns) {
        ArrayList<Object> values = new ArrayList<>();
        Word row = WORD_SPARSE_ARRAY.get((int) id);
        for (String column : columns) {
            if (column.equals(_ID)) {
                Log.d(TAG, "getRow _id: " + row.getId());
                values.add(row.getId());
                continue;
            }
            if (column.equals(NAME)) {
                Log.d(TAG, "getRow name: " + row.getName());
                values.add(row.getName());
                continue;
            }
            if (column.equals(WORDS)) {
                Log.d(TAG, "getRow words: " + row.getWords());
                values.add(row.getWords());
                continue;
            }
            if (column.equals(DATE)) {
                Log.d(TAG, "getRow date: " + row.getDate());
                values.add(row.getDate());
                continue;
            }
        }
        return values.toArray(new Object[values.size()]);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case ROW_ITEM:
                int id = (int) ContentUris.parseId(uri);
                synchronized (WORD_SPARSE_ARRAY) {
                    Word row = WORD_SPARSE_ARRAY.get(id);
                    if (row != null) {
                        if (values.containsKey(NAME)) {
                            row.setName(values.getAsString(NAME));
                        }
                        if (values.containsKey(WORDS)) {
                            row.setWords(values.getAsString(WORDS));
                        }
                        if (values.containsKey(DATE)) {
                            row.setDate(values.getAsInteger(DATE));
                        }

                        getContext().getContentResolver().notifyChange(uri, null);
                        count = 1;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case ROW_ITEM:
                int id = (int) ContentUris.parseId(uri);
                synchronized (WORD_SPARSE_ARRAY) {
                    if (WORD_SPARSE_ARRAY.indexOfKey(id) >= 0) {
                        WORD_SPARSE_ARRAY.remove(id);

                        getContext().getContentResolver().notifyChange(uri, null);
                        count = 1;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("The argument is not correct.");
        }
        return count;
    }
}
