package com.sskj.bfex.v.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.TabItem;
import com.sskj.bfex.common.adapter.MyFragmentPagerAdapter;
import com.sskj.bfex.common.widget.MyTabLayout;
import com.sskj.bfex.m.bean.TradeInfoBean;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.TradePresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.v.base.BaseActivity;
import com.sskj.bfex.v.fragment.NewTradePositionFragment;
import com.sskj.bfex.v.fragment.TradeDealFragment;
import com.sskj.bfex.v.fragment.TradeDeputeFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 持仓
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TradeActivity extends BaseActivity<TradePresenter> {
    @BindView(R.id.slidingTabLayout)
    MyTabLayout slidingTabLayout;
    @BindView(R.id.tv_trade_id)
    TextView tvTradeId;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trade;
    }

    @Override
    public TradePresenter getPresenter() {
        return new TradePresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getTradeInfo();
    }

    @Override
    protected void onViewBinding() {
        ClickUtil.click(ivBack, () -> onBackPressed());
        ivRight.setVisibility(View.GONE);
//        ClickUtil.click(ivRight, () -> {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        });
        UserInfo mUserInfo = (UserInfo) SPUtils.getBean(this, Constants.SP_USER_INFO);
        tvTradeId.setText("交易ID：" + mUserInfo.account);
//        mPresenter.getTradeInfo();
        ArrayList<CustomTabEntity> tabItems=new ArrayList<>();
        tabItems.add(new TabItem("持仓",0,0));
        tabItems.add(new TabItem("委托",0,0));
        tabItems.add(new TabItem("成交",0,0));
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewTradePositionFragment());
        fragments.add(new TradeDeputeFragment());
        fragments.add(new TradeDealFragment());
        slidingTabLayout.setTabData(tabItems,this,R.id.trade_content,fragments);
    }

    private void setItem(int itemId, String title, String content) {
        ((TextView) findViewById(itemId).findViewById(R.id.tv_title)).setText(title);
        ((TextView) findViewById(itemId).findViewById(R.id.tv_content)).setText(content);
    }

    public void updateTrade(TradeInfoBean bean) {
        setItem(R.id.trade_item_dtqy, "动态权益", bean.getTotalusdt());
        setItem(R.id.trade_item_kyzj, "可用资金", bean.getKeyong_price());
        setItem(R.id.trade_item_djbzj, "冻结保证金", bean.getTotaldeposit());
        setItem(R.id.trade_item_fdyk, "浮动盈亏", bean.getYingkui());
        setItem(R.id.trade_item_pcyk, "平仓盈亏", bean.getPingcang());
        setItem(R.id.trade_item_fxl, "风险率", bean.getRisk());
    }

    @Override
    protected void onDestroy() {
        mPresenter.cancel();
        super.onDestroy();
    }
}
