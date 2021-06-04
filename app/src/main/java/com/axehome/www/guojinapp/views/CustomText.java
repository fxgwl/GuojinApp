package com.axehome.www.guojinapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Axehome_Mr.z on 2019/5/20 20:05
 * $Describe  主要由于屏幕密度density不一样导致。于是同样大小(sp)的文字显示在不同设备上会出现1容纳不下的情况。
 * 为解决这一问题。于是我就做了一个可自动调节textSize的TextView.
 */
public class CustomText extends AppCompatTextView implements ViewTreeObserver.OnGlobalLayoutListener {


    public CustomText(Context context) {
        this(context, null);
    }

    public CustomText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //第一步：给TextVIew添加布局改变监听，以便当调用setText方法时收到通知
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }
    @Override
    public void onGlobalLayout() {
//当外部调用setText(String text)方法时回调
        int lineCount = getLineCount();//获取当前行数
        if (lineCount > 1) {//如果当前行数大于1行
            float textSize = getTextSize();//获得的是px单位
            textSize--;//将size-1；
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);//重新设置大小,该方法会立即触发onGlobalLayout()方法。这里相当于递归调用，直至文本行数小于1行为止。
        }
    }

}
