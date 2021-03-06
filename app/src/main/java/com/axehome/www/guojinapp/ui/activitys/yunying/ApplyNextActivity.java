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
    private ArrayList<AreaCityBean> provinceList = new ArrayList<>();//????????????????????????????????????

    private ArrayList<String> cities;//??????????????????????????????
    private ArrayList<List<String>> citiesList = new ArrayList<>();//???????????????????????????????????????

    private ArrayList<String> areas;//?????????????????????????????????
    private ArrayList<List<String>> areasList;//???????????????????????????????????????
    private ArrayList<List<List<String>>> areasListsList = new ArrayList<>();//??????????????????????????????????????????

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
        title.setText("????????????");
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
                            ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//????????????
                } else {
                    pictype = "duo";
                    Album.startAlbum(activity, 111,10
                            ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//????????????
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
                        ,getResources().getColor(R.color.background),getResources().getColor(R.color.barcolor));//????????????

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

    //???????????????json?????????,?????????????????????
    private void parseJson() {
        if (provinceList != null && provinceList.size() > 0) {
            for (AreaCityBean i : provinceList) {
                cities = new ArrayList<>();//??????????????????????????????
                areasList = new ArrayList<>();//??????????????????????????????????????????
                if (i.getChildren() != null && i.getChildren().size() > 0) {
                    for (AreaCityBean j : i.getChildren()) {
                        cities.add(j.getName());
                        areas = new ArrayList<>();//?????????????????????????????????
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
        //???????????????????????????
        pvClass.setPicker(proList, citiesList, areasListsList, true);
        //????????????????????????,true???????????????????????????,false?????????????????????
        pvClass.setCyclic(true, false, false);
        //???????????????????????????
        pvClass.setSelectOptions(0, 0, 0);
        pvClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //?????????????????????????????????????????????
                String city = proList.get(options1);
                String address;
                //  ?????????????????????????????????????????????????????????/???
                if ("?????????".equals(city) || "?????????".equals(city) || "?????????".equals(city) || "?????????".equals(city) || "??????".equals(city) || "??????".equals(city)) {
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
                //Toast.makeText(getApplicationContext(),"???????????????",Toast.LENGTH_LONG).show();
            }
        });
        pvClass.show();
    }

    /**
     * ???????????????(????????????Activity???????????????Fragment)
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
        //????????????????????????????????????
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
            //????????????????????????????????????????????????
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) { // ?????????????????????
                // ?????????????????????????????????List???
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
            } else if (resultCode == RESULT_CANCELED) { // ?????????????????????
                // ??????????????????????????????????????????
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
                        Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
                        picAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", ">>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT).show();
                            }
                        }
                        picAdapter.notifyDataSetChanged();
                    }
                });
    }
    private void toSubmit() {
        if(etName.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"??????????????????",Toast.LENGTH_SHORT).show();
            return;
        }
        if(s_pro.equals("")||s_city.equals("")||s_area.equals("")){
            Toast.makeText(getApplicationContext(),"???????????????",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etDetail.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"????????????????????????",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"?????????????????????",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
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
