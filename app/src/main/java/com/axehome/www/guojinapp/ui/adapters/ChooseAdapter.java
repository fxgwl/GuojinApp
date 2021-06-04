package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.CheckedBean;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class ChooseAdapter extends BaseAdapter {
    private Context context;
    private List<CheckedBean> mList;
    private CheckedListenter listenter;

    public ChooseAdapter(Context context, List<CheckedBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
    }

    public void setListener(CheckedListenter listenter) {
        this.listenter = listenter;
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
                    R.layout.item_choose, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvText.setText(mList.get(position).getName());
        holder.tvText.setChecked(mList.get(position).isChecked());
        holder.tvText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    listenter.on_Clicked(position);
                }
            }
        });

        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_text)
        CheckBox tvText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
