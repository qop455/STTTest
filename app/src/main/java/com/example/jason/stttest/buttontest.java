package com.example.jason.stttest;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Jason on 2015/8/13.
 */
public class buttontest extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,View.OnClickListener{
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    public final String API_KEY = DeveloperKey.DEVELOPER_KEY;
    public final String VIDEO_ID = DeveloperKey.VIDEO_ID;
    private YouTubePlayerView youTubeView;
    private YouTubePlayer player;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton normal;
    private ImageButton vibrete;
    private ImageButton volume_up;
    private ImageButton volume_down;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stttest);
        youTubeView=(YouTubePlayerView)findViewById(R.id.youtube_view);
        play=(ImageButton)findViewById(R.id.play);
        pause=(ImageButton)findViewById(R.id.pause);
        normal=(ImageButton)findViewById(R.id.normal);
        vibrete=(ImageButton)findViewById(R.id.vibrete);
        volume_up=(ImageButton)findViewById(R.id.volume_up);
        volume_down=(ImageButton)findViewById(R.id.volume_down);
        youTubeView.initialize(API_KEY, this);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        normal.setOnClickListener(this);
        vibrete.setOnClickListener(this);
        volume_up.setOnClickListener(this);
        volume_down.setOnClickListener(this);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onClick(View v) {
        if (v == play) {
            player.play();
        }else if (v == pause) {
            player.pause();
        }else if (v== normal) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }else if (v== vibrete) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else if (v== volume_up) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
        }else if (v== volume_down) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        this.player=youTubePlayer;
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        if (!wasRestored) {
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(VIDEO_ID);

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }
}
