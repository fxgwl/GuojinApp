package com.axehome.www.guojinapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.GuoJinApp;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.BannerBean;
import com.axehome.www.guojinapp.beans.GoodClassBean;
import com.axehome.www.guojinapp.beans.MsgBean;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.axehome.www.guojinapp.ui.activitys.GoodDetailActivity;
import com.axehome.www.guojinapp.ui.activitys.SearchGoodActivity;
import com.axehome.www.guojinapp.ui.activitys.WebViewTitalActivity;
import com.axehome.www.guojinapp.ui.activitys.ZiQiangActivity;
import com.axehome.www.guojinapp.ui.activitys.yunying.ApplyActivity;
import com.axehome.www.guojinapp.ui.adapters.GoodClassAdapter;
import com.axehome.www.guojinapp.ui.adapters.GoodNormalAdapter;
import com.axehome.www.guojinapp.ui.adapters.MsgAdapter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.utils.SPUtils;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
/*import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;*/
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.axehome.www.guojinapp.GuoJinApp.isLogin;

public class HomeFragment extends Fragment {


    @BindView(R.id.cb_banner)
    Banner cbBanner;
    /*@BindView(R.id.mqv)
    MarqueeView mqv;*/
    @BindView(R.id.gv_good_class)
    MyGridView gvGoodClass;
    @BindView(R.id.et_text)
    TextView etText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.mgv_good_list)
    MyGridView mgvGoodList;
    @BindView(R.id.mgv_list)
    MyGridView mgvList;
    @BindView(R.id.riv_ziqiang)
    RoundedImageView rivZiqiang;
    @BindView(R.id.ll_tuijian)
    LinearLayout llTuijian;
    @BindView(R.id.riv_feiyi)
    RoundedImageView rivFeiyi;
    @BindView(R.id.b_shenqing)
    Button bShenqing;

    private HomeViewModel homeViewModel;
    private Unbinder unbinder;
    private MyLoader mMyLoader;
    private List<String> listAdvert = new ArrayList<>();
    private List<BannerBean> listBanner = new ArrayList<BannerBean>();
    private List<GoodClassBean> classBeanList = new ArrayList<>();
    private List<MsgBean> msgList = new ArrayList<>();
    private MsgAdapter msgAdapter;
    private List<PreGoodsBean> goodsList = new ArrayList<>();
    private GoodNormalAdapter goodNormalAdapter;
    private GoodClassAdapter goodClassAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

        msgAdapter = new MsgAdapter(getActivity(), msgList);
        mgvList.setAdapter(msgAdapter);
        goodClassAdapter = new GoodClassAdapter(getActivity(), classBeanList);
        gvGoodClass.setAdapter(goodClassAdapter);
        gvGoodClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_msg);
            }
        });

        goodNormalAdapter = new GoodNormalAdapter(getActivity(), goodsList);
        mgvGoodList.setAdapter(goodNormalAdapter);
        mgvGoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //???????????????
                startActivity(new Intent(getContext(), GoodDetailActivity.class)
                        .putExtra("good", JSON.toJSONString(goodsList.get(i))));
            }
        });
        homeViewModel.getBannerList().observe(this, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(List<BannerBean> bannerBeans) {
                if (listBanner != null && listBanner.size() > 0) {

                } else {
                    listAdvert.clear();
                    listBanner.addAll(bannerBeans);
                    for (int i = 0; i < listBanner.size(); i++) {
                        listAdvert.add(NetConfig.baseurl + listBanner.get(i).getBanner_path());
                    }
                    startBanner();
                }
            }
        });
        homeViewModel.getMsgList().observe(this, new Observer<List<MsgBean>>() {
            @Override
            public void onChanged(List<MsgBean> msgBeanList) {
                if (msgBeanList != null && msgBeanList.size() > 0) {
                    msgList.clear();
                    /*msgList.addAll(msgBeanList);*/
                    for(int i=0;i<4;i++){
                        MsgBean bean =new MsgBean();
                        bean.setMsg_content("????????????");
                        msgList.add(bean);
                    }
                    msgAdapter.notifyDataSetChanged();
                }
            }
        });
        homeViewModel.getClassList().observe(this, new Observer<List<GoodClassBean>>() {
            @Override
            public void onChanged(List<GoodClassBean> mClassList) {
                if (mClassList != null && mClassList.size() > 0) {
                    classBeanList.clear();
                    classBeanList.addAll(mClassList);
                    goodClassAdapter.notifyDataSetChanged();
                }
            }
        });
        homeViewModel.getGoodList().observe(this, new Observer<List<PreGoodsBean>>() {
            @Override
            public void onChanged(List<PreGoodsBean> mGoodList) {
                if (mGoodList != null && mGoodList.size() > 0) {
                    goodsList.clear();
                    goodsList.addAll(mGoodList);
                    goodNormalAdapter.notifyDataSetChanged();
                }
            }
        });
        initView();
        startBanner();
        return root;
    }

    public void initView() {
        String str= (String) SPUtils.get("YinSi","");
        if(str==""){
            showPopUpWindow(etText);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void startBanner() {

        cbBanner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        });
        cbBanner.setClipToOutline(true);
        mMyLoader = new MyLoader();
        //??????????????????????????????????????????????????????????????????
        cbBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        /*//?????????????????????
        mConvenBanner.setBannerTitles(listAdvertId);*/
        //?????????????????????
        cbBanner.setImageLoader(mMyLoader);
        //???????????????????????????????????????true
        cbBanner.isAutoPlay(true);
        //???????????????????????????????????????????????????
        cbBanner.setIndicatorGravity(BannerConfig.CENTER);
        cbBanner.setDelayTime(3000);

        cbBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                /*if (listBanner != null) {
                    Log.e("aaa", "---listAdvertId.size()---->" + listBanner.size());
                    Intent intent = new Intent(getActivity(), WebViewTxtActivity.class);
                    intent.putExtra("htmlText", listBanner.get(position).getUe_content())
                            .putExtra("name", listBanner.get(position).getBanner_order());
                    startActivity(intent);
                }*/
            }
        });
        //????????????
        cbBanner.setImages(listAdvert)
                .start();
    }

    @OnClick({R.id.riv_ziqiang, R.id.ll_tuijian, R.id.riv_feiyi, R.id.b_shenqing,R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.riv_ziqiang:
                getActivity().startActivity(new Intent(getContext(), ZiQiangActivity.class));
                break;
            case R.id.ll_tuijian:
                getActivity().startActivity(new Intent(getContext(), SearchGoodActivity.class)
                        .putExtra("type", "3"));
                break;
            case R.id.riv_feiyi:
                getActivity().startActivity(new Intent(getContext(), SearchGoodActivity.class)
                        .putExtra("type", "4"));
                break;
            case R.id.b_shenqing:
                if (isLogin()) {
                    getActivity().startActivity(new Intent(getContext(), ApplyActivity.class));
                }else{
                    Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_search:
                getActivity().startActivity(new Intent(getContext(), SearchGoodActivity.class)
                        .putExtra("type",""));
                break;
        }
    }

    /**
     * ??????????????????
     * ?????????Glide??????????????????
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getActivity())
                    .load((String) path)
                    .into(imageView);
        }
    }

    /**
     * getBanner
     */
    public void getBannerList() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("limit", "5");
        OkHttpUtils.post()
                .url(NetConfig.getBaseurl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:105)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"????????????",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            listBanner.clear();
                            listAdvert.clear();
                            listBanner.addAll(JSON.parseArray(jsonObject.getString("data"), BannerBean.class));
                            for (int i = 0; i < listBanner.size(); i++) {
                                listAdvert.add(NetConfig.baseurl + listBanner.get(i).getBanner_path());
                            }
                            startBanner();
                        } else {

                        }
                    }
                });
    }

    /*private void getMsgList() {
        OkHttpUtils.post()
                .url(NetConfig.getMsg)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:239)" + response);
                        if(response==null){
                            //Toast.makeText(getApplicationContext(),"????????????",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if(jsonObject.getInteger("code")==0){
                            List<MsgBean> msgBeanList = JSON.parseArray(jsonObject.getJSONObject("data").getString("list"),MsgBean.class);
                            for (int i = 0; i < msgBeanList.size(); i++) {
                                //??????
                                Marquee marquee = new Marquee();
                                marquee.setFirstimgUrl("??????");
                                marquee.setFirsttitle(msgBeanList.get(i).getMsg_tital());
                                marquee.setImgUrl(msgBeanList.get(i).getMsg_http());
                                marquees.add(marquee);
                            }
                            mqv.startWithList(marquees);
                            mqv.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position, View textView) {
                                    Intent intent = new Intent(getActivity(), WebViewTitalActivity.class);
                                    intent.putExtra("url", marquees.get(position).getImgUrl())
                                            .putExtra("name","??????");
                                    startActivity(intent);
                                }
                            });
                        }else{

                        }
                    }
                });
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            initView();
        }
    }

    /*public void GoMinProgram() {
        String appId = "wxf2d79823c8cc3f8d"; // ?????????AppId wx5e6b6d208b9c6b7b
        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), appId);

        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_45fa73f7199a"; // ??????????????????id
        //req.path = path;                  ////??????????????????????????????????????????????????????????????????????????????????????????????????????????????? query ????????????????????????????????????????????? "?foo=bar"???
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// ???????????? ?????????????????????????????????
        api.sendReq(req);
    }*/


    private void showPopUpWindow(TextView lpsm) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_yin_si, null);
        final PopupWindow popWnd = new PopupWindow(getActivity());
        popWnd.setContentView(contentView);
