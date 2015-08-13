package com.example.jason.stttest;

import android.graphics.Bitmap;
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
    public final String API_KEY = "AIzaSyASBYXyMiX27kdbzD0BP8KV6uNPGAnESdw";
    public final String VIDEO_ID = "2zNSgSzhBfM";
    private YouTubePlayerView youTubeView;
    private YouTubePlayer player;
    private ImageButton play;
    private ImageButton pause;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stttest);
        youTubeView=(YouTubePlayerView)findViewById(R.id.youtube_view);
        play=(ImageButton)findViewById(R.id.play);
        pause=(ImageButton)findViewById(R.id.pause);
        youTubeView.initialize(API_KEY,this);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == play) {
            player.play();
        } else if (v == pause) {
            player.pause();
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
}
