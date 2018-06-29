package com.sskj.bfex.v.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.MarketWebSocket;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.Time;
import com.sskj.bfex.m.bean.TimeDetail;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.p.activity.MarketPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.GsonUtil;
import com.sskj.bfex.utils.MathUtil;
import com.sskj.bfex.v.base.BaseActivity;
import com.sskj.bfex.v.fragment.MinuteFragment;
import com.sskj.bfex.v.fragment.SummaryFragment;
import com.sskj.bfex.v.fragment.TradeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 行情
 *
 * @author Hey
 *         created at 2018/4/2 18:20
 */
public class MarketActivity extends BaseActivity<MarketPresenter> {
    @BindView(R.id.priceNow)
    TextView priceNow;
    @BindView(R.id.changeRate)
    TextView changeRate;
    @BindView(R.id.marketBuy)
    TextView mMarketBuy;
    @BindView(R.id.marketSale)
    TextView mMarketSale;
    @BindView(R.id.highPrice)
    TextView highPrice;
    @BindView(R.id.lowPrice)
    TextView lowPrice;
    @BindView(R.id.marketTab)
    TabLayout marketTab;
    @BindView(R.id.marketToolBar)
    Toolbar toolbar;
    @BindView(R.id.market_tab)
    TabLayout tradeTabLayout;
    @BindView(R.id.market_scrollView)
    public NestedScrollView refreshLayout;

    @BindView(R.id.market_info)
    RelativeLayout marketInfo;

    private List<MinuteFragment> fragmentList = new ArrayList<>();

    String code = "BCH_USDT";

    String[] goodsType = {"minute", "minute15", "minute30", "minute60", "day"};
    private int currentIndex = 0;
    DecimalFormat format = new DecimalFormat("0.0000");

    private TradeFragment tradeFragment;
    private SummaryFragment summaryFragment;
    private MarketWebSocket webSocket;

    private Time mTime;

    /**
     * 0 五大币 1 交易币
     */
    public Currency type;

