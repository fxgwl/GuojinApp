package com.axehome.www.guojinapp.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import com.axehome.www.guojinapp.R;


/**
 * Created by Administrator on 2017/3/20.
 */

public class MyGridViewBj extends GridView {

    private Bitmap rowBackground;

    public MyGridViewBj(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得列数
        rowBackground = BitmapFactory.decodeResource(getResources(), R.drawable.btn_grey);
    }

    public MyGridViewBj(Context context) {
        super(context);
    }

    public MyGridViewBj(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        /*int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);*/
    }
    @Override
    protected void dispatchDraw(Canvas canvas){
        int count = getChildCount();
        int top = count>0 ? getChildAt(0).getTop() : 0;
        //int backgroundWidth = rowBackground.getWidth();
        //int backgroundHeight = rowBackground.getHeight();
        int backgroundWidth = rowBackground.getWidth();
        int backgroundHeight = rowBackground.getHeight();
        if(count>0){
            backgroundWidth = getChildAt(0).getWidth()*count;
            backgroundHeight = getChildAt(0).getHeight();
        }
        int width = getWidth();
        int height = getHeight();

        for (int y = top; y<height; y += backgroundHeight){
            for (int x = 0; x<width; x += backgroundWidth){
                canvas.drawBitmap(rowBackground, x, y, null);
            }
        }
        super.dispatchDraw(canvas);
    }
}
