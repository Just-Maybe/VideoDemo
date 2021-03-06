package com.example.videodemo.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import com.example.videodemo.BasePermissionsActivity;
import com.example.videodemo.R;

public class CameraActivity extends BasePermissionsActivity implements SurfaceHolder.Callback {
    private static final String TAG = CameraActivity.class.getSimpleName();
    private SurfaceView surfaceView;
    Camera camera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
    }

    private void initView() {
        surfaceView = findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(this);
    }

    // surface 预览
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            //Android 中Google支持的 Camera Preview Callback的YUV常用格式有两种：
            // 一个是NV21，一个是YV12。Android一般默认使用YCbCr_420_SP的格式（NV21）。
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.set("orientation", "portrait");  //竖屏预览
            camera.setDisplayOrientation(90);   // 旋转90度
            camera.setParameters(parameters);
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }

}
