package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.CourseBean;
import com.axehome.www.guojinapp.listeners.CheckedListenter;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class CourseAdapter extends BaseAdapter {
    private Context context;
    private List<CourseBean> mList;
    private String type="0";
    private CheckedListenter listener;

    public CourseAdapter(Context context, List<CourseBean> mList, String type) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
        this.type = type;
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
                    R.layout.item_course, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] strPic = mList.get(position).getCourse_imgs().split(",");
        holder.tvName.setText(mList.get(position).getCourse_name());
        holder.tvDetail.setText(mList.get(position).getCourse_detail());

        Glide.with(context).load(NetConfig.baseurl + strPic[0]).error(R.drawable.pt12).into(holder.ivImg);
        if (type.equals("1")) {
            holder.llValue.setVisibility(View.VISIBLE);
            holder.tvValue.setText("￥"+mList.get(position).getCourse_value());
            holder.bSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.on_Clicked(position);
                    /*context.startActivity(new Intent(context, CourseDetailActivity.class)
                            .putExtra("bean", JSON.toJSONString(mList.get(position))));*/
                }
            });
        }else{
            holder.bSubmit.setClickable(false);
        }

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_img)
        CircleImageView ivImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_value)
        TextView tvValue;
        @BindView(R.id.b_submit)
        Button bSubmit;
        @BindView(R.id.ll_value)
        LinearLayout llValue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
