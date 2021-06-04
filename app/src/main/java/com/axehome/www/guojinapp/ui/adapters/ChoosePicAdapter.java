package com.axehome.www.guojinapp.ui.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.PicBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.listeners.PicListenter;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class ChoosePicAdapter extends BaseAdapter {
    private Activity context;
    private List<PicBean> mList;
    private PicListenter listener;

    public ChoosePicAdapter(Activity context, List<PicBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
    }

    public void setListener(PicListenter listener) {
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
                    R.layout.item_choose_pic, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivDel.setVisibility(View.GONE);
        holder.tvText.setText(mList.get(position).getPic_name());
        if(mList.get(position).isIs_must()){
            Drawable drawable= context.getResources().getDrawable(R.drawable.xing_pic);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tvText.setCompoundDrawables(drawable,null,null,null);
        }
        if(mList.get(position).getPic_url()!=null && !mList.get(position).getPic_url().equals("")){
            Glide.with(context).load(NetConfig.baseurl+mList.get(position).getPic_url()).into(holder.ivPic);
            holder.ivDel.setVisibility(View.VISIBLE);
        }else{
            Glide.with(context).load(R.drawable.pic_back).into(holder.ivPic);
        }
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.del_pic(position);
            }
        });
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.on_Clicked(position);
            }
        });
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.iv_del)
        ImageView ivDel;
        @BindView(R.id.tv_text)
        TextView tvText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
