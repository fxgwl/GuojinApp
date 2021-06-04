package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.activitys.WebViewTitalActivity;
import com.axehome.www.guojinapp.utils.NetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;
    @BindView(R.id.ll_next)
    LinearLayout llNext;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ButterKnife.bind(this);
        title.setText("我要入驻");
    }

    @OnClick({R.id.back_top, R.id.ll_next,R.id.tv_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_next:
                if (cbXieyi.isChecked()) {
                    startActivity(new Intent(getApplicationContext(), ApplyNextActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "请先阅读并同意相关协议", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_xieyi:
                startActivity(new Intent(getApplicationContext(), WebViewTitalActivity.class)
                        .putExtra("url", NetConfig.baseurl+"haide_xieyi.html")
                        .putExtra("name","相关协议"));
                break;
        }
    }
}
