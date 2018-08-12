package com.hongenit.multimediademo.imageFilter;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongenit.multimediademo.R;

import java.io.IOException;
import java.io.InputStream;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorMatrixFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDarkenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDivideBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLinearBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLuminosityBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageThresholdEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWeakPixelInclusionFilter;

/**
 * Created by Xiaohong on 2018/8/3.
 * desc:
 */
public class ImageFilterActivity extends AppCompatActivity {
    private ImageView iv_result_image;
    private TextView tv_filter_name;
    private GPUImage gpuImage;
    private Handler handler = new Handler();

    private GPUImageFilter[] gpuImageFilters = {new GPUImage3x3ConvolutionFilter(), new GPUImage3x3TextureSamplingFilter(),
            new GPUImageGaussianBlurFilter(),
            new GPUImageAddBlendFilter(),
            new GPUImageAlphaBlendFilter(),
            new GPUImageBilateralFilter(),
            new GPUImageBoxBlurFilter(),
            new GPUImageBrightnessFilter(),
            new GPUImageBulgeDistortionFilter(),
            new GPUImageCGAColorspaceFilter(),
            new GPUImageChromaKeyBlendFilter(),
            new GPUImageColorBalanceFilter(),
            new GPUImageColorBlendFilter(),
            new GPUImageColorBurnBlendFilter(),
            new GPUImageColorDodgeBlendFilter(),
            new GPUImageColorInvertFilter(),
            new GPUImageColorMatrixFilter(),
            new GPUImageCrosshatchFilter(),
            new GPUImageChromaKeyBlendFilter(),
            new GPUImageDarkenBlendFilter(),
            new GPUImageDifferenceBlendFilter(),
            new GPUImageColorDodgeBlendFilter(),
            new GPUImageDivideBlendFilter(),
            new GPUImageFalseColorFilter(),
            new GPUImageEmbossFilter(),
            new GPUImageExclusionBlendFilter(),
            new GPUImageGlassSphereFilter(),
            new GPUImageGammaFilter(),
            new GPUImageGrayscaleFilter(),
            new GPUImageHueFilter(),
            new GPUImageHalftoneFilter(),
            new GPUImageHardLightBlendFilter(),
            new GPUImageHighlightShadowFilter(),
            new GPUImageKuwaharaFilter(),
            new GPUImageLuminosityBlendFilter(),
            new GPUImageLookupFilter(),
            new GPUImageLinearBurnBlendFilter(),
            new GPUImageLightenBlendFilter(),
            new GPUImageLevelsFilter(),
            new GPUImageLaplacianFilter(),
            new GPUImageMultiplyBlendFilter(),
            new GPUImageMonochromeFilter(),
            new GPUImagePixelationFilter(),
            new GPUImageNonMaximumSuppressionFilter(),
            new GPUImageOpacityFilter(),
            new GPUImageRGBDilationFilter(),
            new GPUImageSaturationBlendFilter(),
            new GPUImageToneCurveFilter(),
            new GPUImageToneCurveFilter(),
            new GPUImageToneCurveFilter(),
            new GPUImageThresholdEdgeDetection(),
            new GPUImageToonFilter(),
            new GPUImageWeakPixelInclusionFilter(),
            new GPUImageVignetteFilter()

    };
    int pos;
    private Runnable filterTask = new Runnable() {
        @Override
        public void run() {
            // 使用GPUImage处理图像
            int i = pos++ % gpuImageFilters.length;
            GPUImageFilter filter = gpuImageFilters[i];
            gpuImage.setFilter(filter);
            Bitmap bitmap = gpuImage.getBitmapWithFilterApplied();
            //显示处理后的图片
            iv_result_image.setImageBitmap(bitmap);
            tv_filter_name.setText(i + ".  " + filter.getClass().getSimpleName());
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        initView();
    }

    private void initView() {

        iv_result_image = findViewById(R.id.iv_result_image);
        tv_filter_name = findViewById(R.id.tv_filter_name);

        //获得Assets资源文件
        AssetManager as = getAssets();
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            //注意名字要与图片名字一致
            is = as.open("link.jpg");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            Log.e("GPUImage", "Error");
        }

        System.out.println("-------------gpuImage");
        gpuImage = new GPUImage(this);
        gpuImage.setImage(bitmap);

    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("-------------onresume()");
        handler.postDelayed(filterTask, 1000);

    }

}
