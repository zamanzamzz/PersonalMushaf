package com.android.personalmushaf.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NonSwipingViewPager extends ViewPager {
    private boolean isSwipeEnabled;
    public NonSwipingViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isSwipeEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled)
            return super.onTouchEvent(ev);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled)
            return super.onInterceptTouchEvent(ev);
        return false;
    }

    public void setSwipeEnabled(boolean isSwipeEnabled) {
        this.isSwipeEnabled = isSwipeEnabled;
    }
}
