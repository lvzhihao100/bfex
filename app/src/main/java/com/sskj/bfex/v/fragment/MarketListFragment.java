package com.sskj.bfex.v.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.LoadingDialog;
import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.common.rxbus2.NetSubScriber;
import com.sskj.bfex.m.bean.Crowd;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.Time;
import com.sskj.bfex.m.bean.TimeDetail;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.p.fragment.MarketFragmentPst;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.GsonUtil;
import com.sskj.bfex.utils.RxSchedulersHelper;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.activity.MarketActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Flowable;

/**
 * 行情列表
 * create by Hey at 2018/5/4 15:33
 */
public class MarketListFragment extends BaseFragment<MainActivity, MarketFragmentPst> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerView crowdRecyclerView;
    RecyclerView raiseRecyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private BaseAdapter adapter;

    private BaseQuickAdapter timeAdapter;
    private BaseQuickAdapter raiseAdapter;

    private View timeView, raiseView;

    private LoadingDialog dialog;

    private List<NewStock> stockList = new ArrayList<>();

    private List<Time> timeList = new ArrayList<>();


    //type 1侧边栏 0 行情
    private int type = 1;

    //侧边栏是否第一次加载
    private boolean isFirstLoad = true;


    private DecimalFormat priceFormat = new DecimalFormat("0.0000");

    /**
     * @param type 0行情 1侧边栏 默认为1
     * @return
     */
    public static MarketListFragment newInstance(int type) {
        Bundle args = new Bundle();
        MarketListFragment fragment = new MarketListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_market_list;
    }

    @Override
    public MarketFragmentPst getPresenter() {
        return new MarketFragmentPst();
    }


    public void closeWebSocket() {
        mPresenter.closeWebSocket();
    }

    public void openWebSocket() {
        mPresenter.initWebSocket(type);
    }


    @Override
    public void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        timeView = mActivity.getLayoutInflater().inflate(R.layout.fragment_market_foot, null);
        raiseView = mActivity.getLayoutInflater().inflate(R.layout.fragment_market_foot1, null);

        crowdRecyclerView = timeView.findViewById(R.id.crowd_recyclerView);
        raiseRecyclerView = raiseView.findViewById(R.id.raise_recyclerView);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));
        refreshLayout.setOnRefreshListener(refreshLayout1 -> mPresenter.getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        crowdRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        raiseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setNestedScrollingEnabled(false);
        crowdRecyclerView.setNestedScrollingEnabled(false);
        raiseRecyclerView.setNestedScrollingEnabled(false);
        initAdapter();
    }


    private void initAdapter() {
        adapter = new BaseAdapter<NewStock>(R.layout.recy_item_market, new ArrayList<>(), recyclerView) {
            @Override
            public void bind(ViewHolder injector, NewStock stock) {
                injector.setText(R.id.tv_sale_price, priceFormat.format(Float.parseFloat(stock.getPrice())));
                String name[] = stock.getName().split("_");
                if (name != null) {
                    injector.setText(R.id.tv_type, name[0]);
                    injector.setText(R.id.tv_unit, "/" + name[1]);
                } else {
                    injector.setText(R.id.tv_type, stock.getName());
                }
                Double change = Double.parseDouble(stock.getChange());
                if (change > 0) {
                    injector.setBackgroundColor(R.id.tv_condition, getResources().getColor(R.color.market_up));
                } else if (change == 0) {
                    injector.setBackgroundColor(R.id.tv_condition, getResources().getColor(R.color.gray_cf));
                } else {
                    injector.setBackgroundColor(R.id.tv_condition, getResources().getColor(R.color.market_down));
                }
                injector.setText(R.id.tv_condition, stock.getChangePercentage());

                ClickUtil.click(injector.getView(R.id.market_item), new ClickUtil.Click() {
                    @Override
                    public void click() {
                        if (mActivity.mDrawerIsOpen) {
                            mActivity.closeDrawerLayout();
                            mActivity.updateCurrentStock(stock, Currency.BTC);
                            mActivity.upDateFragment(mActivity.index);
                        } else {
                            mActivity.updateCurrentStock(stock, Currency.BTC);
                            MarketActivity.start(getActivity(), stock.getCode(), stock.getName(), Currency.BTC, null, 2);
                        }
                    }
                });


            }

        };

        timeAdapter = new BaseQuickAdapter<Time, BaseViewHolder>(R.layout.recy_item_market, new ArrayList<>()) {
            @Override
            protected void convert(BaseViewHolder injector, Time stock) {
                injector.setText(R.id.tv_sale_price, priceFormat.format(Float.parseFloat(stock.getPrice())));
                if (stock.getPname().contains("/")) {
                    injector.setText(R.id.tv_type, stock.getPname().split("/")[0]);
                } else {
                    injector.setText(R.id.tv_type, stock.getPname());
                }
                injector.setText(R.id.tv_unit, "/" + "USDT");
                if (stock.getChange() > 0) {
                    injector.setBackgroundColor(R.id.tv_condition, getResources().getColor(R.color.market_up));
                } else if (stock.getChange() == 0) {
                    injector.setBackgroundColor(R.id.tv_condition, getResources().getColor(R.color.gray_cf));
                } else {
                    injector.setBackgroundColor(R.id.tv_condition, getResources().getColor(R.color.market_down));
                }
                injector.setText(R.id.tv_condition, stock.getChangePercentage());

                ClickUtil.click(injector.getView(R.id.market_item), new ClickUtil.Click() {
                    @Override
                    public void click() {
                        if (mActivity.mDrawerIsOpen) {
                            mActivity.closeDrawerLayout();
                            mActivity.updateCurrentStock(stock, Currency.TIME);
                            mActivity.upDateFragment(mActivity.index);
                        } else {
                            mActivity.updateCurrentStock(stock, Currency.TIME);
                            MarketActivity.start(mActivity, stock.getMark(), stock.getPname(), Currency.TIME, stock, 2);
                        }
                    }
                });
            }
        };
        adapter.setEmtyView();
        adapter.setFooterView(timeView, 0);
    }

    @Override
    public void initData() {

        if (type == 1) {
            mActivity.setOnDrawOpenListener(new MainActivity.onDrawOpenListener() {
                @Override
                public void onOpen() {
                    openWebSocket();
                    if (isFirstLoad) {
                        mPresenter.getData();
                        isFirstLoad = false;
                    }
                }

                @Override
                public void onClose() {
                    closeWebSocket();
                }
            });
        } else {
            openWebSocket();
            mPresenter.getData();
        }
    }


    public void stopRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    public void loading() {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(getActivity());
        builder.loadText(R.string.loading);
        builder.cancelable(true);
        builder.canceledOnTouchOutside(false);
        dialog = builder.show();
        dialog.show();
    }

    public void stopLoading() {
        if (dialog != null) {
            dialog.cancel();
        }
    }


    public void update(String s) {
        Flowable.just(s)
                .compose(RxSchedulersHelper.transformer())
                .map(s1 -> GsonUtil.GsonToBean(s1, NewStock.class))
                .subscribe(new NetSubScriber<NewStock>() {
                    @Override
                    public void onSuccess(NewStock newStock) {
                        for (int i = 0; i < stockList.size(); i++) {
                            if (stockList.get(i).getCode().equals(newStock.getCode())) {
                                stockList.set(i, newStock);
                                if (newStock.getName().equals(mActivity.mCurrentStockName)) { //TODO 这里做杠杆交易界面，全球币种 价格刷新
                                    mActivity.mCurrentStock = newStock;
                                }
                            }
                        }
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    public void updateTime(String message) {
        Flowable.just(message)
                .compose(RxSchedulersHelper.transformer())
                .map(s1 -> GsonUtil.GsonToBean(s1, TimeDetail.class))
                .subscribe(new NetSubScriber<TimeDetail>() {
                    @Override
                    public void onSuccess(TimeDetail timeDetail) {
                        for (int i = 0; i < timeList.size(); i++) {
                            if (timeList.get(i).getMark().equals(timeDetail.getCode())) {
                                Time time = timeList.get(i);
                                time.setPrice(timeDetail.getClosePrice() + "");
                                time.setMark(timeDetail.getCode());
                                time.setPname(timeDetail.getName());
                                time.setChange(timeDetail.getChange());
                                time.setChangePercentage(timeDetail.getChangePercentage());
                                time.setBuy(timeDetail.getBuy());
                                time.setSell(timeDetail.getSell());
                                timeList.set(i, time);
                                if (timeDetail.getCode().equals(mActivity.mCurrentStockName)) { //TODO 这里做杠杆交易界面，自有币种 价格刷新
                                    mActivity.mCurrentStock = time;
                                }
                            }
                        }
                        if (timeAdapter != null) {
                            timeAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }


    public void setAdapter(Map<Integer, Object> data) {
        stockList = (List<NewStock>) data.get(1);
        if (stockList != null && stockList.get(0) != null && mActivity.mCurrentStock == null && mActivity.mTransactionFragmentUp == false) {
            mActivity.mCurrentStock = stockList.get(0);
            mActivity.mCurrentStockName = stockList.get(0).getName();
        }
        timeList = ((Crowd) data.get(2)).getTimeData();
        adapter.setNewData(stockList);
        recyclerView.setAdapter(adapter);
        timeAdapter.setNewData(timeList);
        crowdRecyclerView.setAdapter(timeAdapter);
//        if (type == 0) {
//            raiseAdapter.setNewData(((Crowd) data.get(2)).getRaiseData());
//            raiseRecyclerView.setAdapter(raiseAdapter);
//        }
        stopLoading();
        stopRefresh();
    }
}
