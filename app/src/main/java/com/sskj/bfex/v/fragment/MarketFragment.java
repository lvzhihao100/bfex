package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sskj.bfex.R;
import com.sskj.bfex.p.fragment.MarketFragmentPst;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.base.BaseFragment;

/**
 * Created by Lee on 2018/2/28 0028.
 */

public class MarketFragment extends BaseFragment<MainActivity, MarketFragmentPst> {

    private MarketListFragment marketListFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    public MarketFragmentPst getPresenter() {
        return new MarketFragmentPst();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        marketListFragment = MarketListFragment.newInstance(0);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.market_list, marketListFragment);
        ft.show(marketListFragment);
        ft.commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (marketListFragment != null) {
            if (hidden) {
                marketListFragment.closeWebSocket();
            } else {
                marketListFragment.openWebSocket();
            }
        }

    }


}
