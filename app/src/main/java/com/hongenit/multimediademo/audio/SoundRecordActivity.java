package com.hongenit.multimediademo.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongenit.multimediademo.R;
import com.hongenit.multimediademo.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Xiaohong on 2018/7/27.
 * desc:
 */

public class SoundRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int BUFFER_SIZE = 2048;
    private View record_btn;

    private boolean isStartedRecord = false;
    private File folder;
    private File mAudioRecordFile;
    private FileOutputStream mFileOutputStream;
    private int count;
    private AudioRecord audioRecord;
    private boolean isRecording;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_record);

        initView();

    }

    private void initView() {
        record_btn = findViewById(R.id.record_btn);
        record_btn.setOnClickListener(this);
        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "audiofile");
        if (!folder.exists()) {
            boolean mkdir = folder.mkdir();
        }
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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doRecord();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            record_btn.setBackgroundResource(R.drawable.ic_mic);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doStop();
                }
            }).start();
        }
        isStartedRecord = !isStartedRecord;

    }

    // 停止录音
    private void doStop() {
        isRecording = false;
        audioRecord.stop();
        audioRecord.release();
    }

    // 开启录音
    private void doRecord() throws IOException {
        mAudioRecordFile = new File(folder, "audio" + count++ + ".pcm");
        try {
            mAudioRecordFile.createNewFile();
            //创建文件输出流
            mFileOutputStream = new FileOutputStream(mAudioRecordFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //配置AudioRecord
        int audioSource = MediaRecorder.AudioSource.MIC;
        //所有android系统都支持
        int sampleRate = 44100;
        //单声道输入
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        //PCM_16是所有android系统都支持的
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        //计算AudioRecord内部buffer最小
        int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        //buffer不能小于最低要求，也不能小于我们每次我们读取的大小。
        audioRecord = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, Math.max(minBufferSize, BUFFER_SIZE));

        byte[] buffer = new byte[BUFFER_SIZE];
        audioRecord.startRecording();
        isRecording = true;
        while (isRecording) {
            int read = audioRecord.read(buffer, 0, BUFFER_SIZE);
            LogUtils.hong("read = " + read);
            if (read > 0) {
                mFileOutputStream.write(buffer, 0, read);
            }else {
                return;
            }
        }
        LogUtils.hong("end doRecord()");
        mFileOutputStream.close();
    }
}
