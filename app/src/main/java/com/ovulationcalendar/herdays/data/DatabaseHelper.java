package com.ovulationcalendar.herdays.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mleano on 3/15/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mInstance = null;

    private DatabaseHelper(Context mContext) {
        super(mContext, "pesosense_db", null, 4);
    }


    public static DatabaseHelper getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(mContext);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tbl_notes(id integer primary key, date varchar, title varchar, message varchar)");
    //    db.execSQL("CREATE TABLE IF NOT EXISTS tbl_tips(id integer primary key, tips varchar)");
        populate(db);

    }

    private void populate(SQLiteDatabase db) {

        db.execSQL("INSERT INTO tbl_notes(id, date, title, message) VALUES (1, 'date', 'title', 'message')");

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_notes");
        onCreate(db);
    }

}
