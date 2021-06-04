package com.axehome.www.guojinapp.ui.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.axehome.www.guojinapp.R;
import com.axehome.www.guojinapp.beans.MsgBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admtrator on 2017/8/10.
 */

public class ClassMenuAdapter extends BaseAdapter {
    private Activity context;
    private List<MsgBean> mList;

    public ClassMenuAdapter(Activity context, List<MsgBean> mList) {
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
                    R.layout.item_class_menu, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cbName.setText(mList.get(position).getMsg_tital());
        if (position == mList.size() - 1) {
            Drawable drawable_n = context.getDrawable(R.drawable.baozhang_pic);
            drawable_n.setBounds(0, 0, drawable_n.getMinimumWidth(),drawable_n.getMinimumHeight());  //此为必须写的
            holder.cbName.setCompoundDrawables(null, null, drawable_n, null);
        }

        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.cb_name)
        CheckBox cbName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
