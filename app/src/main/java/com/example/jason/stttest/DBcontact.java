package com.example.jason.stttest;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Pika on 2015/8/23.
 */
public class DBcontact {
    int _id;
    String _Command;
    String _Confirm;
    long _Time;

    public DBcontact(){
        _Command= "";
        _Confirm = "";

    }

    public DBcontact(int id,String command ,String confirm ,long time){
        this._id =id;
        this._Command= command;
        this._Time=time;
    }

    public int getId(){
        return  _id ;
    }
    public void Setid(int id){
        this._id=id;
    }

    public String get_Command(){
        return _Command;
    }
    public void Set_Command(String command){
        this._Command=command;
    }
    
     public long get_Time(){
         return _Time;
     }
    //裝置區域的日期時間
    public String getLocaleDatetime(){
        return String.format(Locale.getDefault(),"%tF  %<tR", new Date(_Time));
    }
    public void set_Time(long time){
        this._Time =time;
    }

    public String get_Confirm(){
        return _Confirm;
    }
    public void set_Confirm(String confirm){
        this._Confirm=confirm;
    }

}
