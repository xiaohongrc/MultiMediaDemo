package com.hongenit.multimediademo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.hongenit.multimediademo.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_start -> start()
            R.id.bt_pause -> pause()
            R.id.bt_stop -> stop()
        }
    }


    fun start() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)

    }

    fun pause() {

    }

    fun stop() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        bt_start.setOnClickListener(this)
        bt_pause.setOnClickListener(this)
        bt_stop.setOnClickListener(this)
    }


}
