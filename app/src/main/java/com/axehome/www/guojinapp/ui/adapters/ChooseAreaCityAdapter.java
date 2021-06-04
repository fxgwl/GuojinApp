package com.axehome.www.guojinapp.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.AreaCityBean;
import com.axehome.www.guojinapp.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class ChooseAreaCityAdapter extends BaseAdapter {
    private Activity context;
    private List<AreaCityBean> mList;
    private int type = 1;
    public ChooseAreaCityAdapter(Activity context, List<AreaCityBean> mList, int type) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
        this.type = type;
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
                    R.layout.item_choose_class, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /*if(type==1){
            holder.tvText.setText(mList.get(position).getPro_name());
        }else if(type==2){
            holder.tvText.setText(mList.get(position).getCity_name());
        }else if(type==3){
            holder.tvText.setText(mList.get(position).getDist_name());
        }*/


        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_text)
        TextView tvText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}