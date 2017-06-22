package com.android.sample.contentprovider.contract;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yoon on 2017. 6. 21..
 */

public interface WordContract {

    public static final String AUTHORITY = "com.android.sample.contentprovider";
    public static final String TABLE_NAME = "Word";
    public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://"
            + AUTHORITY + "/" + TABLE_NAME);

    public static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
    public static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;

    public interface WordColumns extends BaseColumns {
        public static final String NAME = "name";
        public static final String WORDS = "words";
        public static final String DATE = "date";
    }
}
