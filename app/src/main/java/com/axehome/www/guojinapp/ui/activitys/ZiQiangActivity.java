package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.BannerBean;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.GoodAdapter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
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

public class ZiQiangActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.cb_banner)
    Banner cbBanner;
    @BindView(R.id.prl_list)
    PullToRefreshScrollView scrollView;
    @BindView(R.id.mgv_list)
    MyGridView mgvList;

    private List<BannerBean> listBanner = new ArrayList<BannerBean>();
    private MyLoader mMyLoader;
    private List<String> listAdvert = new ArrayList<>();
    private GoodAdapter goodAdapter;
    private List<PreGoodsBean> preGoodsBeanList = new ArrayList<>();

    private int curPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_qiang);
        ButterKnife.bind(this);
        initView();
        getGoodList();
        GetBanner();
    }

    private void initView() {
        title.setText("????????????");

        goodAdapter = new GoodAdapter(this, preGoodsBeanList, new VipListenter() {
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
        mgvList.setAdapter(goodAdapter);
        mgvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //???????????????
                startActivity(new Intent(getApplicationContext(), GoodDetailActivity.class)
                        .putExtra("good", JSON.toJSONString(preGoodsBeanList.get(position))));
            }
        });
        PullLisition();
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
        // 1.??????????????????
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        // ?????????????????????????????????
        scrollView.getLoadingLayoutProxy(false, true).setPullLabel("????????????");
        scrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel("?????????...");
        scrollView.getLoadingLayoutProxy(false, true).setReleaseLabel("????????????");
        // ????????????
        scrollView.getLoadingLayoutProxy(true, false).setPullLabel("????????????");
        scrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel("?????????...");
        scrollView.getLoadingLayoutProxy(true, false).setReleaseLabel("????????????");

        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                String label = DateUtils.formatDateTime(
                        getApplicationContext().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                curPage = 1;
                getGoodList();

//                mPresenter.getDetails(String.valueOf(pageNo));

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                // ???????????????header??????
                scrollView.getLoadingLayoutProxy(false, true)
                        .setPullLabel("????????????...");
                scrollView.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("??????????????????????????????...");
                scrollView.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("??????????????????...");

                curPage++;
                getGoodList();
//                mPresenter.getDetails(String.valueOf(pageNo));
            }
        });
    }

    private void startBanner() {

        /*cbBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        });*/
        cbBanner.setClipToOutline(true);
        mMyLoader = new MyLoader();
        //??????????????????????????????????????????????????????????????????
        cbBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        /*//?????????????????????
        mConvenBanner.setBannerTitles(listAdvertId);*/
        //?????????????????????
        cbBanner.setImageLoader(mMyLoader);
        //???????????????????????????????????????true
        cbBanner.isAutoPlay(true);
        //???????????????????????????????????????????????????
        cbBanner.setIndicatorGravity(BannerConfig.CENTER);
        cbBanner.setDelayTime(3000);

        /*cbBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });*/
        //????????????
        cbBanner.setImages(listAdvert)
                .start();
    }

    /**
     * ??????????????????
     * ?????????Glide??????????????????
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getApplicationContext())
                    .load((String) path)
                    .into(imageView);
        }
    }

    private void getGoodList() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("good_type", "6");
        OkHttpUtils.post()
                .url(NetConfig.getGoodsList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                        scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"????????????",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            if (curPage == 1 && preGoodsBeanList.size() > 0) {
                                preGoodsBeanList.clear();
                            }
                            List<PreGoodsBean> preGoodsBeans = new ArrayList<>();
                            preGoodsBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), PreGoodsBean.class));
                            preGoodsBeanList.addAll(preGoodsBeans);
                            goodAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        scrollView.onRefreshComplete();
                    }
                });
    }

    public void GetBanner() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("limit", "5");
        map.put("banner_type", "3");
        OkHttpUtils.post()
                .url(NetConfig.getBaseurl)
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
                            //Toast.makeText(getApplicationContext(),"????????????",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            listBanner.clear();
                            listBanner.addAll(JSON.parseArray(jsonObject.getString("data"), BannerBean.class));
                            if (listBanner != null && listBanner.size() > 0) {
                                listAdvert.clear();
                                for (int i = 0; i < listBanner.size(); i++) {
                                    listAdvert.add(NetConfig.baseurl + listBanner.get(i).getBanner_path());
                                }
                                startBanner();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
