package com.axehome.www.guojinapp.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axehome.www.guojinapp.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Axehome_Mr.z on 2020/3/30 12:22
 */
public class MenuAdapter extends BaseAdapter {
    List<String> mList = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }
    public MenuAdapter(Context context, List<String> mList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.mList = mList;
    }
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 页面
        final ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.menu_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvText.setText(mList.get(position));
        /*holder.tvText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));*/
        if(position>4 && position==getCount()-1){
            Drawable drawable = context.getResources().getDrawable(R.drawable.shaixuan_pic);
            holder.tvText.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, drawable, null);
            holder.tvText.setCompoundDrawablePadding(4);
        }
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
