package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.tu.loadingdialog.LoadingDailog;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.OrderBean;
import com.axehome.www.guojinapp.beans.UserCouponBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PayWayNextActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_good_img)
    RoundedImageView ivGoodImg;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_value)
    TextView tvGoodValue;
    @BindView(R.id.tv_order_value)
    TextView tvOrderValue;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_coupon_value)
    TextView tvCouponValue;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.rb_ali_pay)
    RadioButton rbAliPay;
    @BindView(R.id.rb_wechat_pay)
    RadioButton rbWechatPay;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.ll_jie_suan)
    LinearLayout llJieSuan;

    private UserCouponBean userCouponBean;
    private OrderBean orderBean;
    private String order_id="",sys_tel="",pay_way="alipay";//alipay，wxpay
    private LoadingDailog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_way_next);
        ButterKnife.bind(this);
        order_id=getIntent().getStringExtra("order_id");
        if(order_id!=null){
            getOrderDetail();
        }
        title.setText("订单支付");
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
        SysInfo();
    }

    private void initView() {
        if(orderBean.getOrder_good_img()!=null){
            String[] strPic = orderBean.getOrder_good_img().split(",");
            Glide.with(getApplicationContext()).load(NetConfig.baseurl + strPic[0]).error(R.drawable.pt12).into(ivGoodImg);
        }
        tvGoodName.setText(orderBean.getOrder_good_name());
        tvGoodValue.setText("￥"+String.valueOf(orderBean.getOrder_good_value()));
        Double value =orderBean.getOrder_value()!=null?orderBean.getOrder_value():orderBean.getOrder_good_value();
        tvOrderValue.setText(String.valueOf(value));
        tvOrderNum.setText(String.valueOf(orderBean.getOrder_num()));
        tvValue.setText(String.valueOf(value));
        tvAddress.setText(orderBean.getOrder_address());
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mDialog = loadBuilder.create();
    }

    //开始倒计时
    public void startRunTime(int num) {
        /*倒计时60秒，一次1秒 */

        CountDownTimer timer = new CountDownTimer(num * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                tvTime.setText("" + millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    @OnClick({R.id.back_top, R.id.ll_coupon, R.id.ll_jie_suan,R.id.b_link_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_coupon:
                if(orderBean.getGoodsBean().getIs_coupon().equals("1")){
                    startActivityForResult(new Intent(getApplicationContext(),MyCouponActivity.class)
                            .putExtra("from","order"),11);
                }else{
                    Toast.makeText(getApplicationContext(),"此商品不能试用优惠券", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_jie_suan:
                toPay();
                break;
            case R.id.b_link_kefu:
                //拨打电话
                if(sys_tel!=null && !sys_tel.equals("")){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + sys_tel);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && resultCode==1){
            userCouponBean = JSONObject.parseObject(data.getStringExtra("couponBean"), UserCouponBean.class);
            if(userCouponBean!=null && orderBean!=null){
                orderBean.setCoupon_id(userCouponBean.getId());
                orderBean.setCoupon_value(userCouponBean.getCouponBean().getCoupon_value());
                Double value =orderBean.getOrder_good_value()-userCouponBean.getCouponBean().getCoupon_value();
                if(value>0.00){
                    orderBean.setOrder_value(value);
                    tvValue.setText(String.valueOf(value));
                    tvOrderValue.setText(String.valueOf(value));
                }else{
                    orderBean.setOrder_value(0.00);
                    tvValue.setText(String.valueOf(value));
                    tvOrderValue.setText(String.valueOf(value));
                }
                tvCouponValue.setText("-"+orderBean.getCoupon_value());
            }
        }else if(requestCode==22){
            startActivity(new Intent(getApplication(), MainActivity.class)
                    .putExtra("from", "login"));
            finish();
        }
    }

    private void getOrderDetail(){
        Map<String, String> map = new HashMap<>();
        map.put("order_id", order_id);
        OkHttpUtils.post()
                .url(NetConfig.getOrderDetailUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getOrderDetail.java:420)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getOrderDetail.java:71)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            orderBean= JSON.parseObject(jsonObject.getString("data"), OrderBean.class);
                            Integer num=jsonObject.getInteger("times");
                            if(num!=null && num>0){
                                startRunTime(num);
                            }

                            initView();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void toPay(){
        if(!pay_way.equals("alipay")){
            Toast.makeText(getApplicationContext(),"暂未开通微信支付",Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("order_id", order_id);
        String value=tvOrderValue.getText().toString().trim();
        map.put("order_value", value.equals("")?"0":value);
        if(userCouponBean!=null){
            map.put("coupon_id", String.valueOf(userCouponBean.getId()));
            map.put("coupon_value", String.valueOf(userCouponBean.getCouponBean().getCoupon_value()));
        }
        map.put("order_pay_way", pay_way);
        OkHttpUtils.post()
                .url(NetConfig.toPayForOrderUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getOrderDetail.java:420)" + e.getMessage());
                        mDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getOrderDetail.java:71)" + response);
                        if (response == null) {
                            mDialog.dismiss();
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {

                            String url=jsonObject.getJSONObject("data").getString("qrData");
                            if(pay_way.equals("alipay")){
                                url= "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+url;
                            }else{
                                url=jsonObject.getJSONObject("data").getString("qrData");
                            }
                            mDialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivityForResult( intent ,22);
                            /*startActivityForResult(new Intent(getApplicationContext(),WebViewTitalActivity.class)
                                    .putExtra("url",url).putExtra("name","支付"),22);*/
                            /*try {
                                url= URLDecoder.decode(url, "UTF-8");
                                if(pay_way.equals("alipay")){
                                    url= "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+url;
                                }else{
                                    url=jsonObject.getJSONObject("data").getString("qrData");
                                }
                                startActivityForResult(new Intent(getApplicationContext(),WebViewTitalActivity.class)
                                        .putExtra("url",url).putExtra("name","支付"),22);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                return;
                            }*/
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SysInfo() {

        OkHttpUtils.post()
                .url(NetConfig.getSysinfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            int code = jsonObject.getInteger("code");
                            if (code == 0) {
                                sys_tel=jsonObject.getJSONObject("data").getString("sys_phone");
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
