package com.hongenit.multimediademo.audio;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;

import com.hongenit.multimediademo.Constans;
import com.hongenit.multimediademo.R;
import com.hongenit.multimediademo.utils.LogUtils;

/**
 * Created by Xiaohong on 2018/8/5.
 * desc:
 */
public class SoundRecordFragment extends Fragment implements View.OnClickListener {
    private View record_btn;

    private boolean isStartedRecord = false;

    private ISoundRecorder soundRecorder = null;

    private Chronometer cm_time_count;
    private CheckBox cb_is_media_record;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_record, container, false);
        initView(inflate);
        return inflate;
    }


    private void initView(View inflate) {
        soundRecorder = new AudioRecordImpl();
        if (!Constans.APP_AUDIO_FOLDER.exists()) {
            boolean mkdir = Constans.APP_AUDIO_FOLDER.mkdir();
        }
        record_btn = inflate.findViewById(R.id.record_btn);
        cm_time_count = inflate.findViewById(R.id.cm_time_count);
        record_btn.setOnClickListener(this);
        cb_is_media_record = inflate.findViewById(R.id.cb_is_media_record);
        cb_is_media_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.hong("onCheckedChanged isChecked = " + isChecked);
                if (isChecked) {
                    soundRecorder = new MediaRecordImpl();
                    LogUtils.hong("MediaRecordImpl");
                } else {
                    soundRecorder = new AudioRecordImpl();
                }
            }
        });

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


    public static Fragment newInstance() {
        return new SoundRecordFragment();
    }
}
