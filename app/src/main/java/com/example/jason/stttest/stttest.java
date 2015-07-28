package com.example.jason.stttest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;


public class stttest extends Activity {
    private SpeechRecognizer sr;
    private static final String TAG = "stttest";
    ImageButton img_btn=null;
    WindowManager wm=null;
    WindowManager.LayoutParams wmParams=null;
    private final int REQ_CODE_SPEECH_INPUT = 1;
    private static final int RECOVERY_DIALOG_REQUEST = 10;
    private int xLast=0;
    private int yLast=0;
    private int xC=100;
    private int yC=100;
    private boolean isListen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        if(wm==null){
            createView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createView() {
        img_btn = new ImageButton(getApplicationContext());
        img_btn.setBackgroundResource(R.drawable.heart);
        wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type=WindowManager.LayoutParams.TYPE_PHONE;// 漂浮層次
        wmParams.format= PixelFormat.RGBA_8888;//透明按鍵
        wmParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 下這個才可以移動背景
        wmParams.gravity= Gravity.LEFT| Gravity.TOP;// 設定座標的基準左上
        wmParams.width=150;// 設定IB寬度
        wmParams.height=150;//設定IB高度
        wmParams.x=xC;// 初始x位置
        wmParams.y=yC; //初始y位置
        wm.addView(img_btn, wmParams);// 將IB與wmParam加入wm中

        img_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (xLast != 0 && yLast != 0) {
                        xC += (int) event.getRawX() - xLast;
                        yC += (int) event.getRawY() - yLast;
                        updateView(xC, yC);
                    }
                    xLast = (int) event.getRawX();
                    yLast = (int) event.getRawY();
                    return true;
                }
                return false;
            }
        });
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isListen) {
                    SpeechListening();
                } else {
                    StopListening();
                }
            }
        });
    }

    private void StopListening() {
        Toast.makeText(stttest.this,"Stop listening.",Toast.LENGTH_SHORT).show();
        sr.stopListening();
        Log.d(TAG, "stopListening");
    }

    private void updateView(int x,int y) {
        wmParams.x=x;
        wmParams.y=y;
        wm.updateViewLayout(img_btn, wmParams);
    }
        /**
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hey Dude What's up!");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,"en-US");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),getString(R.string.speech_not_supported),Toast.LENGTH_SHORT).show();
    }
}**/

    private void SpeechListening(){
        isListen=true;
        Toast.makeText(stttest.this,"I'm listening.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        sr.startListening(intent);
        Log.d(TAG, "startListening");
    }


    /**
     * Receiving speech input
     * */
    /**
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultmsg=result.get(0);
                    casetest(resultmsg);
                }
                break;
            }
        }
    }
**/

    @Override
    protected void onStop() {
        super.onStop();
        wm.removeViewImmediate(img_btn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void casetest(String resultmsg) {
        if(resultmsg!=null){
            Toast.makeText(stttest.this, resultmsg, Toast.LENGTH_SHORT).show();
            switch (resultmsg){
                case "camera":
                    startActivity(new Intent(this,cameratest.class));
                    this.finish();
                    break;
                case "tube":
                    startActivity(new Intent(this,tubetest.class));
                    this.finish();
                    break;
                case "listen":
                    if(sr!=null){
                        sr.destroy();
                    }
                    startService(new Intent(this, servicetest.class));
                    this.finish();
                    break;
                case "goodbye":
                    this.finish();
                    break;
            }
        }
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        // todo imagebutton animation onRmsChanged !
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            isListen = false;
            Log.d(TAG, "error " + error+ ":no result");
            Toast.makeText(stttest.this, "error :no result",Toast.LENGTH_SHORT).show();
        }
        public void onResults(Bundle results)
        {
            isListen=false;
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String str=(String)data.get(0);
            casetest(str);
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
