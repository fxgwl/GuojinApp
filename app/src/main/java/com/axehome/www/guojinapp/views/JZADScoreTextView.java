package com.axehome.www.guojinapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Axehome_fxg on 2021/4/27 12:22
 */
public class JZADScoreTextView extends TextView {


 public JZADScoreTextView(Context context) {
super(context);
 }

 public JZADScoreTextView(Context context, AttributeSet attrs) {
super(context, attrs);
 }

@Override
 protected void onDraw(Canvas canvas) {
//倾斜度45,上下左右居中
canvas.rotate(16, getMeasuredWidth()/3, getMeasuredHeight()/3);
super.onDraw(canvas);
}
}
