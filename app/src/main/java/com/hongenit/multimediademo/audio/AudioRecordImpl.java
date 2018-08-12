package com.hongenit.multimediademo.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;

import com.hongenit.multimediademo.Constans;
import com.hongenit.multimediademo.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hongenit on 2018/7/30.
 * desc:
 */

public class AudioRecordImpl implements ISoundRecorder {
    private final Handler recordHandler;
    private File mAudioRecordFile;
    private FileOutputStream mFileOutputStream;
    private int count;
    private AudioRecord audioRecord;
    private boolean isRecording;
    private static final int BUFFER_SIZE = 2048;


    public AudioRecordImpl() {
        HandlerThread handlerThread = new HandlerThread("recorder thread");
        handlerThread.start();
        recordHandler = new Handler(handlerThread.getLooper());
    }

    // 停止录音
    public void doStopRecord() {
        // 将pcm文件转成wav文件
        String currentFilePath = mAudioRecordFile.getAbsolutePath();
        copyWaveFile(currentFilePath, currentFilePath.replace(".pcm", ".wav"));

        isRecording = false;
        audioRecord.stop();
        audioRecord.release();
    }

    // 开启录音
    public void doStartRecord() {
        recordHandler.post(new Runnable() {
            @Override
            public void run() {
                mAudioRecordFile = new File(Constans.APP_AUDIO_FOLDER, "audio" + count++ + ".pcm");
                try {
                    mAudioRecordFile.createNewFile();
                    //创建文件输出流
                    mFileOutputStream = new FileOutputStream(mAudioRecordFile);
                    //配置音频输入源，如果只录制系统声音，使用REMOTE_SUBMIX无法初始化AudioRecord。
                    // 试了一下其它的参数都会录进去外面的声音，使用CAMCORDER（摄像头旁边的麦克风）参数在安静环境下录制系统声音效果还行。
                    int audioSource = MediaRecorder.AudioSource.CAMCORDER;
                    //所有android系统都支持
                    int sampleRate = 44100;
                    //单声道输入
                    // int channelConfig = AudioFormat.CHANNEL_IN_MONO;
                    int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
                    //PCM_16是所有android系统都支持的
                    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
                    //计算AudioRecord内部buffer最小
                    int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
                    //buffer不能小于最低要求，也不能小于我们每次我们读取的大小。
                    audioRecord = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, minBufferSize * 2);

                    byte[] buffer = new byte[BUFFER_SIZE];
                    audioRecord.startRecording();
                    isRecording = true;
                    while (isRecording) {
                        int read = audioRecord.read(buffer, 0, BUFFER_SIZE);
                        LogUtils.hong("read = " + read);
                        if (read > 0) {
                            mFileOutputStream.write(buffer, 0, read);
                        } else {
                            return;
                        }
                    }
                    LogUtils.hong("end doRecord()");
                    mFileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // 这里得到可播放的音频文¬件
    private static void copyWaveFile(String inFilename, String outFilename) {
        FileInputStream in;
        FileOutputStream out;
        long totalAudioLen;
        long totalDataLen;
        long longSampleRate = 44100;
        int channels = 2;
        long byteRate = 16 * longSampleRate * channels / 8;
        byte[] data = new byte[BUFFER_SIZE];
        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);
            while (in.read(data) != -1) {
                out.write(data);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 这里提供一个头信息。插入这些信息就可以得到可以播放的文件。
     * 为我为啥插入这44个字节，这个还真没深入研究，不过你随便打开一个wav
     * 音频的文件，可以发现前面的头文件可以说基本一样哦。每种格式的文件都有
     * 自己特有的头文件。
     */
    private static void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
                                            long totalDataLen, long longSampleRate, int channels, long byteRate)
            throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8); // block align
        header[33] = 0;
        header[34] = 16; // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }

}
