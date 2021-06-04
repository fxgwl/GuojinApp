package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.PermissionInterface;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.adapters.PicAdapter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.PermissionHelper;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ServiceTuoGuanActivity extends BaseActivity implements PermissionInterface {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.good_name)
    EditText goodName;
    @BindView(R.id.gv_list)
    MyGridView gvList;
    @BindView(R.id.et_good_guige)
    EditText etGoodGuige;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.iv_zheng_shu)
    ImageView ivZhengShu;
    @BindView(R.id.b_submit)
    Button bSubmit;

    private int currentPic = 0;
    private String[] pic_path = new String[4];

    private Activity activity;
    private PicAdapter picAdapter;
    private List<String> picBeanList = new ArrayList<>();
    private PermissionHelper mPermissionHelper;
    private String pictype="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_tuo_guan);
        ButterKnife.bind(this);
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
        activity = this;
        title.setText("托管服务");
    }

    private void initView() {
        picAdapter = new PicAdapter(getApplicationContext(), picBeanList,10);
        gvList.setAdapter(picAdapter);
        picAdapter.setListener(new PicAdapter.PicClickListener() {
            @Override
            public void addPic() {
                if (picBeanList.size() < 10 && picBeanList.size() != 0) {
                    pictype = "normal";
                    Album.startAlbum(activity, 111, 10 - picBeanList.size()
                            ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//相册多选
                } else {
                    pictype = "duo";
                    Album.startAlbum(activity, 111, 10
                            ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//相册多选
                }
            }

            @Override
            public void delPic(int position) {
                picBeanList.remove(position);
                picAdapter.notifyDataSetChanged();
            }

            @Override
            public void ChoosePic(int position) {
                pictype = "dan";
                currentPic = position;
                Album.startAlbum(activity, 111, 1
                        ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//相册单选

            }

            @Override
            public void setContentItem(int position) {

            }
        });
    }

    @OnClick({R.id.back_top, R.id.iv_zheng_shu, R.id.b_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.iv_zheng_shu:
                Album.startAlbum(activity, 222, 1);//相册单选
                break;
            case R.id.b_submit:
                AlertDialog dlg = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                        .setTitle("是否确认提交？")
                        .setItems(new String[]{"确认", "取消"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // 这里item是根据选择的方式，
                                if (item == 0) {
                                    Submit();
                                } else if (item == 1) {

                                }
                            }
                        }).create();
                dlg.show();
                break;
        }
    }

    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        initView();
    }

    @Override
    public void requestPermissionsFail() {
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                /*if (pictype.equals("duo")) {
                    picBeanList.clear();
                }*/
                for (int i = 0; i < pathList.size(); i++) {
                    int finalI = i;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            fileUpload(new File(pathList.get(finalI)));
                        }
                    }.start();
                }
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }else if (requestCode == 111) {
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                if (pictype.equals("duo")) {
                    picBeanList.clear();
                }
                for (int i = 0; i < pathList.size(); i++) {
                    int finalI = i;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            fileUpload1(new File(pathList.get(finalI)));
                        }
                    }.start();
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
                .addParams("user_id", String.valueOf(MyUtils.getUser().getUser_id()))
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
                            if (com.alibaba.fastjson.JSONObject.parseObject(response).getInteger("code") == 0) {
                                pic_path[0] = com.alibaba.fastjson.JSONObject.parseObject(response).getString("data");
                                Glide.with(getApplicationContext()).load(NetConfig.baseurl+pic_path[0]).into(ivZhengShu);
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void fileUpload1(File file) {
        OkHttpUtils.post()
                .url(NetConfig.fileUpload)
                .addFile("file", "crop.jpg", file)
                .addParams("user_id", String.valueOf(MyUtils.getUser().getUser_id()))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", ">>" + e.getMessage());
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        picAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", ">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            if (com.alibaba.fastjson.JSONObject.parseObject(response).getInteger("code") == 0) {
                                List<String> pic = new ArrayList<>();
                                pic.add(com.alibaba.fastjson.JSONObject.parseObject(response).getString("data"));
                                if (pictype.equals("duo")) {
                                    picBeanList.addAll(pic);
                                } else if (pictype.equals("normal")) {
                                    picBeanList.addAll(pic);
                                } else {
                                    picBeanList.set(currentPic, pic.get(0));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                        picAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void Submit() {
        if(goodName.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"物品名称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etGoodGuige.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"物品规格不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDetail.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"物品详情不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pic_path[0]==null || pic_path[0].equals("")){
            Toast.makeText(getApplicationContext(),"证书不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String imgs="";
        if(picBeanList!=null && picBeanList.size()>0){
            for(int i=0;i<picBeanList.size();i++){
                if(imgs.equals("")){
                    imgs=picBeanList.get(i);
                }else{
                    imgs += ","+picBeanList.get(i);
                }
            }
        }else{
            Toast.makeText(getApplicationContext(),"请上传物品图片",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("good_name", goodName.getText().toString().trim());
        map.put("good_guige", etGoodGuige.getText().toString().trim());
        map.put("good_detail", etDetail.getText().toString().trim());
        map.put("good_zhengshu", pic_path[0]);
        map.put("good_imgs", imgs);
        OkHttpUtils.post()
                .url(NetConfig.submitTuoGuan)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    Toast.makeText(getApplication(), "提交成功", Toast.LENGTH_SHORT).show();
                                    setResult(1);
                                    finish();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
