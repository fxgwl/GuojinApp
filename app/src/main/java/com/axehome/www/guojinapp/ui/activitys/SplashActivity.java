package com.axehome.www.guojinapp.ui.activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.Call;
import util.DownloadAppUtils;


public class SplashActivity extends AppCompatActivity {


    private static final int DOWNLOAD_SUCCESS = 6;
    private static final int REQUEST_CALL_PHONE = 400;
    private static final int sleepTime = 2000;
    private final int UPDATE_NO_NEED = 0;
    private final int UPDATE_CLIENT = 3;
    private final int GET_UPDATE_INFO_ERROR = 2;
    private final int DOWN_ERROR = 4;
    private String type;
    private String localVersion;
    private File file = null;
    private ProgressDialog pd;
    private String[] permissions = new String[]{/*Manifest.permission.ACCESS_FINE_LOCATION*/};
    /*private String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WAKE_LOCK
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.RECEIVE_BOOT_COMPLETED
            , Manifest.permission.STATUS_BAR
            , Manifest.permission.SYSTEM_ALERT_WINDOW
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.CAMERA
    };*/
    private ArrayList<String> mPermission = new ArrayList<>();
    private long startTime;
    private String versions;
    private int REQUEST_CODE_ACCESS_COARSE_LOCATION;
    private boolean isFirst;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_NO_NEED:
                    checkAutoLogin();
                    break;
                case UPDATE_CLIENT:
                    String url = (String) msg.obj;
                    showUpdateDialog(url);
//                    startActivity(new Intent(SplashActivity.this, ToUpdateActivity.class).putExtra("url",url));
//                    finish();
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case GET_UPDATE_INFO_ERROR:
                    checkAutoLogin();
                    break;
                case DOWN_ERROR:
                    //ToastUtil.showToastShort("下载新版本失败");
                    checkAutoLogin();
                    break;
                case DOWNLOAD_SUCCESS:
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
            }
        }
    };// 进度条对话框

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_splash);
        isFirst = getSharedPreferences("isFirst", Context.MODE_PRIVATE).getBoolean("first", true);
        startTime = System.currentTimeMillis();
//        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
//        animation.setDuration(3000);
//        rootLayout.startAnimation(animation);
        requestPermission();
        getVersionInfo();
    }


    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            mPermission.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(SplashActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermission.add(permissions[i]);
                }
            }
            if (mPermission.isEmpty()) {//未授予的权限为空，表示都授予了
//                Toast.makeText(SplashActivity.this, "已经授权", Toast.LENGTH_LONG).show();
                //checkAutoLogin();
            } else {//请求权限方法
                String[] permissions = mPermission.toArray(new String[mPermission.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }

        } else {
            Log.e("aaa",
                    "(SplashActivity.java:111)" + "不用获取权限");
            //API 版本在23以下
            //checkAutoLogin();
        }
    }

    private void checkAutoLogin() {
        new Thread(new Runnable() {
            public void run() {
                long costTime = System.currentTimeMillis() - startTime;
                //wait
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                checkWebEnvironment();
            }
        }).start();
    }

    private void checkWebEnvironment() {
//        if (isFirst) {
//            startActivity(new Intent(SplashActivity.this, LeadActivity.class));
//            finish();
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i]);
                    if (showRequestPermission) {
//                        Toast.makeText(this, "权限未申请", Toast.LENGTH_SHORT).show();
                        requestPermission();
                    }
                }
            }

            Log.e("aaa",
                    "(SplashActivity.java:176)<--运行自动登录-->");
            //checkAutoLogin();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /*获取服务器和本地版本信息进行对比*/
    public void getVersionInfo() {
        OkHttpUtils.post()
                .url(NetConfig.newestApp)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                        checkAutoLogin();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if(response==null){
                            checkAutoLogin();
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getJSONObject("data")!=null){
                            versions = jsonObject.getJSONObject("data").getString("newestAppVersion");
                        }else{
                            checkAutoLogin();
                            return;
                        }
                        //type = data.getString("type");
                        try {
                            if (versions.equals(getVersionName())) {
                                Message msg = new Message();
                                msg.what = UPDATE_NO_NEED;
                                handler.sendMessage(msg);
                            } else {
                                String url = jsonObject.getJSONObject("data").getString("newestAppUrl");
                                Message msg = new Message();
                                msg.what = UPDATE_CLIENT;
                                msg.obj = url;
                                handler.sendMessage(msg);
                            }
                        } catch (Exception e) {
                            checkAutoLogin();
                            e.printStackTrace();
                        }
                    }
                });
    }


    // 获取应用当前版本信息
    private String getVersionName() throws Exception {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }

    //显示更新dialog
    protected void showUpdateDialog(final String url) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("更新");
        builer.setMessage("软件有更新版本是否更新？");
        // 当点确定按钮时从服务器上下载 新的apk 然后安装 װ
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk(url);
                dialog.dismiss();
            }
        });
        builer.setNegativeButton("暂不", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent;
                dialog.dismiss();
                /*if (type.equals("1")) {
                    Toast.makeText(SplashActivity.this, "msg", Toast.LENGTH_SHORT).show();
                } else*/
                    checkAutoLogin();
//                if (isFirstIn) {
//                    // start guideactivity1
//                    intent = new Intent(LoginActivity.this,
//                            ExampleGuideActivity.class);
//                } else {
//                    // start TVDirectActivity
//                    intent = new Intent(LoginActivity.this, MainActivity.class);
//                }
//                startActivity(intent);
            }
        });
        builer.setCancelable(false);
        AlertDialog dialog = builer.create();
        dialog.show();
    }

//    //显示更新dialog
//    protected void showUpdateDialog(String versionName,String url) {
////        UpdateAppUtils.from(this)
////                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
////                .serverVersionName(versionName)
////                .apkPath(Internet.BASE_URL+url)
////                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
////                .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
////                .update();
//    }

    /*
     * 从服务器中下载APK
     */
    /*protected void downLoadApk(final String url) {
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread() {
            @Override
            public void run() {
                try {
//                    DownloadAppUtils.downloadForAutoInstall(SplashActivity.this, Internet.BASE_URL + url, "UD.apk", versions);
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();

    }*/

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk(String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    DownloadAppUtils.downloadForWebView(SplashActivity.this, NetConfig.baseurl + url);
                    //DownloadAppUtils.downloadForAutoInstall(MainActivity.this, NetConfig.baseUrl + url, "demo.apk", ii);
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }
}
