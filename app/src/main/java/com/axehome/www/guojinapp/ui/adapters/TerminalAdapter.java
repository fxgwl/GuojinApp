package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.TerminalBean;
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

public class TerminalAdapter extends BaseAdapter {
    private Context context;
    private List<TerminalBean> mList;
    private VipListenter listenter;

    public TerminalAdapter(Context context, List<TerminalBean> mList, VipListenter listenter) {
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
                    R.layout.item_terminal, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvStoreName.setText(mList.get(position).getStoreBean().getStore_name());
        holder.tvTerminalName.setText(mList.get(position).getTerminal_name());
        holder.tvTerminalCode.setText(mList.get(position).getTerminal_id());
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.iv_stroe_img)
        ImageView ivStroeImg;
        @BindView(R.id.tv_terminal_name)
        TextView tvTerminalName;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_terminal_code)
        TextView tvTerminalCode;
        @BindView(R.id.b_submit)
        Button bSubmit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
