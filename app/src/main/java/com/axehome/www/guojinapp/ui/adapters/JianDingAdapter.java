package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.JianDingBean;
import com.axehome.www.guojinapp.utils.DateUtils;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.axehome.www.guojinapp.views.MyGridView;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admtrator on 2017/8/10.
 * 落地代表
 */

public class JianDingAdapter extends BaseAdapter {
    private Context context;
    private List<JianDingBean> mList;

    public JianDingAdapter(Context context, List<JianDingBean> mList) {
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
                    R.layout.item_jianding, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        List<String> pics=new ArrayList<>();
        if(mList.get(position).getGood_imgs()!=null){
            String [] strPic=mList.get(position).getGood_imgs().split(",");

            if(strPic!=null && strPic.length>0){
                for(int i=0;i<strPic.length;i++){
                    pics.add(strPic[i]);
                }
            }
            ImgAdapter imgAdapter=new ImgAdapter(context,pics);
            holder.mgvList.setAdapter(imgAdapter);
        }

        holder.tvName.setText(mList.get(position).getUsername());
        holder.tvDateTime.setText(DateUtils.formatDateTime(mList.get(position).getSetup_datetime()));
        Glide.with(context).load(NetConfig.baseurl+mList.get(position).getHead_img()).error(R.drawable.pt12).into(holder.ivStroeImg);
        if(mList.get(position).getReal_num()!=null && !mList.get(position).getReal_num().equals("")){
            holder.tvRealNum.setText("真品度 "+mList.get(position).getReal_num());
        }else{
            holder.tvRealNum.setText("真品度 暂无");
        }
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.iv_stroe_img)
        CircleImageView ivStroeImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.mgv_list)
        MyGridView mgvList;
        @BindView(R.id.tv_real_num)
        TextView tvRealNum;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
