package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TiXianMoneyBagActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.iv_qian_bao)
    ImageView ivQianBao;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.iv_tui_jian)
    ImageView ivTuiJian;
    @BindView(R.id.tv_tixian_able)
    TextView tvTixianAble;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    @BindView(R.id.et_value)
    EditText etValue;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;
    @BindView(R.id.b_submit)
    Button bSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xian_money_bag);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("基金提现");
    }

    @OnClick({R.id.back_top,R.id.b_submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:

                break;
        }
    }
}
