package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.StoreBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class StroeAdapter extends BaseAdapter {
    private Context context;
    private List<StoreBean> mList;
    private VipListenter listenter;

    public StroeAdapter(Context context, List<StoreBean> mList, VipListenter listenter) {
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
                    R.layout.item_stroe, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(mList.get(position).getStore_name());
        holder.tvAddress.setText(mList.get(position).getStore_addre());
        if(mList.get(position).getShopBean()!=null &&mList.get(position).getShopBean().getImg_logo()!=null){
            Glide.with(context).load(NetConfig.baseurl+mList.get(position).getStore_logo()).into(holder.ivStroeImg);
        }
        if(mList.get(position).getJiuli()!=null){
            holder.tvJiuli.setText(FormatJuli(mList.get(position).getJiuli()));
        }
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_stroe_img)
        ImageView ivStroeImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_jiuli)
        TextView tvJiuli;
        @BindView(R.id.b_submit)
        Button bSubmit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private String FormatJuli(Double value){
        DecimalFormat df = new DecimalFormat("#.00");
        if(value<1){
            return "<1 km";
        }else{
            return df.format(value)+" km";
        }
    }
}
