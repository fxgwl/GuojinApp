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
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class BandPhoneActivity extends BaseActivity {

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
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private boolean able = true;
    private String YZM = "";
    private String type = "", openId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_phone);
        ButterKnife.bind(this);
        title.setText("绑定手机");
    }

    @OnClick({R.id.back_top, R.id.tv_code, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_code:
                if (!isMobileNO(etTel.getText().toString())) {
                    Toast.makeText(getApplication(), "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                } else {
                    if (able) {
                        able = false;
                        startRunTime();
                        getYZM();
                    }
                }
                break;
            case R.id.bt_submit:
                if (!able && etCode.getText().toString().trim() != "" && etTel.getText().toString().trim() != "") {
                    ChangePhone();
                } else {
                    Toast.makeText(getApplication(), "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_del:
                etTel.setText("");
                break;
        }
    }

    private void ChangePhone() {
        if (!YZM.equals(etCode.getText().toString().trim())) {
            Toast.makeText(getApplication(), "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("phone", etTel.getText().toString().trim());
        map.put("openId", MyUtils.getUser().getOpenId());
        map.put("password", etCode.getText().toString().trim());
        OkHttpUtils.post()
                .url(NetConfig.getUserAddPhone)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", ">>" + e.getMessage());
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", ">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            if (JSONObject.parseObject(response).getInteger("code") == 0) {
                                String data = JSONObject.parseObject(response).getString("data");
                                SPUtils.put("user", data);
                                startActivity(new Intent(getApplication(), MainActivity.class)
                                        .putExtra("from", "login"));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    //开始倒计时
    public void startRunTime() {
/** 倒计时60秒，一次1秒 */
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
}
