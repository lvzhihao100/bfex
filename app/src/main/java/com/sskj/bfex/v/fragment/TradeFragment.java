package com.sskj.bfex.v.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.m.bean.TradeDealBean;
import com.sskj.bfex.p.fragment.TradePresenter;
import com.sskj.bfex.v.activity.MarketActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * 成交
 *
 * @author Hey
 *         created at 2018/4/4 14:14
 */
public class TradeFragment extends BaseFragment<MarketActivity, TradePresenter> {


    @BindView(R.id.trade_recyclerView)
    RecyclerView tradeRecyclerView;

    private String code;

    private BaseAdapter adapter;

    public TradeFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trade;
    }

    @Override
    public TradePresenter getPresenter() {
        return new TradePresenter();
    }

    @Override
    public void initView() {
        tradeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tradeRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        code = getArguments().getString("code");
        mPresenter.getTradeList(code, false);
    }


    public void setData(List<TradeDealBean> data) {

        adapter = new BaseAdapter<TradeDealBean>(R.layout.recycler_item_trade, data, tradeRecyclerView) {
            @Override
            public void bind(ViewHolder injector, TradeDealBean item) {
                injector.setText(R.id.trade_time, item.getAddtime());
                if (item.getOtype() == 1) {
                    injector.setText(R.id.trade_type, "买入");
                } else {
                    injector.setText(R.id.trade_type, "卖出");
                }
                injector.setText(R.id.trade_price, item.getBuyprice());
                injector.setText(R.id.trade_num, item.getBuynum());
            }
        };
        adapter.setEmtyView(R.layout.empty_view_no_img);
        adapter.setEmptyText("没有成交记录");
        tradeRecyclerView.setAdapter(adapter);
    }


    public static TradeFragment newInstance(String code) {
        TradeFragment fragment = new TradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void update(List<TradeDealBean> rest) {
        adapter.setNewData(rest);
    }


}
