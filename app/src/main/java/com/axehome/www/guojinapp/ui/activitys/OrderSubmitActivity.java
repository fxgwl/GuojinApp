package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.AddressBean;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.axehome.www.guojinapp.utils.MyUtils;
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

public class OrderSubmitActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.iv_good_img)
    RoundedImageView ivGoodImg;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_value)
    TextView tvGoodValue;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.b_submit)
    Button bSubmit;

    private PreGoodsBean goodsBean;
    private AddressBean addressBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submit);
        ButterKnife.bind(this);
        String goodJson = getIntent().getStringExtra("good");
        if (goodJson != null) {
            goodsBean = JSONObject.parseObject(goodJson, PreGoodsBean.class);
            initView();
        }
    }

    private void initView() {
        title.setText("选择地址");
        if (goodsBean.getGood_img() != null) {
            String[] strImgs = goodsBean.getGood_img().split(",");
            Glide.with(getApplicationContext()).load(NetConfig.baseurl+strImgs[0]).into(ivGoodImg);
        }
        if(MyUtils.getUser().getAddressBean()!=null){
            addressBean = MyUtils.getUser().getAddressBean();
            tvAddress.setText(addressBean.getAddress_city()+addressBean.getAddress_area()+addressBean.getAddress_detail());
        }
        tvGoodName.setText(goodsBean.getGood_name());
        tvGoodValue.setText(String.valueOf(goodsBean.getGood_value()));
        tvValue.setText(String.valueOf(goodsBean.getGood_value()));
    }

    @OnClick({R.id.back_top, R.id.ll_address, R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_address:
                startActivityForResult(new Intent(getApplicationContext(),AddressListActivity.class),11);
                break;
            case R.id.b_submit:
                orderSubmit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && resultCode==1){
            addressBean = JSONObject.parseObject(data.getStringExtra("addressBean"),AddressBean.class);
            tvAddress.setText(addressBean.getAddress_city()+addressBean.getAddress_area()+addressBean.getAddress_detail());
        }
    }

    /**
     * 用户下单
     * orderSubmit
     */
    public void orderSubmit() {
        Map<String, String> map = new HashMap<>();
        map.put("good_id", String.valueOf(goodsBean.getGood_id()));
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        if(addressBean==null){
            Toast.makeText(getApplicationContext(),"请先选择地址",Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("address_id", String.valueOf(addressBean.getAddress_id()));
        OkHttpUtils.post()
                .url(NetConfig.saveOrder)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                            String order_id= jsonObject.getJSONObject("data").getString("order_id");
                            startActivity(new Intent(getApplicationContext(),PayWayNextActivity.class)
                                    .putExtra("order_id",order_id));
                            finish();
                            //listBanner.addAll(JSON.parseArray(jsonObject.getString("data"), BannerBean.class));
                        } else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
