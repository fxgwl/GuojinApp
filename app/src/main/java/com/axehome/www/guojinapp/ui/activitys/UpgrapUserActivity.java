package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.axehome.www.guojinapp.beans.RoleBean;
import com.axehome.www.guojinapp.beans.RoleOrderBean;
import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class






UpgrapUserActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_curunt_leve)
    TextView tvCuruntLeve;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.rb_zhong)
    RadioButton rbZhong;
    @BindView(R.id.rb_gao)
    RadioButton rbGao;
    @BindView(R.id.rb_zong)
    RadioButton rbZong;

    private User user;
    private List<RoleBean> roleBeanList = new ArrayList<>();
    private String role_name = "",type="",value="",role_id="";//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrap_user);
        ButterKnife.bind(this);
        type=getIntent().getStringExtra("type");
        title.setText("??????");
        user = MyUtils.getUser();
        initView();
        initData();
    }

    private void initView() {
        if(type.equals("daili")){
        }else{
            tvInfo.setText(R.string.leve1);
        }
        switch (user.getRoleBean().getRole_name()) {
            case "?????????":
                rgMenu.setClickable(false);
                rbZong.setClickable(false);
                rbZhong.setClickable(false);
                rbGao.setClickable(false);
                rbZong.setChecked(false);
                tvCuruntLeve.setText("???????????????");
                role_name="?????????";
                break;
            case "????????????":
                rbZhong.setClickable(false);
                rbGao.setClickable(false);
                rbZong.setChecked(true);
                tvCuruntLeve.setText("???????????????");
                role_name="?????????";
                break;
            case "????????????":
                rbZhong.setClickable(false);
                rbGao.setChecked(true);
                tvCuruntLeve.setText("???????????????");
                role_name="????????????";
                break;
            case "????????????":
                tvCuruntLeve.setText("???????????????");
                role_name="????????????";
                break;
            case "???????????????":
                rgMenu.setClickable(false);
                rbZhong.setChecked(false);
                rbZhong.setClickable(false);
                rbGao.setClickable(false);
                rbZong.setClickable(false);
                rbZong.setVisibility(View.GONE);
                rbZhong.setText("???????????????");
                rbGao.setText("???????????????");
                tvCuruntLeve.setText("???????????????");
                role_name="???????????????";
                break;
            case "???????????????":
                rbZong.setVisibility(View.GONE);
                rbZong.setClickable(false);
                rbZhong.setChecked(false);
                rbZhong.setClickable(false);
                rbZong.setChecked(false);
                rbGao.setChecked(true);
                rbZhong.setText("???????????????");
                rbGao.setText("???????????????");
                tvCuruntLeve.setText("???????????????");
                role_name="???????????????";
                break;
            case "???????????????":
                rbZong.setVisibility(View.GONE);
                rbZhong.setChecked(true);
                rbGao.setChecked(false);
                rbZong.setChecked(false);
                rbZhong.setText("???????????????");
                rbGao.setText("???????????????");
                tvCuruntLeve.setText("???????????????");
                role_name="???????????????";
                break;

        }
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_zhong:
                        if(type.equals("daili")){
                            role_name="????????????";
                        }else{
                            role_name="???????????????";
                        }
                        getValue();
                        break;
                    case R.id.rb_gao:
                        if(type.equals("daili")){
                            role_name="????????????";
                        }else{
                            role_name="???????????????";
                        }
                        getValue();
                        break;
                    case R.id.rb_zong:
                        role_name="?????????";
                        getValue();
                        break;
                }
            }
        });
    }

    @OnClick({R.id.back_top,R.id.b_submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                Submit();
                break;
        }
    }

    public void getValue(){
        for(int i=0;i<roleBeanList.size();i++){
            if(roleBeanList.get(i).getRole_name().equals(role_name)){
                value=String.valueOf(roleBeanList.get(i).getRole_value()-user.getRoleBean().getRole_value());
                role_id= String.valueOf(roleBeanList.get(i).getRole_id());
            }
        }
        tvValue.setText("???"+value);
    }

    private void initData() {
        OkHttpUtils.post()
                .url(NetConfig.roleList)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("UpgrapUserActivity>>>>","error>>"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    String data = jsonObject.getString("data");
                                    roleBeanList.addAll(JSON.parseArray(data,RoleBean.class));
                                    getValue();
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

    private void Submit() {

        OkHttpUtils.postString()
                .url(NetConfig.userUpgroup)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(JSON.toJSONString(user))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e("userUpgroup  ","error==="+e.toString());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    //????????????
                                    RoleOrderBean bean = JSON.parseObject(jsonObject.getString("data"),RoleOrderBean.class);
                                    startActivity(new Intent(getApplicationContext(), ChoosePayWayActivity.class)
                                            .putExtra("roleOrderId",String.valueOf(bean.getOrder_id()))
                                            .putExtra("value",String.valueOf(bean.getOrder_value())));
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

}
