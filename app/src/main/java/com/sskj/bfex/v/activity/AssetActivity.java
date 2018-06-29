package com.sskj.bfex.v.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.decoration.DividerLineItemDecoration;
import com.sskj.bfex.m.bean.AseetListBean;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.AssetPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.base.BaseActivity;



import java.util.ArrayList;

import butterknife.BindView;


public class AssetActivity extends BaseActivity<AssetPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_title)
    TextView ivTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SlimAdapter slimAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_asset;
    }

    @Override
    public AssetPresenter getPresenter() {
        return new AssetPresenter();
    }

    @Override
    protected void onViewBinding() {
        ClickUtil.click(ivBack, () -> onBackPressed());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerLineItemDecoration(this).setLeftPadding((int) RudenessScreenHelper.pt2px(this, 30)));
        slimAdapter = SlimAdapter.create().register(R.layout.recy_item_asset, new SlimInjector<AseetListBean.AseetBean>() {
            @Override
            public void onInject(AseetListBean.AseetBean data, IViewInjector injector) {
                injector.text(R.id.tv_type, data.pname)
                        .text(R.id.tv_first_title, data.usable == null ? "0" : data.usable)
                        .text(R.id.tv_first_content, "可用")
                        .text(R.id.tv_second_title, data.frost == null ? "0" : data.frost)
                        .text(R.id.tv_second_content, "冻结");

            }
        }).attachTo(recyclerView).updateData(new ArrayList<>());
        mPresenter.loadData();
    }

    public void updateUserInfo(UserInfo data) {
        tvTotalMoney.setText(data.account);
    }

    public void refresh(AseetListBean asset) {
        slimAdapter.updateData(asset.asset);
        tvTotalMoney.setText(asset.wallone);


    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AssetActivity.class));

    }
}