package com.axehome.www.guojinapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.axehome.www.guojinapp.utils.MyUtils;
import com.mob.MobSDK;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Axehome_Mr.z on 2020/7/13 11:18
 */
public class GuoJinApp extends Application{
    private static GuoJinApp application;
    private List<Activity> activityList = new LinkedList();
    public static String key="4c7c2a60e2c74e33902f8215ed361375";
    public static String inst_no="52000888";
    public static String orgid = "35175570";
    public static Context getContext() {
        return application;
    }
    public static Double lat = null;
    public static Double lng = null;
    public static GuoJinApp getInstance() {

        synchronized (Application.class){
            if (null == application) {
                application = new GuoJinApp();
            }
            return application;
        }
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    public void out() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MobSDK.submitPolicyGrantResult(true, null);
    }

    public static boolean isLogin() {
        if (MyUtils.getUser() != null && MyUtils.getUser().getUser_id() != null) {
            return true;
        } else {
            return false;
        }
    }
}
