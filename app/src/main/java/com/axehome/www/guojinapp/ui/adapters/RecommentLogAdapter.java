package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.RecommentLogBean;
import com.axehome.www.guojinapp.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class RecommentLogAdapter extends BaseAdapter {
    private Context context;
    private List<RecommentLogBean> mList;

    public RecommentLogAdapter(Context context, List<RecommentLogBean> mList) {
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
                    R.layout.item_recomment_log, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mList.get(position).getRecommend_type().contains("ls")){
            holder.tvTypeName.setText("个人流水分润");
        }else if(mList.get(position).getRecommend_type().contains("zt")){
            holder.tvTypeName.setText("直推奖金");
        }else if(mList.get(position).getRecommend_type().contains("zd")){
            holder.tvTypeName.setText("培训津贴");
        }else if(mList.get(position).getRecommend_type().contains("jt")){
            holder.tvTypeName.setText("分润津贴");
        }else{
            holder.tvTypeName.setText("其他");
        }
        holder.tvLogNum.setText(mList.get(position).getRecommend_log_id()+"");
        holder.tvLogTime.setText("支付时间："+mList.get(position).getRecommend_setup());
        holder.tvTotal.setText("总计："+mList.get(position).getRecommend_user_value());
        holder.tvValue.setText("￥"+mList.get(position).getRecommend_b_user_value());
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;
        @BindView(R.id.tv_log_num)
        TextView tvLogNum;
        @BindView(R.id.tv_log_time)
        TextView tvLogTime;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.tv_value)
        TextView tvValue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
