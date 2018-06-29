package com.sskj.bfex.common.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;

import java.util.List;

/**
 * @author Hey
 *         created at 2018/4/12 17:20
 */
public class HeyTabLayout extends TabLayout {


    private TabAdapter mAdapter;

    public HeyTabLayout(Context context) {
        this(context, null);
    }

    public HeyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(TabAdapter mAdapter) {
        this.mAdapter = mAdapter;
        this.addOnTabSelectedListener(mAdapter.getTabSelectListener());
        selectTab(0);
    }

    public TabAdapter getAdapter() {
        return mAdapter;
    }


    public void selectTab(int index) {
        if (index > getTabCount()) {
            return;
        }
        getTabAt(index).select();
    }


    public static class TabAdapter {
        private List<Fragment> fragmentList;
        private FragmentManager fragmentManager;
        private Fragment preFragment;
        private int layoutRes;
        private OnTabSelectedListener listener;
        private int currentIndex = -1;


        public TabAdapter(@IdRes int layoutRes, List<Fragment> fragments, FragmentManager fragmentManager) {
            this.fragmentList = fragments;
            this.fragmentManager = fragmentManager;
            this.layoutRes = layoutRes;
            listener = new OnTabSelectedListener() {
                @Override
                public void onTabSelected(Tab tab) {
                    selectTab(tab.getPosition());
                }

                @Override
                public void onTabUnselected(Tab tab) {

                }

                @Override
                public void onTabReselected(Tab tab) {
                    selectTab(tab.getPosition());
                }
            };

        }

        public void selectTab(int index) {
            if (currentIndex == index) {
                return;
            }
            FragmentTransaction ft = fragmentManager.beginTransaction();
            preFragment = fragmentList.get(index);
            Fragment fragment1;
            for (Fragment fragment : fragmentList) {
                fragment1 = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
                if (fragment1 != null) {
                    ft.hide(fragment1);
                }
            }
            if (preFragment != null) {
                if (!preFragment.isAdded()) {
                    ft.add(layoutRes, preFragment, preFragment.getClass().getSimpleName());
                }
                ft.show(preFragment);
            }
            ft.commitNow();
            currentIndex = index;
        }

        public OnTabSelectedListener getTabSelectListener() {
            return listener;
        }

        public void setTabSelectListener(OnTabSelectedListener selectListener) {
            this.listener = selectListener;
        }

        public int getCurrentIndex() {
            return currentIndex;
        }

        public void setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
        }

    }


}
