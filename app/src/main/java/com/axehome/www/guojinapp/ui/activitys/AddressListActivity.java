package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.AddressBean;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.AddressAdapter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
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

public class AddressListActivity extends BaseActivity {

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
    @BindView(R.id.b_submit)
    Button bSubmit;

    private int curPage = 1;
    private List<AddressBean> addressList = new ArrayList<>();
    private AddressAdapter addressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("我的收货地址");
        PullLisition();
        addressAdapter = new AddressAdapter(this, addressList, new VipListenter() {
            @Override
            public void edit(int position) {
                startActivityForResult(new Intent(getApplicationContext(),NewAddressActivity.class)
                        .putExtra("addressBean",JSON.toJSONString(addressList.get(position))),11);
            }

            @Override
            public void del(int position) {//选择返回上一级
                setResult(1,new Intent()
                        .putExtra("addressBean",JSON.toJSONString(addressList.get(position))));
                finish();
            }

            @Override
            public void refresh(int position) {
                curPage=1;
                getAddressList();
            }

            @Override
            public void detail(int position) {

            }
        });
        plvList.setAdapter(addressAdapter);

        getAddressList();
    }

    @OnClick({R.id.back_top, R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                startActivityForResult(new Intent(getApplicationContext(),NewAddressActivity.class),11);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11 && resultCode==1){
            curPage = 1;
            getAddressList();
        }
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
                /*if (terminalBeanList != null && terminalBeanList.size() > 0){
                    terminalBeanList.clear();
                }*/
                curPage = 1;
                getAddressList();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                curPage++;
                getAddressList();
//                mPresenter.getSdcgList(pageNo);
            }
        });
    }

    public void getAddressList() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("page", String.valueOf(curPage));
        OkHttpUtils.post()
                .url(NetConfig.getAddressList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(getAddressList.java:168)" + e.getMessage());
                        plvList.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getAddressList.java:175)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            if (curPage == 1) {
                                addressList.clear();
                            }
                            List<AddressBean> addressBeans = new ArrayList<>();
                            addressBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), AddressBean.class));
                            addressList.addAll(addressBeans);
                            addressAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        //terminalAdapter.notifyDataSetChanged();
                        plvList.onRefreshComplete();
                    }
                });
    }
}
