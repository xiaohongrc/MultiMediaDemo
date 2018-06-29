package com.hongenit.multimediademo.camera

import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.hongenit.multimediademo.BaseActivity
import com.hongenit.multimediademo.R
import com.hongenit.multimediademo.utils.LogUtils
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Xiaohong on 2018/6/25.
 * desc:
 */
class CameraActivity : BaseActivity(), View.OnClickListener, Camera.PictureCallback {
    override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
        // 拍照的回调
        LogUtils.d(this, "data.size = ${data?.size}")

        // 保存照片
        val file: File = getPhotoSavePath()
        val fileOutputStream = FileOutputStream(file)

        try {
            fileOutputStream.write(data)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream.close()
        }

        camera?.startPreview()

    }

    private fun getPhotoSavePath(): File {
        val storageDirectory = Environment.getExternalStorageDirectory()
        return File(storageDirectory.path + File.separator + System.currentTimeMillis() + ".jpg")

    }

    override fun onClick(v: View?) {
        // 拍照
        previewSurfaceView?.takePic(this)
    }

    private var previewSurfaceView: PreviewSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        previewSurfaceView = PreviewSurfaceView(this)
        preview_container.addView(previewSurfaceView)
        bt_take_pic.setOnClickListener(this)

    }


}