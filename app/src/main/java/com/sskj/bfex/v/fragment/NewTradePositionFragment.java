package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.common.MarketWebSocket;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.common.decoration.DividerLineItemDecoration;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.TradePositionListBean;
import com.sskj.bfex.p.fragment.NewTradePositionFragmentPst;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.TipUtil;
import com.sskj.bfex.v.activity.TradeActivity;
import com.sskj.bfex.v.base.BaseViewPagerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class NewTradePositionFragment extends BaseViewPagerFragment<TradeActivity, NewTradePositionFragmentPst> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private MarketWebSocket mWebSocket;

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd");

    private BaseAdapter<TradePositionListBean> adapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_trade_position;
    }

    @Override
    public NewTradePositionFragmentPst getPresenter() {
        return new NewTradePositionFragmentPst();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initWebSocket();
        } else {
            if (mWebSocket != null) {
                mWebSocket.closeWebSocket();
            }
        }

        Log.e("TradePositionFragment", isVisibleToUser + "");
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mWebSocket != null) {
            mWebSocket.closeWebSocket();
        }
    }

    @Override
    public void initView() {
        refreshLayout.setRefreshHeader(new BezierCircleHeader(mActivity));
        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            mPresenter.loadData();
        });
    }

    public void stopRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void initData() {
        mPresenter.loadData();
    }


    public void initWebSocket() {
        String text = "{\"type\":\"vb_okex\"}";
        mWebSocket = new MarketWebSocket(HttpConfig.BTC_WS, "NewTradePositionFragment", text);
        mWebSocket.setListener(message -> mPresenter.loadData());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity()).setFirstDraw(false));
        adapter = new BaseAdapter<TradePositionListBean>(R.layout.recy_item_trade_position_h, new ArrayList<>(), recyclerView) {
            @Override
            public void bind(ViewHolder holder, TradePositionListBean data) {
                holder.setText(R.id.tv_bz, data.getPname())
                        .setText(R.id.tv_lx, data.getOtype().equals("1") ? "做多" : "做空")
                        .setTextColor(R.id.tv_lx, getResources().getColor(data.getOtype().equals("1") ? R.color.market_down : R.color.market_up))
                        .setText(R.id.tv_cjj, data.getBuyprice())
                        .setText(R.id.tv_ccl, data.getBuynum())
                        .setText(R.id.tv_je, data.getTotalprice())
                        .setText(R.id.tv_xj, data.getNewprice())
                        .setText(R.id.tv_fdyk, data.getFdyk())
                        .setText(R.id.tv_sxf, data.getSxfee())
                        .setText(R.id.tv_dayfee, data.getDayfee())
                        .setText(R.id.tv_time, dateFormat.format(data.getAddtime() * 1000));
                ClickUtil.click(holder.getView(R.id.trade_sale), () -> {
                    TipUtil.getTip(getActivity(), "确定卖出吗", () -> {
                        mPresenter.pingcang(data.getHold_id(), data.getNewprice());
                    }).show();
                });
            }
        };
        adapter.setEmtyView();
        recyclerView.setAdapter(adapter);
    }

    public void updateData(List<TradePositionListBean> data) {
        adapter.setNewData(data);
    }
}
