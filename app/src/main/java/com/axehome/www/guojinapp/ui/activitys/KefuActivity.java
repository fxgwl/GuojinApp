package com.axehome.www.guojinapp.ui.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.autolayout.utils.AutoUtils;
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

public class KefuActivity extends BaseActivity {


    @BindView(R.id.back_top)
    ImageView backTop;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_jiubao)
    ImageView ivJiubao;
    @BindView(R.id.plv_list)
    PullToRefreshListView plvList;
    private List<KefuBean> mList = new ArrayList<>();
    private KefuAdapter kefuAdapter;
    private String sys_tel="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu);
        ButterKnife.bind(this);
        title.setText("联系客服");
        initView();
        initData();
    }

    private void initView() {
        kefuAdapter = new KefuAdapter(getApplicationContext(), mList, new CheckedListenter() {
            @Override
            public void on_Clicked(int position) {
                //拨打电话
                if(sys_tel!=null && !sys_tel.equals("")){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + sys_tel);
                    intent.setData(data);
                    startActivity(intent);
                }
            }
        });
        plvList.setAdapter(kefuAdapter);
    }

    private void initData() {
        getKefuList();
        SysInfo();
    }

    @OnClick({R.id.back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_top:
                finish();
                break;
        }

    }

    private void getKefuList() {
        Map<String, String> map = new HashMap<>();
        map.put("kefu_type", "");
        OkHttpUtils.post()
                .url(NetConfig.kefuList)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SettingsFragment.java:105)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SettingsFragment.java:112)" + response);
                        if (response == null) {
                            //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (jsonObject.getInteger("code") == 0) {
                            mList.addAll(JSON.parseArray(jsonObject.getJSONObject("data").getString("list"), KefuBean.class));
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        kefuAdapter.notifyDataSetChanged();
                    }
                });
    }

    private static class KefuBean {
        private Integer kefu_id;
        private String kefu_question;
        private String kefu_reply;
        private String kefu_setup;
        private String kefu_reply_id;
        private String kefu_reply_date;
        private String kefu_state;
        private Integer kefu_user_id;
        private String kefu_type;

        public Integer getKefu_id() {
            return kefu_id;
        }

        public void setKefu_id(Integer kefu_id) {
            this.kefu_id = kefu_id;
        }

        public String getKefu_question() {
            return kefu_question == null ? "" : kefu_question;
        }

        public void setKefu_question(String kefu_question) {
            this.kefu_question = kefu_question;
        }

        public String getKefu_reply() {
            return kefu_reply == null ? "" : kefu_reply;
        }

        public void setKefu_reply(String kefu_reply) {
            this.kefu_reply = kefu_reply;
        }

        public String getKefu_setup() {
            return kefu_setup == null ? "" : kefu_setup;
        }

        public void setKefu_setup(String kefu_setup) {
            this.kefu_setup = kefu_setup;
        }

        public String getKefu_reply_id() {
            return kefu_reply_id == null ? "" : kefu_reply_id;
        }

        public void setKefu_reply_id(String kefu_reply_id) {
            this.kefu_reply_id = kefu_reply_id;
        }

        public String getKefu_reply_date() {
            return kefu_reply_date == null ? "" : kefu_reply_date;
        }

        public void setKefu_reply_date(String kefu_reply_date) {
            this.kefu_reply_date = kefu_reply_date;
        }

        public String getKefu_state() {
            return kefu_state == null ? "" : kefu_state;
        }

        public void setKefu_state(String kefu_state) {
            this.kefu_state = kefu_state;
        }

        public Integer getKefu_user_id() {
            return kefu_user_id;
        }

        public void setKefu_user_id(Integer kefu_user_id) {
            this.kefu_user_id = kefu_user_id;
        }

        public String getKefu_type() {
            return kefu_type == null ? "" : kefu_type;
        }

        public void setKefu_type(String kefu_type) {
            this.kefu_type = kefu_type;
        }
    }

    public static class KefuAdapter extends BaseAdapter {
        private Context context;
        private List<KefuBean> mList;
        private CheckedListenter listenter;

        public KefuAdapter(Context context, List<KefuBean> mList,CheckedListenter listenter) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.mList = mList;
            this.listenter = listenter;
        }

    /*public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }*/

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            // 页面
            final ViewHolder holder;

            LayoutInflater inflater = LayoutInflater.from(context);
            if (convertView == null) {
                convertView = inflater.inflate(
                        R.layout.item_kefu, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                AutoUtils.autoSize(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTitle.setText(mList.get(position).getKefu_question());
            holder.tvContent.setText(mList.get(position).getKefu_reply());
            holder.llContent.setVisibility(View.GONE);
            holder.llTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.llContent.isShown()){
                        holder.llContent.setVisibility(View.GONE);
                    }else{
                        holder.llContent.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.tvLinkKf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenter.on_Clicked(position);
                }
            });
            return convertView;
        }

        static
        class ViewHolder {
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.ll_title)
            LinearLayout llTitle;
            @BindView(R.id.tv_content)
            TextView tvContent;
            @BindView(R.id.tv_link_kf)
            TextView tvLinkKf;
            @BindView(R.id.ll_content)
            LinearLayout llContent;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /*拨打电话*/
    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.windows_kefu, null);
        final PopupWindow popWnd = new PopupWindow(this);
        popWnd.setContentView(contentView);
//    popWnd.setWidth(263);
//    popWnd.setHeight(320);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();
        Button tvOk = contentView1.findViewById(R.id.b_tel);
        ImageView ivClose = contentView1.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),yuanyin,Toast.LENGTH_SHORT).show();
                //拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "4001727858");
                intent.setData(data);
                startActivity(intent);
            }
        });

        popWnd.setTouchable(true);
        popWnd.setFocusable(true);
        popWnd.setOutsideTouchable(true);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);

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

        //popWnd.showAsDropDown(mTvLine, 200, 0);
        popWnd.showAtLocation(title, Gravity.CENTER, 0, 0);

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

    private void SysInfo() {

        OkHttpUtils.post()
                .url(NetConfig.getSysinfo)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", getClass().getName() + "117>>" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(getApplicationContext(), "网络故障", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = JSONObject.parseObject(response);
                            int code = jsonObject.getInteger("code");
                            if (code == 0) {
                                sys_tel=jsonObject.getJSONObject("data").getString("sys_phone");
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
