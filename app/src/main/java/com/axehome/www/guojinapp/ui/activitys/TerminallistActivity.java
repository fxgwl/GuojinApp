package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.TerminalBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.TerminalAdapter;
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

public class TerminallistActivity extends BaseActivity {

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
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    private int curPage = 1;
    private List<TerminalBean> terminalBeanList = new ArrayList<>();
    private TerminalAdapter terminalAdapter;
    private String merchant_no = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminallist);
        ButterKnife.bind(this);
        merchant_no = getIntent().getStringExtra("merchant_no");
        title.setText("终端管理");
        initView();
        initData();
    }

    private void initView() {
        PullLisition();
        terminalAdapter = new TerminalAdapter(getApplicationContext(), terminalBeanList, new VipListenter() {
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
        plvList.setAdapter(terminalAdapter);

    }

    private void initData() {
        getTerminalList();
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
                if (terminalBeanList != null && terminalBeanList.size() > 0)
                    terminalBeanList.clear();
                curPage = 1;
                getTerminalList();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                curPage++;
                getTerminalList();
//                mPresenter.getSdcgList(pageNo);
            }
        });
    }

    @OnClick({R.id.back_top, R.id.tv_search,R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_search:
                curPage=1;
                getTerminalList();
                break;
            case R.id.b_submit:
                startActivityForResult(new Intent(getApplicationContext(),AddTerminalActivity.class)
                        .putExtra("merchant_no",merchant_no),11);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 11) {
            curPage = 1;
            initData();
        }
    }

    public void getTerminalList() {
        if (merchant_no == null || merchant_no.equals("")) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("merchant_no", merchant_no);
        if (!etSearch.getText().toString().trim().equals("")) {
            map.put("terminal_name", etSearch.getText().toString().trim());
        }
        OkHttpUtils.post()
                .url(NetConfig.getTerminalListByStore)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(terminalList.java:168)" + e.getMessage());
                        plvList.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(terminalList.java:175)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            if (curPage == 1) {
                                terminalBeanList.clear();
                            }
                            List<TerminalBean> terminalBeans = new ArrayList<>();
                            terminalBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), TerminalBean.class));
                            terminalBeanList.addAll(terminalBeans);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        terminalAdapter.notifyDataSetChanged();
                        plvList.onRefreshComplete();
                    }
                });
    }
}
