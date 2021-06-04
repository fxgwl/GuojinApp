package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.axehome.www.guojinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewTitalActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.web_view)
    WebView webView;

    private String url="";
    private String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_tital);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        name=getIntent().getStringExtra("name");
        title.setText(name);
        setWebView();
    }

    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
//        wvClassInfo.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setBlockNetworkImage(false);
        //去掉放大缩小按钮
        webView.getSettings().setDisplayZoomControls(false);

        //wbContent.loadDataWithBaseURL(null, getHtmlData(content), "text/html", "utf-8", null);
        //        if (TextUtils.isEmpty(uid)) {
//            wbContent.loadUrl(NetConfig.WB_BASE_URL + url);
//        } else {
//            Log.e("aaa", "(WebViewActivity.java:64)<---->" + "bbb");
//            wbContent.loadUrl(NetConfig.WB_BASE_URL + url + uid);
//        }

        Log.i("webActivity_fxg",url);
        //设置Web视图
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {

                WebView.HitTestResult hit = view.getHitTestResult();
                //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
                    //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                    Log.e("重定向", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
                    Log.e("重定向", "GetURL: " + view.getUrl() + "\n" +"getOriginalUrl()"+ view.getOriginalUrl());
                    Log.d("重定向", "URL: " + url);
                }

                if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                    view.loadUrl(url);
                    return false; //返回false表示此url默认由系统处理,url未加载完成，会继续往下走

                }else{ //加载的url是自定义协议地址
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

            }
        });
        webView.loadUrl(url);
        /*webView.setWebViewClient(new webViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return super.shouldOverrideUrlLoading(view, url);
                if (url == null) return false;

                try{
                    if(!url.startsWith("http://") && !url.startsWith("https://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }

                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });*/
        //clearWebViewCache();

    }
    @OnClick(R.id.back_top)
    public void onViewClicked() {
        finish();
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.e("aaa", "(webViewClient.java:78)<--拦截到的url-->" + url);

            view.loadUrl(url);

            return true;
        }
    }
}
