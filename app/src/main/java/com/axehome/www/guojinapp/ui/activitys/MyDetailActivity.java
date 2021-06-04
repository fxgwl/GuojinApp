package com.axehome.www.guojinapp.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.GuoJinApp;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.User;
import com.axehome.www.guojinapp.listeners.PermissionInterface;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.PermissionHelper;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.bumptech.glide.Glide;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class MyDetailActivity extends BaseActivity implements PermissionInterface {


    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_fankui)
    LinearLayout llFankui;
    @BindView(R.id.b_submit)
    Button bSubmit;
    private PermissionHelper mPermissionHelper;
    private User user;
    private String username = "", head_img = "", ali_num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail);
        ButterKnife.bind(this);
        user = MyUtils.getUser();
        initView();
    }

    private void initView() {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("保存");
        title.setText("个人资料");
        Glide.with(getApplicationContext()).load(NetConfig.baseurl + user.getHead_img()).into(civHead);
        etName.setText(user.getUsername());
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
                Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        Album.startAlbum(this, 111, 1, getResources().getColor(R.color.background), getResources().getColor(R.color.barcolor));
    }

    @Override
    public void requestPermissionsFail() {

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
                .addParams("user_id",String.valueOf(MyUtils.getUser().getUser_id()))
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
                                head_img = JSONObject.parseObject(response).getString("data");
                                Glide.with(getApplicationContext()).load(NetConfig.baseurl + head_img).into(civHead);
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void UpdataUser() {
        Map<String, String> map = new HashMap<>();
        if (!etName.getText().toString().trim().equals("")) {
            map.put("username", etName.getText().toString().trim());
        } else {
            Toast.makeText(getApplicationContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!head_img.equals("")) {
            map.put("head_img", head_img);
        }
        map.put("user_id", String.valueOf(user.getUser_id()));
        OkHttpUtils.post()
                .url(NetConfig.userModify)
                .params(map)
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
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.back_top, R.id.civ_head, R.id.ll_username, R.id.ll_about, R.id.ll_fankui,
            R.id.b_submit,R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.civ_head:
                mPermissionHelper = new PermissionHelper(this, this);
                mPermissionHelper.requestPermissions();
                break;
            case R.id.ll_username:
                break;
            case R.id.ll_about:
                break;
            case R.id.ll_fankui:
                break;
            case R.id.b_submit:
                SPUtils.clear(this);
                startActivity(new Intent(this, LoginActivity.class)
                        .putExtra("from", "setup"));
                GuoJinApp.getInstance().out();
                break;
            case R.id.tv_right:
                UpdataUser();
                break;

        }
    }
}
