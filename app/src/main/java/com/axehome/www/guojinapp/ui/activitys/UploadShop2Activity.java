package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.beans.BankBean;
import com.axehome.www.guojinapp.beans.RateBean;
import com.axehome.www.guojinapp.beans.ShopBean;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.adapters.ChooseAreaCityAdapter;
import com.axehome.www.guojinapp.ui.adapters.ChooseFenBankAdapter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class UploadShop2Activity extends BaseActivity {


    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.iv_img_license)
    ImageView ivImgLicense;
    @BindView(R.id.et_license_no)
    EditText etLicenseNo;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.ll_license_expire)
    LinearLayout llLicenseExpire;
    @BindView(R.id.ll_duigong)
    LinearLayout llDuigong;
    @BindView(R.id.tv_account_type)
    TextView tvAccountType;
    @BindView(R.id.ll_account_type)
    LinearLayout llAccountType;
    @BindView(R.id.tv_license_type)
    TextView tvLicenseType;
    @BindView(R.id.ll_license_type)
    LinearLayout llLicenseType;
    @BindView(R.id.tv_settlement_type)
    TextView tvSettlementType;
    @BindView(R.id.ll_settlement_type)
    LinearLayout llSettlementType;
    @BindView(R.id.iv_img_idcard_a)
    ImageView ivImgIdcardA;
    @BindView(R.id.iv_img_idcard_b)
    ImageView ivImgIdcardB;
    @BindView(R.id.et_merchant_id_no)
    EditText etMerchantIdNo;
    @BindView(R.id.s_merchant_id_expire)
    Switch sMerchantIdExpire;
    @BindView(R.id.tv_merchant_id_expire)
    TextView tvMerchantIdExpire;
    @BindView(R.id.ll_merchant_id_expire)
    LinearLayout llMerchantIdExpire;
    @BindView(R.id.ll_jie_suan_ren)
    LinearLayout llJieSuanRen;
    @BindView(R.id.iv_img_bankcard_a)
    ImageView ivImgBankcardA;
    @BindView(R.id.iv_img_bankcard_b)
    ImageView ivImgBankcardB;
    @BindView(R.id.ll_ru_zhang_ka)
    LinearLayout llRuZhangKa;
    @BindView(R.id.et_artif_nm)
    EditText etArtifNm;
    @BindView(R.id.et_legal_idnum)
    EditText etLegalIdnum;
    @BindView(R.id.switch2)
    Switch switch2;
    @BindView(R.id.ll_legal_idnum_expire)
    LinearLayout llLegalIdnumExpire;
    @BindView(R.id.ll_fa_ren)
    LinearLayout llFaRen;
    @BindView(R.id.et_account_name)
    EditText etAccountName;
    @BindView(R.id.et_account_no)
    EditText etAccountNo;
    @BindView(R.id.tv_khh)
    TextView tvKhh;
    @BindView(R.id.ll_khh)
    LinearLayout llKhh;
    @BindView(R.id.tv_khdz)
    TextView tvKhdz;
    @BindView(R.id.ll_khdz)
    LinearLayout llKhdz;
    @BindView(R.id.tv_khzh)
    TextView tvKhzh;
    @BindView(R.id.ll_khzh)
    LinearLayout llKhzh;
    @BindView(R.id.et_account_phone)
    EditText etAccountPhone;
    @BindView(R.id.tv_fl)
    TextView tvFl;
    @BindView(R.id.ll_fl)
    LinearLayout llFl;
    @BindView(R.id.iv_img_private_idcard_a)
    ImageView ivImgPrivateIdcardA;
    @BindView(R.id.iv_img_private_idcard_b)
    ImageView ivImgPrivateIdcardB;
    @BindView(R.id.tv_license_expire)
    TextView tvLicenseExpire;
    @BindView(R.id.tv_legal_idnum_expire)
    TextView tvLegalIdnumExpire;
    @BindView(R.id.et_company_account_name)
    EditText etCompanyAccountName;
    @BindView(R.id.et_company_account_no)
    EditText etCompanyAccountNo;
    @BindView(R.id.tv_khh_gong)
    TextView tvKhhGong;
    @BindView(R.id.ll_khh_gong)
    LinearLayout llKhhGong;
    @BindView(R.id.tv_khdz_gong)
    TextView tvKhdzGong;
    @BindView(R.id.ll_khdz_gong)
    LinearLayout llKhdzGong;
    @BindView(R.id.tv_company_bank_name)
    TextView tvCompanyBankName;
    @BindView(R.id.ll_company_bank_name)
    LinearLayout llCompanyBankName;
    @BindView(R.id.ll_duigong_zhang_hu)
    LinearLayout llDuigongZhangHu;
    private ShopBean shopBean = new ShopBean();
    private SharedPreferences mSp;
    private Integer currentPic = null;
    private List<String> accountType = new ArrayList<>();
    private List<String> settlementType = new ArrayList<>();
    private List<String> licenceType = new ArrayList<>();
    private OptionsPickerView pvClass;
    private List<BankBean> bankBeanList = new ArrayList<>();//总行列表
    private List<BankBean> fenBankList = new ArrayList<>();//分行列表
    private List<String> bankList = new ArrayList<>();
    private List<AreaCityBean> areaCityBeanList = new ArrayList<>();
    private String pro = "", city = "", dist = "", khdz_city_code = "",khdz_city_code_gong="";
    private int city_type = 1;
    private PopupWindow popWnd;
    private ChooseAreaCityAdapter chooseAreaCityAdapter;
    private ChooseFenBankAdapter chooseFenBankAdapter;
    private List<RateBean>rateCodeList=new ArrayList<>();//费率列表
    private List<String> rateList=new ArrayList<>();
    private TimePickerView timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_shop2);
        ButterKnife.bind(this);
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
        title.setText("添加商户");
        accountType.add("对公");//(1对公、2对私)
        accountType.add("对私");
        settlementType.add("法人");//(1法人、2非法人)
        settlementType.add("非法人");
        licenceType.add("营业执照");//0营业执照，1三证合一，2手持身份证
        licenceType.add("三证合一");
        licenceType.add("手持身份证");
        initView();
        initData();
    }

    private void initData() {
        getBankName();
        getJsonData(getApplicationContext());
        getRateList(getApplicationContext());
    }

    private void initView() {
        switch (shopBean.getMerchant_business_type()) {
            case 1:
                llLicenseType.setClickable(false);
                llLicenseType.setBackgroundColor(getResources().getColor(R.color.f6));
                tvLicenseType.setText("三证合一营业执照");
                break;
            case 2:
                llDuigongZhangHu.setVisibility(View.GONE);
                llLicenseType.setClickable(false);
                llLicenseType.setBackgroundColor(getResources().getColor(R.color.f6));
                tvLicenseType.setText("三证合一营业执照");
                break;
            case 3:
                llDuigongZhangHu.setVisibility(View.GONE);
                llDuigong.setVisibility(View.GONE);
                llLicenseType.setVisibility(View.GONE);
                llFaRen.setVisibility(View.GONE);
                llLicenseType.setClickable(false);
                llLicenseType.setBackgroundColor(getResources().getColor(R.color.f6));
                tvLicenseType.setText("三证合一营业执照");
                llAccountType.setClickable(false);
                llAccountType.setBackgroundColor(getResources().getColor(R.color.f6));
                tvAccountType.setText("对私");
                llSettlementType.setVisibility(View.GONE);
                break;
        }
        if(shopBean.getLicense_no()!=null && !shopBean.getLicense_no().equals(""))etLicenseNo.setText(shopBean.getLicense_no());
        if(shopBean.getImg_license()!=null && !shopBean.getImg_license().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_license()).into(ivImgLicense);
        if(shopBean.getLicense_expire()!=null && !shopBean.getLicense_expire().equals("")) tvLegalIdnumExpire.setText(shopBean.getLicense_expire());
        else switch1.setChecked(true);
        if(shopBean.getAccount_type()!=null){
            switch (shopBean.getAccount_type()){
                case "1":
                    tvAccountType.setText("对公");
                    llSettlementType.setVisibility(View.GONE);
                    llJieSuanRen.setVisibility(View.GONE);
                    if(shopBean.getMerchant_business_type()==1){
                        llDuigongZhangHu.setVisibility(View.GONE);
                    }
                    break;
                case "2":
                    tvAccountType.setText("对私");
                    if(shopBean.getMerchant_business_type()==1){
                        llDuigongZhangHu.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
        if(shopBean.getSettlement_type()!=null){
            switch (shopBean.getSettlement_type()){
                case "1":
                    tvSettlementType.setText("法人");
                    llFaRen.setVisibility(View.GONE);
                    break;
                case "2":
                    tvSettlementType.setText("非法人");
                    break;
            }
        }
        if(shopBean.getMerchant_id_no()!=null && !shopBean.getMerchant_id_no().equals(""))etMerchantIdNo.setText(shopBean.getMerchant_id_no());
        if(shopBean.getMerchant_id_expire()!=null && !shopBean.getMerchant_id_expire().equals(""))tvMerchantIdExpire.setText(shopBean.getMerchant_id_expire());
        else sMerchantIdExpire.setChecked(true);
        if(shopBean.getImg_private_idcard_a()!=null && !shopBean.getImg_private_idcard_a().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_private_idcard_a()).into(ivImgPrivateIdcardA);
        if(shopBean.getImg_private_idcard_b()!=null && !shopBean.getImg_private_idcard_b().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_private_idcard_b()).into(ivImgPrivateIdcardB);
        /*法人*/
        if(shopBean.getArtif_nm()!=null && !shopBean.getArtif_nm().equals("")) etArtifNm.setText(shopBean.getArtif_nm());
        if(shopBean.getImg_idcard_a()!=null && !shopBean.getImg_idcard_a().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_idcard_a()).into(ivImgIdcardA);
        if(shopBean.getImg_idcard_b()!=null&&!shopBean.getImg_idcard_b().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_idcard_b()).into(ivImgIdcardB);
        if(shopBean.getLegalIdnum()!=null && !shopBean.getLegalIdnum().equals("")) etLegalIdnum.setText(shopBean.getLegalIdnum());
        if(shopBean.getLegalIdnumExpire()!=null && !shopBean.getLegalIdnumExpire().equals("")) tvLegalIdnumExpire.setText(shopBean.getLegalIdnumExpire());
        else switch2.setChecked(true);
        /*银行卡*/
        if(shopBean.getImg_bankcard_a()!=null && !shopBean.getImg_bankcard_a().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_bankcard_a()).into(ivImgBankcardA);
        if(shopBean.getImg_bankcard_b()!=null && !shopBean.getImg_bankcard_b().equals("")) Glide.with(getApplicationContext())
                .load(NetConfig.baseurl+shopBean.getImg_bankcard_b()).into(ivImgBankcardB);
        if(shopBean.getAccount_name()!=null && !shopBean.getAccount_name().equals("")) etAccountName.setText(shopBean.getAccount_name());
        if(shopBean.getAccount_no()!=null && !shopBean.getAccount_no().equals("")) etAccountNo.setText(shopBean.getAccount_no());
        if(shopBean.getBank_name()!=null && !shopBean.getBank_name().equals("")) tvKhzh.setText(shopBean.getBank_name());
        if(shopBean.getKhh_name()!=null && !shopBean.getKhh_name().equals("")) tvKhh.setText(shopBean.getKhh_name());
        if(shopBean.getKhdz()!=null && !shopBean.getKhdz().equals("")) tvKhdz.setText(shopBean.getKhdz());
        if(shopBean.getAccount_phone()!=null && !shopBean.getAccount_phone().equals("")) etAccountPhone.setText(shopBean.getAccount_phone());
        if(shopBean.getShop_fl()!=null && !shopBean.getShop_fl().equals("")) tvFl.setText(String.valueOf(shopBean.getShop_fl())+"%");
        /*对公账户*/
        if(shopBean.getCompany_account_name()!=null && !shopBean.getCompany_account_name().equals("")) etCompanyAccountName.setText(shopBean.getCompany_account_name());
        if(shopBean.getCompany_account_no()!=null && !shopBean.getCompany_account_no().equals("")) etCompanyAccountNo.setText(shopBean.getCompany_account_no());
        if(shopBean.getCompany_bank_name()!=null && !shopBean.getCompany_bank_name().equals("")) tvCompanyBankName.setText(shopBean.getCompany_bank_name());
        if(shopBean.getGetKhh_name_gong()!=null && !shopBean.getGetKhh_name_gong().equals("")) tvKhhGong.setText(shopBean.getGetKhh_name_gong());
        if(shopBean.getKhdz_gong()!=null && !shopBean.getKhdz_gong().equals("")) tvKhdzGong.setText(shopBean.getKhdz_gong());

    }

    @OnClick({R.id.back_top, R.id.iv_img_license, R.id.iv_img_idcard_a, R.id.iv_img_bankcard_a
            , R.id.iv_img_bankcard_b, R.id.iv_img_idcard_b, R.id.ll_account_type, R.id.ll_settlement_type
            , R.id.ll_license_type, R.id.iv_img_private_idcard_a, R.id.iv_img_private_idcard_b
            , R.id.ll_khh, R.id.ll_khdz, R.id.ll_khzh,R.id.ll_fl,R.id.ll_license_expire,R.id.ll_merchant_id_expire
            , R.id.ll_legal_idnum_expire,R.id.b_submit,R.id.ll_company_bank_name,R.id.ll_khh_gong
            , R.id.ll_khdz_gong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.iv_img_license:
                currentPic = 1;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.iv_img_idcard_a:
                currentPic = 2;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.iv_img_idcard_b:
                currentPic = 3;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.iv_img_bankcard_a:
                currentPic = 4;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.iv_img_bankcard_b:
                currentPic = 5;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.iv_img_private_idcard_a:
                currentPic = 6;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.iv_img_private_idcard_b:
                currentPic = 7;
                Album.startAlbum(this, 111, 1,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));
                break;
            case R.id.ll_account_type:
                //chooseClass(accountType, tvAccountType);
                break;
            case R.id.ll_settlement_type:
                //chooseClass(settlementType, tvSettlementType);
                break;
            case R.id.ll_license_type:
                //chooseClass(licenceType, tvLicenseType);
                break;
            case R.id.ll_khh:
                //chooseZongHang(bankList, tvKhh,"si");
                break;
            case R.id.ll_khdz:
                //showPopUpWindow_city(tvKhdz,"si");
                break;
            case R.id.ll_khzh:
                getFenBankName("si");
                break;
            case R.id.ll_company_bank_name:
                getFenBankName("gong");
                break;
            case R.id.ll_khh_gong:
                //chooseZongHang(bankList, tvKhhGong,"gong");
                break;
            case R.id.ll_khdz_gong:
                //showPopUpWindow_city(tvKhdzGong,"gong");
                break;
            case R.id.ll_fl:
                //chooseRate(rateList,tvFl);
                break;
            case R.id.ll_license_expire:
                if (timePicker != null && timePicker.isShowing()) {
                    timePicker.dismiss();
                } else {
                    //timePicker(tvLicenseExpire);
                }
                break;
            case R.id.ll_merchant_id_expire:
                if (timePicker != null && timePicker.isShowing()) {
                    timePicker.dismiss();
                } else {
                    //timePicker(tvMerchantIdExpire);
                }
                break;
            case R.id.ll_legal_idnum_expire:
                if (timePicker != null && timePicker.isShowing()) {
                    timePicker.dismiss();
                } else {
                    //timePicker(tvLegalIdnumExpire);
                }
                break;
            case R.id.b_submit:
                gotoNext();
                break;
        }
    }

    /*private void chooseClass(List<String> list, TextView textView) {
        pvClass = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                *//*tvClassName.setText(option2.get(options1));*//*
                textView.setText(list.get(options1));
                llSettlementType.setVisibility(View.VISIBLE);
                llJieSuanRen.setVisibility(View.VISIBLE);
                llFaRen.setVisibility(View.VISIBLE);
                switch (list.get(options1)) {
                    case "对公":
                        shopBean.setAccount_type("1");
                        llSettlementType.setVisibility(View.GONE);
                        llJieSuanRen.setVisibility(View.GONE);
                        if(shopBean.getMerchant_business_type()==1){
                            llDuigongZhangHu.setVisibility(View.GONE);
                        }
                        break;
                    case "对私":
                        shopBean.setAccount_type("2");
                        if(shopBean.getMerchant_business_type()==1){
                            llDuigongZhangHu.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "法人":
                        shopBean.setSettlement_type("1");
                        llFaRen.setVisibility(View.GONE);
                        break;
                    case "非法人":
                        shopBean.setSettlement_type("2");
                        break;
                    case "营业执照":
                        shopBean.setLicense_type(0);
                        break;
                    case "三证合一":
                        shopBean.setLicense_type(1);
                        break;
                    case "手持身份证":
                        shopBean.setLicense_type(2);
                        break;
                }
            }
        })
                .setTitleText("选择类型")
                .isCenterLabel(true)
                .build();
        pvClass.setNPicker(list, null, null);
        pvClass.show();
    }

    private void chooseZongHang(List<String> list, TextView textView,String type) {
        pvClass = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                *//*tvClassName.setText(option2.get(options1));*//*
                if(type.equals("gong")){
                    shopBean.setGetKhh_name_gong(list.get(options1));
                }else{
                    shopBean.setKhh_name(list.get(options1));
                }
                textView.setText(list.get(options1));
            }
        })
                .setTitleText("选择类型")
                .isCenterLabel(true)
                .build();
        pvClass.setNPicker(list, null, null);
        pvClass.show();
    }
    private void chooseRate(List<String> list, TextView textView) {
        pvClass = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                *//*tvClassName.setText(option2.get(options1));*//*
                textView.setText(list.get(options1));
                for(int i=0;i<rateCodeList.size();i++){
                    if(list.get(options1).equals(rateCodeList.get(i).getValue())){
                        shopBean.setRate_code(rateCodeList.get(i).getName());
                        String value=rateCodeList.get(i).getValue().replace("%","");
                        shopBean.setShop_fl(Double.valueOf(value));
                    }
                }
            }
        })
                .setTitleText("选择费率")
                .isCenterLabel(true)
                .build();
        pvClass.setNPicker(list, null, null);
        pvClass.show();
    }*/
    /*private void showPopUpWindow_city(TextView textView,String type) {
        if(popWnd!=null && popWnd.isShowing()){
            popWnd.dismiss();
        }
        city_type = 1;
        View contentView = LayoutInflater.from(this).inflate(R.layout.windows_choose, null);
        popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();
        RadioGroup rgMenu = contentView1.findViewById(R.id.rg_menu);
        TextView tvWindowtitle = contentView1.findViewById(R.id.tv_window_title);
        TextView tvClose = contentView1.findViewById(R.id.tv_close);
        ListView listView = contentView1.findViewById(R.id.lv_list);
        RadioButton rbClass01 = contentView1.findViewById(R.id.rb_class01);
        RadioButton rbClass02 = contentView1.findViewById(R.id.rb_class02);
        RadioButton rbClass03 = contentView1.findViewById(R.id.rb_class03);
        tvWindowtitle.setText("开户地址选择");
        rbClass01.setText("选择省");
        rbClass02.setText("选择市");
        rbClass03.setText("选择县");
        rbClass03.setVisibility(View.GONE);
        List<AreaCityBean> mList = new ArrayList<>();
        mList.addAll(areaCityBeanList);
        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShop2Activity.this, mList, 1);
        listView.setAdapter(chooseAreaCityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (city_type == 1) {
                    pro = mList.get(position).getPro_name();
                    //shopBean.setMerchant_province(pro);
                    //shopBean.setMerchant_province_code(mList.get(position).getPro_code());
                    rbClass02.setChecked(true);
                } else if (city_type == 2) {
                    city = mList.get(position).getCity_name();
                    //tvHylb.setText(mList.get(position).getOne_class()+"->"+mList.get(position).getTwo_class()+"->"+mList.get(position).getThree_class());
                    if(type.equals("gong")){
                        khdz_city_code_gong=mList.get(position).getCity_code();
                        tvKhdzGong.setText(pro + "->" + city);
                        shopBean.setKhdz_gong(pro + "->" + city);
                    }else{
                        khdz_city_code = mList.get(position).getCity_code();
                        tvKhdz.setText(pro + "->" + city);
                        shopBean.setKhdz(pro + "->" + city);
                    }
                    popWnd.dismiss();
                } else if (city_type == 3) {
                    *//*dist=mList.get(position).getDist_name();
                    shopBean.setMerchant_county(dist);
                    shopBean.setMerchant_county_code(mList.get(position).getDist_code());
                    tvAddress.setText(pro+"->"+city+"->"+dist);
                    popWnd.dismiss();*//*
                }
            }
        });
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rb_class01:
                        city_type = 1;
                        mList.clear();
                        mList.addAll(areaCityBeanList);
                        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShop2Activity.this, mList, city_type);
                        listView.setAdapter(chooseAreaCityAdapter);
                        break;
                    case R.id.rb_class02:
                        city_type = 2;
                        mList.clear();
                        for (int i = 0; i < areaCityBeanList.size(); i++) {
                            if (pro.equals(areaCityBeanList.get(i).getPro_name())) {
                                mList.addAll(areaCityBeanList.get(i).getClist());
                            }
                        }
                        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShop2Activity.this, mList, city_type);
                        listView.setAdapter(chooseAreaCityAdapter);
                        break;
                    case R.id.rb_class03:
                        city_type = 3;
                        List<AreaCityBean> mmmlist = new ArrayList<>();
                        mmmlist.addAll(mList);
                        mList.clear();
                        for (int i = 0; i < mmmlist.size(); i++) {
                            if (city.equals(mmmlist.get(i).getCity_name())) {
                                mList.addAll(mmmlist.get(i).getClist());
                            }
                        }
                        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShop2Activity.this, mList, city_type);
                        listView.setAdapter(chooseAreaCityAdapter);
                        break;
                }
            }
        });

        popWnd.setTouchable(true);
        popWnd.setFocusable(false);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        //添加pop窗口关闭事件
        popWnd.setOnDismissListener(new poponDismissListener());
        popWnd.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWnd.dismiss();
                    return true;
                }
                return false;
            }
        });

        //popWnd.showAsDropDown(llList, 0, 0);
        popWnd.showAtLocation(textView, Gravity.BOTTOM, 0, 0);
    }*/

    private void showPopUpWindow_fen_bank(TextView textView,String type) {
        if(popWnd!=null && popWnd.isShowing()){
            popWnd.dismiss();
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.windows_choose, null);
        popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();
        RadioGroup rgMenu = contentView1.findViewById(R.id.rg_menu);
        TextView tvWindowtitle = contentView1.findViewById(R.id.tv_window_title);
        TextView tvClose = contentView1.findViewById(R.id.tv_close);
        ListView listView = contentView1.findViewById(R.id.lv_list);
        RadioButton rbClass01 = contentView1.findViewById(R.id.rb_class01);
        RadioButton rbClass02 = contentView1.findViewById(R.id.rb_class02);
        RadioButton rbClass03 = contentView1.findViewById(R.id.rb_class03);
        tvWindowtitle.setText("分行选择");
        rbClass01.setText("选择分行");
        rbClass02.setText("");
        rbClass03.setText("");
        rbClass03.setVisibility(View.GONE);
        rbClass02.setVisibility(View.GONE);
        List<BankBean> mList = new ArrayList<>();
        mList.addAll(fenBankList);
        chooseFenBankAdapter = new ChooseFenBankAdapter(UploadShop2Activity.this, mList);
        listView.setAdapter(chooseFenBankAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(type.equals("gong")){
                    shopBean.setCompany_bank_name(mList.get(position).getZhi_hang_name());
                    shopBean.setCompany_bank_no(mList.get(position).getZhi_hang_code());
                }else{
                    shopBean.setBank_name(mList.get(position).getZhi_hang_name());
                }
                textView.setText(mList.get(position).getZhi_hang_name());
                popWnd.dismiss();
            }
        });
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rb_class01:
                        mList.clear();
                        mList.addAll(fenBankList);
                        chooseFenBankAdapter = new ChooseFenBankAdapter(UploadShop2Activity.this, mList);
                        listView.setAdapter(chooseFenBankAdapter);
                        break;
                }
            }
        });

        popWnd.setTouchable(true);
        popWnd.setFocusable(false);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        //添加pop窗口关闭事件
        popWnd.setOnDismissListener(new poponDismissListener());
        popWnd.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWnd.dismiss();
                    return true;
                }
                return false;
            }
        });

        //popWnd.showAsDropDown(llList, 0, 0);
        popWnd.showAtLocation(textView, Gravity.BOTTOM, 0, 0);
    }

    //存数据
    public void setShopBean() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString("shopBean", JSON.toJSONString(shopBean));
        editor.commit();
        //editor.apply();
        startActivity(new Intent(getApplicationContext(), UploadShop31Activity.class)
                .putExtra("type","shopBean"));
    }

    //读数据
    public String getShopBean() {
        String info = mSp.getString("shopBean", "");
        return info;
    }

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
            //rlBlackback.setVisibility(View.GONE);
        }
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
    }
    private void getRateList(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("rate_code.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            // GsonFormat
            rateCodeList = JSON.parseArray(stringBuffer.toString(), RateBean.class);
            for(int i=0;i<rateCodeList.size();i++){
                Double my_num= MyUtils.getUser().getRoleBean().getRole_rate();
                Double dou_num=Double.valueOf(rateCodeList.get(i).getValue().replace("%",""));
                if(my_num>dou_num){
                }else{
                    rateList.add(rateCodeList.get(i).getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                                switch (currentPic) {
                                    case 1:
                                        shopBean.setImg_license(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_license()).into(ivImgLicense);
                                        break;
                                    case 2:
                                        shopBean.setImg_idcard_a(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_idcard_a()).into(ivImgIdcardA);
                                        break;
                                    case 3:
                                        shopBean.setImg_idcard_b(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_idcard_b()).into(ivImgIdcardB);
                                        break;
                                    case 4:
                                        shopBean.setImg_bankcard_a(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_bankcard_a()).into(ivImgBankcardA);
                                        break;
                                    case 5:
                                        shopBean.setImg_bankcard_b(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_bankcard_b()).into(ivImgBankcardB);
                                        break;
                                    case 6:
                                        shopBean.setImg_private_idcard_a(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_private_idcard_a()).into(ivImgPrivateIdcardA);
                                        break;
                                    case 7:
                                        shopBean.setImg_private_idcard_b(JSONObject.parseObject(response).getString("data"));
                                        Glide.with(getApplicationContext()).load(NetConfig.baseurl+shopBean.getImg_private_idcard_b()).into(ivImgPrivateIdcardB);
                                        break;
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void gotoNext() {
        shopBean.setLicense_type(1);
        if (shopBean.getMerchant_business_type() == 3) {
            shopBean.setAccount_type("2");
            shopBean.setSettlement_type("1");
            shopBean.setLicense_type(2);
            shopBean.setMerchant_id_no(etMerchantIdNo.getText().toString());
            if(shopBean.getMerchant_id_no()==null ||shopBean.getMerchant_id_no().equals("")){
                Toast.makeText(getApplicationContext(), "请输入结算人的身份证号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (sMerchantIdExpire.isChecked()) {
                shopBean.setMerchant_id_expire("29991231");
                llMerchantIdExpire.setBackgroundColor(getResources().getColor(R.color.f6));
            } else {
                shopBean.setMerchant_id_expire(tvMerchantIdExpire.getText().toString());
            }
            shopBean.setBank_name(tvKhzh.getText().toString());
            shopBean.setAccount_name(etAccountName.getText().toString());
            shopBean.setAccount_no(etAccountNo.getText().toString());
            if(shopBean.getBank_name().equals("")||shopBean.getAccount_name().equals("")||shopBean.getAccount_no().equals("")){
                Toast.makeText(getApplicationContext(), "请输入完整入账银行信息", Toast.LENGTH_SHORT).show();
                return;
            }
            shopBean.setAccount_phone(etAccountPhone.getText().toString());
            //shopBean.setRate_code(tvFl.getText().toString());
        } else if (shopBean.getMerchant_business_type() == 2) {
            if(shopBean.getAccount_type()==null || shopBean.getSettlement_type()==null){
                Toast.makeText(getApplicationContext(), "请选择结算类型 和 结算账户", Toast.LENGTH_SHORT).show();
                return;
            }else if(shopBean.getAccount_type().equals("1")){
                shopBean.setSettlement_type("1");
            }
            if (shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("2")) {//个体户对私非法人结算
                shopBean.setMerchant_id_no(etMerchantIdNo.getText().toString());
                if (shopBean.getMerchant_id_no() == null) {
                    Toast.makeText(getApplicationContext(), "请填写结算人证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sMerchantIdExpire.isChecked()) {
                    shopBean.setMerchant_id_expire("29991231");
                    llMerchantIdExpire.setBackgroundColor(getResources().getColor(R.color.f6));
                } else {
                    shopBean.setMerchant_id_expire(tvMerchantIdExpire.getText().toString());
                }
                shopBean.setLicense_no(etLicenseNo.getText().toString());
                if (switch1.isChecked()) {
                    shopBean.setLicense_expire(null);
                } else {
                    shopBean.setLicense_expire(tvLicenseExpire.getText().toString());
                }
                if(shopBean.getLicense_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入营业执照证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setArtif_nm(etArtifNm.getText().toString());
                shopBean.setLegalIdnum(etLegalIdnum.getText().toString());
                if (switch2.isChecked()) {
                    shopBean.setLegalIdnumExpire(null);
                } else {
                    shopBean.setLegalIdnumExpire(tvLegalIdnumExpire.getText().toString());
                }
                if(shopBean.getArtif_nm().equals("")||shopBean.getLegalIdnum().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整法人信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setBank_name(tvKhzh.getText().toString());
                shopBean.setAccount_name(etAccountName.getText().toString());
                shopBean.setAccount_no(etAccountNo.getText().toString());
                shopBean.setAccount_phone(etAccountPhone.getText().toString());
                //shopBean.setRate_code(tvFl.getText().toString());
                //写判断
            } else if (shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("1")) {//个体户对私法人结算
                shopBean.setMerchant_id_no(etMerchantIdNo.getText().toString());
                if (shopBean.getMerchant_id_no() == null) {
                    Toast.makeText(getApplicationContext(), "请填写结算人证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sMerchantIdExpire.isChecked()) {
                    shopBean.setMerchant_id_expire("29991231");
                    llMerchantIdExpire.setBackgroundColor(getResources().getColor(R.color.f6));
                } else {
                    shopBean.setMerchant_id_expire(tvMerchantIdExpire.getText().toString());
                }
                shopBean.setLicense_no(etLicenseNo.getText().toString());
                if (switch1.isChecked()) {
                    shopBean.setLicense_expire(null);
                } else {
                    shopBean.setLicense_expire(tvLicenseExpire.getText().toString());
                }
                if(shopBean.getLicense_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入营业执照证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setBank_name(tvKhzh.getText().toString());
                shopBean.setAccount_name(etAccountName.getText().toString());
                shopBean.setAccount_no(etAccountNo.getText().toString());
                shopBean.setAccount_phone(etAccountPhone.getText().toString());
                if(shopBean.getBank_name().equals("")||shopBean.getAccount_name().equals("")||shopBean.getAccount_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整入账银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                //shopBean.setRate_code(tvFl.getText().toString());
                //写判断

            } else if (shopBean.getAccount_type().equals("1")) {//个体户对公结算
                shopBean.setSettlement_type("1");

                shopBean.setLicense_no(etLicenseNo.getText().toString());
                if (switch1.isChecked()) {
                    shopBean.setLicense_expire(null);
                } else {
                    shopBean.setLicense_expire(tvLicenseExpire.getText().toString());
                }
                if(shopBean.getLicense_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入营业执照证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setArtif_nm(etArtifNm.getText().toString());
                shopBean.setLegalIdnum(etLegalIdnum.getText().toString());
                if (switch2.isChecked()) {
                    shopBean.setLegalIdnumExpire(null);
                } else {
                    shopBean.setLegalIdnumExpire(tvLegalIdnumExpire.getText().toString());
                }
                if(shopBean.getArtif_nm().equals("")||shopBean.getLegalIdnum().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整法人信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setBank_name(tvKhzh.getText().toString());
                shopBean.setAccount_name(etAccountName.getText().toString());
                shopBean.setAccount_no(etAccountNo.getText().toString());
                shopBean.setAccount_phone(etAccountPhone.getText().toString());
                if(shopBean.getBank_name().equals("")||shopBean.getAccount_name().equals("")||shopBean.getAccount_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整入账银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                //shopBean.setRate_code(tvFl.getText().toString());
                //写判断
            }
        } else if (shopBean.getMerchant_business_type() == 1) {
            if(shopBean.getAccount_type()==null || shopBean.getSettlement_type()==null){
                Toast.makeText(getApplicationContext(), "请选择结算类型 和 结算账户", Toast.LENGTH_SHORT).show();
                return;
            }else if(shopBean.getAccount_type().equals("1")){
                shopBean.setSettlement_type("1");
            }
            if (shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("2")) {//企业对私非法人结算
                shopBean.setMerchant_id_no(etMerchantIdNo.getText().toString());
                if (shopBean.getMerchant_id_no() == null) {
                    Toast.makeText(getApplicationContext(), "请填写结算人证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sMerchantIdExpire.isChecked()) {
                    shopBean.setMerchant_id_expire("29991231");
                    llMerchantIdExpire.setBackgroundColor(getResources().getColor(R.color.f6));
                } else {
                    shopBean.setMerchant_id_expire(tvMerchantIdExpire.getText().toString());
                }
                shopBean.setLicense_no(etLicenseNo.getText().toString());
                if (switch1.isChecked()) {
                    shopBean.setLicense_expire(null);
                } else {
                    shopBean.setLicense_expire(tvLicenseExpire.getText().toString());
                }
                if(shopBean.getLicense_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入营业执照证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setArtif_nm(etArtifNm.getText().toString());
                shopBean.setLegalIdnum(etLegalIdnum.getText().toString());
                if (switch2.isChecked()) {
                    shopBean.setLegalIdnumExpire(null);
                } else {
                    shopBean.setLegalIdnumExpire(tvLegalIdnumExpire.getText().toString());
                }
                if(shopBean.getArtif_nm().equals("")||shopBean.getLegalIdnum().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整法人信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setBank_name(tvKhzh.getText().toString());
                shopBean.setAccount_name(etAccountName.getText().toString());
                shopBean.setAccount_no(etAccountNo.getText().toString());
                shopBean.setAccount_phone(etAccountPhone.getText().toString());
                //shopBean.setRate_code(tvFl.getText().toString());
                if(shopBean.getBank_name().equals("")||shopBean.getAccount_name().equals("")||shopBean.getAccount_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整入账银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setCompany_account_name(etCompanyAccountName.getText().toString());
                shopBean.setCompany_account_no(etCompanyAccountNo.getText().toString());
                if(shopBean.getCompany_bank_name()==null ||shopBean.getCompany_bank_no()==null||shopBean.getCompany_account_no().equals("")
                        ||shopBean.getCompany_account_name().equals("")){
                    Toast.makeText(getApplication(), "请输入完整对公银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                //写判断
            }else if(shopBean.getAccount_type().equals("2") && shopBean.getSettlement_type().equals("1")){//企业对私法人结算
                shopBean.setMerchant_id_no(etMerchantIdNo.getText().toString());
                if (shopBean.getMerchant_id_no() == null) {
                    Toast.makeText(getApplicationContext(), "请填写结算人证件号码", Toast.LENGTH_SHORT).show();

                }
                if (sMerchantIdExpire.isChecked()) {
                    shopBean.setMerchant_id_expire("29991231");
                    llMerchantIdExpire.setBackgroundColor(getResources().getColor(R.color.f6));
                } else {
                    shopBean.setMerchant_id_expire(tvMerchantIdExpire.getText().toString());
                }
                shopBean.setLicense_no(etLicenseNo.getText().toString());
                if (switch1.isChecked()) {
                    shopBean.setLicense_expire(null);
                } else {
                    shopBean.setLicense_expire(tvLicenseExpire.getText().toString());
                }
                if(shopBean.getLicense_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入营业执照证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setBank_name(tvKhzh.getText().toString());
                shopBean.setAccount_name(etAccountName.getText().toString());
                shopBean.setAccount_no(etAccountNo.getText().toString());
                shopBean.setAccount_phone(etAccountPhone.getText().toString());
                if(shopBean.getBank_name().equals("")||shopBean.getAccount_name().equals("")||shopBean.getAccount_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整入账银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                //shopBean.setRate_code(tvFl.getText().toString());

                shopBean.setCompany_account_name(etCompanyAccountName.getText().toString());
                shopBean.setCompany_account_no(etCompanyAccountNo.getText().toString());
                if(shopBean.getCompany_bank_name()==null ||shopBean.getCompany_bank_no()==null||shopBean.getCompany_account_no().equals("")
                        ||shopBean.getCompany_account_name().equals("")){
                    Toast.makeText(getApplication(), "请输入完整对公银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                //写判断
            }else if(shopBean.getAccount_type().equals("1")){
                shopBean.setSettlement_type("1");

                shopBean.setLicense_no(etLicenseNo.getText().toString());
                if (switch1.isChecked()) {
                    shopBean.setLicense_expire(null);
                } else {
                    shopBean.setLicense_expire(tvLicenseExpire.getText().toString());
                }
                if(shopBean.getLicense_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入营业执照证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setArtif_nm(etArtifNm.getText().toString());
                shopBean.setLegalIdnum(etLegalIdnum.getText().toString());
                if (switch2.isChecked()) {
                    shopBean.setLegalIdnumExpire(null);
                } else {
                    shopBean.setLegalIdnumExpire(tvLegalIdnumExpire.getText().toString());
                }
                if(shopBean.getArtif_nm().equals("")||shopBean.getLegalIdnum().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整法人信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                shopBean.setBank_name(tvKhzh.getText().toString());
                shopBean.setAccount_name(etAccountName.getText().toString());
                shopBean.setAccount_no(etAccountNo.getText().toString());
                shopBean.setAccount_phone(etAccountPhone.getText().toString());
                if(shopBean.getBank_name().equals("")||shopBean.getAccount_name().equals("")||shopBean.getAccount_no().equals("")){
                    Toast.makeText(getApplicationContext(), "请输入完整入账银行信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                //shopBean.setRate_code(tvFl.getText().toString());
                //写判断
            }
        }
        if(shopBean.getRate_code()==null||shopBean.getRate_code().equals("")){
            Toast.makeText(getApplication(), "您还没有选择费率", Toast.LENGTH_SHORT).show();
            return;
        }
        setShopBean();
    }

    public void getBankName() {
        OkHttpUtils.post()
                .url(NetConfig.getBankName)
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
                                String data = JSONObject.parseObject(response).getString("data");
                                bankBeanList.addAll(JSON.parseArray(data, BankBean.class));
                                for (int i = 0; i < bankBeanList.size(); i++) {
                                    bankList.add(bankBeanList.get(i).getZong_hang_name());
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void getFenBankName(String type) {
        fenBankList.clear();
        String str="";
        if(type.equals("gong")){
            str = tvKhhGong.getText().toString();
        }else{
            str = tvKhh.getText().toString();
        }
        String zong_hang_code = "";
        for (int i = 0; i < bankBeanList.size(); i++) {
            if (str.equals(bankBeanList.get(i).getZong_hang_name())) {
                zong_hang_code = bankBeanList.get(i).getZong_hang_code();
            }
        }
        Map<String, String> map = new HashMap<>();
        if(type.equals("gong")){
            map.put("city_code", khdz_city_code_gong);
            if(zong_hang_code.equals("")||khdz_city_code_gong.equals("")){
                Toast.makeText(getApplication(), "请选择开户行和地址信息", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            map.put("city_code", khdz_city_code);
            if(zong_hang_code.equals("")||khdz_city_code.equals("")){
                Toast.makeText(getApplication(), "请选择开户行和地址信息", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        map.put("zong_hang_code", zong_hang_code);
        OkHttpUtils.post()
                .url(NetConfig.getFenHangClass)
                .params(map)
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
                                String data = JSONObject.parseObject(response).getString("data");
                                fenBankList.addAll(JSON.parseArray(data, BankBean.class));
                                showPopUpWindow_fen_bank(tvKhzh,type);
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    /*
    * 设置时间
    * */
    /*private void timePicker(final TextView textView) {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(year_int-3, mouth_int, day_int);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int+50, mouth_int - 1, day_int);

        //时间选择器
        timePicker = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                *//*btn_Time.setText(getTime(date));*//*

                //time.setText(getTime(date));
                textView.setText(getTime(date));
            }
        })

                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(R.color.c6)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTextColorOut(R.color.c6)//设置没有被选中项的颜色
                .setContentSize(21)
                .setDate(selectedDate)
                .setLineSpacingMultiplier(1.2f)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        timePicker.show();
    }*/

    private String getTime(Date date) {//可根据需要自行截取数据显示


        //SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }
}
