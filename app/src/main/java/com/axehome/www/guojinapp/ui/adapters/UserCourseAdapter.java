package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.UserCourseBean;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.axehome.www.guojinapp.utils.DateUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class UserCourseAdapter extends BaseAdapter {
    private Context context;
    private List<UserCourseBean> mList;
    private CheckedListenter listener;

    public UserCourseAdapter(Context context, List<UserCourseBean> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
    }

    public void setListener(CheckedListenter listener) {
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
                    R.layout.item_user_course, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] strPic = mList.get(position).getCourseBean().getCourse_imgs().split(",");
        holder.tvName.setText(mList.get(position).getCourse_name());
        holder.tvDetail.setText(mList.get(position).getCourseBean().getCourse_detail());
        Glide.with(context).load(NetConfig.baseurl + strPic[0]).into(holder.ivImg);
        Glide.with(context).load(NetConfig.baseurl + mList.get(position).getCompany_logo())
                .error(R.drawable.pt12).into(holder.ivCompanyLogo);

        holder.tvValue.setText("￥" + mList.get(position).getCourse_value());
        holder.tvCompanyName.setText(mList.get(position).getCompany_name());
        holder.tvDateTime.setText(DateUtils.formatDateTime(mList.get(position).getOrder_datetime()));

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_company_logo)
        CircleImageView ivCompanyLogo;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.iv_img)
        RoundedImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.ll_value)
        LinearLayout llValue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
