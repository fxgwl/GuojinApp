package com.axehome.www.guojinapp.views;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.axehome.www.guojinapp.beans.JsonData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axehome_Mr.z on 2020/7/17 11:31
 * 城市选择的View
 */
public class CitySelect extends ListView {
    public CitySelect(Context context, AttributeSet attrs) {
                 super(context, attrs);
                 getJsonData(context);

                 MyAdapter myAdapter = new MyAdapter(context);
                 this.setAdapter(myAdapter);
             }

              private JsonData jsonData;

              public JsonData getJsonData(Context context) {
                 // 加载 assets 中的文件, 并得到数据
                 AssetManager assetManager = context.getAssets();
                 try {
                         InputStream is = assetManager.open("city.json");
                         BufferedReader br = new BufferedReader(new InputStreamReader(is));
                         StringBuffer stringBuffer = new StringBuffer();
                         String str = null;
                         while ((str = br.readLine()) != null) {
                                 stringBuffer.append(str);
                             }
                         // GsonFormat
                         jsonData = new Gson().fromJson(stringBuffer.toString(), JsonData.class);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 return jsonData;
             }

          public class MyAdapter extends BaseAdapter {
            private List<JsonData.ResponseDataBean.CommonBean> common;
            private List<JsonData.ResponseDataBean.CommonBean.ItemsBean> items;
            private Context context;

            public MyAdapter(Context context) {
                this.context = context;
            }

            // 一次性得到所有城市个数 (可以用下面的 getData() 方法)
            @Override
            public int getCount() {
                common = jsonData.getResponseData().getCommon();
                int sum = 0;
                for (int i = 0; i < common.size(); i++) {
                    items = common.get(i).getItems();
                    sum += items.size();
                }
                return jsonData == null ? 0 : sum;
            }

                  @Override
          public JsonData.ResponseDataBean getItem(int position) {
                         return jsonData.getResponseData();
                     }

                  @Override
          public long getItemId(int position) {
                         return position;
                     }

                  @Override
          public View getView(int position, View convertView, ViewGroup parent) {
                         JsonData.ResponseDataBean item = getItem(position);
                         ViewHolder viewHolder;
                         if (convertView == null) {
                                 viewHolder = new ViewHolder();
                                 // 直接使用 系统布局
                                 convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);

                                 viewHolder.tv_city = (TextView) convertView.findViewById(android.R.id.text1);
                                 convertView.setTag(viewHolder);
                             } else {
                                 viewHolder = (ViewHolder) convertView.getTag();
                             }
                         // 修改数据 (得到城市内容)
                         viewHolder.tv_city.setText(getData().get(position));

                         return convertView;
                     }

                 // 加载所有城市的内容
                 public ArrayList<String> getData() {
                         ArrayList<String> datas = new ArrayList<>();
                         for (int i = 0; i < common.size(); i++) {
                                 for (int j = 0; j < common.get(i).getItems().size(); j++) {
                                         datas.add(common.get(i).getItems().get(j).getCityName());
                                     }
                             }
                         return datas;
                    }
     }
    class ViewHolder {
        TextView tv_city;

    }
}
