package com.axehome.www.guojinapp.ui.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bigkoo.pickerview.OptionsPickerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class JiFenServiceActivity extends BaseActivity {

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
    @BindView(R.id.ll_choose)
    LinearLayout llChoose;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.et_password)
    EditText etPassword;

    private ArrayList<String> nameList = new ArrayList<>();
    private OptionsPickerView pvClass;
    private String value = "", mallType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_fen_service);
        ButterKnife.bind(this);
        initView();
        getServiceList();
        /*nameList.add("one");
        nameList.add("three");*/
        title.setText("积分充值");
    }

    private void initView() {

    }

    @OnClick({R.id.back_top, R.id.bt_submit, R.id.ll_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.bt_submit:
                getUserJifen();
                break;
            case R.id.ll_service:
                chooseCity(tvName);
                break;
        }
    }

    private void chooseCity(TextView textView) {
        hideSoftKeyboard(this);
        pvClass = new OptionsPickerView(this);
        //设置三级联动的效果
        pvClass.setPicker(nameList);

        //设置可以循环滚动,true表示这一栏可以循环,false表示不可以循环
        pvClass.setCyclic(false, false, false);

        //设置默认选中的位置
        pvClass.setSelectOptions(0);
        pvClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if(nameList!=null){
                    mallType = nameList.get(options1);
                    textView.setText(mallType);
                    getJiFenNum(mallType);
                }
                //返回的分别是三个级别的选中位置
                //Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_LONG).show();
            }
        });
        pvClass.show();
    }

    private void getServiceList() {
        OkHttpUtils.post()
                .url(NetConfig.getServiceList)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getServiceList.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getServiceList.java:239)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            nameList.addAll(JSON.parseArray(jsonObject.getString("data"), String.class));
                        } else {

                        }
                    }
                });
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


    private void getJiFenNum(String type) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", MyUtils.getUser().getPhone());
        map.put("mallType", type);
        OkHttpUtils.post()
                .url(NetConfig.getHaideMallJiFen)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getJiFenNum.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getJiFenNum.java:239)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {

                            value = jsonObject.getJSONObject("data").getString("data");
                            tvValue.setText("可充值积分 " + value);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getUserJifen() {
        if(etPassword.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(value==null || value.equals("") ||value.equals("0")){
            Toast.makeText(getApplicationContext(), "积分为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mallType==null || mallType.equals("")){
            Toast.makeText(getApplicationContext(), "请先选择所属服务平台", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("mallType", mallType);
        map.put("phone", MyUtils.getUser().getPhone());
        map.put("buy_password", etPassword.getText().toString().trim());
        map.put("jifen", value);
        OkHttpUtils.post()
                .url(NetConfig.getUserJifen)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getUserJifen.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getUserJifen.java:239)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            Toast.makeText(getApplicationContext(), "兑换成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {

                        }
                    }
                });
    }

}
