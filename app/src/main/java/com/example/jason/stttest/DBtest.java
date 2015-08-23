package com.example.jason.stttest;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

/**
 * Created by Pika on 2015/8/20.
 */
public class DBtest extends Activity {
    private DBHelper dbhelper;
    // 表格名稱
    public static final String TABLE_NAME ="record";
    //編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";
    // 其它表格欄位名稱
    public static final String COMMAND = "command";
    public static final String CONFIRM = "confirm";
    public static final String TIME = "time";


    //建立表格的SQL指令
    public static  final String CREATE_TABLE =
            "CREATE TABLE" + TABLE_NAME + " (" +
                    KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COMMAND + "TEXT NOT NULL, " +
                    CONFIRM + "TEXT ,"  +
                    TIME + "INTEGER NOT NULL  )";

    //資料庫物件
    private SQLiteDatabase db;

    //建構子
    public DBtest(Context context)
    {
        db = DBHelper.getDatabase(context);
    }

    //關閉資料庫
    public  void close(){
        db.close();
    }


    //新增參數指定的物件
    public DBcontact insert (DBcontact item){
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(COMMAND,item.ge);


    }







    @Override
    protected void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

}
