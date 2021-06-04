package com.axehome.www.guojinapp.ui.activitys;

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
import com.axehome.www.guojinapp.beans.JianDingBean;
import com.axehome.www.guojinapp.ui.adapters.MyJianDingAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MyJianDingActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.mlv_list)
    MyListView mlvList;
    @BindView(R.id.scroll_view)
    PullToRefreshScrollView scrollView;
    @BindView(R.id.rb_class01)
    RadioButton rbClass01;
    @BindView(R.id.rb_class02)
    RadioButton rbClass02;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;

    private List<JianDingBean> jianDingBeanList = new ArrayList<>();
    private MyJianDingAdapter jianDingAdapter;
    private int curPage = 1;
    private String type = "0";//0：待鉴定；1：已鉴定；2：待支付

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jian_ding);
        ButterKnife.bind(this);
        title.setText("我的鉴定");
        initView();
        PullLisition();
        getMyCouponList();
    }

    private void initView() {
        jianDingAdapter = new MyJianDingAdapter(getApplicationContext(), jianDingBeanList);
        mlvList.setAdapter(jianDingAdapter);
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_class01:
                        curPage=1;
                        type="0";
                        getMyCouponList();
                        break;
                    case R.id.rb_class02:
                        curPage=1;
                        type="1";
                        getMyCouponList();
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
                getMyCouponList();

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
                getMyCouponList();
//                mPresenter.getDetails(String.valueOf(pageNo));
            }
        });
    }

    private void getMyCouponList() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("status", type);//0：待鉴定；1：已鉴定；2：待支付
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.getJianDingUrl)
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
                                jianDingBeanList.clear();
                            }

                            List<JianDingBean> jianDingBeans = new ArrayList<>();
                            jianDingBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), JianDingBean.class));
                            jianDingBeanList.addAll(jianDingBeans);

                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        jianDingAdapter.notifyDataSetChanged();
                        scrollView.onRefreshComplete();
                    }
                });
    }
}
