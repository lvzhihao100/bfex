package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.TabItem;
import com.sskj.bfex.common.rxbus2.RxBus;
import com.sskj.bfex.common.rxbus2.Subscribe;
import com.sskj.bfex.common.rxbus2.ThreadMode;
import com.sskj.bfex.common.widget.MyTabLayout;
import com.sskj.bfex.common.widget.NoticeDialog;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.Time;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.p.activity.MainPresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.v.base.BaseActivity;
import com.sskj.bfex.v.fragment.FabiFragment;
import com.sskj.bfex.v.fragment.InformationFragment;
import com.sskj.bfex.v.fragment.LevelTransactionFragment;
import com.sskj.bfex.v.fragment.MarketFragment;
import com.sskj.bfex.v.fragment.MineFragment;
import com.sskj.bfex.v.fragment.TransactionFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by Hey at 2018/5/3 18:45
 */
public class MainActivity extends BaseActivity<MainPresenter> {

    @BindView(R.id.mainContent)
    FrameLayout mTabcontent;
    @BindView(R.id.tabLayout)
    MyTabLayout tabLayout;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_left_drawer_layout)
    LinearLayout mLeftLayout;

    public UserInfo mUserInfo;
    public boolean mDrawerIsOpen;
    public Object mCurrentStock;
    public Currency mCurrency = Currency.BTC;

    /**
     * 0 买 1 卖
     */
    public int status;

    private onDrawOpenListener listener;

    ArrayList<Fragment> fragmentList = new ArrayList<>();

    public boolean mTransactionFragmentUp = false;

    NoticeDialog dialog;
    Runnable runnable;
    ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    public String mCurrentStockName;

    public int index;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mMobile) & Constants.mIsLogin) {
            mPresenter.upDateUserInfo(mMobile); //更新用户数据
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int currentIndex = intent.getIntExtra("index", 0);
        this.status = intent.getIntExtra("type", 0);
        if (currentIndex == 2) {
            mUserInfo = (UserInfo) SPUtils.getBean(mActivity, Constants.SP_USER_INFO);
            tabLayout.setCurrentTab(currentIndex);
        } else {
            tabLayout.setCurrentTab(currentIndex);
        }

    }

    @Override
    protected void onViewBinding() {
        mUserInfo = (UserInfo) SPUtils.getBean(mActivity, Constants.SP_USER_INFO);
        RxBus.getDefault().register(this);
//        mPresenter.getNotice();
        //设置菜单内容之外其他区域的背景色
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止滑动开启
        mUserInfo = (UserInfo) SPUtils.getBean(mActivity, Constants.SP_USER_INFO);
        initFragment();
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                if (listener != null) {
                    listener.onOpen();
                }
                mDrawerIsOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerIsOpen = false;
                if (listener != null) {
                    listener.onClose();
                }
            }
        });

    }

    private void initFragment() {
        fragmentList.add(new MarketFragment());
        fragmentList.add(TransactionFragment.newInstance());
        fragmentList.add(LevelTransactionFragment.newInstance());
        fragmentList.add(FabiFragment.newInstance());
        fragmentList.add(new MineFragment());

        tabEntities.add(new TabItem("行情", R.mipmap.icon_market_nor, R.mipmap.icon_market_pre));
        tabEntities.add(new TabItem("交易", R.mipmap.icon_transaction_nor, R.mipmap.icon_transaction_pre));
        tabEntities.add(new TabItem("杠杆交易", R.mipmap.icon_level_transaction_nor, R.mipmap.icon_level_transaction_pre));
        tabEntities.add(new TabItem("法币交易", R.mipmap.icon_information_nor, R.mipmap.icon_information_pre));
        tabEntities.add(new TabItem("我的", R.mipmap.icon_mine_nor, R.mipmap.icon_mine_pre));
        tabLayout.setTabData(tabEntities, this, R.id.mainContent, fragmentList);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                index = position;
                mUserInfo = (UserInfo) SPUtils.getBean(mActivity, Constants.SP_USER_INFO);
                upDateFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public void upDateFragment(int index) {

        if (index == 1) {
            TransactionFragment transactionFragment = (TransactionFragment) getSupportFragmentManager().findFragmentByTag(TransactionFragment.class.getSimpleName());
            if (transactionFragment != null) {
                transactionFragment.upDate();
            }
        }

        if (index == 2) {
            LevelTransactionFragment levelTransactionFragment = (LevelTransactionFragment) getSupportFragmentManager().findFragmentByTag(LevelTransactionFragment.class.getSimpleName());
            if (levelTransactionFragment != null) {
                levelTransactionFragment.upDate();
            }
        }


    }

    /**
     * @param context c
     * @param index   要跳转的页面index
     * @param type    0 买 1卖
     */
    public static void start(Context context, int index, int type) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void openDrawerLayout() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeDrawerLayout() {
        mDrawerLayout.closeDrawers();
    }

    @OnClick(R.id.main_left_close)
    public void onViewClick() {
        mDrawerLayout.closeDrawers();
    }


    /**
     * 更新用户信息
     *
     * @param user 用户信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInfo(UserInfo user) {
        if (user != null) {
            mUserInfo = user;
            SPUtils.putBean(mActivity, Constants.SP_USER_INFO, user);
        }
    }


    @Subscribe(code = Constants.USER_LOGIN, threadMode = ThreadMode.MAIN)
    public void userLogin() {
        mActivity.mMobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
    }


    /**
     * 更新最新币种数据
     *
     * @param stock    币
     * @param currency 类型
     */
    public void updateCurrentStock(Object stock, Currency currency) {
        this.mCurrency = currency;
        this.mCurrentStock = stock;
        if (currency == Currency.BTC) {
            mCurrentStockName = ((NewStock) stock).getName();
        } else if (currency == Currency.TIME) {
            mCurrentStockName = ((Time) stock).getPname();
        }
    }

    public void setOnDrawOpenListener(onDrawOpenListener onDrawOpenListener) {
        this.listener = onDrawOpenListener;
    }

    public interface onDrawOpenListener {
        void onOpen();

        void onClose();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.MARKET:
                    onNewIntent(data);
                    break;
                case Constants.PAY_PWD_INPUT:
                    LevelTransactionFragment fragment = (LevelTransactionFragment) getSupportFragmentManager().findFragmentByTag(LevelTransactionFragment.class.getSimpleName());
                    if (fragment != null) {
                        fragment.onActivityResult(requestCode, resultCode, data);
                    }
                    break;
            }
        }

    }


    public void showNotice(String tpgg) {
        dialog = new NoticeDialog.Builder(this)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .loadText(tpgg)
                .build();
        dialog.show();

        Handler handler = new Handler();

        runnable = new Runnable() {
            int i = 4;

            @Override
            public void run() {
                if (i != 0) {
                    Log.e("MAIN", i + "");
                    handler.postDelayed(runnable, 1000);
                    dialog.getButton().setText("(" + i + ")" + "我知道了");
                    dialog.getButton().setEnabled(false);
                    i--;
                } else {
                    dialog.getButton().setText("我知道了");
                    dialog.getButton().setEnabled(true);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();

    }
}
