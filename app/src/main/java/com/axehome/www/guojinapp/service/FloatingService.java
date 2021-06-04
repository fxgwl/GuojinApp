/*
package com.axehome.www.jufuapp.service;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import static android.content.Context.WINDOW_SERVICE;

*/
/**
 * Created by Axehome_fxg on 2021/1/30 14:24
 *//*

public class FloatingService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                // 获取WindowManager服务
                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

                // 新建悬浮窗控件
                Button button = new Button(getApplicationContext());
                button.setText("Floating Window");
                button.setBackgroundColor(Color.BLUE);

                // 设置LayoutParam
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                layoutParams.format = PixelFormat.RGBA_8888;
                layoutParams.width = 500;
                layoutParams.height = 100;
                layoutParams.x = 300;
                layoutParams.y = 300;

                // 将悬浮窗控件添加到WindowManager
                windowManager.addView(button, layoutParams);
            }
        }
    }

    private void showFloatingWindow() {

        button.setOnTouchListener(new FloatingOnTouchListener());

    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;

                    // 更新悬浮窗控件布局
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
*/
