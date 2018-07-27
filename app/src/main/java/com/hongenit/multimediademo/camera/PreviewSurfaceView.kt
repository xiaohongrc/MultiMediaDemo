package com.hongenit.multimediademo.camera

import android.content.Context
import android.graphics.PixelFormat
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.hongenit.multimediademo.utils.LogUtils

/**
 * Created by Xiaohong on 2018/6/25.
 * desc:
 */
class PreviewSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    init {
        // 监听surfaceView 的回调
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSPARENT)
    }


    private var camera: Camera? = null

    override fun surfaceCreated(holder: SurfaceHolder?) {
        LogUtils.d(this, "surfaceCreated()")
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
        camera?.setPreviewDisplay(holder)
        camera?.setDisplayOrientation(90)
        camera?.startPreview()
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        // surface发生改变时。
        LogUtils.d(this, "surfaceChanged()")

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        LogUtils.d(this, "surfaceDestroyed()")
        camera?.stopPreview()
        camera?.release()
        camera = null
    }

    // 拍照
    fun takePic(callback: Camera.PictureCallback) {
        camera?.takePicture(null, null, callback)
    }


}