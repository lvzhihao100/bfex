package com.sskj.bfex.v.activity;

import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.RaiseListBean;
import com.sskj.bfex.p.activity.RaiseInfoPresenter;
import com.sskj.bfex.utils.DateUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.Date;

import butterknife.BindView;

/**
 * <pre>
 *     author : 李岩
 *     e-mail : 465357793@qq.com
 *     time   : 2018/04/03
 *     desc   : 个人中心
 *     version: 1.0
 * </pre>
 */
public class RaiseInfoActivity extends BaseActivity<RaiseInfoPresenter> {


    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_raise_coin_type)
    TextView tvRaiseCoinType;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_raise_info;
    }

    @Override
    public RaiseInfoPresenter getPresenter() {
        return new RaiseInfoPresenter();
    }

    @Override
    protected void onViewBinding() {
        String coinType = getIntent().getStringExtra(Constants.ID);
        title.setText("抢筹明细");
        mPresenter.loadData(coinType);
        toolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
    }

    public void updateUserTitle(RaiseListBean bean) {
        tvRaiseCoinType.setText(bean.pname);
        tvNum.setText(bean.buynum + "");
        tvMoney.setText(bean.totalprice + "");
        tvPrice.setText(bean.buyprice);
        tvTime.setText(DateUtil.getDateFormat(new Date(Long.parseLong(bean.addtime) * 1000), DateUtil.PATTERN_J) + (bean.state.equals("1") ? "(未完成)" : "(已完成)"));
    }

}
