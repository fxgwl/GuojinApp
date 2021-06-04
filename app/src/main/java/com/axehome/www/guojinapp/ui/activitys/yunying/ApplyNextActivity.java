package com.axehome.www.guojinapp.ui.activitys.yunying;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.listeners.PermissionInterface;
import com.axehome.www.guojinapp.ui.activitys.BaseActivity;
import com.axehome.www.guojinapp.ui.adapters.PicAdapter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.PermissionHelper;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.yanzhenjie.album.Album;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ApplyNextActivity extends BaseActivity implements PermissionInterface {

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
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.ll_pro)
    LinearLayout llPro;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.et_backup)
    EditText etBackup;
    @BindView(R.id.gv_list)
    MyGridView gvList;

    private OptionsPickerView pvClass;

    private ArrayList<String> proList = new ArrayList<>();
    private ArrayList<AreaCityBean> provinceList = new ArrayList<>();//创建存放省份实体类的集合

    private ArrayList<String> cities;//创建存放城市名称集合
    private ArrayList<List<String>> citiesList = new ArrayList<>();//创建存放城市名称集合的集合

    private ArrayList<String> areas;//创建存放区县名称的集合
    private ArrayList<List<String>> areasList;//创建存放区县名称集合的集合
    private ArrayList<List<List<String>>> areasListsList = new ArrayList<>();//创建存放区县集合的集合的集合

    private String s_pro = null, s_city = null, s_area = null;

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
        setContentView(R.layout.activity_apply_next);
        ButterKnife.bind(this);
        activity= this;
        mPermissionHelper = new PermissionHelper(this, this);
        mPermissionHelper.requestPermissions();
        getJsonData(getApplicationContext());
        title.setText("入驻信息");
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
                    Album.startAlbum(activity, 111,10
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
            provinceList.addAll(JSONObject.parseArray(stringBuffer.toString(), AreaCityBean.class));
            parseJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //解析获得的json字符串,放在各个集合中
    private void parseJson() {
        if (provinceList != null && provinceList.size() > 0) {
            for (AreaCityBean i : provinceList) {
                cities = new ArrayList<>();//创建存放城市名称集合
                areasList = new ArrayList<>();//创建存放区县名称的集合的集合
                if (i.getChildren() != null && i.getChildren().size() > 0) {
                    for (AreaCityBean j : i.getChildren()) {
                        cities.add(j.getName());
                        areas = new ArrayList<>();//创建存放区县名称的集合
                        if (j.getChildren() != null && j.getChildren().size() > 0) {
                            for (AreaCityBean k : j.getChildren()) {
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

    @OnClick({R.id.back_top, R.id.ll_pro,R.id.ll_subimt})
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
            case R.id.ll_subimt:
                toSubmit();
                break;
        }
    }

    private void chooseCity(TextView textView) {
        hideSoftKeyboard(this);
        pvClass = new OptionsPickerView(this);
        //设置三级联动的效果
        pvClass.setPicker(proList, citiesList, areasListsList, true);
        //设置可以循环滚动,true表示这一栏可以循环,false表示不可以循环
        pvClass.setCyclic(true, false, false);
        //设置默认选中的位置
        pvClass.setSelectOptions(0, 0, 0);
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
                s_pro = proList.get(options1);
                s_city = citiesList.get(options1).get(option2);
                s_area = areasListsList.get(options1).get(option2).get(options3);

                textView.setText(address);
                //Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_LONG).show();
            }
        });
        pvClass.show();
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
        if (requestCode == 111) {
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
    private void toSubmit() {
        if(etName.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"姓名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(s_pro.equals("")||s_city.equals("")||s_area.equals("")){
            Toast.makeText(getApplicationContext(),"请选择地址",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDetail.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"详细地址不能为空",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"请上传附件图片",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));
        map.put("real_name", etName.getText().toString().trim());
        map.put("pro", s_pro);
        map.put("detail", etDetail.getText().toString().trim());
        map.put("city", s_city);
        map.put("area", s_area);
        map.put("order_pic2", imgs);
        map.put("backup", etBackup.getText().toString().trim());
        OkHttpUtils.post()
                .url(NetConfig.toGongyingshang)
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
                                    //setResult(1);
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
