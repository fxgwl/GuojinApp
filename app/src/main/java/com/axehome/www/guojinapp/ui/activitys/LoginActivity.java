package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.tu.loadingdialog.LoadingDailog;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

/**
 * Created by Axehome_Mr.Z on ${date}
 */
public class LoginActivity extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;

    private String type = "";
    private boolean able = true;
    private String YZM = "";
    private LoadingDailog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("from") == null ? "" : getIntent().getStringExtra("from");
        title.setText("用户登陆");
        initView();
    }

    @OnClick({R.id.bt_submit, R.id.back_top, R.id.tv_code,R.id.iv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                if (etCode.getText().toString().trim() != "" && etTel.getText().toString().trim() != "") {
                    mDialog.show();
                    Login(null);
                } else {
                    Toast.makeText(getApplication(), "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_code:
                getYZM();
                break;
            case R.id.iv_wechat:
                if(ivWechat.isClickable()){
                    wechatLogin();
                }
                break;
        }
    }

    private void initView() {
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mDialog = loadBuilder.create();
    }

    @Override
    public void onBackPressed() {
        if (type.equals("setup")) {
            startActivity(new Intent(getApplication(), MainActivity.class)
                    .putExtra("home", "home"));
        } else {
            super.onBackPressed();
        }
    }

    /*用户登录、注册*/
    private void Login(String open_id) {
        Map<String, String> map = new HashMap<>();
        if(open_id!=null){
            map.put("openId", open_id);
        }else{
            map.put("phone", etTel.getText().toString().trim());
            map.put("password", etCode.getText().toString().trim());
        }
        OkHttpUtils.post()
                .url(NetConfig.userLogin)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        ivWechat.setClickable(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    String data = jsonObject.getString("data");
                                    SPUtils.put("user", data);
                                    if(MyUtils.getUser().getPhone()!=null && !MyUtils.getUser().getPhone().equals("")){
                                        startActivity(new Intent(getApplication(), MainActivity.class)
                                                .putExtra("from", "login"));
                                    }else{
                                        startActivity(new Intent(getApplication(), BandPhoneActivity.class));
                                    }
                                    finish();
                                    return;
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ivWechat.setClickable(true);
                    }
                });
    }

/*
    //开始倒计时
    public void startRunTime() {
*/

    /**
     * 倒计时60秒，一次1秒
     *//*

        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                butCode.setText("还剩" + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                butCode.setText("请重新获取验证码");
                able = true;
            }
        }.start();
    }
*/
    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    //    获取验证码
    public void getYZM() {
        able = false;
        if (isMobileNO(etTel.getText().toString())) {

        } else {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = NetConfig.sendcode;
        OkHttpUtils.post()
                .url(url)
                .addParams("user_phone", etTel.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("onError", getClass().getName() + ">>" + e.getMessage());
                        tvCode.setText("请重新获取验证码");
                        able = true;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + ">>" + response);
                        if (response.isEmpty()) {
                            Toast.makeText(getApplication(), "网络请求失败", Toast.LENGTH_SHORT).show();
                            tvCode.setText("请重新获取验证码");
                            able = true;
                        } else {
                            if (JSON.parseObject(response).getString("data") != null) {
                                YZM = JSON.parseObject(response).getString("data");
                                startRunTime();
                            } else {
                                able = true;
                                Toast.makeText(getApplication(), "获取验证码失败", Toast.LENGTH_SHORT).show();
                                tvCode.setText("请重新获取验证码");
                            }
                        }
                    }
                });
    }

    //开始倒计时
    public void startRunTime() {
        /*倒计时60秒，一次1秒 */

        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                tvCode.setText("还剩" + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                tvCode.setText("请重新获取验证码");
                able = true;
            }
        }.start();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.d("wechat.backinfo==",hashMap.toString());
        String openId = (String) hashMap.get("openid");
        String headImg = (String) hashMap.get("headimgurl");
        platform.removeAccount(true);
        Login(openId);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ivWechat.setClickable(true);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ivWechat.setClickable(true);
        platform.removeAccount(true);
    }

    private void wechatLogin() {
        ivWechat.setClickable(false);
        //LuliApp.loginkg=0;
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.removeAccount(true);//执行此操作就可以移除掉本地授权状态和授权信息
        if (!wechat.isClientValid()) {
            Toast.makeText(LoginActivity.this, "微信未安装,请先安装微信", Toast.LENGTH_LONG).show();
            return;
        }

        wechat.setPlatformActionListener(this); // 设置分享事件回调
        wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
        //wechat.authorize();
        wechat.showUser(null);//授权并获取用户信息
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
