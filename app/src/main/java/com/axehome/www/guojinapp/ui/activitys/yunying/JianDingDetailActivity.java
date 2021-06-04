package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.JianDingBean;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.adapters.ImgAdapter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JianDingDetailActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.tv_good_guige)
    TextView tvGoodGuige;
    @BindView(R.id.tv_good_detail)
    TextView tvGoodDetail;
    @BindView(R.id.iv_zheng_shu)
    ImageView ivZhengShu;
    @BindView(R.id.tv_real_num)
    TextView tvRealNum;
    @BindView(R.id.tv_good_value)
    TextView tvGoodValue;
    @BindView(R.id.ll_jian_ding)
    LinearLayout llJianDing;

    private JianDingBean bean;
    List<String> pics = new ArrayList<>();
    ImgAdapter imgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_ding_detail);
        ButterKnife.bind(this);
        String beanJson = getIntent().getStringExtra("bean");
        if (beanJson != null) {
            bean = JSONObject.parseObject(beanJson, JianDingBean.class);
            initView();
        }
    }

    private void initView() {
        title.setText("鉴定详情");
        tvGoodName.setText(bean.getGood_name());
        tvGoodDetail.setText(bean.getGood_detail());
        tvGoodGuige.setText(bean.getGood_guige());
        if (bean.getReal_num() != null) {
            tvRealNum.setText(bean.getReal_num());
        }
        if (bean.getGood_value() != null) {
            tvGoodValue.setText(String.valueOf(bean.getGood_value()));
        }
        Glide.with(getApplicationContext()).load(NetConfig.baseurl + bean.getGood_zhengshu()).error(R.drawable.pt12).into(ivZhengShu);

        if (bean.getGood_imgs() != null) {
            String[] strPic = bean.getGood_imgs().split(",");

            if (strPic != null && strPic.length > 0) {
                for (int i = 0; i < strPic.length; i++) {
                    pics.add(strPic[i]);
                }
            }
            imgAdapter = new ImgAdapter(getApplicationContext(), pics);
            gvList.setAdapter(imgAdapter);
        }
        if(bean.getNeed_zheng().equals("1")){
            llJianDing.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.back_top)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
        }
    }
}
