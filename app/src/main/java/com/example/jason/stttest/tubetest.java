package com.example.jason.stttest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Jason on 2015/7/27.
 */

public class tubetest extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    AudioManager audio;
    private static final String TAG = "tubetest";
    public static final String API_KEY = "AIzaSyASBYXyMiX27kdbzD0BP8KV6uNPGAnESdw";
    public static final String VIDEO_ID = "2zNSgSzhBfM";

    YouTubePlayerView youTubePlayerView ;
    WindowManager wm=null;
    WindowManager.LayoutParams wmParams=null;
    public int xLast=0;
    public int yLast=0;
    public int xC =60;
    public int yC =40;
    private boolean isMoving= false;
    private boolean isFirst =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Log.i(TAG, "onCreate");
        if(wm==null){
            createView();
            Log.i(TAG, "createView()");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        wm.removeViewImmediate(youTubePlayerView);
    }

    private void createView() {
        final AlertDialog alertDialog = getAlertDialog("TubeTest","Exit or not");
        youTubePlayerView = new YouTubePlayerView(tubetest.this);
        youTubePlayerView.initialize(API_KEY, this);
        wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type=WindowManager.LayoutParams.TYPE_PHONE;// 漂浮層次
        wmParams.format= PixelFormat.RGBA_8888;//透明按鍵
        wmParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 下這個才可以移動背景
        wmParams.gravity= Gravity.LEFT| Gravity.TOP;// 設定座標的基準左上
        wmParams.width=960;// 設定IB寬度
        wmParams.height=540;//設定IB高度
        wmParams.x=xC;// 初始x位置
        wmParams.y=yC; //初始y位置
        wm.addView(youTubePlayerView, wmParams);// 將IB與wmParam加入wm中

        youTubePlayerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    isMoving = true;
                    if(!isFirst){
                        xC = xC + (int) event.getRawX() - xLast;
                        yC = yC + (int) event.getRawY() - yLast;
                        updateView(xC, yC);
                    }
                    xLast = (int) event.getRawX();
                    yLast = (int) event.getRawY();
                    isFirst=false;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    isMoving=false;
                    isFirst =true;
                }
                return false;
            }
        });
        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMoving){
                    //do something here
                }
            }
        });
        youTubePlayerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isMoving){
                    alertDialog.show();
                }
                return true;
            }
        });;
    }

    private AlertDialog getAlertDialog(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(tubetest.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(tubetest.this, "BYE", Toast.LENGTH_SHORT).show();
                tubetest.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return builder.create();
    }

    private void updateView(int x,int y) {
        wmParams.x=x;
        wmParams.y=y;
        wm.updateViewLayout(youTubePlayerView, wmParams);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
            default:
                return false;
        }
    }
}
