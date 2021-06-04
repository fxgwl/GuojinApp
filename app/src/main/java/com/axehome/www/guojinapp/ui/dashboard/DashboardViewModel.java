package com.axehome.www.guojinapp.ui.dashboard;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.JianDingBean;
import com.axehome.www.guojinapp.beans.ShopBean;
import com.axehome.www.guojinapp.beans.StoreBean;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Call;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<JianDingBean>> payInfoList;
    private MutableLiveData<List<ShopBean>> shopList;
    private MutableLiveData<List<StoreBean>> storeList;
    private MutableLiveData<String> mBalance;
    private MutableLiveData<String> mBalanceAble;
    public DashboardViewModel() {
        payInfoList = new MutableLiveData<>();
        shopList = new MutableLiveData<>();
        storeList = new MutableLiveData<>();
        mBalance = new MutableLiveData<>();
        mBalanceAble = new MutableLiveData<>();
        //mText.setValue("This is dashboard fragment");
        PayInfoList();
        //ShopBeanList();
        //StoreBeanList();
    }

    public LiveData<List<JianDingBean>> getPayInfoList() {
        return payInfoList;
    }
    public LiveData<List<ShopBean>> getShopList() {
        return shopList;
    }
    public LiveData<List<StoreBean>> getStoreList() {
        return storeList;
    }
    public LiveData<String> getBalance() {
        return mBalance;
    }
    public LiveData<String> getBalanceAble() {
        return mBalanceAble;
    }
    private void PayInfoList() {
        if(MyUtils.getUser()==null){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        /*map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));*/
        OkHttpUtils.post()
                .url(NetConfig.getJianDingUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(payInfo.java:71)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<JianDingBean> payInfoBeans= new ArrayList<>();
                            payInfoBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), JianDingBean.class));
                            payInfoList.setValue(payInfoBeans);
                            mBalance.setValue(String.valueOf(jsonObject.getDouble("trace_money")));
                        }else{

                        }
                    }
                });
    }
    private void ShopBeanList(){
        if(MyUtils.getUser()==null){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.getShopList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<ShopBean> shopBeans= new ArrayList<>();
                            shopBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), ShopBean.class));
                            shopList.setValue(shopBeans);
                        }else{

                        }
                    }
                });
    }
    private void StoreBeanList(){
        if(MyUtils.getUser()==null){
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.getStoreAllForUserId)
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
                            storeList.setValue(storeBeans);
                        } else {
                            //Toast.makeText(getApplicationContext(),"无此数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}