package com.hongenit.multimediademo.audio;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;

import com.hongenit.multimediademo.Constans;
import com.hongenit.multimediademo.R;

/**
 * Created by Xiaohong on 2018/7/27.
 * desc:
 */

public class SoundRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private View record_btn;

    private boolean isStartedRecord = false;

    private ISoundRecorder soundRecorder = null;

    private Chronometer cm_time_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundRecorder = new AudioRecordImpl();
        if (!Constans.APP_AUDIO_FOLDER.exists()) {
            boolean mkdir = Constans.APP_AUDIO_FOLDER.mkdir();
        }
        setContentView(R.layout.activity_sound_record);
        initView();
    }

    private void initView() {
        record_btn = findViewById(R.id.record_btn);
        cm_time_count = findViewById(R.id.cm_time_count);
        record_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_btn:
                startRecord();
                break;
            default:

        }
    }

    private void startRecord() {
        if (!isStartedRecord) {
            record_btn.setBackgroundResource(R.drawable.ic_stop);
            cm_time_count.setBase(SystemClock.elapsedRealtime());
            cm_time_count.start();
            soundRecorder.doStartRecord();

        } else {
            record_btn.setBackgroundResource(R.drawable.ic_mic);
            cm_time_count.stop();

            soundRecorder.doStopRecord();

        }
        isStartedRecord = !isStartedRecord;

    }


}
