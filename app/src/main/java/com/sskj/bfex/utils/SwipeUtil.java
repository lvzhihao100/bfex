package com.sskj.bfex.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.bulong.rudeness.RudenessScreenHelper;
import com.recyclerview.swipe.SwipeMenuBridge;
import com.recyclerview.swipe.SwipeMenuCreator;
import com.recyclerview.swipe.SwipeMenuItem;
import com.recyclerview.swipe.SwipeMenuRecyclerView;


/**
 * Created by lv on 17-8-14.
 */

public class SwipeUtil {
    public static void openSwipe(Context context, SwipeMenuRecyclerView swipeMenuRecyclerView, SwipeDeleteClickListener clickListener) {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(context); // 各种文字和图标属性设置。
            deleteItem.setBackgroundColor(Color.RED)
                    .setText("删除")
                    .setWidth((int) RudenessScreenHelper.pt2px(context,150))
                    .setHeight(height)
                    .setTextColor(Color.WHITE);
            rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
        };
        swipeMenuRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(menuBridge -> {
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                clickListener.click(menuBridge, adapterPosition);
            }
        });
    }

    public interface SwipeDeleteClickListener {
         void click(SwipeMenuBridge menuBridge, int pos);
    }
}
