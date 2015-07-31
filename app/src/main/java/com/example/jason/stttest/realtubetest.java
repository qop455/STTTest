package com.example.jason.stttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
/**
 * Created by Jason on 2015/7/30.
 */
public class realtubetest extends Activity{
    public static final String VIDEO_ID = "2zNSgSzhBfM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+VIDEO_ID));
        startActivity(myIntent);
        finish();
    }
}
