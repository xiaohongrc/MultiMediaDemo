package com.hongenit.multimediademo.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 * Created by hongenit on 2018/8/12.
 * desc:
 */

public interface ICamera {

    void startPreview(SurfaceHolder holder);
    void stopPreview();
    void takePicture(Camera.PictureCallback callback);

}
