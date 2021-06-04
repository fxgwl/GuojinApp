package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.UserCouponBean;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.axehome.www.guojinapp.utils.DateUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class CouponAdapter extends BaseAdapter {
    private Context context;
    private List<UserCouponBean> mList;
    private CheckedListenter listener;

    public CouponAdapter(Context context, List<UserCouponBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
    }

    public void setListener(CheckedListenter listener) {
        this.listener = listener;
    }

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
                    R.layout.item_coupon, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvValue.setText("￥ "+String.valueOf(mList.get(position).getCouponBean().getCoupon_value())+" 元");
        holder.tvName.setText(mList.get(position).getCouponBean().getCoupon_name());
        holder.tvDateTime.setText(DateUtils.
                formatDateTime(mList.get(position).getCouponBean().getCoupon_datetime_end()));
        //0:未使用；1：已使用；2：已过期
        switch (mList.get(position).getCoupon_type()){
            case "0":
                holder.llBg.setBackgroundResource(R.drawable.coupon_bg_yes);
                holder.tvState.setText("未使用");
                holder.tvState.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "1":
                holder.llBg.setBackgroundResource(R.drawable.coupon_bg_no);
                holder.tvState.setText("已使用");
                holder.tvState.setTextColor(context.getResources().getColor(R.color.c6));
                break;
            case "2":
                holder.llBg.setBackgroundResource(R.drawable.coupon_bg_no);
                holder.tvState.setText("已过期");
                holder.tvState.setTextColor(context.getResources().getColor(R.color.c6));
                break;
        }

        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.ll_value)
        LinearLayout llValue;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.ll_bg)
        LinearLayout llBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
