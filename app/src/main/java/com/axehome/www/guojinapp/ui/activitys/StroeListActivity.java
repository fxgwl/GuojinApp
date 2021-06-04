package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.StoreBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.StroeAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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

public class StroeListActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.plv_list)
    PullToRefreshListView plvList;

    private int curPage=1;
    private List<StoreBean> storeBeanList = new ArrayList<>();
    private StroeAdapter stroeAdapter;
    private String merchant_no="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroe_list);
        ButterKnife.bind(this);
        merchant_no = getIntent().getStringExtra("merchant_no");
        title.setText("门店管理");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("新建门店");
        initView();
        initData();
    }

    private void initView() {
        PullLisition();
        stroeAdapter=new StroeAdapter(getApplicationContext(), storeBeanList, new VipListenter() {
            @Override
            public void edit(int position) {

            }

            @Override
            public void del(int position) {

            }

            @Override
            public void refresh(int position) {

            }

            @Override
            public void detail(int position) {

            }
        });
        plvList.setAdapter(stroeAdapter);

    }

    private void initData() {
        getStoreForShop();
    }
    public void PullLisition() {
        plvList.setMode(PullToRefreshBase.Mode.BOTH);
// 上拉加载更多，分页加载
        plvList.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        plvList.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        plvList.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        plvList.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        plvList.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        plvList.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        plvList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                if (storeBeanList != null && storeBeanList.size() > 0) storeBeanList.clear();
                curPage = 1;
                getStoreForShop();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                curPage++;
                getStoreForShop();
//                mPresenter.getSdcgList(pageNo);
            }
        });
    }

    @OnClick({R.id.back_top,R.id.tv_right})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_right:
                startActivityForResult(new Intent(getApplicationContext(),AddStroeActivity.class)
                        .putExtra("merchant_no",merchant_no),11);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && resultCode==11){
            curPage=1;
            initData();
        }
    }

    public void getStoreForShop() {
        if(merchant_no==null||merchant_no.equals("")){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("merchant_no", merchant_no);
        OkHttpUtils.post()
                .url(NetConfig.getStoreForShop)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SettingsFragment.java:105)" + e.getMessage());
                        plvList.onRefreshComplete();
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
                            if (curPage == 1) {
                                storeBeanList.clear();
                            }
                            List<StoreBean> storeBeans = new ArrayList<>();
                            storeBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), StoreBean.class));
                            storeBeanList.addAll(storeBeans);
                        } else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        }
                        stroeAdapter.notifyDataSetChanged();
                        plvList.onRefreshComplete();
                    }
                });
    }
}
