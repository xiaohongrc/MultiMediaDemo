package com.hongenit.multimediademo.camera;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by hongenit on 2018/8/12.
 * desc:
 */

public class CameraImpl1 implements ICamera {

    private Camera camera = null;

    public CameraImpl1() {
    }

    public void startPreview(SurfaceHolder holder) {
        try {
            if (camera == null) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopPreview() {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public void takePicture(Camera.PictureCallback callback) {
        camera.takePicture(null, null,callback);
    }
}
