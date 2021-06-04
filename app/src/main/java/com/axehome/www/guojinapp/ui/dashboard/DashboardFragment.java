package com.axehome.www.guojinapp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.beans.CheckedBean;
import com.axehome.www.guojinapp.beans.CourseBean;
import com.axehome.www.guojinapp.beans.JianDingBean;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.axehome.www.guojinapp.ui.activitys.yunying.CompanyDetailActivity;
import com.axehome.www.guojinapp.ui.activitys.yunying.JianDingDetailActivity;
import com.axehome.www.guojinapp.ui.activitys.yunying.ServiceJianDingActivity;
import com.axehome.www.guojinapp.ui.activitys.yunying.ServiceTuoGuanActivity;
import com.axehome.www.guojinapp.ui.adapters.ChooseAdapter;
import com.axehome.www.guojinapp.ui.adapters.CourseAdapter;
import com.axehome.www.guojinapp.ui.adapters.JianDingAdapter;
import com.axehome.www.guojinapp.ui.adapters.MenuAdapter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.axehome.www.guojinapp.views.MyListView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/*服务*/
public class DashboardFragment extends Fragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.rb_jian_ding)
    RadioButton rbJianDing;
    @BindView(R.id.rb_tuo_guan)
    RadioButton rbTuoGuan;
    @BindView(R.id.rb_pei_xun)
    RadioButton rbPeiXun;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.mlv_jianding_list)
    MyListView mlvJiandingList;
    @BindView(R.id.ll_jian_ding)
    LinearLayout llJianDing;
    @BindView(R.id.sc_view)
    PullToRefreshScrollView scrollView;
    @BindView(R.id.b_jian_ding)
    Button bJianDing;
    @BindView(R.id.b_tuo_guan)
    Button bTuoGuan;
    @BindView(R.id.ll_tuo_guan)
    LinearLayout llTuoGuan;
    @BindView(R.id.gv_class_menu)
    MyGridView gvClassMenu;
    @BindView(R.id.mlv_class_list)
    MyListView mlvClassList;
    @BindView(R.id.ll_pei_xun)
    LinearLayout llPeiXun;
    @BindView(R.id.iv_kefu_tel)
    ImageView ivKefuTel;
    @BindView(R.id.iv_mengban)
    ImageView ivMengban;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.mgv_list)
    MyGridView mgvList;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.ll_pro)
    LinearLayout llPro;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.b_cancel)
    Button bCancel;
    @BindView(R.id.b_ok)
    Button bOk;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;

    private DashboardViewModel dashboardViewModel;
    private Unbinder unbinder;
    private PopupWindow popWnd;
    private String course_class = "", sys_tel = "";
    private JianDingAdapter jianDingAdapter;
    private List<JianDingBean> jianDingBeans = new ArrayList<>();

    private List<String> className = new ArrayList<>();
    private List<String> courseClassList = new ArrayList<>();
    private MenuAdapter menuAdapter;
    /*课程列表*/
    List<CourseBean> courseBeanList = new ArrayList<>();
    private CourseAdapter courseAdapter;

    private OptionsPickerView pvClass;
    private ArrayList<String> proList = new ArrayList<>();
    private ArrayList<AreaCityBean> provinceList = new ArrayList<>();//创建存放省份实体类的集合

    private List<String> cities;//创建存放城市名称集合
    private ArrayList<List<String>> citiesList = new ArrayList<>();//创建存放城市名称集合的集合

    private ArrayList<String> areas;//创建存放区县名称的集合
    private ArrayList<List<String>> areasList;//创建存放区县名称集合的集合
    private ArrayList<List<List<String>>> areasListsList = new ArrayList<>();//创建存放区县集合的集合的集合
    private String s_pro = "", s_city = "", s_area = null;

    /*点击筛选*/
    private ChooseAdapter chooseAdapter;
    private List<CheckedBean> checkedBeanList = new ArrayList<>();


    private int curPage = 1, menu_type = 1;//

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, root);
        jianDingAdapter = new JianDingAdapter(getContext(), jianDingBeans);
        mlvJiandingList.setAdapter(jianDingAdapter);
        mlvJiandingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), JianDingDetailActivity.class).putExtra("bean", JSON.toJSONString(jianDingBeans.get(i))));
            }
        });
        dashboardViewModel.getPayInfoList().observe(this, new Observer<List<JianDingBean>>() {
            @Override
            public void onChanged(@Nullable List<JianDingBean> list) {
                if (jianDingBeans != null && jianDingBeans.size() > 0) {
                    jianDingBeans.clear();
                }
                jianDingBeans.addAll(list);
                jianDingAdapter.notifyDataSetChanged();
            }
        });
        dashboardViewModel.getBalance().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                /*balance = str;*/
                //tvBalance.setText(balance);
            }
        });
        initView();
        PullLisition();
        getCourseClassList();
        SysInfo();
        getJsonData(getContext());
        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void initView() {
        title.setText("海得服务");
        ivMengban.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /*不触发点击*/
                return true;
            }
        });
        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                llJianDing.setVisibility(View.GONE);
                llTuoGuan.setVisibility(View.GONE);
                llPeiXun.setVisibility(View.GONE);
                switch (i) {
                    case R.id.rb_jian_ding:
                        menu_type = 1;
                        curPage = 1;
                        llJianDing.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_tuo_guan:
                        menu_type = 2;
                        curPage = 1;
                        llTuoGuan.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_pei_xun:
                        menu_type = 3;
                        curPage = 1;
                        llPeiXun.setVisibility(View.VISIBLE);
                        getCourseList();
                        break;
                }
            }
        });

        menuAdapter = new MenuAdapter(getContext(), courseClassList);
        gvClassMenu.setAdapter(menuAdapter);
        gvClassMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menu_type = 3;
                curPage = 1;
                if(position==5){

                }else{
                    course_class = String.valueOf(courseClassList.get(position));
                }
                if (position == courseClassList.size() - 1) {
                    //showPopUpWindow();
                    openWindows();
                } else {
                    getCourseList();
                }
            }
        });
        courseAdapter = new CourseAdapter(getContext(), courseBeanList, "0");
        mlvClassList.setAdapter(courseAdapter);
        mlvClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), CompanyDetailActivity.class)
                        .putExtra("company_id", String.valueOf(courseBeanList.get(i).getCompany_id())));
            }
        });
    }

    public void PullLisition() {
        // 1.设置刷新模式
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        // 上拉加载更多，分页加载
        scrollView.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        scrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        scrollView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        scrollView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        scrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        scrollView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                String label = DateUtils.formatDateTime(
                        getActivity().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                curPage = 1;
                switch (menu_type) {
                    case 1:
                        PayInfoList();
                        break;
                    case 2:
                        scrollView.onRefreshComplete();
                        break;
                    case 3:
                        getCourseList();
                        break;
                }

//                mPresenter.getDetails(String.valueOf(pageNo));

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                // 自定义上拉header内容
                scrollView.getLoadingLayoutProxy(false, true)
                        .setPullLabel("上拉加载...");
                scrollView.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("正在为你加载更多内容...");
                scrollView.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("松开自动加载...");

                curPage++;
                switch (menu_type) {
                    case 1:
                        PayInfoList();
                        break;
                    case 2:
                        scrollView.onRefreshComplete();
                        break;
                    case 3:
                        getCourseList();
                        break;
                }
//                mPresenter.getDetails(String.valueOf(pageNo));
            }
        });
    }

    @OnClick({R.id.b_jian_ding, R.id.b_tuo_guan, R.id.iv_kefu_tel,R.id.iv_close,R.id.ll_pro,R.id.ll_city
            ,R.id.b_cancel,R.id.b_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_jian_ding:
                startActivityForResult(new Intent(getContext(), ServiceJianDingActivity.class), 11);
                break;
            case R.id.b_tuo_guan:
                startActivityForResult(new Intent(getContext(), ServiceTuoGuanActivity.class), 22);
                break;
            case R.id.iv_kefu_tel:
                //拨打电话
                if (sys_tel != null && !sys_tel.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + sys_tel);
                    intent.setData(data);
                    startActivity(intent);
                }
                break;
            case R.id.iv_close:
                rlContent.setVisibility(View.GONE);
                break;
            case R.id.ll_pro:
                if (pvClass != null && pvClass.isShowing()) {
                    pvClass.dismiss();
                }
                choosePro(tvPro);
                break;
            case R.id.ll_city:
                if (pvClass != null && pvClass.isShowing()) {
                    pvClass.dismiss();
                }
                chooseCity(tvCity);
                break;
            case R.id.b_cancel:
                s_pro="";
                s_city="";
                course_class="";

                for (int i = 0; i < checkedBeanList.size(); i++) {
                    checkedBeanList.get(i).setChecked(false);
                }
                if(s_pro.equals("")){
                    tvPro.setText(s_pro);
                }
                if(s_city.equals("")){
                    tvCity.setText(s_city);
                }
                chooseAdapter.notifyDataSetChanged();
                break;
            case R.id.b_ok:
                curPage = 1;
                getCourseList();
                rlContent.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {

        }
    }

    private void PayInfoList() {
        if (MyUtils.getUser() == null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        /*map.put("user_id", String.valueOf(MyUtils.getUser().getUser_id()));*/
        OkHttpUtils.post()
                .url(NetConfig.getJianDingUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                        scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(payInfo.java:71)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            scrollView.onRefreshComplete();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            if (curPage == 1) {
                                jianDingBeans.clear();
                            }
                            List<JianDingBean> payInfoBeans = new ArrayList<>();
                            payInfoBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), JianDingBean.class));
                            jianDingBeans.addAll(payInfoBeans);
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        jianDingAdapter.notifyDataSetChanged();
                        scrollView.onRefreshComplete();
                    }
                });
    }

    private void getCourseClassList() {
        OkHttpUtils.post()
                .url(NetConfig.getCourseClassUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                        scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(payInfo.java:71)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            scrollView.onRefreshComplete();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            className.clear();
                            className = new ArrayList<>();
                            className.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), String.class));
                            List<String> list = new ArrayList<>();
                            if (className.size() > 5) {
                                for (int i = 0; i < 5; i++) {
                                    list.add(className.get(i));
                                }
                                list.add("筛选");
                                courseClassList.addAll(list);
                            } else {
                                courseClassList.addAll(className);
                            }

                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        menuAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getCourseList() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(curPage));
        map.put("course_type", course_class);
        if(!s_pro.equals("")){
            map.put("company_pro", s_pro);
        }
        if(!s_city.equals("")){
            map.put("company_city", s_city);
        }

        OkHttpUtils.post()
                .url(NetConfig.getCourseUrl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(getCourseList.java:420)" + e.getMessage());
                        scrollView.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(getCourseList.java:71)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            scrollView.onRefreshComplete();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            if (curPage == 1) {
                                courseBeanList.clear();
                            }

                            List<CourseBean> courseBeans = new ArrayList<>();
                            courseBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), CourseBean.class));
                            courseBeanList.addAll(courseBeans);

                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        courseAdapter.notifyDataSetChanged();
                        scrollView.onRefreshComplete();
                    }
                });
    }

    private void SysInfo() {

        OkHttpUtils.post()
                .url(NetConfig.getSysinfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getContext(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            int code = jsonObject.getInteger("code");
                            if (code == 0) {
                                sys_tel = jsonObject.getJSONObject("data").getString("sys_phone");
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_course_class, null);
        final PopupWindow popWnd = new PopupWindow(getContext());
        popWnd.setContentView(contentView);
//    popWnd.setWidth(263);
//    popWnd.setHeight(320);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();
        MyGridView mgvList = contentView1.findViewById(R.id.mgv_list);
        ImageView ivClose = contentView1.findViewById(R.id.iv_close);
        ImageView ivMengban = contentView1.findViewById(R.id.iv_mengban);
        Button bCancel = contentView1.findViewById(R.id.b_cancel);
        Button bOk = contentView1.findViewById(R.id.b_ok);
        /*选择城市*/
        LinearLayout llPro = contentView1.findViewById(R.id.ll_pro);
        LinearLayout llCity = contentView1.findViewById(R.id.ll_city);
        TextView tvPro = contentView1.findViewById(R.id.tv_pro);
        TextView tvCity = contentView1.findViewById(R.id.tv_city);

        List<CheckedBean> checkedBeanList = new ArrayList<>();
        for (int i = 0; i < className.size(); i++) {
            CheckedBean bean = new CheckedBean();
            bean.setName(className.get(i));
            bean.setChecked(false);
            checkedBeanList.add(bean);
        }
        /*点击类别*/
        ChooseAdapter chooseAdapter = new ChooseAdapter(getContext(), checkedBeanList);
        chooseAdapter.setListener(new CheckedListenter() {
            @Override
            public void on_Clicked(int position) {
                course_class = checkedBeanList.get(position).getName();
                for (int i = 0; i < checkedBeanList.size(); i++) {
                    if (i == position) {
                        checkedBeanList.get(i).setChecked(true);
                    } else {
                        checkedBeanList.get(i).setChecked(false);
                    }
                }
                chooseAdapter.notifyDataSetChanged();
            }
        });
        mgvList.setAdapter(chooseAdapter);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMengban.setVisibility(View.GONE);
                popWnd.dismiss();
            }
        });
        llPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvClass != null && pvClass.isShowing()) {
                    pvClass.dismiss();
                }
                choosePro(tvPro);
            }
        });
        llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvClass != null && pvClass.isShowing()) {
                    pvClass.dismiss();
                }
                chooseCity(tvCity);
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curPage = 1;
                getCourseList();
                ivMengban.setVisibility(View.GONE);
                popWnd.dismiss();
            }
        });
        popWnd.setTouchable(true);
        popWnd.setFocusable(true);
        popWnd.setOutsideTouchable(false);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);

        ivMengban.setVisibility(View.VISIBLE);
        //添加pop窗口关闭事件
        popWnd.setOnDismissListener(new poponDismissListener());
        popWnd.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWnd.dismiss();
                    return true;
                }
                return false;
            }
        });

        //popWnd.showAsDropDown(gvClassMenu, 0, 0);
        gvClassMenu.post(new Runnable() {
            @Override
            public void run() {
                popWnd.showAtLocation(gvClassMenu, Gravity.BOTTOM, 0, 0);
            }
        });


    }

    public void openWindows(){
        checkedBeanList = new ArrayList<>();
        for (int i = 0; i < className.size(); i++) {
            CheckedBean bean = new CheckedBean();
            if(course_class!=null && course_class.equals(className.get(i))){
                bean.setChecked(true);
            }else{
                bean.setChecked(false);
            }
            bean.setName(className.get(i));
            checkedBeanList.add(bean);
        }
        if(s_pro!=null && !s_pro.equals("")){
            tvPro.setText(s_pro);
        }
        if(s_city!=null &&!s_city.equals("")){
            tvCity.setText(s_city);
        }
        /*点击类别*/
        chooseAdapter = new ChooseAdapter(getContext(), checkedBeanList);
        chooseAdapter.setListener(new CheckedListenter() {
            @Override
            public void on_Clicked(int position) {
                course_class = checkedBeanList.get(position).getName();
                for (int i = 0; i < checkedBeanList.size(); i++) {
                    if (i == position) {
                        checkedBeanList.get(i).setChecked(true);
                    } else {
                        checkedBeanList.get(i).setChecked(false);
                    }
                }
                chooseAdapter.notifyDataSetChanged();
            }
        });
        mgvList.setAdapter(chooseAdapter);
        rlContent.setVisibility(View.VISIBLE);
    }

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void choosePro(TextView textView) {

        pvClass = new OptionsPickerView(getContext());
        //设置三级联动的效果
        pvClass.setPicker(proList);

        //设置可以循环滚动,true表示这一栏可以循环,false表示不可以循环
        pvClass.setCyclic(false, false, false);

        //设置默认选中的位置
        pvClass.setSelectOptions(0);
        pvClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String pro = proList.get(options1);
                cities = citiesList.get(options1);

                s_pro = proList.get(options1);
                //s_city=citiesList.get(options1).get(option2);

                textView.setText(pro);
                //Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_LONG).show();
            }
        });
        pvClass.show();
    }

    private void chooseCity(TextView textView) {
        if(cities==null || cities.size()==0){
            Toast.makeText(getContext(),"请先选择省",Toast.LENGTH_LONG).show();
            return;
        }
        pvClass = new OptionsPickerView(getContext());
        //设置三级联动的效果
        pvClass.setPicker((ArrayList) cities);

        //设置可以循环滚动,true表示这一栏可以循环,false表示不可以循环
        pvClass.setCyclic(false, false, false);

        //设置默认选中的位置
        pvClass.setSelectOptions(0);
        pvClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = cities.get(options1);

                s_city = cities.get(options1);
                textView.setText(city);
                //Toast.makeText(getApplicationContext(),"我被点击了",Toast.LENGTH_LONG).show();
            }
        });
        pvClass.show();
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
        cities.clear();
    }
}