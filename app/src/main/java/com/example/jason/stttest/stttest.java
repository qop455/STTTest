package com.example.jason.stttest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class stttest extends Activity {
    public static final int TYPE_PHONE=2003;
    ImageButton img_btn=null;
    WindowManager wm=null;
    WindowManager.LayoutParams wmParams=null;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        wmParams.type=TYPE_PHONE;// 漂浮層次
        wmParams.format=1;
        wmParams.flags=40; // 下這個才可以移動背景
        wmParams.width=150;// 設定IB寬度
        wmParams.height=150;//設定IB高度
        wmParams.gravity= Gravity.LEFT| Gravity.TOP;// 設定座標的基準左上
        wmParams.x=100;// 初始x位置
        wmParams.y=100; //初始y位置
        wm.addView(img_btn, wmParams);// 將IB與wmParam加入wm中

        img_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int xOffset, yOffset;
                int x, y;
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    xOffset = v.getWidth();
                    yOffset = v.getHeight();
                    x = (int) event.getRawX() - xOffset;
                    y = (int) event.getRawY() - yOffset;
                    updateView(x, y);
                }
                return false;
            }
        });
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        img_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Todo
                return false;
            }
        });
    }

    private void updateView(int x,int y) {
        wmParams.x=x;
        wmParams.y=y;
        wm.updateViewLayout(img_btn, wmParams);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en-US");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),getString(R.string.speech_not_supported),Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
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

    private void casetest(String resultmsg) {
        if(resultmsg!=null){
            Toast.makeText(stttest.this, resultmsg, Toast.LENGTH_SHORT).show();
            Intent goIntent=new Intent();
            switch (resultmsg){
                case "camera":
                    goIntent.setClass(this, cameratest.class);
                    startActivity(goIntent);
                    break;

                //Todo other case
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stttest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
