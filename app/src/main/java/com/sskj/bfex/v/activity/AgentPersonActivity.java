package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.AgentNumBean;
import com.sskj.bfex.p.activity.AgentPersonPresenter;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lv on 18-6-7.
 */

public class AgentPersonActivity extends BaseActivity<AgentPersonPresenter> {
    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView titleText;
    @BindView(R.id.tv_all_person_num)
    TextView tvAllPersonNum;
    @BindView(R.id.tv_today_money)
    TextView tvTodayMoney;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;


    @Override
    protected void initView() {
        toolBar.setLeftButtonOnClickLinster((v) -> onBackPressed());
        titleText.setText("经纪人");
    }

    @Override
    protected void initData() {
        mPresenter.loadData();

    }

    @OnClick({R.id.ll_spread, R.id.ll_custom, R.id.ll_money_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_spread://我要推广
                SpreadActivity.start(this);
                break;
            case R.id.ll_custom://我的客户
                CustomActivity.start(this);
                break;
            case R.id.ll_money_detail://佣金明细
                CommissionDetailActivity.start(this);
                break;
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AgentPersonActivity.class);
        context.startActivity(starter);
    }

    public void updateUI(AgentNumBean agentNumBean) {

        tvAllPersonNum.setText(agentNumBean.getLowUsersCount() + "");//累计开户
        tvTodayMoney.setText(agentNumBean.getToDayCommission() + "");//今日收益
        tvAllMoney.setText(agentNumBean.getCommissionTotal() + "");//累计收益
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_agent_person;
    }

    @Override
    public AgentPersonPresenter getPresenter() {
        return new AgentPersonPresenter();
    }
}
