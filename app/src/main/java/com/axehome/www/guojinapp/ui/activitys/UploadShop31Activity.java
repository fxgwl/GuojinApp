package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.PicBean;
import com.axehome.www.guojinapp.beans.ShopBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.PicListenter;
import com.axehome.www.guojinapp.ui.adapters.ChoosePicAdapter;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class UploadShop31Activity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_look_info)
    TextView tvLookInfo;
    @BindView(R.id.mgv_list)
    MyGridView mgvList;
    @BindView(R.id.b_submit)
    Button bSubmit;

    private SharedPreferences mSp;
    private ChoosePicAdapter choosePicAdapter;
    private List<PicBean> picBeanList = new ArrayList<>();
    private String jsonStr = "[{\"pic_name\":\"门头照片\",\"pic_url\":\"\",\"is_must\":true}" +
            ",{\"pic_name\":\"内部前台(如无收银前台,上传收款码台卡照片或者刷脸支付设备照片)\",\"pic_url\":\"\",\"is_must\":true}" +
            ",{\"pic_name\":\"店内环境照片\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"其他证明材料\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"手持身份证\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"开户许可证照片或对公账户信息（加银行业务章）照片\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"非法人结算授权函(商户为企业性质，须盖章。商户为个体工商户性质，需签字按手印)\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"商户总分店关系证明\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"商户增值协议照片\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"第三方平台截图\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"支付宝支付物料照片\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"业务员门头合照\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"微信支付物料照片\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"组织机构代码证照片\",\"pic_url\":\"\",\"is_must\":false}" +
            ",{\"pic_name\":\"税务登记证照片\",\"pic_url\":\"\",\"is_must\":false}" +
            "]";
    private ShopBean shopBean = new ShopBean();
    private Integer currentPic=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_shop31);
        ButterKnife.bind(this);
        title.setText("添加商户");
        mSp = super.getSharedPreferences("shopBean.class", MODE_PRIVATE);
        if (getIntent().getStringExtra("type").equals("shopBean")) {
            if (!getShopBean().equals("")) {
                shopBean = JSON.parseObject(getShopBean(), ShopBean.class);
            } else {
                //shopBean=null;
            }
        } else {
            //shopBean=null;
        }
        initView();
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.back_top,R.id.b_submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                gotoNext();
                break;
        }
    }

    private void initView() {
        picBeanList.addAll(JSON.parseArray(jsonStr,PicBean.class));
        if(shopBean.getMerchant_business_type() == 3){
            for(int i=0;i<picBeanList.size();i++){
                if(picBeanList.get(i).getPic_name().equals("手持身份证")){
                    picBeanList.get(i).setIs_must(true);
                }
            }
        }else if(shopBean.getMerchant_business_type() == 2){
            if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("2")){
                for(int i=0;i<picBeanList.size();i++){
                    if(picBeanList.get(i).getPic_name().contains("非法人结算授权函")){
                        picBeanList.get(i).setIs_must(true);
                    }
                }
            }else if(shopBean.getAccount_type().equals("1") && shopBean.getSettlement_type().equals("1")){//个体户对公结算
                for(int i=0;i<picBeanList.size();i++){
                    if(picBeanList.get(i).getPic_name().contains("开户许可证照片")||picBeanList.get(i).getPic_name().equals("组织机构代码证照片")
                            ||picBeanList.get(i).getPic_name().equals("税务登记证照片")){
                        picBeanList.get(i).setIs_must(true);
                    }
                }
            }

        }else if(shopBean.getMerchant_business_type() == 1){
            if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("2")){//企业对私非法人结算
                for(int i=0;i<picBeanList.size();i++){
                    if(picBeanList.get(i).getPic_name().contains("开户许可证照片")||picBeanList.get(i).getPic_name().equals("组织机构代码证照片")
                            ||picBeanList.get(i).getPic_name().equals("税务登记证照片")||picBeanList.get(i).getPic_name().contains("非法人结算授权函")){
                        picBeanList.get(i).setIs_must(true);
                    }
                }
            }else if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("1")){
                for(int i=0;i<picBeanList.size();i++){
                    if(picBeanList.get(i).getPic_name().contains("开户许可证照片")||picBeanList.get(i).getPic_name().equals("组织机构代码证照片")
                            ||picBeanList.get(i).getPic_name().equals("税务登记证照片")){
                        picBeanList.get(i).setIs_must(true);
                    }
                }
            }else if(shopBean.getAccount_type().equals("1") && shopBean.getSettlement_type().equals("1")){//企业对公结算
                for(int i=0;i<picBeanList.size();i++){
                    if(picBeanList.get(i).getPic_name().contains("开户许可证照片")||picBeanList.get(i).getPic_name().equals("组织机构代码证照片")
                            ||picBeanList.get(i).getPic_name().equals("税务登记证照片")){
                        picBeanList.get(i).setIs_must(true);
                    }
                }
            }
        }
        /*数据回填*/
        if(shopBean.getImg_logo()!=null && !shopBean.getImg_logo().equals("")) picBeanList.get(0).setPic_url(shopBean.getImg_logo());
        if(shopBean.getImg_indoor()!=null && !shopBean.getImg_indoor().equals("")) picBeanList.get(1).setPic_url(shopBean.getImg_indoor());
        if(shopBean.getImg_contract()!=null && !shopBean.getImg_contract().equals("")) picBeanList.get(2).setPic_url(shopBean.getImg_contract());
        if(shopBean.getImg_other()!=null && !shopBean.getImg_other().equals("")) picBeanList.get(3).setPic_url(shopBean.getImg_other());
        if(shopBean.getImg_idcard_holding()!=null && !shopBean.getImg_idcard_holding().equals("")) picBeanList.get(4).setPic_url(shopBean.getImg_idcard_holding());
        if(shopBean.getImg_open_permits()!=null && !shopBean.getImg_open_permits().equals("")) picBeanList.get(5).setPic_url(shopBean.getImg_open_permits());
        if(shopBean.getImg_unincorporated()!=null && !shopBean.getImg_unincorporated().equals("")) picBeanList.get(6).setPic_url(shopBean.getImg_unincorporated());
        if(shopBean.getImg_standard_protocol()!=null && !shopBean.getImg_standard_protocol().equals("")) picBeanList.get(7).setPic_url(shopBean.getImg_standard_protocol());
        if(shopBean.getImg_val_add_protocol()!=null && !shopBean.getImg_val_add_protocol().equals("")) picBeanList.get(8).setPic_url(shopBean.getImg_val_add_protocol());
        if(shopBean.getImg_3rd_part()!=null && !shopBean.getImg_3rd_part().equals("")) picBeanList.get(9).setPic_url(shopBean.getImg_3rd_part());
        if(shopBean.getImg_alicashier()!=null && !shopBean.getImg_alicashier().equals("")) picBeanList.get(10).setPic_url(shopBean.getImg_alicashier());
        if(shopBean.getImg_salesman_logo()!=null && !shopBean.getImg_salesman_logo().equals("")) picBeanList.get(11).setPic_url(shopBean.getImg_salesman_logo());
        if(shopBean.getImg_cashier()!=null && !shopBean.getImg_cashier().equals("")) picBeanList.get(12).setPic_url(shopBean.getImg_cashier());
        if(shopBean.getImg_org_code()!=null && !shopBean.getImg_org_code().equals("")) picBeanList.get(13).setPic_url(shopBean.getImg_org_code());
        if(shopBean.getImg_tax_reg()!=null && !shopBean.getImg_tax_reg().equals("")) picBeanList.get(14).setPic_url(shopBean.getImg_tax_reg());

        choosePicAdapter = new ChoosePicAdapter(UploadShop31Activity.this,picBeanList);
        mgvList.setAdapter(choosePicAdapter);
        choosePicAdapter.setListener(new PicListenter() {
            @Override
            public void on_Clicked(int position) {
                currentPic=position;
                Album.startAlbum(UploadShop31Activity.this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
            }

            @Override
            public void del_pic(int position) {
                picBeanList.get(position).setPic_url("");
                choosePicAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                for (int i = 0; i < pathList.size(); i++) {
                    fileUpload(new File(pathList.get(i)));
                }
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }
    //存数据
    public void setShopBean() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString("shopBean", JSON.toJSONString(shopBean));
        editor.commit();
        //editor.apply();
        startActivity(new Intent(getApplicationContext(), UploadShop4Activity.class)
                .putExtra("type","shopBean")
                .putExtra("picList",JSON.toJSONString(picBeanList)));
    }

    //读数据
    public String getShopBean() {
        String info = mSp.getString("shopBean", "");
        return info;
    }
    public void fileUpload(File file) {
        OkHttpUtils.post()
                .url(NetConfig.fileUpload)
                .addFile("file", "crop.jpg", file)
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
                            if (JSONObject.parseObject(response).getInteger("code") == 0) {
                                picBeanList.get(currentPic).setPic_url(JSONObject.parseObject(response).getString("data"));
                                choosePicAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void gotoNext() {
        shopBean.setImg_logo(picBeanList.get(0).getPic_url());
        shopBean.setImg_indoor(picBeanList.get(1).getPic_url());
        if(shopBean.getImg_logo().equals("")||shopBean.getImg_indoor().equals("")){
            Toast.makeText(getApplicationContext(),"请上传完整资料",Toast.LENGTH_SHORT).show();
            return;
        }
        shopBean.setImg_contract(picBeanList.get(2).getPic_url());
        shopBean.setImg_other(picBeanList.get(3).getPic_url());
        shopBean.setImg_idcard_holding(picBeanList.get(4).getPic_url());
        shopBean.setImg_open_permits(picBeanList.get(5).getPic_url());
        shopBean.setImg_unincorporated(picBeanList.get(6).getPic_url());
        shopBean.setImg_standard_protocol(picBeanList.get(7).getPic_url());
        shopBean.setImg_val_add_protocol(picBeanList.get(8).getPic_url());
        shopBean.setImg_3rd_part(picBeanList.get(9).getPic_url());
        shopBean.setImg_alicashier(picBeanList.get(10).getPic_url());
        shopBean.setImg_salesman_logo(picBeanList.get(11).getPic_url());
        shopBean.setImg_cashier(picBeanList.get(12).getPic_url());
        shopBean.setImg_org_code(picBeanList.get(13).getPic_url());
        shopBean.setImg_tax_reg(picBeanList.get(14).getPic_url());

        if(shopBean.getMerchant_business_type() == 3){
            if(shopBean.getImg_idcard_holding().equals("")){
                Toast.makeText(getApplicationContext(),"请上传手持身份证",Toast.LENGTH_SHORT).show();
                return;
            }
        }else if(shopBean.getMerchant_business_type() == 2){
            if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("2")){
                if(shopBean.getImg_unincorporated().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传入账非法人证明照片",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else if(shopBean.getAccount_type().equals("1") && shopBean.getSettlement_type().equals("1")){//个体户对公结算
                if(shopBean.getImg_open_permits().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传开户许可证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_org_code().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传组织机构代码证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_tax_reg().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传税务登记证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }else if(shopBean.getMerchant_business_type() == 1){
            if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("2")){//企业对私非法人结算
                if(shopBean.getImg_open_permits().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传开户许可证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_org_code().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传组织机构代码证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_tax_reg().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传税务登记证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_unincorporated().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传入账非法人证明照片",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("1")){
                if(shopBean.getImg_open_permits().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传开户许可证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_org_code().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传组织机构代码证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_tax_reg().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传税务登记证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else if(shopBean.getAccount_type().equals("1") && shopBean.getSettlement_type().equals("1")){//企业对公结算
                if(shopBean.getImg_open_permits().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传开户许可证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_org_code().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传组织机构代码证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(shopBean.getImg_tax_reg().equals("")){
                    Toast.makeText(getApplicationContext(),"请上传税务登记证照片",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        setShopBean();
    }
}
