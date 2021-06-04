package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.JsonData;
import com.axehome.www.guojinapp.utils.FirstLetter;
import com.axehome.www.guojinapp.views.CitySelect;
import com.axehome.www.guojinapp.views.MyGridView;
import com.axehome.www.guojinapp.views.SlideBar;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.adapters.ChooseCityAdapter;
import com.google.gson.Gson;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCityActivity extends BaseActivity implements TencentLocationListener {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.vvv)
    View vvv;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_szm)
    TextView tvSzm;
    @BindView(R.id.lv_show)
    CitySelect lvShow;
    @BindView(R.id.lv_szm)
    SlideBar lvSzm;
    @BindView(R.id.tv_show_szm)
    TextView tvShowSzm;
    @BindView(R.id.activity_city)
    RelativeLayout activityCity;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.mgv_list)
    MyGridView mgvList;
    @BindView(R.id.ll_content2)
    LinearLayout llContent2;
    /*private CitySelect lv_show;
    private CitySelect.MyAdapter myAdapter;
    private TextView tv_szm;
    private TextView tv_show_szm;
    private SlideBar lv_szm;*/
    private View lastClickedView;
    List<JsonData.ResponseDataBean.CommonBean> common;
    private List<JsonData.ResponseDataBean.CommonBean.ItemsBean> items;
    private String theText = "A";
    private JsonData jsonData;
    private ChooseCityAdapter chooseCityAdapter;
    private TencentLocationManager mLocationManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        ButterKnife.bind(this);
        //getSupportActionBar().hide();

        /*lv_show = (CitySelect) findViewById(R.id.lv_show);
        tv_szm = ((TextView) findViewById(R.id.tv_szm));
        tv_show_szm = ((TextView) findViewById(R.id.tv_show_szm));*/

        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // MainActivity 的startAcitivityForResult
                Intent intent = getIntent();
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                intent.putExtra("city", text1.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        lvShow.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //                TextView childAt = (TextView) lv_show.getChildAt(firstVisibleItem);
                //                if (childAt != null) {
                // 通过城市名得到 首字母并转换大写
                theText = FirstLetter.getFirstLetter(((CitySelect.MyAdapter) lvShow.getAdapter()).getData().get(firstVisibleItem).toCharArray()[0]);
                tvSzm.setText(theText);
                //                }
            }
        });

        common = lvShow.getJsonData(this).getResponseData().getCommon();
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < common.size(); i++) {
            titles.add(common.get(i).getTitle());
        }
        lvSzm = (SlideBar) findViewById(R.id.lv_szm);
        lvSzm.getTitles(titles);
        // 接口回调的实现
        lvSzm.setOnSlideBarCharSelectListener(new SlideBar.OnSlideBarCharSelectListener() {

            @Override
            public void onCharClick(String selectChar) {
                tvShowSzm.setText(selectChar);
                tvShowSzm.setVisibility(View.VISIBLE);

                tvSzm.setText(selectChar);
                int commonPosition = 0;
                for (int i = 0; i < common.size(); i++) {
                    if (common.get(i).getTitle().equals(selectChar)) {
                        commonPosition = i;
                    }
                }
                int position = 0;
                for (int j = 0; j < commonPosition; j++) {
                    items = common.get(j).getItems();
                    position += items.size();
                }
                //                lv_show.smoothScrollToPosition(position); // 带动画 显示 某项 - notifyDataSetChanged() 对其没影响
                lvShow.setSelection(position); // 跳转并使某项为 第一项 - notifyDataSetChanged() 则无效
            }

            @Override
            public void onClickUp() {
                tvShowSzm.setVisibility(View.GONE);
            }
        });
        //        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.item_szm, titles);
        //        lv_szm.setAdapter(arrayAdapter);
        //        lv_szm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //                if (lastClickedView == null) {
        //                    lastClickedView = view;
        //                }
        //                if (lastClickedView == view) {
        //                } else {
        //                    ((TextView) lastClickedView).setTextColor(Color.parseColor("#666666"));
        //                }
        //                ((TextView) view).setTextColor(Color.WHITE);
        //                lastClickedView = view;
        //            }
        //        });

        mLocationManager = TencentLocationManager.getInstance(getApplicationContext());
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(10000);//用户可以自定义定位间隔，时间单位为毫秒，不得小于1000毫秒:
        request. setRequestLevel(TencentLocationRequest. REQUEST_LEVEL_ADMIN_AREA);//设置请求级别
        request.setAllowGPS(true);//是否允许使用GPS
        /*request. setAllowDirection(true);//是否需要获取传感器方向
        request.setIndoorLocationMode(true);//是否需要开启室内定位*/
        mLocationManager.requestLocationUpdates(request, this);
        initData();
    }

    private void initData() {
        getJsonData(this);
    }

    private void getJsonData(Context context) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("city.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            // GsonFormat
            jsonData = new Gson().fromJson(stringBuffer.toString(), JsonData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        chooseCityAdapter = new ChooseCityAdapter(this, jsonData.getResponseData().getHot());
        mgvList.setAdapter(chooseCityAdapter);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

        Log.d("latitude",tencentLocation.getCity()+tencentLocation.getLatitude()+"===longitude经度=="+tencentLocation.getLongitude());
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.d("i==",i+"=="+s+"s1="+s1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(this);
    }
}