//    popWnd.setWidth(263);
//    popWnd.setHeight(320);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();

        Button bNo = contentView1.findViewById(R.id.b_no);
        Button bYes = contentView1.findViewById(R.id.b_yes);
        TextView tvYinsi = contentView1.findViewById(R.id.tv_yinsi);
        final ImageView ivMengban = contentView1.findViewById(R.id.iv_mengban);

        final SpannableStringBuilder style = new SpannableStringBuilder();

        //????????????
        style.append("\u3000\u3000???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n" +
                "\u3000\u3000???app???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????" +
                "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");

        //??????????????????????????????
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getActivity(), WebViewTitalActivity.class)
                        .putExtra("url",NetConfig.baseurl+"haide_xieyi.html")
                        .putExtra("name","????????????"));
                //Toast.makeText(getActivity(), "????????????!", Toast.LENGTH_SHORT).show();
            }
        };
        style.setSpan(clickableSpan, 34, 45, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvYinsi.setText(style);

        //????????????????????????
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 34, 45, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //?????????TextView
        tvYinsi.setMovementMethod(LinkMovementMethod.getInstance());
        tvYinsi.setText(style);

        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMengban.setVisibility(View.GONE);
                popWnd.dismiss();
                GuoJinApp.getInstance().exit();
            }
        });
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),yuanyin,Toast.LENGTH_SHORT).show();
                ivMengban.setVisibility(View.GONE);
                popWnd.dismiss();
                SPUtils.put("YinSi","yes");
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
        popWnd.setOutsideTouchable(false);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);

        ivMengban.setVisibility(View.VISIBLE);
        //??????pop??????????????????
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

        //popWnd.showAsDropDown(mTvLine, 200, 0);
        lpsm.post(new Runnable() {
            @Override
            public void run() {
                popWnd.showAtLocation(lpsm, Gravity.CENTER, 0, 0);
            }
        });


    }

    /**
     * ???????????????popWin????????????????????????????????????????????????????????????
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "??????????????????");
            backgroundAlpha(1f);
        }

    }

    /**
     * ????????????????????????????????????
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}