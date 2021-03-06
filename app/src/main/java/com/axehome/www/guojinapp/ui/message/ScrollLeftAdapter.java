package com.axehome.www.guojinapp.ui.message;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollLeftAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<TextView> tv = new ArrayList<>();
    private List<View> v = new ArrayList<>();

    public ScrollLeftAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.left_text, item)
                .addOnClickListener(R.id.item);
        //将左侧item中的TextView添加到集合中
        tv.add((TextView) helper.getView(R.id.left_text));
        v.add((View) helper.getView(R.id.v_right));
        //设置进入页面之后,左边列表的初始状态
        if (tv != null && getData() != null && tv.size() == getData().size()) {
            selectItem(0);
        }

        helper.getView(R.id.item).setSelected(true);
    }

    //传入position,设置左侧列表相应item高亮
    public void selectItem(int position) {
        for (int i = 0; i < getData().size(); i++) {
            if (position == i) {
                /*tv.get(i).setBackgroundColor(0xff0068b7);*/
                tv.get(i).setBackgroundColor(0xffffffff);
                tv.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                v.get(i).setVisibility(View.VISIBLE);
                //以下是指定某一个TextView滚动的效果
                tv.get(i).setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tv.get(i).setFocusable(true);
                tv.get(i).setFocusableInTouchMode(true);
                tv.get(i).setMarqueeRepeatLimit(-1);
            } else {
                tv.get(i).setBackgroundColor(0xffffffff);
                tv.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.black));
                v.get(i).setVisibility(View.GONE);
                //失去焦点则停止滚动
                tv.get(i).setEllipsize(TextUtils.TruncateAt.END);
                tv.get(i).setFocusable(false);
                tv.get(i).setFocusableInTouchMode(false);
                tv.get(i).setMarqueeRepeatLimit(0);
            }
        }
    }
}
