package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.beans.BusinessBean;
import com.axehome.www.guojinapp.beans.ShopSxfBean;
import com.axehome.www.guojinapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopDetailActivity extends BaseActivity {


    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.iv_shop_logo)
    ImageView ivShopLogo;
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
    @BindView(R.id.ll_duigong)
    LinearLayout llDuigong;
    @BindView(R.id.ll_zhongduan)
    LinearLayout llZhongduan;
    @BindView(R.id.ll_store)
    LinearLayout llStore;
    @BindView(R.id.tv_shou)
    TextView tvShou;
    @BindView(R.id.ll_zhan_kan)
    LinearLayout llZhanKan;
    @BindView(R.id.ll_license_type)
    LinearLayout llLicenseType;

    private SharedPreferences mSp;
    private ShopSxfBean shopBean = new ShopSxfBean();
    List<BusinessBean> businessBeanList = new ArrayList<>();
    private List<AreaCityBean> bankAreaCityBeanList = new ArrayList<>();
    private List<AreaCityBean> areaCityBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        getBankCityJson(getApplicationContext());
        getBusinessJson(getApplicationContext());
        title.setText("商户详情");
        String shopJson = getIntent().getStringExtra("shopBean") != null ? getIntent().getStringExtra("shopBean") : "";
        shopBean = JSON.parseObject(shopJson, ShopSxfBean.class);
        tvShou.setText("展开");
        llZhanKan.setVisibility(View.GONE);
        getJsonData(getApplicationContext());
        initData();
    }

    @OnClick({R.id.back_top, R.id.ll_zhongduan, R.id.ll_store, R.id.tv_shou})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_zhongduan:
                startActivity(new Intent(getApplicationContext(), TerminallistActivity.class)
                        .putExtra("merchant_no", shopBean.getMno()));
                break;
            case R.id.ll_store:
                startActivity(new Intent(getApplicationContext(), StroeListActivity.class)
                        .putExtra("merchant_no", shopBean.getMno()));
                break;
            case R.id.tv_shou:
                if (tvShou.getText().toString().equals("展开")) {
                    llZhanKan.setVisibility(View.VISIBLE);
                    tvShou.setText("收起");
                } else {
                    llZhanKan.setVisibility(View.GONE);
                    tvShou.setText("展开");
                }
                break;

        }
    }

    /*private void initView() {

        switch (shopBean.getHaveLicenseNo()) {
            case "03":
                tvMerchantBusinessType.setText("企业");
                break;
            case "02":
                tvMerchantBusinessType.setText("个体户");
                break;
            case "01":
                tvMerchantBusinessType.setText("小微商户");
                break;
        }
        if (shopBean.getMccCd() != null) {
            for (int i = 0; i < businessBeanList.size(); i++) {
                for (int j = 0; j < businessBeanList.get(i).getClist().size(); j++) {
                    for (int k = 0; k < businessBeanList.get(i).getClist().get(j).getClist().size(); k++) {
                        if (shopBean.getMccCd().equals(businessBeanList.get(i).getClist().get(j).getClist().get(k).getClass_id())) {
                            tvBusinessName.setText(businessBeanList.get(i).getClist().get(j).getClist().get(k).getOne_class()
                                    + "->" + businessBeanList.get(i).getClist().get(j).getClist().get(k).getTwo_class()
                                    + "->" + businessBeanList.get(i).getClist().get(j).getClist().get(k).getThree_class());
                        }
                    }
                }
            }
        }
        tvMerchantName.setText(shopBean.getCprRegNmCn() != null ? shopBean.getCprRegNmCn() : "");
        tvMerchantAlias.setText(shopBean.getMecDisNm() != null ? shopBean.getMecDisNm() : "");
        tvMerchantCompany.setText(shopBean.getCprRegNmCn() != null ? shopBean.getCprRegNmCn() : "");
        if (shopBean.getRegDistCd() != null && !shopBean.getRegDistCd().equals("")) {
            for (int i = 0; i < areaCityBeanList.size(); i++) {
                for (int j = 0; j < areaCityBeanList.get(i).getClist().size(); j++) {
                    for (int k = 0; k < areaCityBeanList.get(i).getClist().get(j).getClist().size(); k++) {
                        if (areaCityBeanList.get(i).getClist().get(j).getClist().get(k).getDist_code().equals(shopBean.getRegDistCd())) {
                            shopBean.setPro(areaCityBeanList.get(i).getClist().get(j).getClist().get(k).getPro_name());
                            shopBean.setCity(areaCityBeanList.get(i).getClist().get(j).getClist().get(k).getCity_name());
                            shopBean.setDist(areaCityBeanList.get(i).getClist().get(j).getClist().get(k).getDist_name());
                        }
                    }
                }
            }
        }
        tvProCityDist.setText(shopBean.getPro() + "->" + shopBean.getCity() + "->" + shopBean.getDist());
        tvMerchantAddress.setText(shopBean.getCprRegAddr() != null ? shopBean.getCprRegAddr() : "");
        tvMerchantPerson.setText(shopBean.getIdentityName() != null ? shopBean.getIdentityName() : "");
        tvMerchantPhone.setText(shopBean.getMblNo() != null ? shopBean.getMblNo() : "");
        *//*tvMerchantEmail.setText(shopBean.getMerchant_email() != null ? shopBean.getMerchant_email() : "");*//*
        tvMerchantServicePhone.setText(shopBean.getCsTelNo() != null ? shopBean.getCsTelNo() : "");
        tvLicenseNo.setText(shopBean.getRegistCode());

        if (shopBean.getBusinessLicEnt() != null) {
            if (shopBean.getBusinessLicEnt().equals("29991231")) {
                tvLicenseExpire.setText("长期有效");
            } else {
                tvLicenseExpire.setText(shopBean.getBusinessLicEnt());
            }
        }
        if (shopBean.getLicenseMatch() != null) {
            switch (shopBean.getLicenseMatch()) {
                case "01":
                    tvLicenseType.setText("营业执照");
                    break;
                case "00":
                    tvLicenseType.setText("三证合一");
                    break;
            }
        } else {
            llLicenseType.setVisibility(View.GONE);
        }
        switch (shopBean.getActTyp()) {
            case "00":
                tvLicenseType.setText("对公");
                break;
            case "01":
                tvLicenseType.setText("对私");
                break;
        }
        if (shopBean.getIdentityName() != null && shopBean.getActNm() != null) {
            if (shopBean.getIdentityName().equals(shopBean.getActNm())) {
                tvSettlementType.setText("法人");
            } else {
                tvSettlementType.setText("非法人");
            }
        }

        tvArtifNm.setText(shopBean.getIdentityName() != null ? shopBean.getIdentityName() : "");
        tvLegalIdnum.setText(shopBean.getIdentityNo() != null ? shopBean.getIdentityNo() : "");
        if (shopBean.getLegalPersonLicEnt() != null) {
            if (shopBean.getLegalPersonLicEnt().equals("29991231")) {
                tvLegalIdnumExpire.setText("长期有效");
            } else {
                tvLegalIdnumExpire.setText(shopBean.getLegalPersonLicEnt());
            }
        }
        tvMerchantIdNo.setText(shopBean.getStmManIdNo() != null ? shopBean.getStmManIdNo() : "");
        if (shopBean.getAccountLicEnt() != null) {
            if (shopBean.getAccountLicEnt().equals("29991231")) {
                tvMerchantIdExpire.setText("长期有效");
            } else {
                tvMerchantIdExpire.setText(shopBean.getLegalPersonLicEnt());
            }
        }
        *//*账户信息*//*
        tvAccountName.setText(shopBean.getActNm() != null ? shopBean.getActNm() : "");
        tvAccountNo.setText(shopBean.getStmManIdNo() != null ? shopBean.getStmManIdNo() : "");
        tvKhh.setText(shopBean.getDepoBank() != null ? shopBean.getDepoBank() : "");
        tvBankName.setText(shopBean.getLbnkNm() != null ? shopBean.getLbnkNm() : "");
        //tvKhdz.setText(shopBean.getKhdz() != null ? shopBean.getKhdz() : "");
        tvFl.setText(shopBean.getShop_fl() != null ? String.valueOf(shopBean.getShop_fl()) + "%" : "");
        if (shopBean.getActTyp().equals("00")) {
            llJieSuanRen.setVisibility(View.GONE);
        }
    }*/

    private void initData() {

    }

    private void getBankCityJson(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("bank_city.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            // GsonFormat
            bankAreaCityBeanList = JSON.parseArray(stringBuffer.toString(), AreaCityBean.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void getJsonData(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("area_code.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            // GsonFormat
            areaCityBeanList = JSON.parseArray(stringBuffer.toString(), AreaCityBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initView();
    }
}
