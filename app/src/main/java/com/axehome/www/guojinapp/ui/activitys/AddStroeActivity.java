package com.axehome.www.guojinapp.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.PermissionHelper;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.PermissionInterface;
import com.bumptech.glide.Glide;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddStroeActivity extends BaseActivity implements PermissionInterface, TencentLocationListener {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.et_store_name)
    EditText etStoreName;
    @BindView(R.id.et_store_person)
    EditText etStorePerson;
    @BindView(R.id.et_store_phone)
    EditText etStorePhone;
    @BindView(R.id.et_store_email)
    EditText etStoreEmail;
    @BindView(R.id.et_store_addre)
    EditText etStoreAddre;
    @BindView(R.id.b_submit)
    Button bSubmit;
    @BindView(R.id.iv_store_logo)
    ImageView ivStoreLogo;

    private String merchant_no = "";
    private PermissionHelper mPermissionHelper;
    private TencentLocationManager mLocationManager;
    private double lng = 0, lat = 0;
    private String store_logo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stroe);
        ButterKnife.bind(this);
        merchant_no = getIntent().getStringExtra("merchant_no");
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
        initView();
    }

    private void initView() {
        title.setText("添加门店");
    }

    @OnClick({R.id.back_top, R.id.b_submit,R.id.iv_store_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.b_submit:
                Submit();
                break;
            case R.id.iv_store_logo:
                Album.startAlbum(this, 111, 1, getResources().getColor(R.color.background), getResources().getColor(R.color.barcolor));
                break;
        }
    }

    private void Submit() {
        Map<String, String> map = new HashMap<>();
        if (!etStoreName.getText().toString().trim().equals("")) {
            map.put("store_name", etStoreName.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(), "请填写门店名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!etStorePhone.getText().toString().trim().equals("")) {
            map.put("store_phone", etStorePhone.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(), "请填写联系电话", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!etStoreEmail.getText().toString().trim().equals("")) {
            map.put("store_email", etStoreEmail.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(), "请填写联系邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("merchant_no", merchant_no);
        map.put("store_addre", etStoreAddre.getText().toString().trim());
        map.put("store_person", etStorePerson.getText().toString().trim());
        if (store_logo == null) {
            Toast.makeText(getApplicationContext(), "请上传门店logo", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("store_logo", store_logo);
        if (lat == 0 || lng == 0) {
            Toast.makeText(getApplicationContext(), "获取位置信息错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("store_lat", String.valueOf(lat));
        map.put("store_lng", String.valueOf(lng));
        OkHttpUtils.post()
                .url(NetConfig.addStroe)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SettingsFragment.java:105)" + e.getMessage());
                        Toast.makeText(getApplicationContext(), "系统故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "系统故障", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                            setResult(11);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        SetupLocation();
    }

    @Override
    public void requestPermissionsFail() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(AddStroeActivity.this, permissions[i]);
                    if (!showRequestPermission) {
                        Toast.makeText(this, "请打开系统启用位置权限", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            Log.e(requestCode+"",permissions.toString()+"results =="+grantResults.toString());
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        Log.d("latitude", tencentLocation.getCity() + tencentLocation.getLatitude() + "===longitude经度==" + tencentLocation.getLongitude());
        lng = tencentLocation.getLongitude();
        lat = tencentLocation.getLatitude();
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.d("i==", i + "==" + s + "s1=" + s1);
    }

    public void SetupLocation() {
        mLocationManager = TencentLocationManager.getInstance(getApplicationContext());
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(10000);//用户可以自定义定位间隔，时间单位为毫秒，不得小于1000毫秒:
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);//设置请求级别
        request.setAllowGPS(true);//是否允许使用GPS
        /*request. setAllowDirection(true);//是否需要获取传感器方向
        request.setIndoorLocationMode(true);//是否需要开启室内定位*/
        mLocationManager.requestLocationUpdates(request, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationManager!=null){
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                                store_logo = JSONObject.parseObject(response).getString("data");
                                Glide.with(getApplicationContext()).load(NetConfig.baseurl + store_logo).into(ivStoreLogo);
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
