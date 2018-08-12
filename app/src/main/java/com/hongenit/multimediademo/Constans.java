package com.hongenit.multimediademo;

import android.os.Environment;

import java.io.File;

/**
 * Created by hongenit on 2018/7/30.
 * desc:
 */

public class Constans {

    // 应用根目录
    public static final File APP_ROOT_FOLDER = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "multimediademo");
    // audio模块目录
    public static final File APP_AUDIO_FOLDER = new File(APP_ROOT_FOLDER + File.separator + "audio");


}
