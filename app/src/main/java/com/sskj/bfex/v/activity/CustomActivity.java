package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.LoginBean;
import com.sskj.bfex.mvchelper.ModelRx2DataSource;
import com.sskj.bfex.p.activity.CustomPresenter;
import com.sskj.bfex.utils.NumberUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lv on 18-6-7.
 * 我的客户
 */

public class CustomActivity extends BaseActivity<CustomPresenter> {
    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView titleText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coolRefreshView)
    CoolRefreshView coolRefreshView;
    private SlimAdapter slimAdapter;

    @Override
    protected void initView() {
        toolBar.setLeftButtonOnClickLinster((v)->onBackPressed());
        titleText.setText("我的客户");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapter = SlimAdapter.create().register(R.layout.item_custom, new SlimInjector<LoginBean>() {
            @Override
            public void onInject(LoginBean data, IViewInjector injector) {
                injector.text(R.id.tv_title, data.getNickname())
                        .text(R.id.tv_balance, NumberUtil.keepNoZore(data.getStockUserInfo().getUsableFund()))
                        .text(R.id.tv_time, data.getCreateTime());
            }
        }).attachTo(recyclerView).updateData(new ArrayList());
        ModelRx2DataSource<String> dataSource =
                new ModelRx2DataSource<>((ModelRx2DataSource.OnLoadSource<LoginBean>)
                        page -> mPresenter.loadData(page), 15);
        MVCCoolHelper<List<String>> mvcCoolHelper = new MVCCoolHelper<>(coolRefreshView);
        mvcCoolHelper.setDataSource(dataSource);
        mvcCoolHelper.setAdapter(slimAdapter);
        mvcCoolHelper.refresh();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CustomActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom;
    }

    @Override
    public CustomPresenter getPresenter() {
        return new CustomPresenter();
    }
}
