package com.hongenit.multimediademo.utils;

import android.util.Log;

/**
 * Created by Xiaohong on 2018/6/25.
 * desc:
 */
public class LogUtils {
    public static final boolean WILL_LOG = true;

    public static void e(Object object, String message) {
        if (WILL_LOG) {
            Log.e(object.getClass().getSimpleName(), message);
        }
    }

    public static void d(Object object, String message) {
        if (WILL_LOG) {
            Log.d(object.getClass().getSimpleName(), message);
        }
    }

    public static void i(Object object, String message) {
        if (WILL_LOG) {
            Log.i(object.getClass().getSimpleName(), message);
        }
    }

    public static void v(Object object, String message) {
        if (WILL_LOG) {
            Log.v(object.getClass().getSimpleName(), message);
        }
    }

    public static void w(Object object, String message) {
        if (WILL_LOG) {
            Log.w(object.getClass().getSimpleName(), message);
        }
    }

    public static void hong(String message) {
        if (WILL_LOG) {
            Log.i("xiaohong", message);
        }
    }

}
