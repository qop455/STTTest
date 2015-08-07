package com.example.jason.stttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jason on 2015/7/31.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static String _DBName = "MyDB";
    private final static int _DBVersion = 1;
    private final static String _TableName = "MyCommand";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL="CREATE TABLE IF NOT EXISTS "+_TableName
                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"_COMMAND VARCHAR(50),"
                +"_CONTENT TEXT,"
                +");";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);

    }
}
