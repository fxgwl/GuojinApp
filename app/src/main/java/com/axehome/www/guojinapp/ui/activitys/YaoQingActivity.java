package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.R;
import com.mob.MobSDK;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

public class YaoQingActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.ll_wechat)
    LinearLayout llWechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yao_qing);
        ButterKnife.bind(this);
        title.setText("我要邀请");
    }

    @OnClick({R.id.back_top,R.id.ll_wechat})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_wechat:
                showShare(Wechat.NAME);
                break;
        }
    }

    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("玖pay app");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://m.jiufulimited.com/register.html?code="+ MyUtils.getUser().getInvitation_code());
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://m.jiufulimited.com/images/logo/logo_pic.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://m.jiufulimited.com/register.html?code="+MyUtils.getUser().getInvitation_code());
        //启动分享
        oks.show(MobSDK.getContext());
    }
}
