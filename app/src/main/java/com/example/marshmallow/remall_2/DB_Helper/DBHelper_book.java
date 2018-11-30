package com.example.marshmallow.remall_2.DB_Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MarshMallow on 12.11.2018.
 */

public class DBHelper_book extends SQLiteOpenHelper {
    public DBHelper_book(Context context) {
        super(context, "myDB_book", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table bookdb ("
                + "author text,"
                + "name text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
