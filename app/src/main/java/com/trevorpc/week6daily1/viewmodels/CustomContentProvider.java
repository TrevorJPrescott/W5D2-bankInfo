package com.trevorpc.week6daily1.viewmodels;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trevorpc.week6daily1.models.BankDatabaseSQLHelper;
import com.trevorpc.week6daily1.models.ContentProviderContract;

public class CustomContentProvider extends ContentProvider {
    public static final int CLIENT = 100;
    public static final int  CLIENT_ID = 101;

    private static final UriMatcher sUriMatcher= buildUriMatcher();
    private BankDatabaseSQLHelper bankDatabaseSQLiteHelper;

    public static UriMatcher buildUriMatcher(){
        String content = ContentProviderContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content,ContentProviderContract.PATH_CLIENT, CLIENT);
        matcher.addURI(content,ContentProviderContract.PATH_CLIENT + "/#",CLIENT_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        bankDatabaseSQLiteHelper = new BankDatabaseSQLHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = bankDatabaseSQLiteHelper.getWritableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case CLIENT:
                retCursor = db.query(
                        ContentProviderContract.ClientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case CLIENT_ID:
                long id = ContentUris.parseId(uri);
                retCursor = db.query(
                        ContentProviderContract.ClientEntry.TABLE_NAME,
                        projection,
                        ContentProviderContract.ClientEntry._ID + "= ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri))
        {
            case CLIENT:
                return ContentProviderContract.ClientEntry.CONTENT_TYPE;
            case CLIENT_ID:
                return ContentProviderContract.ClientEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = bankDatabaseSQLiteHelper.getWritableDatabase();
        long id;
        Uri returnUri;

        switch (sUriMatcher.match(uri)){
            case CLIENT:
                id = db.insert(ContentProviderContract.ClientEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentProviderContract.ClientEntry.buildClientUri(id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = bankDatabaseSQLiteHelper.getWritableDatabase();
        int rows;

        switch(sUriMatcher.match(uri)){
            case CLIENT:
                rows = db.delete(ContentProviderContract.ClientEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(selection == null || rows!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = bankDatabaseSQLiteHelper.getWritableDatabase();
        int rows;

        switch(sUriMatcher.match(uri)){
            case CLIENT:
                rows = db.update(ContentProviderContract.ClientEntry.TABLE_NAME, contentValues,selection,selectionArgs );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        if(rows != 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }
}
