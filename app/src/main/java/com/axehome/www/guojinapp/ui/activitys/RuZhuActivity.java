package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RuZhuActivity extends AppCompatActivity {

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
    @BindView(R.id.cb_agree)
    CheckBox cbAgree;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.ll_ru_zhu)
    LinearLayout llRuZhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_zhu);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_top, R.id.tv_xieyi, R.id.ll_ru_zhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_xieyi:
                break;
            case R.id.ll_ru_zhu:
                break;
        }
    }
}
