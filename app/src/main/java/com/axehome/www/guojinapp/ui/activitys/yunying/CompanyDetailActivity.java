package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.CompanyBean;
import com.axehome.www.guojinapp.beans.CourseBean;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.adapters.CourseAdapter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyListView;
import com.bumptech.glide.Glide;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class CompanyDetailActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.iv_img)
    CircleImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.cb_banner)
    Banner cbBanner;
    @BindView(R.id.mlv_list)
    MyListView mlvList;
    @BindView(R.id.sc_view)
    PullToRefreshScrollView scView;
    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    private String company_id = null,tel="";
    private CompanyBean bean;
    /*课程列表*/
    List<CourseBean> courseBeanList = new ArrayList<>();
    private CourseAdapter courseAdapter;
    private MyLoader mMyLoader;
    private List<String> listAdvert = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        company_id = getIntent().getStringExtra("company_id");
        if (company_id != null) {
            getCompanyDetail();
        }
    }

    public void initView() {
        title.setText(bean.getCompany_name());
        String [] strPics = bean.getCompany_imgs().split(",");
        if(strPics!=null && strPics.length>0){
            for (int i = 0; i < strPics.length; i++) {
                listAdvert.add(NetConfig.baseurl + strPics[i]);
            }
            startBanner();
        }
        if (bean.getCourseBeanList() != null) {
            courseBeanList = bean.getCourseBeanList();
        }
        courseAdapter = new CourseAdapter(getApplicationContext(), courseBeanList, "1");
        mlvList.setAdapter(courseAdapter);
        courseAdapter.setListener(new CheckedListenter() {
            @Override
            public void on_Clicked(int position) {
                startActivity(new Intent(getApplicationContext(), CourseDetailActivity.class)
                        .putExtra("bean", JSON.toJSONString(courseBeanList.get(position)))
                        .putExtra("company_name",bean.getCompany_name())
                        .putExtra("company_logo",bean.getCompany_logo()));
            }
        });
        Glide.with(getApplicationContext()).load(NetConfig.baseurl + bean.getCompany_logo()).into(ivImg);
        tvName.setText(bean.getCompany_name());
        tvDetail.setText(bean.getCompany_detail());
        tel = bean.getCompany_tel();

    }

    @OnClick({R.id.back_top,R.id.tv_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_kefu:
                //拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + tel);
                intent.setData(data);
                startActivity(intent);
                break;
        }
    }

    private void getCompanyDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("company_id", company_id);
        OkHttpUtils.post()
                .url(NetConfig.getCompanyDetail)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getCompanyDetail.java:420)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getCompanyDetail.java:71)" + response);
                        if (response == null) {
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            bean = JSON.parseObject(jsonObject.getString("data"), CompanyBean.class);
                            initView();
                        } else {
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
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
        //设置样式，里面有很多种样式可以自己都看看效果
        cbBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        /*//轮播图片的文字
        mConvenBanner.setBannerTitles(listAdvertId);*/
        //设置图片加载器
        cbBanner.setImageLoader(mMyLoader);
        //设置是否为自动轮播，默认是true
        cbBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        cbBanner.setIndicatorGravity(BannerConfig.CENTER);
        cbBanner.setDelayTime(3000);

        /*cbBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });*/
        //网络图片
        cbBanner.setImages(listAdvert)
                .start();
    }
    /**
     * 网络加载图片
     * 使用了Glide图片加载框架
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getApplicationContext())
                    .load((String) path)
                    .into(imageView);
        }
    }
}
