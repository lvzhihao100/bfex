package com.sskj.bfex.v.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.adapter.ItemClickSupport;
import com.sskj.bfex.common.decoration.DividerLineItemDecoration;
import com.sskj.bfex.m.bean.RaiseListBean;
import com.sskj.bfex.p.activity.RaiseListPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.DateUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


public class RaiseListActivity extends BaseActivity<RaiseListPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_title)
    TextView title;

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private SlimAdapter slimAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_raise_list;
    }

    @Override
    public RaiseListPresenter getPresenter() {
        return new RaiseListPresenter();
    }

    @Override
    protected void onViewBinding() {
        title.setText("抢筹明细");
        ClickUtil.click(ivBack, this::onBackPressed);
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadData();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerLineItemDecoration(this).setPaintWidth(10).setFirstDraw(false));
        slimAdapter = SlimAdapter.create().register(R.layout.recy_item_raise, new SlimInjector<RaiseListBean>() {
            @Override
            public void onInject(RaiseListBean data, IViewInjector injector) {
                injector.text(R.id.tv_raise_type, data.pname)
                        .text(R.id.tv_num, data.cro_no)
                        .text(R.id.tv_time, DateUtil.getDateFormat(new Date(Long.parseLong(data.addtime) * 1000), DateUtil.PATTERN_I));
            }
        }).attachTo(recyclerView).updateData(new ArrayList<>());
        smartRefresh.autoRefresh();
        smartRefresh.setRefreshHeader(new BezierCircleHeader(this));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {
            Intent intent = new Intent(this, RaiseInfoActivity.class);
            intent.putExtra(Constants.ID, ((RaiseListBean) slimAdapter.getData().get(position)).cro_id);
            startActivity(intent);
        });
    }

    public void refresh(List<RaiseListBean> data) {
        if (smartRefresh.isRefreshing()) {
            smartRefresh.finishRefresh();
        }
        slimAdapter.updateData(data);
    }


}