package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.adapter.MyFragmentPagerAdapter;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.TradeInfoBean;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.fragment.CapitalPresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 资金
 * Created by Administrator on 2018/4/13 0013.
 */

public class CapitalFragment extends BaseFragment<MainActivity, CapitalPresenter> {
    @BindView(R.id.toolbar)
    ToolBarLayout mToolbar;
    @BindView(R.id.slidingTabLayout)
    TabLayout mSlidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tv_content_a)
    TextView mTvContentA;
    @BindView(R.id.tv_content_b)
    TextView mTvContentB;
    @BindView(R.id.tv_content_c)
    TextView mTvContentC;
    @BindView(R.id.tv_content_d)
    TextView mTvContentD;
    @BindView(R.id.tv_content_e)
    TextView mTvContentE;
    @BindView(R.id.tv_content_f)
    TextView mTvContentF;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_capital;
    }

    @Override
    public CapitalPresenter getPresenter() {
        return new CapitalPresenter();
    }

    public void update(){
        mPresenter.getTradeInfo();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo mUserInfo = (UserInfo) SPUtils.getBean(mActivity, Constants.SP_USER_INFO);
        update();

        mToolbar.setRightButtonOnClickLinster(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MarketActivity.start(getActivity(), , mActivity.mCurrentStock.getName(),0);
            }
        });

        if (mUserInfo != null){

            mToolbar.setTitle("交易ID：" + mUserInfo.account);
        }

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        titles.add("持仓");
        titles.add("委托");
        titles.add("成交");
        fragments.add(new NewTradePositionFragment());
        fragments.add(new TradeDeputeFragment());
        fragments.add(new TradeDealFragment());
        mAdapter = new MyFragmentPagerAdapter(mActivity.getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(mAdapter);
        String[] array = new String[titles.size()];
        titles.toArray(array);
        mSlidingTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.cancel();
    }

    public void updateTrade(TradeInfoBean bean) {
        mTvContentA.setText(String.valueOf(bean.getTotalusdt()));
        mTvContentB.setText(bean.getKeyong_price());
        mTvContentC.setText(bean.getTotaldeposit() + "%");
        mTvContentD.setText(String.valueOf(bean.getYingkui()));
        mTvContentE.setText(bean.getPingcang());
        mTvContentF.setText(bean.getRisk() + "%");
    }

}
