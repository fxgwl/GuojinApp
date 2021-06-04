package com.axehome.www.guojinapp.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.GoodClassBean;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.ui.adapters.GoodAdapter;
import com.axehome.www.guojinapp.ui.adapters.JifenClassAdapter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchGoodActivity extends BaseActivity {

    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rb_normal)
    RadioButton rbNormal;
    @BindView(R.id.rb_value)
    RadioButton rbValue;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.pgv_list)
    PullToRefreshGridView pgvList;

    private int curPage=1;
    private String type=null,search_txt="",order_by="",good_class_id="";
    private GoodAdapter goodAdapter;
    private List<PreGoodsBean> preGoodsBeanList = new ArrayList<>();
    private List<GoodClassBean> mClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_good);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        switch (type){
            case "1":
                title.setText("商铺");
                break;
            case "2":
                title.setText("积分商城");
                rbValue.setText("积分排序");
                tvRight.setText("积分类别");
                tvRight.setVisibility(View.VISIBLE);
                ClassList();
                break;
            case "3":
                title.setText("精品推荐");
                break;
            case "4":
                title.setText("非遗文化");
                break;
            case "5":
                title.setText("精选推荐");
                break;
                default:
                    title.setText("搜索");
        }
        initView();
        PullLisition();
        getGoodList();
    }

    private void initView() {
        goodAdapter = new GoodAdapter(this, preGoodsBeanList, new VipListenter() {
            @Override
            public void edit(int position) {

            }

            @Override
            public void del(int position) {

            }

            @Override
            public void refresh(int position) {

            }

            @Override
            public void detail(int position) {

            }
        });
        pgvList.setAdapter(goodAdapter);
        pgvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //查看详情；
                startActivity(new Intent(getApplicationContext(),GoodDetailActivity.class)
                        .putExtra("good", JSON.toJSONString(preGoodsBeanList.get(position))));
            }
        });

        rgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_normal:
                        order_by="";
                        curPage=1;
                        getGoodList();
                        break;
                    case R.id.rb_value:
                        if(type.equals("2")){
                            order_by="jf_up";
                        }else{
                            order_by="up";
                        }
                        curPage=1;
                        getGoodList();
                        break;
                }
            }
        });
    }

    @OnClick({R.id.back_top,R.id.tv_search,R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
            case R.id.tv_search:
                curPage=1;
                search_txt=etText.getText().toString().trim();
                getGoodList();
                break;
            case R.id.tv_right:
                if(mClassList!=null && mClassList.size()>0){
                    showPopUpWindow();
                }
                break;
        }
    }

    public void PullLisition() {
        pgvList.setMode(PullToRefreshBase.Mode.BOTH);
// 上拉加载更多，分页加载
        pgvList.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        pgvList.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        pgvList.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        pgvList.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        pgvList.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        pgvList.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        pgvList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                /*if (terminalBeanList != null && terminalBeanList.size() > 0){
                    terminalBeanList.clear();
                }*/
                curPage = 1;
                getGoodList();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                curPage++;
                getGoodList();
//                mPresenter.getSdcgList(pageNo);
            }
        });
    }

    private void getGoodList(){
        Map<String,String> map = new HashMap<>();
        map.put("page",String.valueOf(curPage));
        map.put("good_type",type);
        map.put("search_txt",search_txt);
        map.put("order_by",order_by);
        if(good_class_id!=null && !good_class_id.equals("")){
            map.put("good_class_id",good_class_id);
        }
        OkHttpUtils.post()
                .url(NetConfig.getGoodsList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                        pgvList.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            if(curPage==1 && preGoodsBeanList.size()>0){
                                preGoodsBeanList.clear();
                            }
                            List<PreGoodsBean> preGoodsBeans= new ArrayList<>();
                            preGoodsBeans.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), PreGoodsBean.class));
                            preGoodsBeanList.addAll(preGoodsBeans);
                            goodAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                        }
                        pgvList.onRefreshComplete();
                    }
                });
    }

    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.layout_choose, null);
        final PopupWindow popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
        popWnd.setWidth(300);
        /*popWnd.setHeight(320);*/
        /*popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);*/
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();
        MyListView mlvList = contentView1.findViewById(R.id.mlv_list);

        JifenClassAdapter jifenClassAdapter = new JifenClassAdapter(getApplicationContext(), mClassList);
        mlvList.setAdapter(jifenClassAdapter);
        mlvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                backgroundAlpha(1f);
                good_class_id=String.valueOf(mClassList.get(i).getId());
                curPage=1;
                getGoodList();
                popWnd.dismiss();
            }
        });
        /*rgCancel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)contentView1.findViewById(rgCancel.getCheckedRadioButtonId());
                yuanyin=radioButton.getText().toString().trim();
            }
        });*/
        popWnd.setTouchable(true);
        popWnd.setFocusable(true);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);

        //ivMengban.setVisibility(View.VISIBLE);
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

        popWnd.showAsDropDown(tvRight, 200, 0);
        /*gvClassMenu.post(new Runnable() {
            @Override
            public void run() {
                popWnd.showAtLocation(gvClassMenu, Gravity.BOTTOM, 0, 0);
            }
        });*/


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
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }

    private void ClassList() {
        Map<String,String> map = new HashMap<>();
        map.put("good_type",type);
        map.put("page","1");
        OkHttpUtils.post()
                .url(NetConfig.getGoodsClass)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:136)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeViewModel.java:142)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<GoodClassBean> storeBeans = JSON.parseArray(jsonObject.getString("data"),GoodClassBean.class);
                            mClassList.addAll(storeBeans);
                        }else{

                        }
                    }
                });
    }
}
