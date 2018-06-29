package com.sskj.bfex.common;

import android.view.View;

/**
*
*@author Hey
*created at 2018/4/9 15:17
*/
public  abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;//这里设置不能超过多长时间
    private long lastClickTime = 0;
    protected abstract void onViewClick(View v);
    @Override
    public void onClick(View view) {
        long currentTime =System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onViewClick(view);
        }
    }
}
