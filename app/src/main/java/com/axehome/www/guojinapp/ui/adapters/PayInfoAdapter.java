package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.PayInfoBean;
import com.axehome.www.guojinapp.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class PayInfoAdapter extends BaseAdapter {
    private Context context;
    private List<PayInfoBean> mList;

    public PayInfoAdapter(Context context, List<PayInfoBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
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
                    R.layout.item_pay_info, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (mList.get(position).getPay_type()){
            case "010":
                holder.ivPic.setBackgroundResource(R.drawable.chat_pic);
                holder.tvType.setText("微信支付");
                break;
            case "020":
                holder.ivPic.setBackgroundResource(R.drawable.ali_pay);
                holder.tvType.setText("支付宝支付");
                break;

        }
        Double value = Double.valueOf(mList.get(position).getTotal_fee())/100;
        holder.tvValue.setText("￥"+String.valueOf(value));
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_value)
        TextView tvValue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
