package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.RecommentLogBean;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyRadioGroup;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.adapters.RecommentLogAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RecommentLogActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.iv_ti_xian)
    ImageView ivTiXian;
    @BindView(R.id.rb_liushui)
    RadioButton rbLiushui;
    @BindView(R.id.rb_zhi_tui_cha)
    RadioButton rbZhiTuiCha;
    @BindView(R.id.rb_zhi_tui_jiangjin)
    RadioButton rbZhiTuiJiangjin;
    @BindView(R.id.rb_pei_xun)
    RadioButton rbPeiXun;
    @BindView(R.id.rb_fen_run)
    RadioButton rbFenRun;
    @BindView(R.id.rb_other)
    RadioButton rbOther;
    @BindView(R.id.mrg_list)
    MyRadioGroup mrgList;
    @BindView(R.id.plv_list)
    PullToRefreshListView plvList;

    private Date curDate;
    private String type="ls";
    private int curPage = 1;
    private List<RecommentLogBean> recommentLogBeanList = new ArrayList<>();
    private RecommentLogAdapter recommentLogAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomment_log);
        ButterKnife.bind(this);
        curDate = new Date();
        iniView();
        PullLisition();
    }

    private void iniView() {

        mrgList.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_liushui://个人流水分润
                        curPage=1;
                        type="ls";
                        break;
                    case R.id.rb_zhi_tui_cha://直推分润差
                        curPage=1;
                        type="ls";
                        break;
                    case R.id.rb_zhi_tui_jiangjin://直推奖金
                        curPage=1;
                        type="zt";
                        break;
                    case R.id.rb_pei_xun://培训津贴
                        curPage=1;
                        type="zd";
                        break;
                    case R.id.rb_fen_run://分润津贴
                        curPage=1;
                        type="jt";
                        break;
                    case R.id.rb_other://
                        curPage=1;
                        type="ls";
                        break;
                }
                getRecommentList();
            }
        });
        recommentLogAdapter=new RecommentLogAdapter(getApplicationContext(),recommentLogBeanList);
        plvList.setAdapter(recommentLogAdapter);
        getRecommentList();
    }
    @OnClick({R.id.back_top,R.id.iv_ti_xian})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.iv_ti_xian:
                if(curDate.getDay()==10){
                    startActivityForResult(new Intent(getApplicationContext(),TiXianBalanceActivity.class),11);
                }else{
                    Toast.makeText(getApplicationContext(),"每月10号为，申请提现日",Toast.LENGTH_SHORT).show();
                }
                break;
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
                if (recommentLogBeanList != null && recommentLogBeanList.size() > 0) recommentLogBeanList.clear();
                curPage = 1;
                getRecommentList();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                curPage++;
                getRecommentList();
//                mPresenter.getSdcgList(pageNo);
            }
        });
    }

    private void getRecommentList() {
        Map<String,String> map = new HashMap<>();
        map.put("page",String.valueOf(curPage));
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("recommend_type",type);
        OkHttpUtils.post()
                .url(NetConfig.getRecommentLogList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:136)" + e.getMessage());
                        plvList.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:142)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            if(curPage==1 &&recommentLogBeanList!=null &&recommentLogBeanList.size()>0){
                                recommentLogBeanList.clear();
                            }
                            List<RecommentLogBean> list = JSON.parseArray(jsonObject.getJSONObject("data").getString("list"),RecommentLogBean.class);
                            recommentLogBeanList.addAll(list);
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        }
                        recommentLogAdapter.notifyDataSetChanged();
                        plvList.onRefreshComplete();
                    }
                });
    }
}
