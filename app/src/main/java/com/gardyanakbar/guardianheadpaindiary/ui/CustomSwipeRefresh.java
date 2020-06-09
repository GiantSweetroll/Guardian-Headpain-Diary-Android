package com.gardyanakbar.guardianheadpaindiary.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//Credits: https://stackoverflow.com/questions/54559363/viewpager-seekbar-when-trying-to-slide-seekbar-viewpager-changes-fragment-i

public class CustomSwipeRefresh extends SwipeRefreshLayout
{
    //Fields
    private int mTouchSlop;
    private float mPrevX;
    private boolean isDisabled;

    public CustomSwipeRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void disableInterceptTouchEvent(boolean isDisabled) {
        this.isDisabled = isDisabled;
        getParent().requestDisallowInterceptTouchEvent(isDisabled);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDisabled) {
                    return false;
                }
                float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                if (xDiff > mTouchSlop) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(event);
    }
}
