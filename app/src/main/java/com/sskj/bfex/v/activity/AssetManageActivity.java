package com.sskj.bfex.v.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName：bfex
 * DESC: (资产管理)
 * Created by 李岩 on 2018/6/29 0029
 * updateName:(修改人名称)
 * updateTime:(修改时间)
 * updateDesc:(修改内容)
 */
public class AssetManageActivity extends BaseActivity<AssetManagePresenter> implements View.OnClickListener, OnRefreshListener {
    @BindView(R.id.asset_manage_toolbar)
    ToolBarLayout mAssetManageToolbar;
    @BindView(R.id.asset_manage_recyclerview)
    RecyclerView mAssetManageRecyclerview;
    @BindView(R.id.asset_manage_smartrefresh)
    SmartRefreshLayout mAssetManageSmartrefresh;
    private BaseAdapter<String> mAdapter;
    private TextView mAssetBalance;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_asset_manage;
    }

    @Override
    public AssetManagePresenter getPresenter() {
        return new AssetManagePresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mAssetManageToolbar.setLeftButtonOnClickLinster(this);
        mAssetManageSmartrefresh.setRefreshHeader(new BezierCircleHeader(this));
        mAssetManageSmartrefresh.setOnRefreshListener(this);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
          strings.add(i+"");
        }

        mAdapter = new BaseAdapter<String>(R.layout.item_asset_manage, strings, mAssetManageRecyclerview) {

            @Override
            public void bind(ViewHolder holder, String item) {

            }
        };
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_asset_header, null);
        mAssetBalance = headerView.findViewById(R.id.asset_balance);
        mAdapter.addHeaderView(headerView);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mAssetManageSmartrefresh.finishRefresh();
        mPresenter.requestUserAssetList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_leftbutton: finish(); break;
        }
    }
}
