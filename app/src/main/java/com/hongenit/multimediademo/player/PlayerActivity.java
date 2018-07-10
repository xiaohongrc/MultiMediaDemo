package com.hongenit.multimediademo.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;

import com.hongenit.multimediademo.R;

import java.io.IOException;

/**
 * Created by hongenit on 2018/7/10.
 * desc:
 */

public class PlayerActivity extends AppCompatActivity {
    private SurfaceView sv;
    private String videoPath = "http://192.168.0.107/bju/test.mp4";
    private MediaPlayer mMediaPlayer;
    private int NORMAL = 0x1;
    private int PAUSE = 0x2;
    private int STARTED = 0x3;
    private int STOP = 0x4;

    private int currentStatus = NORMAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        sv = findViewById(R.id.sv);

    }


    void play() throws IOException {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDisplay(sv.getHolder());
        mMediaPlayer.setDataSource(videoPath);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
        currentStatus = STARTED;

    }


    public void start(View view) {
        if (currentStatus == STARTED || currentStatus == PAUSE) {
            mMediaPlayer.start();
            return;
        } else if (currentStatus == STOP) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }

        try {
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(View view) {
        currentStatus = STOP;
        mMediaPlayer.stop();
    }

    public void pause(View view) {
        currentStatus = PAUSE;
        mMediaPlayer.pause();
    }
}
