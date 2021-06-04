package com.axehome.www.guojinapp.ui.myshopcentre;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.ui.notifications.NotificationsViewModel;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.activitys.KefuActivity;
import com.axehome.www.guojinapp.ui.activitys.MyDetailActivity;
import com.axehome.www.guojinapp.ui.activitys.PeiSongActivity;
import com.axehome.www.guojinapp.ui.activitys.RecommentLogActivity;
import com.axehome.www.guojinapp.ui.activitys.TiXianMoneyBagActivity;
import com.axehome.www.guojinapp.ui.activitys.UpgrapUserActivity;
import com.axehome.www.guojinapp.ui.activitys.WebViewTitalActivity;
import com.bumptech.glide.Glide;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/*个人中心*/
public class MyshopCentreFragment extends Fragment {

    @BindView(R.id.iv_setup)
    ImageView ivSetup;
    @BindView(R.id.iv_peisong)
    ImageView ivPeisong;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_nike_name)
    TextView tvNikeName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_user_id)
    LinearLayout llUserId;
    @BindView(R.id.tv_user_value)
    TextView tvUserValue;
    @BindView(R.id.tv_tuijian)
    TextView tvTuijian;
    @BindView(R.id.ll_xinyong)
    LinearLayout llXinyong;
    @BindView(R.id.iv_gomydetail)
    ImageView ivGomydetail;
    @BindView(R.id.iv_qian_bao)
    ImageView ivQianBao;
    @BindView(R.id.iv_tui_jian)
    ImageView ivTuiJian;
    @BindView(R.id.ll_update_vip)
    LinearLayout llUpdateVip;
    @BindView(R.id.ll_ll1)
    LinearLayout llLl1;
    @BindView(R.id.ll_daizhifu)
    LinearLayout llDaizhifu;
    @BindView(R.id.ll_daishouhuo)
    LinearLayout llDaishouhuo;
    @BindView(R.id.ll_wanchen)
    LinearLayout llWanchen;
    @BindView(R.id.ll_huanhuo)
    LinearLayout llHuanhuo;
    @BindView(R.id.ll_add_user)
    LinearLayout llAddUser;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    @BindView(R.id.ll_coupon)
    LinearLayout llCoupon;
    @BindView(R.id.ll_report)
    LinearLayout llReport;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_tuijian_value)
    TextView tvTuijianValue;
    @BindView(R.id.tv_dai_biao)
    TextView tvDaiBiao;
    @BindView(R.id.ll_leve)
    LinearLayout llLeve;
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    @BindView(R.id.tv_mno)
    TextView tvMno;
    private NotificationsViewModel notificationsViewModel;
    private Unbinder unbinder;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myshopcentre, container, false);
        notificationsViewModel.getText().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User return_user) {
                user = return_user;
                initView();
            }
        });
        //getUserDetail();
        unbinder = ButterKnife.bind(this, root);
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
        Glide.with(getContext()).load(NetConfig.baseurl + user.getHead_img()).into(civHead);
        tvNikeName.setText(user.getUsername());
        tvUserValue.setText(user.getRegister_time());
        /*if (user.getpUser() != null) {
            String name = user.getpUser().getUsername();
            if (user.getpUser().getRealname() != null && !user.getpUser().getRealname().equals("")) {
                tvTuijian.setText(user.getpUser().getRealname());
            } else {
                tvTuijian.setText(user.getpUser().getUsername());
            }
        }*/
        if (user.getRoleBean() != null) {
            switch (user.getRoleBean().getRole_name()) {
                case "总代理":
                    tvRole.setText("城市服务商");
                    break;
                case "高级代理":
                    tvRole.setText("高级服务商");
                    break;
                case "中级代理":
                    tvRole.setText("中级服务商");
                    break;
                case "初级代理":
                    tvRole.setText("初级服务商");
                    break;
                case "高级合伙人":
                    tvRole.setText("高级合伙商");
                    break;
                case "中级合伙人":
                    tvRole.setText("中级合伙商");
                    break;
                case "初级合伙人":
                    tvRole.setText("初级合伙商");
                    break;
                case "高级客户经理":
                    tvRole.setText("合伙商代表");
                    break;
                case "服务商客户经理":
                    tvRole.setText("服务商代表");
                    break;

            }

            tvBalance.setText(String.valueOf(user.getBalance()));
        } else {
            llLl1.setVisibility(View.GONE);
            llLeve.setVisibility(View.GONE);
        }
        if (user.getRoleBean() != null && user.getRoleBean().getRole_name().contains("代理")) {
            tvDaiBiao.setText("新增服务商代表");
        } else if (user.getRoleBean() != null && user.getRoleBean().getRole_name().contains("合伙人")) {
            tvDaiBiao.setText("新增合伙商代表");
        }
        /*if(user.getShopBean()!=null){
            tvMno.setText(user.getShopBean().getMno());
        }*/
    }

    @OnClick({R.id.iv_gomydetail, R.id.ll_update_vip, R.id.ll_add_user, R.id.ll_address, R.id.ll_coupon
            , R.id.rl_left, R.id.rl_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_gomydetail:
                startActivityForResult(new Intent(getContext(), MyDetailActivity.class), 11);
                break;
            case R.id.ll_update_vip:
                if (MyUtils.getUser().getRoleBean() != null && MyUtils.getUser().getRoleBean().getRole_name().contains("代理")) {
                    startActivityForResult(new Intent(getContext(), UpgrapUserActivity.class)
                            .putExtra("type", "daili"), 11);
                } else if (MyUtils.getUser().getRoleBean() != null && MyUtils.getUser().getRoleBean().getRole_name().contains("合伙人")) {
                    startActivityForResult(new Intent(getContext(), UpgrapUserActivity.class)
                            .putExtra("type", "hhr"), 11);
                } else {
                    Toast.makeText(getContext(), "请先去首页，申请相应身份", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_add_user:
                startActivity(new Intent(getContext(), PeiSongActivity.class));//管理落地代表
                break;
            case R.id.ll_address://隐私协议
                startActivity(new Intent(getContext(), WebViewTitalActivity.class)
                        .putExtra("url", NetConfig.baseurl + "agreement.html")
                        .putExtra("name", "隐私协议"));
                break;
            case R.id.ll_coupon:
                /*startActivity(new Intent(getContext(), WebViewTitalActivity.class)
                        .putExtra("url", NetConfig.baseurl + "contact.html")
                        .putExtra("name", "联系我们"));*/
                startActivity(new Intent(getContext(), KefuActivity.class));
                
                break;
            case R.id.rl_left:
                startActivity(new Intent(getContext(), RecommentLogActivity.class));
                break;
            case R.id.rl_right:
                startActivityForResult(new Intent(getContext(), TiXianMoneyBagActivity.class), 11);
                break;
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
}