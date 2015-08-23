package com.example.jason.stttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jason on 2015/7/31.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static String _DBName = "MyDB";
    private final static int _DBVersion = 1;
    private static SQLiteDatabase database;
    private final static String _TableName = "MyCommand";
    private final static String TAG= "DBhelper";

    private Integer _id= 0;
    private String _COMMAND= "command";
    private Integer _CONFIRM =0;
    private Calendar _TIME ;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static SQLiteDatabase getDatabase(Context context){
        if(database ==null || !database.isOpen()){
            database = new DBHelper(context,_DBName,null,_DBVersion).getWritableDatabase();
        }
        return  database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL="CREATE TABLE IF NOT EXISTS "+_TableName+"("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +_COMMAND+ "VARCHAR(50),"
                +_CONFIRM+" INTEGER,"
                +_TIME+" TEXT,"
                +");";
        db.execSQL(SQL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName;
        db.execSQL(SQL);
        onCreate(db);
    }


    public class InputData
    {
        public String command;
        public String confirmCode;
        public String time;
    }

    private String getTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void add(InputData inputData){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values =new ContentValues();
            values.put(_COMMAND,inputData.command);
            values.put(inputData.confirmCode.toString(), _CONFIRM);
            values.put(String.valueOf(_TIME),getTime());
            db.insertOrThrow(_TableName,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.d(TAG,"Error while trying to add post to database");
        }finally {
            db.endTransaction();
        }
    }


    public List<InputData> getData(){
        List<InputData> data =new ArrayList<>();

        String POST_SELECT_QUERY =
                String.format("SELECT * FROM ",
                        _TableName,_COMMAND,_CONFIRM,getTime()
                        );
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POST_SELECT_QUERY, null);
        try {
            if(cursor.moveToFirst()){
                do {
                    InputData newdata =new InputData();
                    newdata.command =cursor.getString(cursor.getColumnIndex(_COMMAND));
                    newdata.confirmCode = cursor.getString(cursor.getColumnIndex(String.valueOf(_CONFIRM)));
                    newdata.time = cursor.getString(cursor.getColumnIndex(String.valueOf(_TIME)));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG, "Error while trying to get posts from database");
        }finally {
            if(cursor!= null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return data;
    }
    public  void  deleteALL()
    {
        SQLiteDatabase db =getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete(_TableName,null,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG, "Error while trying to delete all record");
        }finally {
            db.endTransaction();
        }
    }


}
