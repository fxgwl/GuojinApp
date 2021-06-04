package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.tu.loadingdialog.LoadingDailog;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.CourseBean;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CoursePayActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_course_name)
    TextView tvCourseName;
    @BindView(R.id.tv_course_value)
    TextView tvCourseValue;
    @BindView(R.id.tv_course_detail)
    TextView tvCourseDetail;
    @BindView(R.id.rb_ali_pay)
    RadioButton rbAliPay;
    @BindView(R.id.rb_wechat_pay)
    RadioButton rbWechatPay;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.b_submit)
    Button bSubmit;

    private CourseBean bean;
    private String company_name="",company_logo="";
    private String pay_way="alipay";//alipay，wxpay
    private LoadingDailog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_pay);
        ButterKnife.bind(this);
        String beanJson = getIntent().getStringExtra("bean");
        company_name = getIntent().getStringExtra("company_name");
        company_logo = getIntent().getStringExtra("company_logo");
        if (beanJson != null) {
            bean = JSONObject.parseObject(beanJson, CourseBean.class);
            initView();
        }
    }

    private void initView() {
        title.setText(company_name);
        tvCourseName.setText(bean.getCourse_name());
        tvCourseValue.setText(bean.getCourse_value());
        tvCourseDetail.setText(bean.getCourse_detail());
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_ali_pay:
                        pay_way="alipay";
                        break;
                    case R.id.rb_wechat_pay:
                        pay_way="wxpay";
                        break;
                }
            }
        });
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mDialog = loadBuilder.create();
    }


    @OnClick({R.id.back_top, R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                mDialog.show();
                toPay();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==22){
            startActivity(new Intent(getApplication(), MainActivity.class)
                    .putExtra("from", "login"));
            finish();
        }
    }

    private void toPay() {
        if(!pay_way.equals("alipay")){
            Toast.makeText(getApplicationContext(),"暂未开通微信支付",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("company_id", String.valueOf(bean.getCompany_id()));
        map.put("course_id", String.valueOf(bean.getId()));
        map.put("company_name",company_name);
        map.put("company_logo",company_logo);
        map.put("course_name",bean.getCourse_name());
        map.put("course_value",bean.getCourse_value());
        map.put("pay_way", pay_way);
        OkHttpUtils.post()
                .url(NetConfig.toPayCourse)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CoursetoPay.java:420)" + e.getMessage());
                        mDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CoursetoPay.java:71)" + response);
                        if (response == null) {
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            String url= jsonObject.getJSONObject("data").getString("qrData");
                            if(pay_way.equals("alipay")){
                                url= "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+url;
                            }else{
                                url=jsonObject.getJSONObject("data").getString("qrData");
                            }
                            mDialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivityForResult( intent ,22);
                            return;
                        } else {
                            Toast.makeText(getApplication(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });
    }
}
