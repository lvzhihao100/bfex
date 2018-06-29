package com.sskj.bfex.v.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.decoration.DividerLineItemDecoration;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.WithdrawListBean;
import com.sskj.bfex.p.activity.WithdrawRecordPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.DateUtil;
import com.sskj.bfex.v.base.BaseActivity;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class WithdrawRecordActivity extends BaseActivity<WithdrawRecordPresenter> {

    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private SlimAdapter slimAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    public WithdrawRecordPresenter getPresenter() {
        return new WithdrawRecordPresenter();
    }

    @Override
    protected void onViewBinding() {

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("1", "待审核");
        stringStringHashMap.put("2", "已审核");
        stringStringHashMap.put("3", "已拒绝");
        mTitle.setText("提现记录");
        ClickUtil.click(llNoData, () -> smartRefresh.autoRefresh());
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerLineItemDecoration(this).setFirstDraw(false).setLeftPadding((int) RudenessScreenHelper.pt2px(this, 30)));
        slimAdapter = SlimAdapter.create().register(R.layout.recy_item_withdraw, new SlimInjector<WithdrawListBean>() {
            @Override
            public void onInject(WithdrawListBean data, IViewInjector injector) {

                injector.text(R.id.tv_address_title, "提现地址")
                        .text(R.id.tv_num_title, "提现数量")
                        .text(R.id.tv_sxf_title, "手续费")
                        .text(R.id.tv_submit_time_title, "提交时间")
                        .text(R.id.tv_check_time_title, "到帐时间")
                        .text(R.id.tv_address, TextUtils.isEmpty(data.getQianbao_url()) ? "" : data.getQianbao_url())
                        .text(R.id.tv_num, data.getPrice())
                        .text(R.id.tv_sxf, data.getTxfee())
                        .text(R.id.tv_status, stringStringHashMap.get(data.getState()))
                        .textColor(R.id.tv_status, getResources().getColor(data.getState().equals("3") ? R.color.red_eb : R.color.textColorNormal))
                        .text(R.id.tv_submit_time, DateUtil.getDateFormat(new Date(Long.valueOf(data.getAddtime()) * 1000), DateUtil.PATTERN_K))
                        .text(R.id.tv_check_time, TextUtils.isEmpty(data.getCheck_time()) ? "" : DateUtil.getDateFormat(new Date(Long.valueOf(data.getCheck_time()) * 1000), DateUtil.PATTERN_K));
//                        .visibility(R.id.ll_check_time, TextUtils.isEmpty(data.getAddtime()) ? View.GONE : View.VISIBLE);
            }
        }).attachTo(recyclerView).updateData(new ArrayList<>());
        smartRefresh.setRefreshHeader(new BezierCircleHeader(this));
        smartRefresh.setOnRefreshListener(refreshLayout -> mPresenter.getWithdrawList());
        smartRefresh.autoRefresh();
    }

    public void refresh(List<WithdrawListBean> data) {
        smartRefresh.finishRefresh();
        if (data == null || data.size() == 0) {
            smartRefresh.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        } else {
            smartRefresh.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
            slimAdapter.updateData(data);
        }
    }


}
