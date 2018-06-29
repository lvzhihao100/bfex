package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.decoration.DividerLineItemDecoration;
import com.sskj.bfex.m.bean.TradeDealBean;
import com.sskj.bfex.p.fragment.TradeDealFragmentPst;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.activity.TradeActivity;
import com.sskj.bfex.v.base.BaseViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Lee on 2018/2/28 0028.
 */

public class TradeDealFragment extends BaseViewPagerFragment<TradeActivity, TradeDealFragmentPst> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SlimAdapter slimAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public TradeDealFragmentPst getPresenter() {
        return new TradeDealFragmentPst();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (slimAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity()).setFirstDraw(false));
            slimAdapter = SlimAdapter.create().register(R.layout.recy_item_trade_deal, new SlimInjector<TradeDealBean>() {
                @Override
                public void onInject(TradeDealBean data, IViewInjector injector) {
                    injector.text(R.id.tv_lx, data.getOtype() == 1 ? "做多" : "做空")
                            .textColor(R.id.tv_lx, getResources().getColor(1 == data.getOtype() ? R.color.market_down : R.color.market_up))
                            .text(R.id.tv_bz, data.getPname())
                            .text(R.id.tv_time, data.getSelltime())
                            .text(R.id.tv_ccj, data.getBuyprice())
                            .text(R.id.tv_pcj, data.getSellprice())
                            .text(R.id.tv_cjl, data.getBuynum())
                            .text(R.id.tv_zje, data.getTotalprice())
                            .text(R.id.tv_sxf, data.getSxfee())
                            .text(R.id.tv_dayfee, data.getDayfee())
                            .text(R.id.tv_fdyk, data.getProfit());
                }
            }).attachTo(recyclerView).updateData(new ArrayList<>());
//            ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {
//                MarketActivity.start(getActivity(), ((TradeDealBean) slimAdapter.getData().get(position)).getPname().replace("/", "_").toLowerCase(), ((TradeDealBean) slimAdapter.getData().get(position)).getPname().replace("/", "_"));
//            });
            ClickUtil.click(llNoData, () -> {
                llNoData.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                refreshLayout.autoRefresh();
            });
            refreshLayout.setOnRefreshListener(refreshLayout -> {
                mPresenter.loadData();
            });
            refreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));
            refreshLayout.autoRefresh();
        }


    }


    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }

    }

    public void updateData(List<TradeDealBean> data) {
        refreshLayout.finishRefresh();
        if (data == null || data.size() == 0) {
            llNoData.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            if (data == null) {
                ((TextView) llNoData.findViewById(R.id.tv_content)).setText("网络错误");
            } else {
                ((TextView) llNoData.findViewById(R.id.tv_content)).setText("暂无数据");
            }
        } else {
            llNoData.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
            slimAdapter.updateData(data);
        }
    }


}
