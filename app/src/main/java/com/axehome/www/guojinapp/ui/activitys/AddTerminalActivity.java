package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.StoreBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.bigkoo.pickerview.OptionsPickerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddTerminalActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.ll_store_name)
    LinearLayout llStoreName;
    @BindView(R.id.tv_terminal_name)
    TextView tvTerminalName;
    @BindView(R.id.ll_terminal_name)
    LinearLayout llTerminalName;
    @BindView(R.id.b_submit)
    Button bSubmit;

    private String merchant_no = "";
    private List<StoreBean>storeBeanList=new ArrayList<>();
    private OptionsPickerView pvClass;
    private ArrayList<String> option1=new ArrayList<>();
    private ArrayList<String> option2=new ArrayList<>();
    private String store_name="",terminal_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terminal);
        ButterKnife.bind(this);
        title.setText("新建终端");
        merchant_no = getIntent().getStringExtra("merchant_no");
        initView();
        initData();
    }

    private void initView() {
        option2.add("名称1");
        option2.add("名称2");
        option2.add("名称3");
    }
    private void initData() {
        getStoreForShop();
    }

    @OnClick({R.id.back_top,R.id.ll_store_name,R.id.ll_terminal_name,R.id.b_submit})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.back_top:
                finish();
                break;
            case R.id.ll_store_name:
                if(option1==null ||option1.size()==0){
                    Toast.makeText(getApplicationContext(),"请先创建门店",Toast.LENGTH_SHORT).show();
                    return;
                }
                chooseClass(option1,tvStoreName,1);
                break;
            case R.id.ll_terminal_name:
                chooseClass(option2,tvTerminalName,0);
                break;
            case R.id.b_submit:
                Submit();
                break;
        }
    }
    private void chooseClass(ArrayList<String> list, TextView textView,int type) {
        if(pvClass!=null && pvClass.isShowing()){
            pvClass.dismiss();
        }
        pvClass = new OptionsPickerView(this);
        pvClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3) {
                /*tvClassName.setText(option2.get(options1));*/
                textView.setText(list.get(options1));
                if(type==1){
                    store_name=list.get(options1);
                }else if(type==0){
                    terminal_name =list.get(options1);
                }
            }
        });
        pvClass.setPicker(list, null, null,false);
        pvClass.show();
    }
    public void getStoreForShop() {
        if(merchant_no==null||merchant_no.equals("")){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("merchant_no", merchant_no);
        OkHttpUtils.post()
                .url(NetConfig.getStoreForShopAll)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SettingsFragment.java:105)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            List<StoreBean> storeBeans = new ArrayList<>();
                            storeBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), StoreBean.class));
                            storeBeanList.addAll(storeBeans);
                            if(option1!=null && option1.size()>0){
                                option1.clear();
                            }
                            for(int i= 0;i<storeBeanList.size();i++){
                                option1.add(storeBeanList.get(i).getStore_name());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Submit() {
        Map<String, String> map = new HashMap<>();
        String store_code="";
        if(!store_name.equals("")){
            for(int i=0;i<storeBeanList.size();i++){
                if(store_name.equals(storeBeanList.get(i).getStore_name())){
                    store_code=storeBeanList.get(i).getStore_code();
                }
            }
            map.put("store_code", store_code);
        }else{
            Toast.makeText(getApplicationContext(),"请选门店名称",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!terminal_name.equals("")){
            map.put("terminal_name", terminal_name);
        }else{
            Toast.makeText(getApplicationContext(),"请选终端类型",Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("merchant_no", merchant_no);
        OkHttpUtils.post()
                .url(NetConfig.addTerminal)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SettingsFragment.java:105)" + e.getMessage());
                        Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            Toast.makeText(getApplicationContext(),"创建成功",Toast.LENGTH_SHORT).show();
                            setResult(11);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
