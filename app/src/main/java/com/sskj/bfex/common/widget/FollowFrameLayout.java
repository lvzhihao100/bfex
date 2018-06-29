package com.sskj.bfex.common.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lee on 2018/2/25 0025.
 */

public class FollowFrameLayout extends View {

    private int mHalfWidth;
    private int mHalfHeight;
    private int mDownX;
    private int mDownY;

    public FollowFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FollowFrameLayout(Context context) {
        super(context);
    }

    public FollowFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHalfHeight = getMeasuredHeight() / 2;
        mHalfWidth = getMeasuredWidth() / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetY = (int) (event.getRawY() - mDownY);
                int offsetX = (int) (event.getRawX() - mDownX);
                moveView(offsetX, offsetY);
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private void moveView(int offsetX, int offsetY) {
        // 方法一
//         layout(getLeft() + offsetX, getTop() + offsetY, getRight() +
//         offsetX, getBottom() + offsetY);

        // 方法二
//         offsetLeftAndRight(offsetX);
//         offsetTopAndBottom(offsetY);

        // 方法三
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
         getLayoutParams();
         layoutParams.leftMargin = getLeft() + offsetX;
         layoutParams.topMargin = getTop() + offsetY;
         setLayoutParams(layoutParams);

        // 方法四
//         CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams)
//         getLayoutParams();
//         layoutParams.leftMargin = getLeft() + offsetX;
//         layoutParams.topMargin = getLeft() + offsetY;
//         setLayoutParams(layoutParams);

//        // 方法五
//        ((View) getParent()).scrollBy(-offsetX, -offsetY);
    }
}
