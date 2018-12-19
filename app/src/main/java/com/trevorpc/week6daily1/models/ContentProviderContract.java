package com.trevorpc.week6daily1.models;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContentProviderContract
{
    public static final String CONTENT_AUTHORITY = "com.trevor.pc.week6daily1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CLIENT = "client";

    public static final class ClientEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLIENT).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_CLIENT;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CLIENT;

        public static final String TABLE_NAME = "ClientTable";
        public static final String ID = "Account Name";
        public static final String COLUMN_HOLDER = "Holder Name";
        public static final String COLUMN_START_DATE = "Open Account Date";
        public static final String COLUMN_BALANCE = "Account Balance";
        public static final String COLUMN_LAST_TRANSACTION_DATE = "Last Transaction Date";


        public static Uri buildClientUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
