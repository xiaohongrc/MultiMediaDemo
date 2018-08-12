package com.hongenit.multimediademo.audio;

import android.media.MediaRecorder;

import com.hongenit.multimediademo.Constans;
import com.hongenit.multimediademo.utils.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Xiaohong on 2018/7/30.
 * desc: 采用MediaRecorder实现录音
 */

public class MediaRecordImpl implements ISoundRecorder {

    private MediaRecorder mRecorder;
    private File mAudioRecordFile;
    private int count;

    public MediaRecordImpl() {
    }

    @Override
    public void doStartRecord() {

        mAudioRecordFile = new File(Constans.APP_AUDIO_FOLDER, "audio" + count++ + ".mp3");
        try {
            mAudioRecordFile.createNewFile();

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setOutputFile(mAudioRecordFile.getPath());

            // 音频编码格式,采用AAC编码格式会比原始PCM音频数据的压缩70倍左右
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setAudioChannels(1);

            mRecorder.prepare();
            mRecorder.start();

        } catch (IOException e) {
            LogUtils.e(this, "prepare() failed");
        }

    }

    @Override
    public void doStopRecord() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
