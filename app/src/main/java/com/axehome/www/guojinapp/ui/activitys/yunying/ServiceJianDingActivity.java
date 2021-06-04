package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.android.tu.loadingdialog.LoadingDailog;
import com.axehome.www.guojinapp.MainActivity;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.PayResult;
import com.axehome.www.guojinapp.listeners.PermissionInterface;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.adapters.PicAdapter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.PermissionHelper;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ServiceJianDingActivity extends BaseActivity implements PermissionInterface {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.good_name)
    EditText goodName;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.et_good_guige)
    EditText etGoodGuige;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.iv_zheng_shu)
    ImageView ivZhengShu;
    @BindView(R.id.cb_zhengshu)
    CheckBox cbZhengshu;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.rb_ali_pay)
    RadioButton rbAliPay;
    @BindView(R.id.rb_wechat_pay)
    RadioButton rbWechatPay;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.b_submit)
    Button bSubmit;

    private int currentPic = 0;
    private String[] pic_path = new String[4];

    private Activity activity;
    private PicAdapter picAdapter;
    private List<String> picBeanList = new ArrayList<>();
    private PermissionHelper mPermissionHelper;
    private String pictype="",pay_way="alipay";
    private Double jd_value=null,zs_value=null;

    private IWXAPI api;
    private static int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。暂不做服务端回调处理
                Toast.makeText(ServiceJianDingActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                /*startActivity(new Intent(getApplication(), MainActivity.class)
                        .putExtra("from","order"));*/
                //orderBean.setOrderPayWay(orderPayWay);
                /*startActivity(new Intent(getApplication(), PaySuccessActivity.class)
                        .putExtra("ShopOrderBean", orderBean));*/
                finish();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。暂不做服务端回调处理
                Toast.makeText(ServiceJianDingActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }

    };

    private LoadingDailog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_jian_ding);
        ButterKnife.bind(this);
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
        activity = this;
        title.setText("鉴定服务");
        SysInfo();
