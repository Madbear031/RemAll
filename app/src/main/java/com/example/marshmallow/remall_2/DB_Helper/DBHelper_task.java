package com.example.marshmallow.remall_2.DB_Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_task extends SQLiteOpenHelper {

    public DBHelper_task(Context context) {
        // конструктор суперкласса
        super(context, "TDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tasksDB ("
                + "id integer primary key autoincrement,"
                + "prior integer ,"
                + "name text,"
                + "year integer,"
                + "month integer,"
                + "day integer,"
                + "hour integer,"
                + "minutes integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
