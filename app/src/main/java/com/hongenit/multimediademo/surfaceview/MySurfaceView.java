package com.hongenit.multimediademo.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hongenit.multimediademo.R;
import com.hongenit.multimediademo.utils.LogUtils;

/**
 * Created by Xiaohong on 2018/7/23.
 * desc:
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Canvas mCanvas;//绘图的画布
    private boolean mIsDrawing;//控制绘画线程的标志位

    private SurfaceHolder surfaceHolder;
    private Path mPath;
    private Paint mPaint;
    private Rect rectDst;
    private Rect rectSrc;
    private Bitmap bitmap;

    public MySurfaceView(Context context) {
        super(context);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPath = new Path();

        rectSrc = new Rect();
        rectSrc.set(0, 0, 2048, 1536);

        rectDst = new Rect();
        rectDst.set(0, 0, 2048, 1536);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test01);


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }


    @Override
    public void run() {
        while (mIsDrawing) {
            long start_time = System.currentTimeMillis();
            draw();
            double random = Math.random();
            int ms = (int) (random * 50) + 1;
            SystemClock.sleep(ms);
            long end_time = System.currentTimeMillis();
            long spend_time = end_time - start_time;
            LogUtils.hong("spend time  = " + spend_time);
            while (spend_time < 50) {
                spend_time = System.currentTimeMillis() - start_time;
                Thread.yield();
            }
        }
    }

    int x = 0;
    int y = 0;

    int middle_line = 200;

    private void draw() {
        try {
            // 计算正弦曲线的轨迹
            x += 10;
            if (x > getWidth()) {
                LogUtils.hong("thread 那么 = " + Thread.currentThread().getName());
                x = 0;
                middle_line += 100;
                mPath.moveTo(x, middle_line);
            }
            y = (int) (100 * Math.sin(x * 2 * Math.PI / 180) + middle_line);

            // 开始绘制图片。
            mCanvas = surfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            rectDst.set(x, y, 600+x, 536+y);
            mCanvas.drawBitmap(bitmap,null,rectDst,mPaint);


//            LogUtils.hong("x = " + x + "   y = " + y);
            // 绘制正弦曲线。
            mPath.lineTo(x, y);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                surfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }


    }


}
