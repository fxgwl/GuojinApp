package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.WuLiuBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class WuliuAdapter extends BaseAdapter {
    private Context context;
    private List<WuLiuBean> mList;


    public WuliuAdapter(Context context, List<WuLiuBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
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
                    R.layout.item_wu_liu, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTime.setText(mList.get(position).getTime());
        holder.tvState.setText(mList.get(position).getStatus());
        holder.vUp.setVisibility(View.INVISIBLE);
        holder.vDown.setVisibility(View.INVISIBLE);
        holder.ivCar.setVisibility(View.INVISIBLE);
        if(position==0){
            holder.vUp.setVisibility(View.VISIBLE);
            holder.ivCar.setVisibility(View.VISIBLE);
        }
        if(position==getCount()-1){
            holder.vDown.setVisibility(View.VISIBLE);
        }
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.tvState.getLayoutParams();
        linearParams.weight=ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.vDown.setMinimumHeight(linearParams.height);
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.v_up)
        View vUp;
        @BindView(R.id.tv_pic)
        ImageView tvPic;
        @BindView(R.id.v_down)
        View vDown;
        @BindView(R.id.ll_ll1)
        LinearLayout llLl1;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.iv_car)
        ImageView ivCar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
