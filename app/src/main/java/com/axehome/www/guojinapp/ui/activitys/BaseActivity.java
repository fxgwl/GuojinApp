package com.axehome.www.guojinapp.ui.activitys;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;

import com.axehome.www.guojinapp.GuoJinApp;
import com.axehome.www.guojinapp.R;
import com.zhy.autolayout.AutoLayoutActivity;

import androidx.annotation.Nullable;

/**
 * Created by Axehome_Mr.Z on ${date}
 */

public class BaseActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //把Activity添加到集合里面
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        GuoJinApp.getInstance().addActivity(this);
        setContentView(R.layout.activity_base);

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


    }

}

