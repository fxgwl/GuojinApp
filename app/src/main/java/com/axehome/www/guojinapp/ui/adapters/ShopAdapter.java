package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.ShopSxfBean;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.VipListenter;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private List<ShopSxfBean> mList;
    private VipListenter listenter;

    public ShopAdapter(Context context, List<ShopSxfBean> mList, VipListenter listenter) {
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
                    R.layout.item_shop, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvMerchantName.setText(mList.get(position).getMecDisNm()!=null?mList.get(position).getMecDisNm():"天津玖付科技有限公司");
        holder.tvMerchantNum.setText(mList.get(position).getMno());
        holder.tvDate.setText(mList.get(position).getShop_setup());
        switch (mList.get(position).getShop_status()!=null?mList.get(position).getShop_status():""){//01商户审核通过 ，02商户审核驳回， 03电子协议签署成功，04电子协议签署失败
            case "0"://0:审核中；1：已通过；2：驳回；3：图片驳回；6：修改商户驳回
                holder.tvState.setText("商户审核中");
                break;
            case "1":
                holder.tvState.setText("商户审核通过");
                break;
            case "2":
                holder.tvState.setText("商户审核驳回");
                break;
            case "3":
                holder.tvState.setText("商户审核图片驳回");
                break;
            case "6":
                holder.tvState.setText("修改商户驳回");
                break;
                default:
                    holder.tvState.setText("未知");
                    break;
        }

        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_merchant_name)
        TextView tvMerchantName;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_merchant_num)
        TextView tvMerchantNum;
        @BindView(R.id.tv_date)
        TextView tvDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
