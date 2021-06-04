package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.User;
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

public class DeliveryManAdapter extends BaseAdapter {
    private Context context;
    private List<User> mList;
    private VipListenter listenter;
    private String from =null;

    public DeliveryManAdapter(Context context, List<User> mList, VipListenter listenter, String from) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
        this.listenter = listenter;
        this.from = from;
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
                    R.layout.item_vip, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTel.setText(mList.get(position).getPhone());
        holder.tvNikeName.setVisibility(View.VISIBLE);
        holder.tvNikeName.setText(mList.get(position).getRealname());
        if(this.from!=null){
            holder.tvChoose.setVisibility(View.VISIBLE);
            holder.tvChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenter.edit(position);
                }
            });
            holder.tvDel.setVisibility(View.GONE);
            holder.tvEdit.setVisibility(View.GONE);
        }else{
            holder.tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenter.del(position);
                }
            });
            holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenter.edit(position);
                }
            });
        }

        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_tel)
        TextView tvTel;
        @BindView(R.id.tv_del)
        TextView tvDel;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_nike_name)
        TextView tvNikeName;
        @BindView(R.id.tv_choose)
        TextView tvChoose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
