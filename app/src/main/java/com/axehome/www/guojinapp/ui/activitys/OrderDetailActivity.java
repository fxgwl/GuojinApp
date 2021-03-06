package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.OrderBean;
import com.axehome.www.guojinapp.beans.WuLiuBean;
import com.axehome.www.guojinapp.ui.adapters.WuliuAdapter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyListView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_shouhuo_time)
    TextView tvShouhuoTime;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.iv_stroe_img)
    RoundedImageView ivStroeImg;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_value2)
    TextView tvValue2;
    @BindView(R.id.tv_youhui)
    TextView tvYouhui;
    @BindView(R.id.ll_shouhuo_time)
    LinearLayout llShouhuoTime;
    @BindView(R.id.mlv_list)
    MyListView mlvList;
    @BindView(R.id.tv_wuliu_name)
    TextView tvWuliuName;
    @BindView(R.id.tv_wuliu_num)
    TextView tvWuliuNum;

    private OrderBean bean;
    private List<WuLiuBean> wuLiuBeanList = new ArrayList<>();
    private WuliuAdapter wuliuAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    wuliuAdapter = new WuliuAdapter(getApplicationContext(), wuLiuBeanList);
                    mlvList.setAdapter(wuliuAdapter);
                    break;
            }
        }
    };// ??????????????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        bean = JSON.parseObject(getIntent().getStringExtra("bean"), OrderBean.class);

        if (bean != null) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    orderSubmit(bean.getOrder_wulv_num());
                }
            }.start();
            initView();
        }
    }

    private void initView() {
        title.setText("????????????");
        if (bean.getOrder_status().equals("2")) {//0:????????????1???????????????2???????????????3????????????

        } else {
        }
        if(bean.getOrder_wulv_name()!=null){
            tvWuliuName.setText(bean.getOrder_wulv_name()+": ");
            tvWuliuNum.setText(bean.getOrder_wulv_num());
        }
        llShouhuoTime.setVisibility(View.GONE);
        switch (bean.getOrder_status()) {
            case "0":
                tvState.setText("?????????");
                break;
            case "1":
                tvState.setText("?????????");
                break;
            case "2":
                tvState.setText("?????????");
                llShouhuoTime.setVisibility(View.VISIBLE);
                break;
            case "3":
                tvState.setText("?????????");
                break;
        }
        tvName.setText(bean.getOrder_shouhuo_name() + " " + bean.getOrder_shouhuo_phone());
        tvAddress.setText(bean.getOrder_address());
        tvGoodName.setText(bean.getOrder_good_name());
        tvOrderNum.setText(bean.getOrder_num());
        if (bean.getCoupon_value() != null) {
            tvYouhui.setText("????????? ???" + String.valueOf(bean.getCoupon_value()));
        }
        if (bean.getGoodsBean().getGood_type().equals("2")) {
            if (bean.getOrder_good_value() != null) {
                tvValue.setText(String.valueOf(bean.getOrder_good_value()) + " + ?????????" + bean.getGoodsBean().getGood_jifen());
            } else {
                tvValue.setText("?????????" + bean.getGoodsBean().getGood_jifen());
            }
            if (bean.getOrder_value() != null) {
                tvValue2.setText(String.valueOf(bean.getOrder_value()) + " + ?????????" + bean.getGoodsBean().getGood_jifen());
            } else {
                tvValue2.setText(String.valueOf(bean.getOrder_good_value()) + " ?????????" + bean.getGoodsBean().getGood_jifen());
            }
        } else {
            if (bean.getOrder_good_value() != null) {
                tvValue.setText(String.valueOf(bean.getOrder_good_value()));
            }
            tvValue2.setText(String.valueOf(bean.getOrder_value() == null ? bean.getOrder_good_value() : bean.getOrder_value()));
        }
        if (bean.getGoodsBean().getGood_img() != null && !bean.getGoodsBean().getGood_img().equals("")) {
            String[] imgs = bean.getGoodsBean().getGood_img().split(",");
            Glide.with(getApplicationContext()).load(NetConfig.baseurl + imgs[0]).into(ivStroeImg);
        }
    }

    @OnClick({R.id.back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
        }
    }

    /**
     * ??????????????????
     * orderSubmit
     */
    public void orderSubmit(String no) {
        String wulvUrl = NetConfig.getWuLuDetail + "?no=" + no;
        try {
            URL url = new URL(wulvUrl);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE 48fb48ba4d22402f80bb718c4a06be98");// ??????Authorization:APPCODE (?????????????????????)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                System.out.println("??????????????????(??????????????????)");
                System.out.println("???????????????json:");
                System.out.print(json);

                if (JSONObject.parseObject(json).getString("status").equals("0")) {
                    wuLiuBeanList.clear();
                    List<WuLiuBean> wuLiuBeans = JSON.parseArray(JSONObject.parseObject(json).getJSONObject("result").getString("list"), WuLiuBean.class);
                    wuLiuBeanList.addAll(wuLiuBeans);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                    /*wuLiuAdapter.notifyDataSetChanged();*/
                } else {

                }

            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode?????? ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("????????? Method???Path ??????????????????");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("????????????");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("????????????????????????URL???Path????????????");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("????????????????????? ");
                } else {
                    System.out.println("??????????????? ??? ????????????");
                    System.out.println(error);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("URL????????????");
        } catch (UnknownHostException e) {
            System.out.println("URL????????????");
        } catch (Exception e) {
            // ??????????????????????????????????????????
            // e.printStackTrace();
        }
    }

    /*
     * ??????????????????
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
