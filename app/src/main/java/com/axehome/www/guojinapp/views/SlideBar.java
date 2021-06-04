package com.axehome.www.guojinapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.axehome.www.guojinapp.R;

import java.util.ArrayList;

/**
 * Created by Axehome_Mr.z on 2020/7/17 11:24
 * 字母bar
 */
public class SlideBar extends View {
    private ArrayList<String> titles; // 首字母的集合(不一定为26个)
    private int position = -1;  // 被选中的字符的位置
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 画笔

    public SlideBar(Context context) {
        super(context);
    }
    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void getTitles(ArrayList<String> titles){
        this.titles = titles;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 得到右侧宽高
        int width = getWidth();
        int height = getHeight() - 16;

        int codeHeight = height / titles.size(); // 计算出每一个字母占有的高度

        for (int i = 0; i < titles.size(); i++) {
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setColor(Color.parseColor("#ff7744"));
            mPaint.setTextSize(30);

            // 给选中的 字母 不同的颜色
            if (position != -1 && position == i) {
                mPaint.setColor(Color.parseColor("#ffffff"));
            }
            float xpos = (width - mPaint.measureText(titles.get(i))) / 2;
            float ypos = (i + 1) * codeHeight;
            // 画出字母
            canvas.drawText(titles.get(i), xpos, ypos, mPaint);
        }
    }

    /**
     * 处理触碰事件
     * 1. 当选择一个字符时, 显示中间的提示
     * 2. 当手势抬起后, 中间的提示取消
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // 右侧选择栏的样式
                setBackgroundResource(R.drawable.shape_all);

                float ypos = event.getY(); // 先获取是哪一个被按下
                position = (int) (ypos / getHeight() * titles.size());// 选中的字符的位置

                if (position >= 0 && position < titles.size()) {
                    if (listener != null) {
                        // 通知 activity 选中的位置
                        listener.onCharClick(titles.get(position));
                    }
                }
                invalidate(); // 重绘
                return true;
            case MotionEvent.ACTION_UP:
                position = -1; // 重置位置
                setBackgroundResource(android.R.color.transparent);
                if (listener != null) {
                    listener.onClickUp();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    // 定义一个回调接口
    public interface OnSlideBarCharSelectListener {
        void onCharClick(String selectChar);
        void onClickUp();
    }
    private OnSlideBarCharSelectListener listener;
    public void setOnSlideBarCharSelectListener(OnSlideBarCharSelectListener listener) {
        this.listener = listener;
    }
}
