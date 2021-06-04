package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.TuoGuanBean;
import com.axehome.www.guojinapp.utils.DateUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class TuoGuanAdapter extends BaseAdapter {
    private Context context;
    private List<TuoGuanBean> mList;

    public TuoGuanAdapter(Context context, List<TuoGuanBean> mList) {
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
                    R.layout.item_tuo_guan, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        List<String> pics = new ArrayList<>();
        if (mList.get(position).getGood_imgs() != null) {
            String[] strPic = mList.get(position).getGood_imgs().split(",");

            if (strPic != null && strPic.length > 0) {
                for (int i = 0; i < strPic.length; i++) {
                    pics.add(strPic[i]);
                }
            }
            ImgAdapter imgAdapter = new ImgAdapter(context, pics);
            holder.mgvList.setAdapter(imgAdapter);
        }

        holder.tvGoodName.setText(mList.get(position).getGood_name());
        holder.tvDateTime.setText(DateUtils.formatDateTime(mList.get(position).getSetup_datetime()));
        Glide.with(context).load(NetConfig.baseurl + mList.get(position).getGood_zhengshu()).error(R.drawable.pt12).into(holder.ivZhengShu);
        holder.tvGoodGuige.setText(mList.get(position).getGood_guige()==null?"" : mList.get(position).getGood_detail());
        holder.tvGoodDetail.setText(mList.get(position).getGood_detail()==null? "" : mList.get(position).getGood_detail());

        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_good_name)
        TextView tvGoodName;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.mgv_list)
        MyGridView mgvList;
        @BindView(R.id.tv_good_guige)
        TextView tvGoodGuige;
        @BindView(R.id.tv_good_detail)
        TextView tvGoodDetail;
        @BindView(R.id.iv_zheng_shu)
        ImageView ivZhengShu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
