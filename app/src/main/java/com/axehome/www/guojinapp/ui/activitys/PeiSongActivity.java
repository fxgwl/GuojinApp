package com.axehome.www.guojinapp.ui.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.utils.MyHttp;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.SelfDialog;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.DeliveryManAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class PeiSongActivity extends BaseActivity implements VipListenter {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.mlv_list)
    ListView mlvList;

    private List<User> mlist = new ArrayList<>();
    private DeliveryManAdapter deliveryManAdapter;
    private SelfDialog selfDialog;
    private String from = null,orderId="",position1="";
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pei_song);
        ButterKnife.bind(this);
        initView();
        initData();
        deliveryManAdapter = new DeliveryManAdapter(getApplicationContext(), mlist, this,from);
        mlvList.setAdapter(deliveryManAdapter);
        user = MyUtils.getUser();
        if(user.getRoleBean()!=null && user.getRoleBean().getRole_name().contains("代理")){
            title.setText("新增服务商代表");
        }else if(user.getRoleBean()!=null && user.getRoleBean().getRole_name().contains("合伙人")){
            title.setText("新增合伙商代表");
        }
        edit.setVisibility(View.VISIBLE);
        edit.setText("新建");
    }
    private void initView() {
        deliveryManAdapter = new DeliveryManAdapter(getApplication(), mlist, this,from);
        mlvList.setAdapter(deliveryManAdapter);
                title.setText("落地代表账户");
                edit.setVisibility(View.VISIBLE);
                edit.setText("新建");
    }

    private void initData() {
        GetShopMemberByShopId();
    }

    @OnClick({R.id.edit, R.id.back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit:
                startActivityForResult(new Intent(this, DeliveryInsertActivity.class), 11);//新建会员
                break;
            case R.id.back_top:
                finish();
                break;
        }
    }
    private void GetShopMemberByShopId() {
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("user_type", "3");
        map.put("page","1");
        map.put("limit","10");
        OkHttpUtils.post()
                .url(NetConfig.userList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("onError", getClass().getName() + "150>>" + e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("GetShopMemberByShopId>>", getClass().getName() + "156>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    mlist.clear();
                                    String data = jsonObject.getString("data");
                                    mlist.addAll(JSONArray.parseArray(data, User.class));
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    //Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        deliveryManAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 1) {
            initData();
        }
    }

    @Override
    public void edit(int position) {
        if(from!=null){
            setResult(1,new Intent()
                    .putExtra("user_id",String.valueOf(mlist.get(position).getUser_id())));
            finish();
        }else{
            startActivityForResult(new Intent(this, DeliveryInsertActivity.class)
                    .putExtra("bean",mlist.get(position)), 11);//新建会员
        }
    }

    @Override
    public void del(int position) {
        selfDialog = new SelfDialog(this);
        selfDialog.setTitle("是否删除");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                DelDeliveryMan(position);
                selfDialog.dismiss();
            }
        });
        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.show();


    }
    @Override
    public void refresh(int position) {

    }

    @Override
    public void detail(int position) {

    }

    /*
    * 删除客户经理
    * */
    public void DelDeliveryMan(int position){
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(mlist.get(position).getUser_id()));
        map.put("status", "0");
        MyHttp myHttp = new MyHttp();
        myHttp.post(map, NetConfig.userUpdate, this.getClass().getName()).getDataLisenter(new MyHttp.GetData() {
            @Override
            public void Data(String s) {
                if (s.isEmpty()) {
                    Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                    return;
                }
//                list = new ArrayList<>();
                if (com.alibaba.fastjson.JSONObject.parseObject(s).getInteger("code") == 0) {
                    Toast.makeText(getApplication(), "删除成功！", Toast.LENGTH_SHORT).show();
                    mlist.clear();
                    initData();
                } else
                    Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }
}
