package com.axehome.www.guojinapp.ui.activitys;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.axehome.www.guojinapp.views.GroupRecyclerView;
import com.axehome.www.guojinapp.R;
import com.haibin.calendarview.CalendarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhangDanListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    @BindView(R.id.recyclerView)
    GroupRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_dan_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText(calendarView.getCurYear()+"-"+calendarView.getCurMonth());
        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                tvTitle.setText(year+"-"+month);
            }
        });
    }
}
