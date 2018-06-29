package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shizhefei.view.hvscrollview.HVScrollView;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.MarketWebSocket;
import com.sskj.bfex.common.decoration.DividerLineItemDecoration;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.TradePositionListBean;
import com.sskj.bfex.p.fragment.TradePositionFragmentPst;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.activity.TradeActivity;
import com.sskj.bfex.v.base.BaseViewPagerFragment;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TradePositionFragment extends BaseViewPagerFragment<TradeActivity, TradePositionFragmentPst> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    HVScrollView lasthvScrollView;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SlimAdapter slimAdapter;
    private MarketWebSocket mWebSocket;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public TradePositionFragmentPst getPresenter() {
        return new TradePositionFragmentPst();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("TradePositionFragment", isVisibleToUser + "");
    }

    @Override
    public void onPause() {
        Log.e("TradePositionFragment", "pause");
        super.onPause();
        if (mWebSocket != null) {
            mWebSocket.closeWebSocket();
        }
    }


    @Override
    public void onResume() {
        Log.e("TradePositionFragment", "resume");
        super.onResume();
        initWebSocket();
    }

    public void initWebSocket() {
        String text = "{\"type\":\"vb_okex\"}";
        mWebSocket = new MarketWebSocket(HttpConfig.BTC_WS, "TradePositionFragment", text);
        mWebSocket.setListener(message -> mPresenter.loadData());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (slimAdapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity()).setFirstDraw(false));
            slimAdapter = SlimAdapter.create().register(R.layout.recy_item_trade_position, new SlimInjector<TradePositionListBean>() {
                @Override
                public void onInject(TradePositionListBean data, IViewInjector injector) {
                    injector.with(R.id.include_bz, view1 -> {
                        ((TextView) (view1.findViewById(R.id.tv_title))).setText("币种");
                        ((TextView) (view1.findViewById(R.id.tv_content))).setText(data.getPname().substring(0, data.getPname().indexOf("/")));
                    }).with(R.id.include_lx, view1 -> {
                        ((TextView) (view1.findViewById(R.id.tv_title))).setText("类型");
                        ((TextView) (view1.findViewById(R.id.tv_content))).setText("买入");
                    }).with(R.id.include_ccl, view1 -> {
                        ((TextView) (view1.findViewById(R.id.tv_title))).setText("持仓量");
                        ((TextView) (view1.findViewById(R.id.tv_content))).setText(data.getBuynum());
                    }).with(R.id.include_bzj, view1 -> {
                        ((TextView) (view1.findViewById(R.id.tv_title))).setText("保证金");
                        ((TextView) (view1.findViewById(R.id.tv_content))).setText(data.getDeposit());
                    }).with(R.id.include_cbxj, view1 -> {
                        ((TextView) (view1.findViewById(R.id.tv_title))).setText("成本/现价");
                        ((TextView) (view1.findViewById(R.id.tv_content))).setText(data.getBuyprice() + "/" + data.getNewprice());
                    }).with(R.id.include_fd, view1 -> {
                        ((TextView) (view1.findViewById(R.id.tv_title))).setText("浮动");
                        ((TextView) (view1.findViewById(R.id.tv_content))).setText(data.getFdyk());
                    }).with(R.id.hvScrollView, view1 -> {
                        HVScrollView hvScrollView = (HVScrollView) view1;
                        hvScrollView.setOnScrollChangeListener(new HVScrollView.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(HVScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                                if (lasthvScrollView != null && lasthvScrollView != v) {
                                    lasthvScrollView.scrollTo(0, 0);
                                }
                                lasthvScrollView = v;
                            }
                        });
                    });
                }
            }).attachTo(recyclerView).updateData(new ArrayList<>());
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (lasthvScrollView != null) {
                        lasthvScrollView.scrollTo(0, 0);
                    }
                }
            });
//            ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {
//                MarketActivity.start(getActivity(), ((TradePositionListBean) slimAdapter.getData().get(position)).getPname().replace("/", "_").toLowerCase(),((TradePositionListBean) slimAdapter.getData().get(position)).getPname().replace("/", "_"));
//            });
            ClickUtil.click(llNoData, () -> {
                llNoData.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                refreshLayout.autoRefresh();
            });
            refreshLayout.setOnRefreshListener(refreshLayout -> {
                mPresenter.loadData();
            });
            refreshLayout.autoRefresh();
        }

    }

    public void updateData(List<TradePositionListBean> data) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
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
