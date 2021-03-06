package com.hongenit.multimediademo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.hongenit.multimediademo.audio.SoundRecordActivity
import com.hongenit.multimediademo.camera.CameraActivity
import com.hongenit.multimediademo.imageFilter.ImageFilterActivity
import com.hongenit.multimediademo.player.PlayerActivity
import com.hongenit.multimediademo.surfaceview.SurfaceViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_player -> forwardPlayerActivity()
            R.id.bt_camera -> forwardCameraActivity()
            R.id.bt_surface -> forwardSurfaceViewActivity()
            R.id.bt_sound_record -> forwardSoundRecordActivity()
            R.id.bt_image_filter -> forwardImageFilterActivity()
        }
    }


    fun forwardPlayerActivity() {
        val intent = Intent(this, PlayerActivity::class.java)
        startActivity(intent)
    }

    fun forwardCameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    fun forwardSurfaceViewActivity() {
        val intent = Intent(this, SurfaceViewActivity::class.java)
        startActivity(intent)
    }

    fun forwardSoundRecordActivity() {
        val intent = Intent(this, SoundRecordActivity::class.java)
        startActivity(intent)
    }

    fun forwardImageFilterActivity() {
        val intent = Intent(this, ImageFilterActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        bt_player.setOnClickListener(this)
        bt_camera.setOnClickListener(this)
        bt_surface.setOnClickListener(this)
        bt_sound_record.setOnClickListener(this)
        bt_image_filter.setOnClickListener(this)
    }


}
