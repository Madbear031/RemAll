package com.example.marshmallow.remall_2.DB_Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MarshMallow on 13.11.2018.
 */

public class DBHelper_shop extends SQLiteOpenHelper {
    public DBHelper_shop(Context context) {
        super(context, "myDB_shop", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table shopdb ("
                + "purchase text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}