package com.axehome.www.guojinapp.ui.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.AddressBean;
import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bigkoo.pickerview.OptionsPickerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class NewAddressActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.b_cancel)
    Button bCancel;
    @BindView(R.id.ll_pro)
    LinearLayout llPro;

    private AddressBean addressBean;

    private OptionsPickerView pvClass;

    private ArrayList<String> proList=new ArrayList<>();
    private ArrayList<AreaCityBean> provinceList = new ArrayList<>();//创建存放省份实体类的集合

    private ArrayList<String> cities ;//创建存放城市名称集合
    private ArrayList<List<String>> citiesList= new ArrayList<>();//创建存放城市名称集合的集合

    private ArrayList<String> areas ;//创建存放区县名称的集合
    private ArrayList<List<String>> areasList ;//创建存放区县名称集合的集合
    private ArrayList<List<List<String>>> areasListsList = new ArrayList<>();//创建存放区县集合的集合的集合

    private String s_pro=null,s_city=null,s_area=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);
        String strJson = getIntent().getStringExtra("addressBean");
        if (strJson != null && !strJson.equals("")) {
            addressBean = JSONObject.parseObject(strJson, AddressBean.class);
            initView();
        }
        getJsonData(getApplicationContext());
    }

    private void initView() {
        etName.setText(addressBean.getAddress_name());
        etTel.setText(addressBean.getAddress_phone());
        etDetail.setText(addressBean.getAddress_detail());
        tvPro.setText(addressBean.getAddress_pro() + addressBean.getAddress_city() + addressBean.getAddress_area());
        s_pro= addressBean.getAddress_pro();
        s_city = addressBean.getAddress_city();
        s_area = addressBean.getAddress_area();

    }

    @OnClick({R.id.back_top, R.id.ll_pro, R.id.b_submit, R.id.b_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_pro:
                if (pvClass != null && pvClass.isShowing()) {
                    pvClass.dismiss();
                }
                chooseCity(tvPro);
                break;
            case R.id.b_submit:
                saveOrUpdate();
                break;
            case R.id.b_cancel:
                dele();
                break;
        }
    }
    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void chooseCity(TextView textView) {
        hideSoftKeyboard(this);
        pvClass = new OptionsPickerView(this);
        //设置三级联动的效果
        pvClass.setPicker(proList,citiesList,areasListsList,true);

        //设置可以循环滚动,true表示这一栏可以循环,false表示不可以循环
        pvClass.setCyclic(true,false,false);

        //设置默认选中的位置
        pvClass.setSelectOptions(0,0,0);
        pvClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = proList.get(options1);
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = proList.get(options1)
                            + " " + areasListsList.get(options1).get(option2).get(options3);
                } else {
                    address = proList.get(options1)
                            + " " + citiesList.get(options1).get(option2)
                            + " " + areasListsList.get(options1).get(option2).get(options3);
                }
                s_pro=proList.get(options1);
                s_city=citiesList.get(options1).get(option2);
                s_area=areasListsList.get(options1).get(option2).get(options3);

                textView.setText(address);
                //Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_LONG).show();
            }
        });
        pvClass.show();
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
            provinceList.addAll(JSONObject.parseArray(stringBuffer.toString(),AreaCityBean.class));
            parseJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析获得的json字符串,放在各个集合中
    private void parseJson(){
        if(provinceList!=null && provinceList.size()>0){
            for(AreaCityBean i : provinceList){
                cities = new ArrayList<>();//创建存放城市名称集合
                areasList = new ArrayList<>();//创建存放区县名称的集合的集合
                if(i.getChildren()!=null && i.getChildren().size()>0){
                    for(AreaCityBean j: i.getChildren()){
                        cities.add(j.getName());
                        areas = new ArrayList<>();//创建存放区县名称的集合
                        if(j.getChildren()!=null && j.getChildren().size()>0){
                            for(AreaCityBean k:j.getChildren()){
                                areas.add(k.getName());
                            }
                            areasList.add(areas);
                        }
                    }
                    citiesList.add(cities);
                    areasListsList.add(areasList);
                }
                proList.add(i.getName());
            }
        }
    }

    private void saveOrUpdate() {
        if(s_pro==null ||s_city==null ||s_area==null){
            Toast.makeText(getApplicationContext(),"参数不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(etName.getText().toString().trim().equals("") ||etTel.getText().toString().trim().equals("")
                ||etDetail.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"参数不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("address_use_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("address_pro", s_pro);
        map.put("address_city", s_city);
        map.put("address_area", s_area);
        map.put("address_detail", etDetail.getText().toString().trim());
        map.put("address_name", etName.getText().toString().trim());
        map.put("address_phone", etTel.getText().toString().trim());
        if(addressBean!=null){
            map.put("address_id", String.valueOf(addressBean.getAddress_id()));
            OkHttpUtils.post()
                    .url(NetConfig.updateAddress)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("eeee", "(updateAddress.java:168)" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("rrr",
                                    "(updateAddress.java:175)" + response);
                            if (response == null) {
                                //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            if (jsonObject.getInteger("code") == 0) {
                                setResult(1);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            map.put("is_checked", "0");
            OkHttpUtils.post()
                    .url(NetConfig.saveAddress)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("eeee", "(saveAddress.java:168)" + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("rrr",
                                    "(saveAddress.java:175)" + response);
                            if (response == null) {
                                //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            if (jsonObject.getInteger("code") == 0) {
                                setResult(1);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    private void dele() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("address_id", String.valueOf(addressBean.getAddress_id()));
        OkHttpUtils.post()
                .url(NetConfig.delAddress)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("eeee", "(saveAddress.java:168)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("rrr",
                                "(saveAddress.java:175)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            setResult(1);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
