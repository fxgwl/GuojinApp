package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.axehome.www.guojinapp.beans.BusinessBean;
import com.axehome.www.guojinapp.beans.PicBean;
import com.axehome.www.guojinapp.beans.ShopBean;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.PicListenter;
import com.axehome.www.guojinapp.ui.adapters.ChoosePicAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class UploadShop4Activity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_merchant_business_type)
    TextView tvMerchantBusinessType;
    @BindView(R.id.tv_business_name)
    TextView tvBusinessName;
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.tv_merchant_alias)
    TextView tvMerchantAlias;
    @BindView(R.id.tv_merchant_company)
    TextView tvMerchantCompany;
    @BindView(R.id.tv_pro_city_dist)
    TextView tvProCityDist;
    @BindView(R.id.tv_merchant_address)
    TextView tvMerchantAddress;
    @BindView(R.id.tv_merchant_person)
    TextView tvMerchantPerson;
    @BindView(R.id.tv_merchant_phone)
    TextView tvMerchantPhone;
    @BindView(R.id.tv_merchant_email)
    TextView tvMerchantEmail;
    @BindView(R.id.tv_merchant_service_phone)
    TextView tvMerchantServicePhone;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_license_no)
    TextView tvLicenseNo;
    @BindView(R.id.tv_license_expire)
    TextView tvLicenseExpire;
    @BindView(R.id.tv_license_type)
    TextView tvLicenseType;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.tv_settlement_type)
    TextView tvSettlementType;
    @BindView(R.id.tv_artif_nm)
    TextView tvArtifNm;
    @BindView(R.id.tv_legalIdnum)
    TextView tvLegalIdnum;
    @BindView(R.id.tv_legalIdnumExpire)
    TextView tvLegalIdnumExpire;
    @BindView(R.id.ll_fa_ren)
    LinearLayout llFaRen;
    @BindView(R.id.tv_merchant_id_no)
    TextView tvMerchantIdNo;
    @BindView(R.id.tv_merchant_id_expire)
    TextView tvMerchantIdExpire;
    @BindView(R.id.ll_jie_suan_ren)
    LinearLayout llJieSuanRen;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_account_no)
    TextView tvAccountNo;
    @BindView(R.id.tv_khh)
    TextView tvKhh;
    @BindView(R.id.tv_khdz)
    TextView tvKhdz;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_account_phone)
    TextView tvAccountPhone;
    @BindView(R.id.tv_fl)
    TextView tvFl;
    @BindView(R.id.tv_company_account_name)
    TextView tvCompanyAccountName;
    @BindView(R.id.tv_company_account_no)
    TextView tvCompanyAccountNo;
    @BindView(R.id.tv_khh_gong)
    TextView tvKhhGong;
    @BindView(R.id.tv_khdz_gong)
    TextView tvKhdzGong;
    @BindView(R.id.tv_company_bank_name)
    TextView tvCompanyBankName;
    @BindView(R.id.mgv_list)
    MyGridView mgvList;
    @BindView(R.id.b_submit_back)
    Button bSubmitBack;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.ll_duigong)
    LinearLayout llDuigong;
    private SharedPreferences mSp;
    private ShopBean shopBean = new ShopBean();
    private List<PicBean> picBeanList = new ArrayList<>();
    private ChoosePicAdapter choosePicAdapter;
    List<BusinessBean> businessBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_shop4);
        ButterKnife.bind(this);
        title.setText("添加商户");
        String picJson = getIntent().getStringExtra("picList");
        picBeanList.addAll(JSON.parseArray(picJson, PicBean.class));
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

    @OnClick({R.id.back_top,R.id.b_submit,R.id.b_submit_back})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                SubmitData();
                break;
            case R.id.b_submit_back:
                finish();
                break;

        }
    }
    private void initView() {
        choosePicAdapter = new ChoosePicAdapter(UploadShop4Activity.this, picBeanList);
        mgvList.setAdapter(choosePicAdapter);
        choosePicAdapter.setListener(new PicListenter() {
            @Override
            public void on_Clicked(int position) {

            }

            @Override
            public void del_pic(int position) {

            }
        });

        getBusinessJson(getApplicationContext());
        switch (shopBean.getMerchant_business_type()) {
            case 1:
                tvMerchantBusinessType.setText("企业(包含事业单位)");
                break;
            case 2:
                tvMerchantBusinessType.setText("个体工商户");
                break;
            case 3:
                tvMerchantBusinessType.setText("小微商户");
                break;
        }
        if (shopBean.getBusiness_code() != null) {
            for (int i = 0; i < businessBeanList.size(); i++) {
                for (int j = 0; j < businessBeanList.get(i).getClist().size(); j++) {
                    for (int k = 0; k < businessBeanList.get(i).getClist().get(j).getClist().size(); k++) {
                        if (shopBean.getBusiness_code().equals(businessBeanList.get(i).getClist().get(j).getClist().get(k).getClass_id())) {
                            tvBusinessName.setText(businessBeanList.get(i).getClist().get(j).getClist().get(k).getOne_class()
                                    + "->" + businessBeanList.get(i).getClist().get(j).getClist().get(k).getTwo_class()
                                    + "->" + businessBeanList.get(i).getClist().get(j).getClist().get(k).getThree_class());
                        }
                    }
                }
            }
        }
        tvMerchantName.setText(shopBean.getMerchant_name() != null ? shopBean.getMerchant_name() : "");
        tvMerchantAlias.setText(shopBean.getMerchant_alias() != null ? shopBean.getMerchant_alias() : "");
        tvMerchantCompany.setText(shopBean.getMerchant_company() != null ? shopBean.getMerchant_company() : "");
        tvProCityDist.setText(shopBean.getMerchant_province() + "->" + shopBean.getMerchant_city() + "->" + shopBean.getMerchant_county());
        tvMerchantAddress.setText(shopBean.getMerchant_address() != null ? shopBean.getMerchant_address() : "");
        tvMerchantPerson.setText(shopBean.getMerchant_person() != null ? shopBean.getMerchant_person() : "");
        tvMerchantPhone.setText(shopBean.getMerchant_phone() != null ? shopBean.getMerchant_phone() : "");
        tvMerchantEmail.setText(shopBean.getMerchant_email() != null ? shopBean.getMerchant_email() : "");
        tvMerchantServicePhone.setText(shopBean.getMerchant_service_phone() != null ? shopBean.getMerchant_service_phone() : "");
        tvLicenseNo.setText(shopBean.getLicense_no());

        if (shopBean.getLicense_expire() != null) {
            if (shopBean.getLicense_expire().equals("")) {
                tvLicenseExpire.setText("长期有效");
            } else {
                tvLicenseExpire.setText(shopBean.getLicense_expire());
            }
        }
        switch (shopBean.getLicense_type()) {
            case 0:
                tvLicenseType.setText("营业执照");
                break;
            case 1:
                tvLicenseType.setText("三证合一");
                break;
            case 2:
                tvLicenseType.setText("手持身份证");
                break;
        }
        switch (shopBean.getAccount_type()) {
            case "1":
                tvLicenseType.setText("对公");
                break;
            case "2":
                tvLicenseType.setText("对私");
                break;
        }
        switch (shopBean.getSettlement_type()){
            case "1":
                tvSettlementType.setText("法人");
                break;
            case "2":
                tvSettlementType.setText("非法人");
                break;
        }

        tvArtifNm.setText(shopBean.getArtif_nm() != null ? shopBean.getArtif_nm() : "");
        tvLegalIdnum.setText(shopBean.getLegalIdnum() != null ? shopBean.getLegalIdnum() : "");
        if (shopBean.getLegalIdnumExpire() != null) {
            if (shopBean.getLegalIdnumExpire().equals("")) {
                tvLegalIdnumExpire.setText("长期有效");
            } else {
                tvLegalIdnumExpire.setText(shopBean.getLegalIdnumExpire());
            }
        }
        tvMerchantIdNo.setText(shopBean.getMerchant_id_no() != null ? shopBean.getMerchant_id_no() : "");
        tvMerchantIdExpire.setText(shopBean.getMerchant_id_expire() != null ? shopBean.getMerchant_id_expire() : "");
        /*账户信息*/
        tvAccountName.setText(shopBean.getAccount_name() != null ? shopBean.getAccount_name() : "");
        tvAccountNo.setText(shopBean.getAccount_no() != null ? shopBean.getAccount_no() : "");
        tvKhh.setText(shopBean.getKhh_name() != null ? shopBean.getKhh_name() : "");
        tvKhdz.setText(shopBean.getKhdz() != null ? shopBean.getKhdz() : "");
        tvBankName.setText(shopBean.getBank_name() != null ? shopBean.getBank_name() : "");
        tvAccountPhone.setText(shopBean.getAccount_phone() != null ? shopBean.getAccount_phone() : "");
        tvFl.setText(shopBean.getShop_fl() != null ? String.valueOf(shopBean.getShop_fl()) + "%" : "");
        tvCompanyAccountName.setText(shopBean.getCompany_account_name() != null ? shopBean.getCompany_account_name() : "");
        tvCompanyAccountNo.setText(shopBean.getCompany_account_no() != null ? shopBean.getCompany_account_no() : "");
        tvKhhGong.setText(shopBean.getGetKhh_name_gong() != null ? shopBean.getGetKhh_name_gong() : "");
        tvKhdzGong.setText(shopBean.getKhdz_gong() != null ? shopBean.getKhdz_gong() : "");
        tvCompanyBankName.setText(shopBean.getCompany_bank_name() != null ? shopBean.getCompany_bank_name() : "");
        if (shopBean.getMerchant_business_type() == 3) {
            llFaRen.setVisibility(View.GONE);
            llDuigong.setVisibility(View.GONE);
        }else if(shopBean.getMerchant_business_type() == 2){
            llDuigong.setVisibility(View.GONE);
            if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("1")){
                llFaRen.setVisibility(View.GONE);
            }else if(shopBean.getAccount_type().equals("1") && shopBean.getSettlement_type().equals("1")){
                llJieSuanRen.setVisibility(View.GONE);
            }
        }else if(shopBean.getMerchant_business_type() == 1){
            if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("1")){
                llFaRen.setVisibility(View.GONE);
            }else if(shopBean.getAccount_type().equals("1") && shopBean.getSettlement_type().equals("1")){
                llJieSuanRen.setVisibility(View.GONE);
                llDuigong.setVisibility(View.GONE);
            }
        }
    }

    private void initData() {

    }

    //存数据
    public void setShopBean() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString("shopBean", JSON.toJSONString(shopBean));
        editor.commit();
        //editor.apply();
        startActivity(new Intent(getApplicationContext(), UploadShop4Activity.class)
                .putExtra("type", "shopBean")
                .putExtra("picList", JSON.toJSONString(picBeanList)));
    }

    //读数据
    public String getShopBean() {
        String info = mSp.getString("shopBean", "");
        return info;
    }

    private void getBusinessJson(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("business.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            // GsonFormat
            businessBeanList = JSON.parseArray(stringBuffer.toString(), BusinessBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * 提交商户信息
    * */
    public void SubmitData(){
        shopBean.setB_user_id(MyUtils.getUser().getUser_id());
        OkHttpUtils.postString()
                .url(NetConfig.shopSave)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(JSON.toJSONString(shopBean))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("shopSave","error>>"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    shopBean=null;
                                    setShopBean();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
