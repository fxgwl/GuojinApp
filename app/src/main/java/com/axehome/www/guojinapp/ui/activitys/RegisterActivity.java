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
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_code)
    TextView tvCode;

    private boolean able = true;
    private String YZM = "";
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("from") == null ? "" : getIntent().getStringExtra("from");
        if(type.equals("forget")){
            title.setText("修改密码");
            btSubmit.setText("确认修改");
        }else{
            title.setText("注册");
        }
        initView();
    }

    @OnClick({R.id.bt_submit,R.id.tv_code,R.id.back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                if(etPassword.getText().toString().trim().length()<6){
                    Toast.makeText(getApplication(), "密码不能小于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!able && !etCode.getText().toString().trim().equals("") && !etTel.getText().toString().trim().equals("")) {
                    if(type.equals("forget")){
                        UpdatePassWord();
                    }else{
                        Register();
                    }

                } else {
                    Toast.makeText(getApplication(), "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_code:
                getYZM();
                break;
            case R.id.back_top:
                finish();
                break;
        }
    }

    private void initView() {

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

    /*用户注册*/
    private void Register() {
        if (!YZM.equals(etCode.getText().toString().trim())) {
            Toast.makeText(getApplication(), "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        String pass_word=etPassword.getText().toString().trim();
        if(pass_word.length()==6){

        }else{
            Toast.makeText(getApplication(), "请输入6位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", etTel.getText().toString().trim());
        map.put("password", etPassword.getText().toString().trim());
        OkHttpUtils.post()
                .url(NetConfig.userRegister)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

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
                                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    /*修改密码*/
    private void UpdatePassWord() {
        if (!YZM.equals(etCode.getText().toString().trim())) {
            Toast.makeText(getApplication(), "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        String pass_word=etPassword.getText().toString().trim();
        if(pass_word.length()<6){
            Toast.makeText(getApplication(), "请至少输入6位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", etTel.getText().toString().trim());
        map.put("password", etPassword.getText().toString().trim());
        OkHttpUtils.post()
                .url(NetConfig.passwordModify)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("passwordModfy","is error:"+e.toString());
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
                                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
        able=false;
        if(isMobileNO(etTel.getText().toString())){

        }else{
            Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
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
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + ">>" + response);
                        if (response.isEmpty()) {
                            Toast.makeText(getApplication(), "网络请求失败", Toast.LENGTH_SHORT).show();
                            tvCode.setText("请重新获取验证码");
                            able = true;
                        } else {
                            if(JSON.parseObject(response).getString("code")!=null){
                                YZM = JSON.parseObject(response).getString("code");
                                startRunTime();
                            }else{
                                able=true;
                                Toast.makeText(getApplication(), "获取验证码失败", Toast.LENGTH_SHORT).show();
                                tvCode.setText("请重新获取验证码");
                            }
                        }
                    }
                });
    }
}
