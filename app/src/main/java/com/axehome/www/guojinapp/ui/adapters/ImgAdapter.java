package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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

public class ImgAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;


    public ImgAdapter(Context context, List<String> mList) {
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
                    R.layout.item_img, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(NetConfig.baseurl+mList.get(position)).error(R.drawable.pt12).into(holder.ivPic);

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
