package com.axehome.www.guojinapp.ui.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.AddressBean;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.utils.MyUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.zhy.autolayout.utils.AutoUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class AddressAdapter extends BaseAdapter {
    private Activity context;
    private List<AddressBean> mList;
    private VipListenter listenter;

    public AddressAdapter(Activity context, List<AddressBean> mList, VipListenter listenter) {
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
                    R.layout.item_address, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(mList.get(position).getAddress_name());
        holder.tvAddress.setText(mList.get(position).getAddress_pro() + mList.get(position).getAddress_city()
                + mList.get(position).getAddress_area() + mList.get(position).getAddress_detail());
        holder.tvTel.setText(mList.get(position).getAddress_phone());
        if (mList.get(position).getIs_checked() != null && mList.get(position).getIs_checked().equals("1")) {
            holder.cbChecked.setChecked(true);
        } else {
            holder.cbChecked.setChecked(false);
        }
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.edit(position);
            }
        });
        holder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.del(position);
            }
        });
        holder.cbChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Map<String, String> map = new HashMap<>();
                boolean ischecked = b;
                if (b) {
                    map.put("is_checked", "1");
                } else {
                    map.put("is_checked", "0");
                }
                map.put("address_id", String.valueOf(mList.get(position).getAddress_id()));
                map.put("address_use_id", String.valueOf(MyUtils.getUser().getUser_id()));
                OkHttpUtils.post()
                        .url(NetConfig.updateAddress)
                        .params(map)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("eeee", "(is_checked.java:124)" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("rrr",
                                        "(is_checked.java:130)" + response);
                                if (response == null) {
                                    //Toast.makeText(getApplicationContext(),"系统故障",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                JSONObject jsonObject = JSONObject.parseObject(response);
                                if (jsonObject.getInteger("code") == 0) {
                                    listenter.refresh(position);
                                    //Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_tel)
        TextView tvTel;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.cb_checked)
        CheckBox cbChecked;
        @BindView(R.id.tv_edit)
        TextView tvEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
