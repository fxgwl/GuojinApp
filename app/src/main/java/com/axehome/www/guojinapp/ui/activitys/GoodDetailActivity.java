package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.axehome.www.guojinapp.GuoJinApp.isLogin;

public class GoodDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.wv_html)
    WebView wvHtml;
    @BindView(R.id.tv_link_kf)
    TextView tvLinkKf;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_jifen_value)
    TextView tvJifenValue;

    private PreGoodsBean goodsBean;
    private MyLoader mMyLoader;
    private List<String> listAdvert = new ArrayList<>();
    private String sys_tel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        title.setText("物品详情");
        String goodJson = getIntent().getStringExtra("good");
        if (goodJson != null) {
            goodsBean = JSONObject.parseObject(goodJson, PreGoodsBean.class);
            initView();
            if (goodsBean.getGood_img() != null) {
                String[] strImgs = goodsBean.getGood_img().split(",");
                for (String i : strImgs) {
                    listAdvert.add(NetConfig.baseurl + i);
                }
            }
            startBanner();
        }
        SysInfo();
    }

    private void initView() {
        tvGoodName.setText(goodsBean.getGood_name());
        tvValue.setText(String.valueOf(goodsBean.getGood_value()));
        //setWebView();
        WebSettings webSettings = wvHtml.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvHtml.loadDataWithBaseURL(NetConfig.baseurl, goodsBean.getGood_detail(), "text/html", "utf-8", null);
        if (goodsBean.getGood_type().equals("2")) {
            tvJifenValue.setVisibility(View.VISIBLE);
            tvJifenValue.setText("所需积分："+goodsBean.getGood_jifen());
        }
    }

    private void setWebView() {
        WebSettings settings = wvHtml.getSettings();
// 设置WebView支持JavaScript
        settings.setJavaScriptEnabled(true);
//支持自动适配
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);  //支持放大缩小
        settings.setBuiltInZoomControls(true); //显示缩放按钮
        settings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        settings.setAllowFileAccess(false);
        settings.setSaveFormData(false);
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//设置不让其跳转浏览器
        wvHtml.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        wvHtml.setWebChromeClient(new WebChromeClient());
        wvHtml.loadDataWithBaseURL(NetConfig.baseurl, goodsBean.getGood_detail(), "text/html", "utf-8", null);
        //wvHtml.loadUrl(URL);
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

    @OnClick({R.id.back_top, R.id.b_submit, R.id.tv_link_kf,R.id.ll_service_info,R.id.ll_buy_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                if (isLogin()) {
                    startActivity(new Intent(getApplicationContext(), OrderSubmitActivity.class)
                            .putExtra("good", JSON.toJSONString(goodsBean)));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_link_kf:
                //拨打电话
                if (sys_tel != null && !sys_tel.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + sys_tel);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.ll_service_info:
                startActivity(new Intent(getApplicationContext(), WebViewTitalActivity.class)
                        .putExtra("url", NetConfig.baseurl+"haide_service_info.html")
                        .putExtra("name","服务声明"));
                break;
            case R.id.ll_buy_info:
                startActivity(new Intent(getApplicationContext(), WebViewTitalActivity.class)
                        .putExtra("url", NetConfig.baseurl+"haide_buy_info.html")
                        .putExtra("name","购物说明"));
                break;
        }
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

    private void SysInfo() {

        OkHttpUtils.post()
                .url(NetConfig.getSysinfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            int code = jsonObject.getInteger("code");
                            if (code == 0) {
                                sys_tel = jsonObject.getJSONObject("data").getString("sys_phone");
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
