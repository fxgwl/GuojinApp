package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class PicAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    private PicClickListener listener;
    private Integer maxNum;


    public PicAdapter(Context context, List<String> mList, Integer maxNum) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
        this.maxNum  = maxNum;
    }
    public void setListener(PicClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mList != null) {
            return mList.size() + 1;
        }
        return 1;
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
                    R.layout.item_pic, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDel.setVisibility(View.GONE);
        if(position==mList.size()){
            holder.tvDel.setVisibility(View.GONE);
            holder.ivPic.setVisibility(View.VISIBLE);
            Glide.with(context).load("").error(R.drawable.pt12).into(holder.ivPic);
            holder.ivPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"添加图片", Toast.LENGTH_SHORT).show();
                    listener.addPic();
                }
            });
            if(position==maxNum){
                holder.ivPic.setVisibility(View.GONE);
            }
        }else{
            holder.tvDel.setVisibility(View.VISIBLE);
            holder.tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delPic(position);
                }
            });
            if(mList.get(position).contains("http")){
                Glide.with(context).load(mList.get(position)).error(R.drawable.pt12).into(holder.ivPic);
            }else{
                Glide.with(context).load(NetConfig.baseurl+mList.get(position)).error(R.drawable.pt12).into(holder.ivPic);
            }
            holder.ivPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,"图片",Toast.LENGTH_SHORT).show();
                    listener.ChoosePic(position);
                }
            });
        }
        return convertView;
    }
    public interface PicClickListener {

        void addPic();

        void delPic(int position);

        void ChoosePic(int position);

        void setContentItem(int position);

    }
    static
    class ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_del)
        TextView tvDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
