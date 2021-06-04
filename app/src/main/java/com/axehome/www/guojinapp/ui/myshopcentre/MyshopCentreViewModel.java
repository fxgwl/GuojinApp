package com.axehome.www.guojinapp.ui.myshopcentre;

import android.text.TextUtils;
import android.util.Log;

import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Call;

public class MyshopCentreViewModel extends ViewModel {

    private MutableLiveData<User> user;


    public MyshopCentreViewModel() {
        user = new MutableLiveData<>();
        getUserDetail();
    }

    public LiveData<User> getText() {
        return user;
    }
    private void getUserDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.userDetail)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Log.e("user_detail","网络故障");
                        } else {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    String data = jsonObject.getString("data");
                                    SPUtils.put("user", data);
                                    user.setValue(MyUtils.getUser());
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Log.e("user_detail","故障"+msg);
                                    //Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}