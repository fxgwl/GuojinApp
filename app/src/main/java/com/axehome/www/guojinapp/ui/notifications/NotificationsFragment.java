package com.axehome.www.guojinapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.ui.activitys.AddressListActivity;
import com.axehome.www.guojinapp.ui.activitys.JiFenServiceActivity;
import com.axehome.www.guojinapp.ui.activitys.KefuActivity;
import com.axehome.www.guojinapp.ui.activitys.MyCouponActivity;
import com.axehome.www.guojinapp.ui.activitys.MyDetailActivity;
import com.axehome.www.guojinapp.ui.activitys.MyJianDingActivity;
import com.axehome.www.guojinapp.ui.activitys.MyOrderListActivity;
import com.axehome.www.guojinapp.ui.activitys.MyPeiXunActivity;
import com.axehome.www.guojinapp.ui.activitys.MyTuoGuanActivity;
import com.axehome.www.guojinapp.ui.activitys.WebViewTitalActivity;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.bumptech.glide.Glide;
import com.mob.MobSDK;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/*个人中心*/
public class NotificationsFragment extends Fragment {


    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_nike_name)
    TextView tvNikeName;
    @BindView(R.id.iv_gomydetail)
    ImageView ivGomydetail;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_daizhifu)
    LinearLayout llDaizhifu;
    @BindView(R.id.ll_daifahuo)
    LinearLayout llDaifahuo;
    @BindView(R.id.ll_daishouhuo)
    LinearLayout llDaishouhuo;
    @BindView(R.id.ll_complete)
    LinearLayout llComplete;
    @BindView(R.id.tv_fankui)
    TextView tvFankui;
    @BindView(R.id.tv_jd_complete)
    TextView tvJdComplete;
    @BindView(R.id.ll_tuo_guan)
    LinearLayout llTuoGuan;
    @BindView(R.id.ll_pei_xun)
    LinearLayout llPeiXun;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.pb_gongyi)
    ProgressBar pbGongyi;
    @BindView(R.id.tv_jifen_value)
    TextView tvJifenValue;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_my_jianding)
    LinearLayout llMyJianding;
    @BindView(R.id.tv_leve)
    TextView tvLeve;
    @BindView(R.id.tv_leve_star)
    TextView tvLeveStar;
    @BindView(R.id.tv_leve_end)
    TextView tvLeveEnd;
    @BindView(R.id.iv_sun)
    ImageView ivSun;
    @BindView(R.id.ll_sun)
    LinearLayout llSun;
    @BindView(R.id.ll_jifen)
    LinearLayout llJifen;
    @BindView(R.id.tv_leve_txt)
    TextView tvLeveTxt;
    private NotificationsViewModel notificationsViewModel;
    private Unbinder unbinder;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User return_user) {
                user = return_user;
                initView();
            }
        });
        getUserDetail();
        unbinder = ButterKnife.bind(this, root);
        //initView();
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void initView() {
        if (user != null) {
            Glide.with(getContext()).load(NetConfig.baseurl + user.getHead_img()).into(civHead);
            tvNikeName.setText(user.getUsername());
            tvJifenValue.setText("积分：" + user.getUser_jifen());
            int progress = new Double(user.getJiangli_value()).intValue();

            getLeveValue(progress);//计算等级
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            getUserDetail();
        }
    }

    private void getUserDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.userDetail)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Log.e("user_detail", "网络故障");
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    String data = jsonObject.getString("data");
                                    SPUtils.put("user", data);
                                    user = MyUtils.getUser();
                                    initView();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Log.e("user_detail", "故障" + msg);
                                    //Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.ll_kefu, R.id.ll_coupon, R.id.ll_address, R.id.ll_pei_xun, R.id.ll_share
            , R.id.ll_name, R.id.ll_my_jianding, R.id.ll_tuo_guan, R.id.ll_daizhifu,
            R.id.ll_daifahuo, R.id.ll_daishouhuo, R.id.ll_complete, R.id.tv_more
            , R.id.ll_jifen,R.id.iv_gongyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_kefu:
                startActivity(new Intent(getContext(), KefuActivity.class));
                break;
            case R.id.ll_coupon:
                startActivity(new Intent(getContext(), MyCouponActivity.class));
                break;
            case R.id.ll_address:
                startActivity(new Intent(getContext(), AddressListActivity.class));
                break;
            case R.id.ll_pei_xun:
                startActivity(new Intent(getContext(), MyPeiXunActivity.class));
                break;
            case R.id.ll_share:
                showShare(null);
                break;
            case R.id.ll_name:
                startActivityForResult(new Intent(getContext(), MyDetailActivity.class), 11);
                break;
            case R.id.ll_my_jianding:
                startActivity(new Intent(getContext(), MyJianDingActivity.class));
                break;
            case R.id.ll_tuo_guan:
                startActivity(new Intent(getContext(), MyTuoGuanActivity.class));
                break;
            case R.id.ll_daizhifu:
                startActivity(new Intent(getContext(), MyOrderListActivity.class)
                        .putExtra("type", "0"));
                break;
            case R.id.ll_daifahuo:
                startActivity(new Intent(getContext(), MyOrderListActivity.class)
                        .putExtra("type", "1"));
                break;
            case R.id.ll_daishouhuo:
                startActivity(new Intent(getContext(), MyOrderListActivity.class)
                        .putExtra("type", "2"));
                break;
            case R.id.ll_complete:
                startActivity(new Intent(getContext(), MyOrderListActivity.class)
                        .putExtra("type", "3"));
                break;
            case R.id.tv_more:
                startActivity(new Intent(getContext(), MyOrderListActivity.class)
                        .putExtra("type", "0"));
                break;
            case R.id.ll_jifen:
                startActivityForResult(new Intent(getContext(), JiFenServiceActivity.class), 11);
                break;
            case R.id.iv_gongyi:
                startActivity(new Intent(getContext(), WebViewTitalActivity.class)
                        .putExtra("url", NetConfig.baseurl+"jifen_xieyi.html")
                        .putExtra("name","相关协议"));
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
        oks.setTitle("海得 app");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(NetConfig.baseurl+"register.html?code=" + MyUtils.getUser().getInvitation_code());
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(NetConfig.baseurl+"upload/logo.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(NetConfig.baseurl+"register.html?code=" + MyUtils.getUser().getInvitation_code());
        //启动分享
        oks.show(MobSDK.getContext());
    }

    public void getLeveValue(int value) {
        if (value>=0) {
            int lever = value / 1000;
            tvLeve.setText("LV." + lever);
            tvLeveStar.setText("LV" + lever);
            tvLeveEnd.setText("LV" + (lever + 1));
            int chaInt=1000-(value-1000*lever);

            pbGongyi.setProgress(value-1000*lever);
            tvLeveTxt.setText("还差"+chaInt+"公益值升级为 LV."+(lever+1)+" 等级");
        }

    }

    public void putTag() {
        String[] strTag = null;
        llSun.removeAllViews();
        /*if (user.getShopTag() != null && !user.getShopTag().equals("")) {
            strTag = user.getShopTag().split(",");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < strTag.length; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setBackgroundResource(R.drawable.my_sunny);
                layoutParams.setMargins(0, 0, 10, 0);
                *//*textView.setGravity(Gravity.CENTER);
                textView.setTextColor(getResources().getColor(R.color.blue));
                textView.setPadding(10, 6, 10, 6);
                textView.setTextSize(9);
                textView.setText(strTag[i]);*//*
                imageView.setLayoutParams(layoutParams);
                llSun.addView(imageView);
            }

        }*/
    }
}