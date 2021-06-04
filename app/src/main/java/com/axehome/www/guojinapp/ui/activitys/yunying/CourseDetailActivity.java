package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.CourseBean;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.wv_html)
    WebView wvHtml;

    private CourseBean bean;
    private MyLoader mMyLoader;
    private List<String> listAdvert = new ArrayList<>();
    private String company_name="",company_logo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);
        String beanJson = getIntent().getStringExtra("bean");
        company_name = getIntent().getStringExtra("company_name");
        company_logo = getIntent().getStringExtra("company_logo");
        if (beanJson != null) {
            bean = JSONObject.parseObject(beanJson, CourseBean.class);
            initView();
        }
    }

    private void initView() {
        title.setText(bean.getCourse_name());
        tvDetail.setText(bean.getCourse_detail());

        tvName.setText(bean.getCourse_name());
        tvValue.setText("￥" + bean.getCourse_value());
        String[] strPics = bean.getCourse_imgs().split(",");
        if (strPics != null && strPics.length > 0) {
            for (int i = 0; i < strPics.length; i++) {
                listAdvert.add(NetConfig.baseurl + strPics[i]);
            }
            startBanner();
        }
        WebSettings webSettings = wvHtml.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvHtml.loadDataWithBaseURL(NetConfig.baseurl, bean.getCourse_list(), "text/html", "utf-8", null);

    }

    @OnClick({R.id.back_top, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.bt_submit:
                if(bean!=null){
                    startActivity(new Intent(getApplicationContext(), CoursePayActivity.class)
                            .putExtra("bean", JSON.toJSONString(bean))
                            .putExtra("company_name",company_name)
                            .putExtra("company_logo",company_logo));
                    finish();
                }
                break;
        }
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
