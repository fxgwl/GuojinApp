package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewTxtActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.web_view)
    WebView webView;

    private String htmlText="",name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_txt);
        ButterKnife.bind(this);
        htmlText =getIntent().getStringExtra("htmlText");
        name=getIntent().getStringExtra("name");
        title.setText(name);
        getinto();
    }

    public void getinto(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
//不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
//不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        webView.loadDataWithBaseURL(NetConfig.baseurl,htmlText, "text/html" , "utf-8", null);
    }
    @OnClick(R.id.back_top)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
        }
    }


}
