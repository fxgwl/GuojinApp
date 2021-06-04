package com.axehome.www.guojinapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by Axehome_Mr.z on 2019/5/16 12:32
 * $Describe
 */
public class MyHttp {
    public MyHttp() {
    }

    public MyHttp post(Map<String, String> map, String url, final String msg) {
        OkHttpUtils.post()
                .url(url)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("onError", msg+">>" + e.getMessage());
                        getData.Data("");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", msg+">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            getData.Data("");
                        } else {
                            getData.Data(response);
                        }
                    }
                });
        return this;
    }

    public MyHttp post(String str, String url, final String msg) {
        OkHttpUtils.post()
                .url(url)
                .addParams("jsonStr",str)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", msg+">>" + e.getMessage());
                        getData.Data("");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", msg+">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            getData.Data("");
                        } else {
                            getData.Data(response);
                        }
                    }
                });
        return this;
    }

    public MyHttp get(String url, final String msg) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", msg+">>" + e.getMessage());
                        getData.Data("");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", msg+">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            getData.Data("");
                        } else {
                            getData.Data(response);
                        }
                    }
                });
        return this;
    }

    //写一个接口
    public interface GetData {
        void Data(String s);
    }

    private GetData getData;

    public void getDataLisenter(GetData getData) {
        this.getData = getData;
    }
}
