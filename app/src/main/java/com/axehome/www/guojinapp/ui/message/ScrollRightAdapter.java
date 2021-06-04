package com.axehome.www.guojinapp.ui.message;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.ui.activitys.GoodDetailActivity;
import com.axehome.www.guojinapp.utils.NetConfig;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Raul_lsj on 2018/3/28.
 */

public class ScrollRightAdapter extends BaseSectionQuickAdapter<ScrollBean, BaseViewHolder> {
    private Context context;
    public ScrollRightAdapter(int layoutResId, int sectionHeadResId, List<ScrollBean> data, Context context) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ScrollBean item) {
        helper.setText(R.id.right_title, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScrollBean item) {
        ScrollBean.ScrollItemBean t = item.t;
        helper.setText(R.id.right_text, t.getPreGoodsBean().getGood_name());
        helper.setText(R.id.right_value, "￥"+t.getPreGoodsBean().getGood_value());
        String [] imgs = t.getPreGoodsBean().getGood_img().split(",");
        Glide.with(context).load(NetConfig.baseurl+imgs[0]).into((RoundedImageView)helper.getView(R.id.riv_pic));
        helper.getView(R.id.riv_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,GoodDetailActivity.class).putExtra("good", JSON.toJSONString(t.getPreGoodsBean())));
            }
        });
    }
}
