package com.axehome.www.guojinapp.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.PreGoodsBean;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class GoodNormalAdapter extends BaseAdapter {
    private Activity context;
    private List<PreGoodsBean> mList;

    public GoodNormalAdapter(Activity context, List<PreGoodsBean> mList) {
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
                    R.layout.scroll_right, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rightText.setText(mList.get(position).getGood_name());
        if(mList.get(position).getGood_img()!=null && !mList.get(position).getGood_img().equals("")){
            String [] imgs = mList.get(position).getGood_img().split(",");
            Glide.with(context).load(NetConfig.baseurl+imgs[0]).into(holder.rivPic);
        }
        holder.rightValue.setText("￥"+String.valueOf(mList.get(position).getGood_value()));
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.riv_pic)
        RoundedImageView rivPic;
        @BindView(R.id.right_text)
        TextView rightText;
        @BindView(R.id.right_value)
        TextView rightValue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