    //要跳转的页面
    private int index = 2;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_market;
    }

    @Override
    public MarketPresenter getPresenter() {
        return new MarketPresenter();
    }

    @Override
    protected void initView() {
        code = getIntent().getStringExtra("code");
        type = (Currency) getIntent().getSerializableExtra("type");
        index = getIntent().getIntExtra("index", 2);
        if (type == Currency.TIME) {
            mTime = (Time) getIntent().getSerializableExtra("time");
        }
        String name = getIntent().getStringExtra("name");
        if (name != null) {
            toolbar.setTitle(name.replace("_", "/"));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTab();
    }


    @Override
    protected void initData() {
        if (type == Currency.BTC) {
            mPresenter.getNewInfo(2, code);
        } else if (type == Currency.TIME) {
            updateTime(mTime);
        }
        //买入
        ClickUtil.click(mMarketBuy, () -> {
            MainActivity.start(mActivity, index, 0);
        });
        //卖出
        ClickUtil.click(mMarketSale, () -> {
            MainActivity.start(mActivity, index, 1);
        });
    }


    public void updateView(NewStock stock) {
        runOnUiThread(() -> {
            if (stock.getCode().equals(code)) {
                double change = Double.parseDouble(stock.getChange());
                if (change > 0) {
                    changeRate.setTextColor(getResources().getColor(R.color.market_up));
                    priceNow.setTextColor(getResources().getColor(R.color.market_up));
                } else {
                    changeRate.setTextColor(getResources().getColor(R.color.market_down));
                    priceNow.setTextColor(getResources().getColor(R.color.market_down));
                }
                changeRate.setText(stock.getChangePercentage());
                highPrice.setText(formate(stock.getHigh()));
                lowPrice.setText(formate(stock.getLow()));
                priceNow.setText(formate(stock.getClosePrice()));
            }

        });
    }

    public void updateTime(TimeDetail stock) {
        runOnUiThread(() -> {
            if (stock.getCode().equals(code)) {
                if (stock.getChange() > 0) {
                    changeRate.setTextColor(getResources().getColor(R.color.market_up));
                    priceNow.setTextColor(getResources().getColor(R.color.market_up));
                } else {
                    changeRate.setTextColor(getResources().getColor(R.color.market_down));
                    priceNow.setTextColor(getResources().getColor(R.color.market_down));
                }
                changeRate.setText(stock.getChangePercentage());
                highPrice.setText(formate(stock.getHigh()));
                lowPrice.setText(formate(stock.getLow()));
                priceNow.setText(formate(stock.getClosePrice()));
            }
        });
    }


    public void updateTime(Time stock) {

        if (stock.getChange() > 0) {
            changeRate.setTextColor(getResources().getColor(R.color.market_up));
        } else {
            changeRate.setTextColor(getResources().getColor(R.color.market_down));
        }
        changeRate.setText(stock.getChangePercentage());
        highPrice.setText(stock.getHigh());
        lowPrice.setText(stock.getLow());
        priceNow.setText(stock.getPrice());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * k线切换
     *
     * @param index
     */
    private void selectTab(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MinuteFragment minuteFragment = fragmentList.get(index);
        MinuteFragment currentFragment = fragmentList.get(currentIndex);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }
        if (minuteFragment == null) {
            minuteFragment = MinuteFragment.newInstance(code, goodsType[index]);
            fragmentList.add(index, minuteFragment);
            ft.add(R.id.marketFrame, minuteFragment);
        } else {
            ft.show(minuteFragment);
        }
        ft.commit();
        currentIndex = index;
    }

    /**
     * 成交和简介切换
     *
     * @param index
     */
    private void selectTab2(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (index == 0) {
            if (summaryFragment != null) {
                ft.hide(summaryFragment);
            }
            if (tradeFragment == null) {
                tradeFragment = TradeFragment.newInstance(code);
                ft.add(R.id.tradeLayout, tradeFragment);
            } else {
                ft.show(tradeFragment);
            }
        } else if (index == 1) {
            if (tradeFragment != null) {
                ft.hide(tradeFragment);
            }
            if (summaryFragment == null) {
                summaryFragment = SummaryFragment.newInstance(code, mTime);
                ft.add(R.id.tradeLayout, summaryFragment);
            } else {
                ft.show(summaryFragment);
            }
        }
        ft.commit();
    }

    /**
     * @param context
     * @param code    type为0时传code，为1时传mark
     * @param name
     * @param type    0 五大币 1 交易币
     * @param time    type为1时传入
     * @param index   要跳转的页面
     */
    public static void start(Activity context, String code, String name, Currency type, Time time, int index) {
        Intent intent = new Intent(context, MarketActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("time", time);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }


    public double calculateChangeRate(NewStock stock) {
        double price = Double.valueOf(stock.getPrice());
        double openPrice = Double.valueOf(stock.getOpenPrice());
        return MathUtil.divide((price - openPrice), openPrice, 6) * 100;
    }

    public double calculateChangeRate1(TimeDetail stock) {
        double price = Double.valueOf(stock.getClosePrice());
        double openPrice = Double.valueOf(stock.getOpenPrice());
        return MathUtil.divide((price - openPrice), openPrice, 6) * 100;
    }

    public String formate(String s) {
        double v = Double.valueOf(s);
        return format.format(v);
    }

    public String formate(Float s) {
        return format.format(s);
    }

    private void initWebSocket() {
        String url = "";
        if (type == Currency.BTC) {
            url = HttpConfig.BTC_WS;
        } else if (type == Currency.TIME) {
            url = HttpConfig.TIME_SOCKET;

        }
        String text = "{\"type\":\"vb_okex\"}";
        webSocket = new MarketWebSocket(url, "marketActivity", text);
        webSocket.setListener(message -> {
            if (type == Currency.BTC) {
                NewStock newStock = GsonUtil.GsonToBean(message, NewStock.class);
                updateView(newStock);
            } else {
                TimeDetail newStock = GsonUtil.GsonToBean(message, TimeDetail.class);
                updateTime(newStock);
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initWebSocket();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webSocket != null) {
            webSocket.closeWebSocket();
        }
    }

    private void initTab() {
        fragmentList.add(null);
        fragmentList.add(null);
        fragmentList.add(null);
        fragmentList.add(null);
        fragmentList.add(null);
        marketTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tradeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab2(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        selectTab(0);
        selectTab2(0);
    }
}
