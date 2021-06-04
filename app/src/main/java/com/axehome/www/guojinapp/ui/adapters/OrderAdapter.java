package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.OrderBean;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderBean> mList;
    private VipListenter listenter;

    public OrderAdapter(Context context, List<OrderBean> mList,VipListenter listenter) {
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
                    R.layout.item_order, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(mList.get(position).getOrder_good_img()!=null){

            String[] strPic = mList.get(position).getOrder_good_img().split(",");
            Glide.with(context).load(NetConfig.baseurl + strPic[0]).error(R.drawable.pt12).into(holder.ivStroeImg);

        }

        holder.tvOrderNum.setText(mList.get(position).getOrder_num());
        holder.tvName.setText(mList.get(position).getOrder_good_name());
        holder.bShouhuo.setVisibility(View.GONE);
        holder.bTopay.setVisibility(View.GONE);
        switch (mList.get(position).getOrder_status()){//0:待付款；1：待发货；2：待收货；3：已完成
            case "0":
                holder.bTopay.setVisibility(View.VISIBLE);
                holder.tvState.setText("待付款");
                break;
            case "1":
                holder.tvState.setText("待发货");
                break;
            case "2":
                holder.bShouhuo.setVisibility(View.VISIBLE);
                holder.tvState.setText("待收货");
                break;
            case "3":
                holder.tvState.setText("已完成");
                break;
        }
        holder.tvValue2.setText(String.valueOf(mList.get(position).getOrder_value()==null?
                mList.get(position).getOrder_good_value():mList.get(position).getOrder_value()));
        holder.tvValue.setText(String.valueOf(mList.get(position).getOrder_good_value()));
        holder.bDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.detail(position);//详请
            }
        });

        holder.bTopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.refresh(position);//去支付
            }
        });
        holder.bShouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenter.edit(position);//去收货
            }
        });
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.iv_stroe_img)
        RoundedImageView ivStroeImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_value2)
        TextView tvValue2;
        @BindView(R.id.b_detail)
        Button bDetail;
        @BindView(R.id.b_topay)
        Button bTopay;
        @BindView(R.id.b_shouhuo)
        Button bShouhuo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
