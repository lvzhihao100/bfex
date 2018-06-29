package com.sskj.bfex.v.fragment;

import android.graphics.Color;
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
import com.sskj.bfex.m.bean.TradeDeputeListBean;
import com.sskj.bfex.p.fragment.TradeDeputeFragmentPst;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.TipUtil;
import com.sskj.bfex.v.activity.TradeActivity;
import com.sskj.bfex.v.base.BaseViewPagerFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * Created by Lee on 2018/2/28 0028.
 */

public class TradeDeputeFragment extends BaseViewPagerFragment<TradeActivity, TradeDeputeFragmentPst> {


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
    public TradeDeputeFragmentPst getPresenter() {
        return new TradeDeputeFragmentPst();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (slimAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity()).setFirstDraw(false));
            slimAdapter = SlimAdapter.create().register(R.layout.recy_item_depute, new SlimInjector<TradeDeputeListBean>() {
                @Override
                public void onInject(TradeDeputeListBean data, IViewInjector injector) {
                    injector.text(R.id.trade_cancel, "撤单")
                            .clicked(R.id.trade_cancel, v -> {
                                TipUtil.getTip(getActivity(), "确定撤单吗", () -> {
                                    mPresenter.cancelTrade(data.getEn_id());
                                }).show();
                            })
                            .text(R.id.tv_wtj, data.getBuyprice())
                            .text(R.id.tv_bz, data.getPname())
                            .text(R.id.tv_lx, "1".equals(data.getOtype()) ? "做多" : "做空")
                            .text(R.id.tv_time, data.getAddtime())
                            .textColor(R.id.tv_lx, "1".equals(data.getOtype()) ? Color.parseColor("#01c485") : Color.parseColor("#eb7147"))
                            .text(R.id.tv_wtl, data.getBuynum())
                            .text(R.id.tv_zje, data.getTotalprice())
                            .text(R.id.tv_sxf, data.getSxfee());
                }

            }).attachTo(recyclerView).updateData(new ArrayList<>());
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


    public void updateData(List<TradeDeputeListBean> data) {
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
