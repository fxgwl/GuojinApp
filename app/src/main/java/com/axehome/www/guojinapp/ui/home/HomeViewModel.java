package com.axehome.www.guojinapp.ui.home;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.BannerBean;
import com.axehome.www.guojinapp.beans.GoodClassBean;
import com.axehome.www.guojinapp.beans.MsgBean;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
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

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<PreGoodsBean>> mGoodList;
    private MutableLiveData<List<BannerBean>> bannerList;
    private MutableLiveData<List<MsgBean>> msgList;
    private MutableLiveData<List<GoodClassBean>> mClassList;
    private List<BannerBean> bannerBeanList = new ArrayList<>();
    public HomeViewModel() {
        mGoodList = new MutableLiveData<>();
        bannerList= new MutableLiveData<>();
        msgList= new MutableLiveData<>();
        mClassList = new MutableLiveData<>();
        GetBanner();
        MsgList();
        ClassList();
        GoodList();
    }

    public LiveData<List<PreGoodsBean>> getGoodList() {
        return mGoodList;
    }
    public LiveData<List<BannerBean>> getBannerList(){
        return bannerList;
    }
    public LiveData<List<MsgBean>> getMsgList(){
        return msgList;
    }
    public LiveData<List<GoodClassBean>> getClassList(){
        return mClassList;
    }
    public void GetBanner(){
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("limit","5");
        OkHttpUtils.post()
                .url(NetConfig.getBaseurl)
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
                            bannerBeanList.clear();
                            bannerBeanList.addAll(JSON.parseArray(jsonObject.getString("data"),BannerBean.class));
                            bannerList.setValue(bannerBeanList);
                        }else{

                        }
                    }
                });
    }
    private void MsgList() {
        OkHttpUtils.post()
                .url(NetConfig.getMsg)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:239)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<MsgBean> msgBeanList = JSON.parseArray(jsonObject.getJSONObject("data").getString("list"),MsgBean.class);
                            msgList.setValue(msgBeanList);
                        }else{

                        }
                    }
                });
    }

    private void ClassList() {
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        OkHttpUtils.post()
                .url(NetConfig.getGoodsClass)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:136)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:142)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<GoodClassBean> storeBeans = JSON.parseArray(jsonObject.getString("data"),GoodClassBean.class);
                            mClassList.setValue(storeBeans);
                        }else{

                        }
                    }
                });
    }
    private void GoodList() {
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("limit","100");
        map.put("good_type","5");
        OkHttpUtils.post()
                .url(NetConfig.getGoodsList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:136)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:142)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<PreGoodsBean> goodsBeans = JSON.parseArray(jsonObject.getJSONObject("data").getString("list"),PreGoodsBean.class);
                            mGoodList.setValue(goodsBeans);
                        }else{

                        }
                    }
                });
    }
}