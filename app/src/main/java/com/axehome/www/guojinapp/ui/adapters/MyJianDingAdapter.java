package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.JianDingBean;
import com.axehome.www.guojinapp.ui.activitys.yunying.JianDingDetailActivity;
import com.axehome.www.guojinapp.utils.DateUtils;
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

public class MyJianDingAdapter extends BaseAdapter {
    private Context context;
    private List<JianDingBean> mList;

    public MyJianDingAdapter(Context context, List<JianDingBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
    }

    /*public void setListener(CheckedListenter listener) {
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
                    R.layout.item_my_jianding, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] strPic = mList.get(position).getGood_imgs().split(",");

        holder.tvGoodDetail.setText(mList.get(position).getGood_detail());
        holder.tvName.setText(mList.get(position).getGood_name());
        holder.tvDateTime.setText(DateUtils.formatDateTime(mList.get(position).getSetup_datetime()));
        Glide.with(context).load(NetConfig.baseurl + strPic[0]).error(R.drawable.pt12).into(holder.ivStroeImg);
        holder.tvGoodGuige.setText(mList.get(position).getGood_guige());
        if(mList.get(position).getNeed_zheng().equals("1")){
            holder.tvNeed.setVisibility(View.VISIBLE);
        }else{
            holder.tvNeed.setVisibility(View.GONE);
        }
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, JianDingDetailActivity.class).putExtra("bean", JSON.toJSONString(mList.get(position))));
            }
        });
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.iv_stroe_img)
        RoundedImageView ivStroeImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_good_guige)
        TextView tvGoodGuige;
        @BindView(R.id.tv_need)
        TextView tvNeed;
        @BindView(R.id.tv_good_detail)
        TextView tvGoodDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
