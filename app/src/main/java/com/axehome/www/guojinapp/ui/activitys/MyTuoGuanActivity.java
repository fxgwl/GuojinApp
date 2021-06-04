package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.TuoGuanBean;
import com.axehome.www.guojinapp.ui.adapters.TuoGuanAdapter;
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

public class MyTuoGuanActivity extends BaseActivity {

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

    private List<TuoGuanBean> tuoGuanBeanList = new ArrayList<>();
    private TuoGuanAdapter tuoGuanAdapter;
    private int curPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tuo_guan);
        ButterKnife.bind(this);
        title.setText("我的托管&寄售");
        initView();
        PullLisition();
        getMyCouponList();
    }
    private void initView() {
        tuoGuanAdapter = new TuoGuanAdapter(getApplicationContext(),tuoGuanBeanList);
        mlvList.setAdapter(tuoGuanAdapter);
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

    private void getMyCouponList(){
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.getMyTuoGuanList)
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
                                tuoGuanBeanList.clear();
                            }

                            List<TuoGuanBean> courseBeans = new ArrayList<>();
                            courseBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), TuoGuanBean.class));
                            tuoGuanBeanList.addAll(courseBeans);

                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        tuoGuanAdapter.notifyDataSetChanged();
                        scrollView.onRefreshComplete();
                    }
                });
    }

}
