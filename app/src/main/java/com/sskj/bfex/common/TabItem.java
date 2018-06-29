package com.sskj.bfex.common;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Administrator on 2018/4/26.
 */

public class TabItem implements CustomTabEntity {

    String title;
    int selectedIcon;
    int unSelectedIcon;

    public TabItem(String title,  int unSelectedIcon,int selectedIcon) {
        this.title = title;
        this.unSelectedIcon = unSelectedIcon;
        this.selectedIcon = selectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