/*调支付宝sdk*/
        /*final String orderInfo = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https://cash.yunfastpay.com/html/%23/cash/ff0342727a9b42c1944e5ac88fbec0d0/adcba7a5d36bdb6faa6b02a0d5d77f64";   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ServiceJianDingActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();*/
    }

    private void initView() {
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        mDialog = loadBuilder.create();
        picAdapter = new PicAdapter(getApplicationContext(), picBeanList,10);
        gvList.setAdapter(picAdapter);
        picAdapter.setListener(new PicAdapter.PicClickListener() {
            @Override
            public void addPic() {
                if (picBeanList.size() < 10 && picBeanList.size() != 0) {
                    pictype = "normal";
                    Album.startAlbum(activity, 111, 10 - picBeanList.size()
                            ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//相册多选
                } else {
                    pictype = "duo";
                    Album.startAlbum(activity, 111, 10
                            ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//相册多选
                }
            }

            @Override
            public void delPic(int position) {
                picBeanList.remove(position);
                picAdapter.notifyDataSetChanged();
            }

            @Override
            public void ChoosePic(int position) {
                pictype = "dan";
                currentPic = position;
                Album.startAlbum(activity, 111, 1);//相册单选
            }

            @Override
            public void setContentItem(int position) {

            }
        });

        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_ali_pay:
                        pay_way="alipay";
                        break;
                    case R.id.rb_wechat_pay:
                        pay_way="wechat";
                        break;
                }
            }
        });
        cbZhengshu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    /*需要证书*/
                    if(jd_value!=null&&zs_value!=null){
                        tvValue.setText(String.valueOf(jd_value+zs_value));
                    }
                }else{
                    if(jd_value!=null){
                        tvValue.setText(String.valueOf(jd_value));
                    }
                }
            }
        });
    }

    @OnClick({R.id.back_top, R.id.iv_zheng_shu, R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.iv_zheng_shu:
                Album.startAlbum(activity, 222, 1);//相册单选
                break;
            case R.id.b_submit:
                AlertDialog dlg = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                        .setTitle("是否确认提交？")
                        .setItems(new String[]{"确认", "取消"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // 这里item是根据选择的方式，
                                if (item == 0) {
                                    Submit();
                                } else if (item == 1) {

                                }
                            }
                        }).create();
                dlg.show();
                break;
        }
    }

    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        initView();
    }

    @Override
    public void requestPermissionsFail() {
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                /*if (pictype.equals("duo")) {
                    picBeanList.clear();
                }*/
                for (int i = 0; i < pathList.size(); i++) {
                    int finalI = i;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            fileUpload(new File(pathList.get(finalI)));
                        }
                    }.start();
                }
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }else if (requestCode == 111) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                if (pictype.equals("duo")) {
                    picBeanList.clear();
                }
                for (int i = 0; i < pathList.size(); i++) {
                    int finalI = i;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            fileUpload1(new File(pathList.get(finalI)));
                        }
                    }.start();
                }
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }else if(requestCode == 22){
            startActivity(new Intent(getApplication(), MainActivity.class)
                    .putExtra("from", "login"));
            finish();
        }
    }

    public void fileUpload(File file) {
        OkHttpUtils.post()
                .url(NetConfig.fileUpload)
                .addFile("file", "crop.jpg", file)
                .addParams("user_id", String.valueOf(MyUtils.getUser().getUser_id()))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", ">>" + e.getMessage());
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", ">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            if (com.alibaba.fastjson.JSONObject.parseObject(response).getInteger("code") == 0) {
                                pic_path[0] = com.alibaba.fastjson.JSONObject.parseObject(response).getString("data");
                                Glide.with(getApplicationContext()).load(NetConfig.baseurl+pic_path[0]).into(ivZhengShu);
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void fileUpload1(File file) {
        OkHttpUtils.post()
                .url(NetConfig.fileUpload)
                .addFile("file", "crop.jpg", file)
                .addParams("user_id", String.valueOf(MyUtils.getUser().getUser_id()))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", ">>" + e.getMessage());
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        picAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", ">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            if (com.alibaba.fastjson.JSONObject.parseObject(response).getInteger("code") == 0) {
                                List<String> pic = new ArrayList<>();
                                pic.add(com.alibaba.fastjson.JSONObject.parseObject(response).getString("data"));
                                if (pictype.equals("duo")) {
                                    picBeanList.addAll(pic);
                                } else if (pictype.equals("normal")) {
                                    picBeanList.addAll(pic);
                                } else {
                                    picBeanList.set(currentPic, pic.get(0));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                        picAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void Submit() {
        if(!pay_way.equals("alipay")){
            Toast.makeText(getApplicationContext(),"暂未开通微信支付",Toast.LENGTH_SHORT).show();
            return;
        }
        if(goodName.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"物品名称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etGoodGuige.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"物品规格不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDetail.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"物品详情不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic_path[0]==null || pic_path[0].equals("")){
            Toast.makeText(getApplicationContext(),"证书不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        /*if(tvValue.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"金额不能为空",Toast.LENGTH_SHORT).show();
            return;
        }*/
        String imgs="";
        if(picBeanList!=null && picBeanList.size()>0){
            for(int i=0;i<picBeanList.size();i++){
                if(imgs.equals("")){
                    imgs=picBeanList.get(i);
                }else{
                    imgs += ","+picBeanList.get(i);
                }
            }
        }else{
            Toast.makeText(getApplicationContext(),"请上传物品图片",Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("good_name", goodName.getText().toString().trim());
        map.put("good_guige", etGoodGuige.getText().toString().trim());
        map.put("good_detail", etDetail.getText().toString().trim());
        map.put("good_zhengshu", pic_path[0]);
        map.put("good_imgs", imgs);
        map.put("need_zheng", cbZhengshu.isChecked()?"1":"0");
        map.put("order_value", tvValue.getText().toString());
        map.put("pay_way", pay_way);
        OkHttpUtils.post()
                .url(NetConfig.submitJianDing)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mDialog.dismiss();
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            mDialog.dismiss();
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            int code = jsonObject.getInteger("code");
                            if (code == 0) {
                                /*Toast.makeText(getApplication(), "提交成功", Toast.LENGTH_SHORT).show();*/
                                String url= jsonObject.getJSONObject("data").getString("qrData");
                                if(pay_way.equals("alipay")){
                                    url= "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+url;
                                }else{
                                    url=jsonObject.getJSONObject("data").getString("qrData");
                                }
                                mDialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivityForResult( intent ,22);
                                //setResult(1);
                                //finish();
                            } else {
                                mDialog.dismiss();
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void SysInfo() {

        OkHttpUtils.post()
                .url(NetConfig.getSysinfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            int code = jsonObject.getInteger("code");
                            if (code == 0) {
                                jd_value=jsonObject.getJSONObject("data").getDouble("sys_jd_value");
                                zs_value=jsonObject.getJSONObject("data").getDouble("sys_zs_value");
                                if(jd_value!=null){
                                    tvValue.setText(String.valueOf(jd_value));
                                }
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
