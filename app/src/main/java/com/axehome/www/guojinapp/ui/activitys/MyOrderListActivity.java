package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.OrderBean;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.OrderAdapter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MyOrderListActivity extends BaseActivity {


    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.rb_class01)
    RadioButton rbClass01;
    @BindView(R.id.rb_class02)
    RadioButton rbClass02;
    @BindView(R.id.rb_class03)
    RadioButton rbClass03;
    @BindView(R.id.rb_class04)
    RadioButton rbClass04;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.mlv_list)
    MyListView mlvList;
    @BindView(R.id.scroll_view)
    PullToRefreshScrollView scrollView;

    private List<OrderBean> orderBeanList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private int curPage = 1;
    private String order_status = "0";//0:待付款；1：待发货；2：待收货；3：已完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ButterKnife.bind(this);
        title.setText("我的订单");
        order_status=getIntent().getStringExtra("type");
        initView();
        switch (order_status){
            case "0":
                getMyOrderList();
                break;
            case "1":
                rbClass02.setChecked(true);
                break;
            case "2":
                rbClass03.setChecked(true);
                break;
            case "3":
                rbClass04.setChecked(true);
                break;
        }
        PullLisition();
    }

    private void initView() {
        orderAdapter = new OrderAdapter(getApplicationContext(), orderBeanList, new VipListenter() {
            @Override
            public void edit(int position) {
//                收货
                getUpdateOrder(position);
            }

            @Override
            public void del(int position) {

            }

            @Override
            public void refresh(int position) {//去支付
                startActivityForResult(new Intent(getApplicationContext(),PayWayNextActivity.class)
                        .putExtra("order_id",String.valueOf(orderBeanList.get(position).getOrder_id())),11);
            }

            @Override
            public void detail(int position) {//详情
                startActivity(new Intent(getApplicationContext(), OrderDetailActivity.class)
                        .putExtra("bean", JSON.toJSONString(orderBeanList.get(position))));

            }
        });
        mlvList.setAdapter(orderAdapter);
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_class01:
                        curPage=1;
                        order_status="0";
                        getMyOrderList();
                        break;
                    case R.id.rb_class02:
                        curPage=1;
                        order_status="1";
                        getMyOrderList();
                        break;
                    case R.id.rb_class03:
                        curPage=1;
                        order_status="2";
                        getMyOrderList();
                        break;
                    case R.id.rb_class04:
                        curPage=1;
                        order_status="3";
                        getMyOrderList();
                        break;
                }
            }
        });
    }

    @OnClick({R.id.back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
        }
    }

    public void PullLisition() {
        // 1.设置刷新模式
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        // 上拉加载更多，分页加载
        scrollView.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        scrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        scrollView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        scrollView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        scrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        scrollView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                curPage = 1;
                getMyOrderList();

//                mPresenter.getDetails(String.valueOf(pageNo));

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                // 自定义上拉header内容
                scrollView.getLoadingLayoutProxy(false, true)
                        .setPullLabel("上拉加载...");
                scrollView.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("正在为你加载更多内容...");
                scrollView.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("松开自动加载...");

                curPage++;
                getMyOrderList();
//                mPresenter.getDetails(String.valueOf(pageNo));
            }
        });
    }

    private void getMyOrderList() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("order_status", order_status);//0：待鉴定；1：已鉴定；2：待支付
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.getOrderListUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getMyCouponList.java:420)" + e.getMessage());
                        scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getMyCouponList.java:71)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            scrollView.onRefreshComplete();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            if (curPage == 1) {
                                orderBeanList.clear();
                            }

                            List<OrderBean> orderBeans = new ArrayList<>();
                            orderBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), OrderBean.class));
                            orderBeanList.addAll(orderBeans);

                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        orderAdapter.notifyDataSetChanged();
                        scrollView.onRefreshComplete();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 1) {
            curPage=1;
            getMyOrderList();
        }
    }

    private void getUpdateOrder(int position) {
        Map<String, String> map = new HashMap<>();
        map.put("order_id", String.valueOf(orderBeanList.get(position).getOrder_id()));
        map.put("order_status", "3");//0：待支付；1：待发货；2：待收货;3已完成
        OkHttpUtils.post()
                .url(NetConfig.OrderUpdateUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getMyCouponList.java:420)" + e.getMessage());
                        Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                        //scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getMyCouponList.java:71)" + response);
                        if (response == null) {
                            Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            //scrollView.onRefreshComplete();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            curPage = 1;
                            getMyOrderList();
                            /*orderBeanList.get(position).setOrder_status("3");
                            orderAdapter.notifyDataSetChanged();*/
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
