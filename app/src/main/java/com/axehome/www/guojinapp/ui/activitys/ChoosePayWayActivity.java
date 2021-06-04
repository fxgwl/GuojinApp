package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoosePayWayActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.rb_ali_pay)
    RadioButton rbAliPay;
    @BindView(R.id.rb_ali_huabei)
    RadioButton rbAliHuabei;
    @BindView(R.id.rb_chat_pay)
    RadioButton rbChatPay;
    @BindView(R.id.rb_huabei_pay)
    RadioButton rbHuabeiPay;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;

    private String value = "", roleOrderId = "", pay_way = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pay_way);
        ButterKnife.bind(this);
        title.setText("选择支付方式");
        value = getIntent().getStringExtra("value");
        roleOrderId = getIntent().getStringExtra("roleOrderId");
        initView();
        initData();
    }

    private void initView() {
        if (value != null) {
            bSubmit.setText("提交 " + value + " 元");
        }
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_ali_pay:
                        pay_way="1";
                        break;
                    case R.id.rb_ali_huabei:
                        pay_way="2";
                        break;
                    case R.id.rb_chat_pay:
                        pay_way="3";
                        break;
                    case R.id.rb_huabei_pay:
                        pay_way="4";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.back_top, R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                Submit();
                break;
        }
    }

    private void Submit() {
        startActivity(new Intent(getApplicationContext(),PayWayNextActivity.class).putExtra("pay_way",pay_way)
                .putExtra("roleOrderId",roleOrderId)
                .putExtra("value",value));
    }

    private void initData() {

    }

}
