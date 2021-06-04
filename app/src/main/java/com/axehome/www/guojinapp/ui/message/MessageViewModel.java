package com.axehome.www.guojinapp.ui.message;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.beans.BannerBean;
import com.axehome.www.guojinapp.beans.GoodClassBean;
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

public class MessageViewModel extends ViewModel {

    private MutableLiveData<List<GoodClassBean>> mClassList;
    private MutableLiveData<List<BannerBean>> bannerList;
    private List<BannerBean> bannerBeanList = new ArrayList<>();

    public MessageViewModel() {
        mClassList = new MutableLiveData<>();
        bannerList= new MutableLiveData<>();
        //mText.setValue("This is home fragment");
        getList();
        GetBanner();
    }

    public LiveData<List<GoodClassBean>> getGoodList() {
        return mClassList;
    }
    public LiveData<List<BannerBean>> getBannerList(){
        return bannerList;
    }

    public void getList(){
        Map<String,String> map = new HashMap<>();
        map.put("good_type","1");
        OkHttpUtils.post()
                .url(NetConfig.getGoodsListForShangPu)
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
                            List<GoodClassBean> goodClassBeans= new ArrayList<>();
                            goodClassBeans.addAll(JSON.parseArray(jsonObject.getString("data"), GoodClassBean.class));
                            mClassList.setValue(goodClassBeans);
                        }else{

                        }
                    }
                });
    }

    public void GetBanner(){
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("limit","5");
        map.put("banner_type","2");
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
}