package com.axehome.www.guojinapp.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Axehome_Mr.z on 2019/3/8 16:34
 * $Describe
 */
public class AutoScrollLayoutManager extends LinearLayoutManager {

    public AutoScrollLayoutManager(Context context) {
        super(context);
    }

    //  客制化需要实现该方法；
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    @Nullable
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return AutoScrollLayoutManager.this.computeScrollVectorForPosition
                                (targetPosition);
                    }

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        // 计算滑动每个像素需要的时间，这里应该与屏幕适配；
                        return 15f / displayMetrics.density;
                    }
                };
        linearSmoothScroller.setTargetPosition(position);

        startSmoothScroll(linearSmoothScroller);

    }
}