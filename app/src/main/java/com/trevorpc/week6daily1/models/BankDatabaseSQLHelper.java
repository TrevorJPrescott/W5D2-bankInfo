package com.trevorpc.week6daily1.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BankDatabaseSQLHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bankList.db";



    public BankDatabaseSQLHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    private void addClientTable(SQLiteDatabase db) {
//        db.execSQL(
//                "CREATE TABLE " + ContentProviderContract.ClientEntry.TABLE_NAME + " (" +
//                        ContentProviderContract.ClientEntry.ID + " INTEGER PRIMARY KEY, " +
//                        ContentProviderContract.ClientEntry.COLUMN_HOLDER + " TEXT UNIQUE NOT NULL)" +
//                        ContentProviderContract.ClientEntry.COLUMN_START_DATE + " TEXT UNIQUE NOT NULL)" +
//                        ContentProviderContract.ClientEntry.COLUMN_BALANCE + " DOUBLE UNIQUE NOT NULL)" +
//                        ContentProviderContract.ClientEntry.COLUMN_LAST_TRANSACTION_DATE + " TEXT UNIQUE NOT NULL);"
//
//        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addClientTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
