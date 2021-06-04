package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.axehome.www.guojinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        setWebView();
    }
    @OnClick({})
    public void onViewClicked(View view){

    }
    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
//        wvClassInfo.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setBlockNetworkImage(false);
        //去掉放大缩小按钮
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);
        //wbContent.loadDataWithBaseURL(null, getHtmlData(content), "text/html", "utf-8", null);
        //        if (TextUtils.isEmpty(uid)) {
//            wbContent.loadUrl(NetConfig.WB_BASE_URL + url);
//        } else {
//            Log.e("aaa", "(WebViewActivity.java:64)<---->" + "bbb");
//            wbContent.loadUrl(NetConfig.WB_BASE_URL + url + uid);
//        }

        Log.i("webActivity_fxg",url);
        //设置Web视图
        webView.setWebViewClient(new webViewClient(){
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
        });
        //clearWebViewCache();

    }
   /* @OnClick(R.id.back_top)
    public void onViewClicked() {
        finish();
    }*/

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.e("aaa", "(webViewClient.java:78)<--拦截到的url-->" + url);

            view.loadUrl(url);

            return true;
        }
    }
}
