package com.axehome.www.guojinapp.ui.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.beans.BusinessBean;
import com.axehome.www.guojinapp.beans.ShopBean;
import com.axehome.www.guojinapp.utils.MD5;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.PermissionHelper;
import com.axehome.www.guojinapp.GuoJinApp;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.PermissionInterface;
import com.axehome.www.guojinapp.ui.adapters.ChooseAreaCityAdapter;
import com.axehome.www.guojinapp.ui.adapters.ChooseClassAdapter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class UploadShopActivity extends BaseActivity implements PermissionInterface {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_shlx)
    TextView tvShlx;
    @BindView(R.id.ll_shlx)
    LinearLayout llShlx;
    @BindView(R.id.tv_hylb)
    TextView tvHylb;
    @BindView(R.id.ll_hylb)
    LinearLayout llHylb;
    @BindView(R.id.tv_ywy)
    TextView tvYwy;
    @BindView(R.id.ll_ywy)
    LinearLayout llYwy;
    @BindView(R.id.et_shmc)
    EditText etShmc;
    @BindView(R.id.et_shjc)
    EditText etShjc;
    @BindView(R.id.et_zcmc)
    EditText etZcmc;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_sfz_a)
    ImageView ivSfzA;
    @BindView(R.id.et_shlxr)
    EditText etShlxr;
    @BindView(R.id.et_shlxr_sfz)
    EditText etShlxrSfz;
    @BindView(R.id.et_lxr_tel)
    EditText etLxrTel;
    @BindView(R.id.et_lxr_email)
    EditText etLxrEmail;
    @BindView(R.id.et_kfdh)
    EditText etKfdh;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.tv_chongfu)
    TextView tvChongfu;
    private List<AreaCityBean> areaCityBeanList = new ArrayList<>();
    private List<BusinessBean> businessBeanList = new ArrayList<>();
    private OptionsPickerView pvClass;
    private List<String> ShangHuClassList = new ArrayList<>();
    private List<String> Classlist1 = new ArrayList<>();
    private PopupWindow popWnd;
    private ChooseClassAdapter chooseClassAdapter;
    private ChooseAreaCityAdapter chooseAreaCityAdapter;
    private String className01 = "";
    private String className02 = "";
    private String className03 = "";
    private String pro = "", city = "", dist = "";
    private int type = 1, city_type = 1;
    private ShopBean shopBean = new ShopBean();
    private SharedPreferences mSp;
    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_shop);
        ButterKnife.bind(this);
        mSp = super.getSharedPreferences("shopBean.class", MODE_PRIVATE);
        getJsonData(getApplicationContext());
        getBusinessJson(getApplicationContext());
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
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
        etShmc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if(!etShmc.getText().toString().trim().equals("")){
                        //Checkname(etShmc.getText().toString().trim());
                    }
                }
            }
        });
        initView();
    }

    private void initView() {
        if (shopBean.getMerchant_business_type() != null) {
            switch (shopBean.getMerchant_business_type()) {
                case 1:
                    tvShlx.setText("企业(包含事业单位)");
                    break;
                case 2:
                    tvShlx.setText("个体工商户");
                    break;
                case 3:
                    tvShlx.setText("小微商户");
                    break;
            }
        }
        if (shopBean.getBusiness_code() != null) {
            for (int i = 0; i < businessBeanList.size(); i++) {
                for (int j = 0; j < businessBeanList.get(i).getClist().size(); j++) {
                    for (int k = 0; k < businessBeanList.get(i).getClist().get(j).getClist().size(); k++) {
                        if (shopBean.getBusiness_code().equals(businessBeanList.get(i).getClist().get(j).getClist().get(k).getClass_id())) {
                            tvHylb.setText(businessBeanList.get(i).getClist().get(j).getClist().get(k).getOne_class()
                                    + "->" + businessBeanList.get(i).getClist().get(j).getClist().get(k).getTwo_class()
                                    + "->" + businessBeanList.get(i).getClist().get(j).getClist().get(k).getThree_class());
                        }
                    }
                }
            }
        }
        if (shopBean.getMerchant_name() != null) {
            etShmc.setText(shopBean.getMerchant_name());
            etShjc.setText(shopBean.getMerchant_alias());
            etZcmc.setText(shopBean.getMerchant_company());
            tvAddress.setText(shopBean.getMerchant_province() + "->" + shopBean.getMerchant_city() + "->" + shopBean.getMerchant_county());
            etAddress.setText(shopBean.getMerchant_address());
            etShlxr.setText(shopBean.getMerchant_person());
            etLxrTel.setText(shopBean.getMerchant_phone());
            etLxrEmail.setText(shopBean.getMerchant_email());
        }
        if (shopBean.getMerchant_service_phone() != "") {
            etKfdh.setText(shopBean.getMerchant_service_phone());
        }
    }

    @OnClick({R.id.back_top, R.id.ll_shlx, R.id.ll_hylb, R.id.iv_sfz_a, R.id.b_submit, R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_shlx:
                //chooseClass(ShangHuClassList);
                break;
            case R.id.ll_hylb:
                //showPopUpWindow();
                break;
            case R.id.iv_sfz_a:
                Album.startAlbum(this, 111, 1);
                break;
            case R.id.b_submit:
                gotoNext();
                break;
            case R.id.ll_address:
                //showPopUpWindow_city();
                break;
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
            ShangHuClassList.add("企业(包含事业单位)");
            ShangHuClassList.add("个体工商户");
            ShangHuClassList.add("小微商户");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*private void chooseClass(List<String> list) {
        pvClass = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                *//*tvClassName.setText(option2.get(options1));*//*
                tvShlx.setText(list.get(options1));
                switch (list.get(options1)) {
                    case "企业(包含事业单位)":
                        shopBean.setMerchant_business_type(1);
                        break;
                    case "个体工商户":
                        shopBean.setMerchant_business_type(2);
                        break;
                    case "小微商户":
                        shopBean.setMerchant_business_type(3);
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

    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.windows_choose, null);
        popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();
        RadioGroup rgMenu = contentView1.findViewById(R.id.rg_menu);
        RadioButton rbClass03 = contentView1.findViewById(R.id.rb_class03);
        TextView tvClose = contentView1.findViewById(R.id.tv_close);
        ListView listView = contentView1.findViewById(R.id.lv_list);
        RadioButton rbClass01 = contentView1.findViewById(R.id.rb_class01);
        RadioButton rbClass02 = contentView1.findViewById(R.id.rb_class02);

        List<BusinessBean> mList = new ArrayList<>();
        mList.addAll(businessBeanList);
        chooseClassAdapter = new ChooseClassAdapter(UploadShopActivity.this, mList, 1);
        listView.setAdapter(chooseClassAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (type == 1) {
                    className01 = mList.get(position).getOne_class();
                    rbClass02.setChecked(true);
                } else if (type == 2) {
                    className02 = mList.get(position).getTwo_class();
                    rbClass03.setChecked(true);
                } else if (type == 3) {
                    className03 = mList.get(position).getThree_class();
                    tvHylb.setText(className01 + "->" + className02 + "->" + className03);
                    shopBean.setBusiness_code(mList.get(position).getClass_id());
                    shopBean.setBusiness_name(className03);
                    popWnd.dismiss();
                }
            }
        });
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.rb_class01:
                        type = 1;
                        mList.clear();
                        mList.addAll(businessBeanList);
                        chooseClassAdapter = new ChooseClassAdapter(UploadShopActivity.this, mList, type);
                        listView.setAdapter(chooseClassAdapter);
                        break;
                    case R.id.rb_class02:
                        type = 2;
                        mList.clear();
                        for (int i = 0; i < businessBeanList.size(); i++) {
                            if (className01.equals(businessBeanList.get(i).getOne_class())) {
                                mList.addAll(businessBeanList.get(i).getClist());
                            }
                        }
                        chooseClassAdapter = new ChooseClassAdapter(UploadShopActivity.this, mList, type);
                        listView.setAdapter(chooseClassAdapter);
                        break;
                    case R.id.rb_class03:
                        type = 3;
                        List<BusinessBean> mmmlist = new ArrayList<>();
                        mmmlist.addAll(mList);
                        mList.clear();
                        for (int i = 0; i < mmmlist.size(); i++) {
                            if (className02.equals(mmmlist.get(i).getTwo_class())) {
                                mList.addAll(mmmlist.get(i).getClist());
                            }
                        }
                        chooseClassAdapter = new ChooseClassAdapter(UploadShopActivity.this, mList, type);
                        listView.setAdapter(chooseClassAdapter);
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
        popWnd.showAtLocation(llHylb, Gravity.BOTTOM, 0, 0);
    }

    private void showPopUpWindow_city() {
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
        tvWindowtitle.setText("省市县选择");
        rbClass01.setText("选择省");
        rbClass02.setText("选择市");
        rbClass03.setText("选择县");
        List<AreaCityBean> mList = new ArrayList<>();
        mList.addAll(areaCityBeanList);
        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShopActivity.this, mList, 1);
        listView.setAdapter(chooseAreaCityAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (city_type == 1) {
                    pro = mList.get(position).getPro_name();
                    shopBean.setMerchant_province(pro);
                    shopBean.setMerchant_province_code(mList.get(position).getPro_code());
                    rbClass02.setChecked(true);
                } else if (city_type == 2) {
                    city = mList.get(position).getCity_name();
                    //tvHylb.setText(mList.get(position).getOne_class()+"->"+mList.get(position).getTwo_class()+"->"+mList.get(position).getThree_class());
                    shopBean.setMerchant_city(city);
                    shopBean.setMerchant_city_code(mList.get(position).getCity_code());
                    rbClass03.setChecked(true);
                    //popWnd.dismiss();
                } else if (city_type == 3) {
                    dist = mList.get(position).getDist_name();
                    shopBean.setMerchant_county(dist);
                    shopBean.setMerchant_county_code(mList.get(position).getDist_code());
                    tvAddress.setText(pro + "->" + city + "->" + dist);
                    popWnd.dismiss();
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
                        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShopActivity.this, mList, city_type);
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
                        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShopActivity.this, mList, city_type);
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
                        chooseAreaCityAdapter = new ChooseAreaCityAdapter(UploadShopActivity.this, mList, city_type);
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
        popWnd.showAtLocation(llHylb, Gravity.BOTTOM, 0, 0);
    }*/

    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void requestPermissionsSuccess() {

    }

    @Override
    public void requestPermissionsFail() {
        finish();
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
            type = 1;
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
                                String img = JSONObject.parseObject(response).getString("data");
                                /*switch (currentPic) {
                                    case 1:
                                        pic_path[0] = JSONObject.parseObject(response).getString("data");
                                        break;
                                    case 2:
                                        pic_path[1] = JSONObject.parseObject(response).getString("data");
                                        break;
                                    case 3:
                                        pic_path[2] = JSONObject.parseObject(response).getString("data");
                                        Glide.with(getApplication()).load(NetConfig.baseurl+pic_path[2]).into(pic3);
                                        break;
                                    case 4:
                                        pic_path[3] = JSONObject.parseObject(response).getString("data");
                                        Glide.with(getApplication()).load(NetConfig.baseurl+pic_path[3]).into(pic4);
                                        break;
                                    case 5:
                                        pic_path[4] = JSONObject.parseObject(response).getString("data");
                                        Glide.with(getApplication()).load(NetConfig.baseurl+pic_path[4]).into(pic5);
                                        break;
                                    case 6:
                                        pic_path[5] = JSONObject.parseObject(response).getString("data");
                                        Glide.with(getApplication()).load(NetConfig.baseurl+pic_path[5]).into(pic6);
                                        break;
                                }*/
                            } else {
                                Toast.makeText(getApplicationContext(), JSONObject.parseObject(response).getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    //存数据
    public void setShopBean() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString("shopBean", JSON.toJSONString(shopBean));
        editor.commit();
        //editor.apply();
        startActivity(new Intent(getApplicationContext(), UploadShop2Activity.class).putExtra("type", "shopBean"));
    }

    //读数据
    public String getShopBean() {
        String info = mSp.getString("shopBean", "");
        return info;
    }

    public void gotoNext() {
        shopBean.setMerchant_name(etShmc.getText().toString());
        shopBean.setMerchant_alias(etShjc.getText().toString());
        shopBean.setMerchant_company(etZcmc.getText().toString());
        shopBean.setMerchant_address(etAddress.getText().toString());
        shopBean.setMerchant_person(etShlxr.getText().toString());
        shopBean.setMerchant_phone(etLxrTel.getText().toString());
        shopBean.setMerchant_email(etLxrEmail.getText().toString());
        shopBean.setMerchant_service_phone(etKfdh.getText().toString());
        if (shopBean.getMerchant_business_type()==null||shopBean.getMerchant_name().isEmpty() || shopBean.getMerchant_alias().isEmpty() || shopBean.getMerchant_company().isEmpty()
                || shopBean.getMerchant_address().isEmpty() || shopBean.getMerchant_person().isEmpty() || shopBean.getMerchant_phone().isEmpty()
                || shopBean.getMerchant_email().isEmpty() || shopBean.getMerchant_county_code() == null || shopBean.getMerchant_county_code().isEmpty()) {
            Toast.makeText(getApplicationContext(), "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }
        setShopBean();
    }

    /*
    * 商户重名检验
    * 接口：http://test.lcsw.cn:8010/lcsw/merchant/200/checkname
    * inst_no	String	15	Y	机构编号，我司分配
        trace_no	String	32	Y	请求流水号
        merchant_name	String	80	Y	商户名称
        key_sign	String	32	Y	签名检验串,拼装所有必传参数+令牌，32位md5加密转换
    * */
    public void Checkname(String merchant_name) {
        String key_sign = "";
        String trace_no = getUUID32();
        Map<String, String> map = new HashMap<>();
        map.put("api_ver", "200");//机构编号，我司分配
        map.put("inst_no", GuoJinApp.inst_no);//机构编号，我司分配
        map.put("trace_no", trace_no);//请求流水号
        map.put("merchant_name", merchant_name);//	商户名称
        key_sign = "api_ver=200&inst_no=" + GuoJinApp.inst_no + "&trace_no=" + trace_no + "&merchant_name=" + merchant_name + "&key=" + GuoJinApp.key;
        key_sign = MD5.md5Password(key_sign);
        map.put("key_sign", key_sign);//请求流水号
        OkHttpUtils.postString()
                .url("http://test.lcsw.cn:8045/lcsw_mch/merchant/200/checkname")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(JSON.toJSONString(map))
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
                            if (JSONObject.parseObject(response).getString("return_code").equals("01")) {
                                tvChongfu.setVisibility(View.GONE);
                            } else {
                                tvChongfu.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    //得到32位的uuid
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
