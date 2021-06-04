package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DeliveryInsertActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_nike_name)
    EditText etNikeName;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    private User bean=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_insert);
        ButterKnife.bind(this);
        bean = (User)getIntent().getSerializableExtra("bean");
        initView();
        initData();
    }

    private void initView() {

        if(bean!=null){
            title.setText("编辑");
            etNikeName.setText(bean.getRealname());
            etTel.setText(bean.getPhone());
        }else{
            title.setText("新建");
        }
    }

    private void initData() {

    }

    @OnClick({R.id.back_top,R.id.bt_submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.bt_submit:
                if(bean==null){
                    Register();
                }else{
                    EditVip();
                }
                break;
        }
    }

    /*用户注册落地代表*/
    private void Register() {
        String phone=etTel.getText().toString().trim();
        if(isMobileNO(phone)){

        }else{
            Toast.makeText(getApplication(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("realname", etNikeName.getText().toString().trim());
        map.put("b_user_code", MyUtils.getUser().getInvitation_code());
        String role_id=null;
        if(MyUtils.getUser().getRoleBean()==null ||MyUtils.getUser().getRoleBean().getRole_name().contains("客户经理")){
            Toast.makeText(getApplication(), "您的权限不足", Toast.LENGTH_SHORT).show();
            return;
        }
        if(MyUtils.getUser().getRoleBean().getRole_name().contains("代理")){
            map.put("role_id","11");
        }else if(MyUtils.getUser().getRoleBean().getRole_name().contains("合伙人")){
            map.put("role_id","10");
        }
        map.put("user_type","3");
        map.put("password","123456");
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
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                    setResult(1);
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
    private void EditVip() {
        String phone=etTel.getText().toString().trim();
        if(isMobileNO(phone)){

        }else{
            Toast.makeText(getApplication(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("realname", etNikeName.getText().toString().trim());
        map.put("username",bean.getUsername());
        map.put("user_id",String.valueOf(bean.getUser_id()));
        String role_id=null;
        if(MyUtils.getUser().getRoleBean()==null ||MyUtils.getUser().getRoleBean().getRole_name().contains("客户经理")){
            Toast.makeText(getApplication(), "您的权限不足", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post()
                .url(NetConfig.userModify)
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
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                                    setResult(1);
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
}
