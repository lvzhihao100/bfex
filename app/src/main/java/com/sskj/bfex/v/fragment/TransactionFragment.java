package com.sskj.bfex.v.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.MoneyValueFilter;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.CurrencyInfo;
import com.sskj.bfex.m.bean.Entrust;
import com.sskj.bfex.m.bean.Time;
import com.sskj.bfex.m.bean.TransactionModel;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.p.fragment.TransactionPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.MathUtil;
import com.sskj.bfex.utils.NumberUtils;
import com.sskj.bfex.utils.RxSchedulersHelper;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.EntrustActivity;
import com.sskj.bfex.v.activity.LoginActivity;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.activity.MarketActivity;
import com.sskj.bfex.v.base.BaseFragment;
import com.sskj.bfex.v.widget.mychart.BubbleSeekBar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;

/**
 * 交易
 * create by Hey at 2018/4/26 9:14
 */
public class TransactionFragment extends BaseFragment<MainActivity, TransactionPresenter> {
    @BindView(R.id.transaction_sell_list)
    RecyclerView transactionSellList;
    @BindView(R.id.transaction_buy_list)
    RecyclerView transactionBuyList;
    @BindView(R.id.transaction_history_list)
    RecyclerView transactionHistoryList;
    @BindView(R.id.transaction_select_buy)
    RadioButton transactionSelectBuy;
    @BindView(R.id.transaction_select_sell)
    RadioButton transactionSelectSell;
    @BindView(R.id.transaction_submit_order)
    TextView submitOrder;
    @BindView(R.id.transaction_history_all)
    TextView historyAll;
    @BindView(R.id.transaction_now_price)
    TextView nowPrice;
    @BindView(R.id.transction_price_textView)
    TextView limitPrice;
    @BindView(R.id.transction_price_editText)
    EditText priceEdit;
    @BindView(R.id.transaction_num_edit)
    EditText numEdit;
    @BindView(R.id.transaction_type)
    TextView transactionType;
    @BindView(R.id.transaction_toolbar)
    ToolBarLayout toolBar;
    @BindView(R.id.transction_increase_price)
    TextView increase;
    @BindView(R.id.transction_decrease_price)
    TextView decrease;
    @BindView(R.id.transaction_count)
    TextView transactionTotal;
    @BindView(R.id.transaction_user_balance)
    TextView usefulNum;

    @BindView(R.id.spinner)
    Spinner selectType;

    @BindView(R.id.transcation_seekbar)
    BubbleSeekBar transcationSeekbar;

    @BindView(R.id.transaction_refresh)
    SmartRefreshLayout smartRefreshLayout;


    private float unit;

    //最新买卖条目
    private final int buyNum = 5;
    /**
     * 委托记录adapter
     */
    private BaseAdapter<Entrust> transactionAdapter;
    private BaseAdapter<TransactionModel.Item> sellAdapter;
    private BaseAdapter<TransactionModel.Item> buyAdapter;


    private DecimalFormat priceFormat = new DecimalFormat("0.0000");
    private DecimalFormat numFormat = new DecimalFormat("0.000");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd");

    private float realPrice;

    //用户余额
    private float money = 0;
    /**
     * 币种名称
     */
    private String name = "";
    /**
     * 币种编码
     */
    private String code = "";

    /**
     * 000
     * 最大数量
     */
    private float max = 0;
    /**
     * 最小数量
     */
    private float min = 0;


    private static final int SHIJIA = 1;
    private static final int XIANJIA = 2;

    //手续费
    private float transFee = 0;

    //持有币的数量
    private float haveNum = 0;


    /**
     * 价格类型 ： 1: 市价   ；   2 ： 限价
     * 默认 限价
     */
    private int orderType = XIANJIA;


    private String currencyType = "";


    @Override
    public int getLayoutId() {
        return R.layout.fragment_transaction;
    }

    @Override
    public TransactionPresenter getPresenter() {
        return new TransactionPresenter();
    }

    @Override
    public void initView() {
        toolBar.setRightButtonIcon(getResources().getDrawable(R.mipmap.icon_a_nor));
        toolBar.setRightButtonOnClickLinster(view12 -> { //跳转股票详情
            switch (mActivity.mCurrency) {
                case TIME:
                    MarketActivity.start(getActivity(), code, name, mActivity.mCurrency, (Time) mActivity.mCurrentStock, 1);
                    break;

                case BTC:
                    MarketActivity.start(getActivity(), code, name, mActivity.mCurrency, null, 1);
                    break;
            }
        });
        initRecyclerView();
        initAdapter();
        initListener();
        numEdit.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(3)});
        priceEdit.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(4)});
    }

    @Override
    public void initData() {
        upDate();
    }


    /**
     * 设置币种信息
     *
     * @param info 币种信息
     */
    public void serCurrencyInfo(CurrencyInfo info) {
        if (!TextUtils.isEmpty(info.getTrans_fee())) {
            transFee = Float.parseFloat(info.getTrans_fee());
        }
        haveNum = Float.parseFloat(numFormat(info.getSyb() + ""));
        if (!TextUtils.isEmpty(info.getWallone())) {
            money = Float.parseFloat(numFormat(info.getWallone()));
        }
        calculateMaxMin();
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        transactionHistoryList.setLayoutManager(linearLayoutManager);
        transactionSellList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, true));
        transactionBuyList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        transactionHistoryList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
    }

    /**
     * 初始化Adapter,数据集为空
     */
    private void initAdapter() {
        transactionAdapter = new BaseAdapter<Entrust>(R.layout.item_recycler_entrust, new ArrayList<>(), transactionHistoryList, true) {
            @Override
            public void bind(ViewHolder holder, Entrust item) {
                holder.setText(R.id.item_entrust_name, item.getPname())
                        .setText(R.id.item_entrust_type, item.getType() == 1 ? "买入" : "卖出")
                        .setText(R.id.item_entrust_time, dateFormat.format(Long.parseLong(item.getAdd_time()) * 1000))
                        .setText(R.id.item_entrust_price, item.getWtprice())
                        .setText(R.id.item_entrust_real_num, item.getCjnum());
                if (item.getOtype() == 2 && item.getType() == 1) {  //市价买入
                    holder.setText(R.id.item_entrust_num_text, "交易额")
                            .setText(R.id.item_entrust_num, item.getTotalprice());
                } else { //其他
                    holder.setText(R.id.item_entrust_num_text, "数量")
                            .setText(R.id.item_entrust_num, item.getWtnum());
                }
                TextView cancel = holder.getView(R.id.item_entrust_cancel);
                switch (item.getStatus()) {
                    case 0:
                    case 1:
                        cancel.setText("撤销");
                        cancel.setOnClickListener(v -> mPresenter.cancelOrder(mActivity.mMobile, item.getOrders_id()));
                        cancel.setTextColor(getResources().getColor(R.color.blue_5d));
                        break;
                    case 2:
                        cancel.setText("已成交");
                        cancel.setTextColor(getResources().getColor(R.color.red_eb));
                        break;
                    case -1:
                        cancel.setText("已撤销");
                        cancel.setTextColor(getResources().getColor(R.color.gray_c9));
                        break;
                }
            }
        };
        sellAdapter = new BaseAdapter<TransactionModel.Item>(R.layout.item_recycler_transaction_sell, new ArrayList<>(), transactionSellList) {
            @Override
            public void bind(ViewHolder holder, TransactionModel.Item item) {
                holder.setText(R.id.transaction_sell_id, holder.getLayoutPosition() + 1 + "");
                holder.setText(R.id.transaction_sell_price, priceFormat(item.getPrice()));
                holder.setText(R.id.transaction_sell_num, numFormat(item.getTotalSize()));
                holder.getView(R.id.transaction_sell_item).setOnClickListener(view -> {
                    setTextAndAnim(priceFormat(item.getPrice()));
                });
            }
        };
        buyAdapter = new BaseAdapter<TransactionModel.Item>(R.layout.item_recycler_transaction_buy, new ArrayList<>(), transactionBuyList) {
            @Override
            public void bind(ViewHolder holder, TransactionModel.Item item) {
                holder.setText(R.id.transaction_buy_id, holder.getLayoutPosition() + 1 + "");
                holder.setText(R.id.transaction_buy_price, priceFormat(item.getPrice()));
                holder.setText(R.id.transaction_buy_num, numFormat(item.getTotalSize()));
                holder.getView(R.id.transaction_item).setOnClickListener(view -> {
                    setTextAndAnim(priceFormat(item.getPrice()));
                });
            }
        };

    }


    public void submitOrderSuccess(String msg) {
        ToastUtil.getInstance(mActivity).showMessage(msg);
        mPresenter.getInfo(mActivity.mMobile, code);
        mPresenter.getEntrustList(mActivity.mMobile, code);
    }

    public void submitOrderFail(String msg) {
        ToastUtil.getInstance(mActivity).showMessage(msg);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(mActivity));
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.getInfo(mActivity.mMobile, code);
            mPresenter.getEntrustList(mActivity.mMobile, code);
        });

        //增加价格
        ClickUtil.click(increase, () -> {
                    realPrice = MathUtil.addF(realPrice, unit);
                    priceEdit.setText(priceFormat(String.valueOf(realPrice)));
                }


        );
        //减少价格
        ClickUtil.click(decrease, () -> {
                    realPrice = MathUtil.subtractF(realPrice, unit);
                    priceEdit.setText(priceFormat(String.valueOf(realPrice)));
                }
        );

        toolBar.setLeftButtonOnClickLinster(view -> mActivity.openDrawerLayout());
        //切换市价与限价
        selectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) { //限价
                    priceEdit.setVisibility(View.VISIBLE);
                    limitPrice.setVisibility(View.INVISIBLE);
                    orderType = XIANJIA;
                    calculateMaxMin();
                } else { //市价
                    priceEdit.setVisibility(View.INVISIBLE);
                    limitPrice.setVisibility(View.VISIBLE);
                    orderType = SHIJIA;
                    calculateMaxMin();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        transactionSelectBuy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mActivity.status = 0;
                checkLogin();
                numEdit.getText().clear();
                switchPrice();
                calculateMaxMin();
            }

        });

        transactionSelectSell.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mActivity.status = 1;
                checkLogin();
                numEdit.getText().clear();
                switchPrice();
                calculateMaxMin();
            }


        });

        transcationSeekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (fromUser) {
                    numEdit.setText(progressFloat + "");
                    calculateTotalPrice();
                }
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        numEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (NumberUtils.pareFloat(s.toString()) > transcationSeekbar.getMax()) {
                    transcationSeekbar.setProgress(transcationSeekbar.getMax());
                    ToastUtil.getInstance(mActivity).showMessage("当前余额不足");
                } else {
                    transcationSeekbar.setProgress(NumberUtils.pareFloat(s.toString()));
                }
                calculateTotalPrice();
            }
        });

        priceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                realPrice = NumberUtils.pareFloat(s.toString());
                calculateMaxMin();
                calculateTotalPrice();
            }
        });
    }


    /**
     * 委托记录
     *
     * @param data 数据源
     */
    public void setHistory(List<Entrust> data) {
        if (transactionAdapter != null) {
            if (data.size() > 5) {
                transactionAdapter.setNewData(data.subList(0, 5));
            } else {
                transactionAdapter.setNewData(data);
            }
        }
    }

    /**
     * 取消订单
     *
     * @param message 返回信息
     * @param success 是否成功取消
     */
    public void cancelOrder(String message, boolean success) {
        ToastUtil.getInstance(mActivity).showMessage(message);
        if (success) {
            mPresenter.getInfo(mActivity.mMobile, code);
            mPresenter.getEntrustList(mActivity.mMobile, code);
        }
    }


    /**
     * 计算加减单位
     */
    public void calculateUnit() {
        String text = priceEdit.getText().toString();
        double price = 0;
        if (!TextUtils.isEmpty(text)) {
            price = Double.parseDouble(text);
        }
        int length = text.length();
        int index = 0;
        if (text.contains(".")) {
            index = text.indexOf(".");
        }
        int x = length - index - 1;
        if (price == 0) {
            unit = 1;
        } else {
            unit = MathUtil.divideF(1, (float) Math.pow(10, x));
        }
    }


    /**
     * 更新界面信息
     */

    public void upDate() {
        selectBuySell();
        changeCoinData();
        if (!TextUtils.isEmpty(mActivity.mMobile)) {
            transactionAdapter.setNewData(new ArrayList<>());
            mPresenter.getEntrustList(mActivity.mMobile, code);
            mPresenter.getInfo(mActivity.mMobile, code);
        }
        if (mActivity.mCurrentStock != null) {
            mActivity.mTransactionFragmentUp = true;
            if (name.contains("_")) {
                String[] split = name.split("_");
                currencyType = split[0];
            } else if (name.contains("/")) {
                String[] split = name.split("/");
                currencyType = split[0];
            } else {
                currencyType = name;
            }
        }
        selectType.setSelection(0);
        calculateUnit();
        calculateMaxMin();
        checkLogin();
    }


    /**
     * 切换币种信息
     * 更新name,code和最新价格
     * 更新长连接
     */
    private void changeCoinData() {
        mPresenter.closeWebSocket();
        if (mActivity.mCurrentStock instanceof NewStock) { //五大币
            NewStock stock = (NewStock) mActivity.mCurrentStock;
            name = stock.getName().replace("_", "/");
            code = stock.getCode();
        } else if (mActivity.mCurrentStock instanceof Time) { //自创币
            Time mCurrentStock = (Time) mActivity.mCurrentStock;
            name = mCurrentStock.getPname().contains("/USDT") ? mCurrentStock.getPname() : (mCurrentStock.getPname() + "/USDT");
            code = mCurrentStock.getMark();
        }
        numEdit.getText().clear();
        initSellBuy();
        mPresenter.openWebSocket(code);
        toolBar.setTitle(name);
        switchPrice();
    }


    /**
     * 切换显示价格
     */

    private void switchPrice() {
        String buyPrice = "";
        String sellPrice = "";
        if (mActivity.mCurrentStock instanceof NewStock) { //五大币
            NewStock stock = (NewStock) mActivity.mCurrentStock;
            buyPrice = stock.getBuy();
            sellPrice = stock.getSell();
        } else if (mActivity.mCurrentStock instanceof Time) { //自创币
            Time time = (Time) mActivity.mCurrentStock;
            buyPrice = TextUtils.isEmpty(time.getBuy()) ? time.getPrice() : time.getBuy();
            sellPrice = TextUtils.isEmpty(time.getSell()) ? time.getPrice() : time.getSell();
        }


        if (mActivity.status == 0) { //买入
            priceEdit.setText(sellPrice);
            realPrice = NumberUtils.pareFloat(sellPrice);
        } else {
            priceEdit.setText(buyPrice);
            realPrice = NumberUtils.pareFloat(buyPrice);
        }
    }


    /**
     * 更改seekBar单位
     * 市价+买 -> USTD
     * 市价+卖 -> UNIT
     * 限价+买 -> UNIT
     * 限价+卖 ->UNIT
     */
    private void changeSeekBar() {
        if (max == 0 && min == 0) {
            transcationSeekbar.setVisibility(View.GONE);
        } else {
            transcationSeekbar.setVisibility(View.VISIBLE);

        }
        transcationSeekbar.getConfigBuilder().min(min).max(max).build();
        if (orderType == SHIJIA && mActivity.status == 0) {
            transcationSeekbar.setCustomSectionTextArray(new TextArray(min, max, "USDT"));
            transactionType.setText("USDT");
            numEdit.setHint("购买金额");
        } else {
            transactionType.setText(currencyType);
            transcationSeekbar.setCustomSectionTextArray(new TextArray(min, max, currencyType));
            if (mActivity.status == 0) {
                numEdit.setHint("购买数量");
            } else {
                numEdit.setHint("卖出数量");
            }
        }

    }


    /**
     * 初始化buy sell 数据
     * 只在启动时调用
     */
    public void initSellBuy() {
        List<TransactionModel.Item> data = new ArrayList<>();
        for (int i = 0; i < buyNum; i++) {
            TransactionModel.Item item = new TransactionModel.Item("---", "---");
            data.add(item);
        }
        buyAdapter.setNewData(data);
        sellAdapter.setNewData(data);
    }

    /**
     * 长连接更新买卖和最新价格信息
     *
     * @param data 数据源
     */
    public void setBuySell(TransactionModel data) {
        Flowable.just(data)
                .compose(RxSchedulersHelper.transformer())
                .subscribe(transactionModel -> {
                    if (transactionModel.getBuy().size() >= buyNum) {
                        buyAdapter.setNewData(transactionModel.getBuy().subList(0, buyNum));
                    }
                    if (transactionModel.getSell().size() >= buyNum) {
                        sellAdapter.setNewData(transactionModel.getSell().subList(0, buyNum));
                    }
                    if (!TextUtils.isEmpty(transactionModel.getNewprice())) {
                        setText(nowPrice, transactionModel.getNewprice());
                    }
                });

    }

    /**
     * 格式化价格保留4位小数
     *
     * @param s price
     * @return price
     */
    public String priceFormat(String s) {
        if (!s.equals("---")) {
            return priceFormat.format(Float.parseFloat(s));
        }
        return s;
    }

    /**
     * 格式化数量保留3位小数
     *
     * @param s num
     * @return num
     */
    public String numFormat(String s) {
        if (!s.equals("---")) {
            return numFormat.format(Float.parseFloat(s));
        }
        return s;
    }


    /**
     * newInstance
     *
     * @return TransactionFragment
     */
    public static TransactionFragment newInstance() {
        TransactionFragment fragment = new TransactionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * SeekBar单位
     */
    class TextArray implements BubbleSeekBar.CustomSectionTextArray {
        SparseArray<String> array = new SparseArray<>();

        TextArray(float min, float max, String unit) {
            array.clear();
            array.put(0, String.valueOf(min));
            array.put(1, numFormat(String.valueOf(max)) + " " + unit);
        }

        @NonNull
        @Override
        public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
            return this.array;
        }
    }


    public void stopRefresh() {
        smartRefreshLayout.finishRefresh();
    }


    public void setTextAndAnim(String text) {
        if (text.equals("---")) {
            return;
        }
        setText(priceEdit, text);
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(500);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.5f, 1f, 1.4f, 1f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(500);
        scaleAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                priceEdit.startAnimation(scaleAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        priceEdit.startAnimation(scaleAnimation1);

    }

    /**
     * 计算最大最小购买数量
     * 设置滑杆最大最小值
     * 最大购买数量=余额/(（1+手续费)*price)
     */
    public void calculateMaxMin() {
        if (Constants.mIsLogin) { //登录
            //买
            if (mActivity.status == 0) {
                if (orderType == SHIJIA) {
                    max = money;
                    numEdit.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(4)});
                } else {

                    float price = NumberUtils.pareFloat(priceEdit.getText().toString());
                    max = MathUtil.divideFFloor(money, (1 + transFee) * price);
//                    max = Float.parseFloat(numFormat(MathUtil.divideF(MathUtil.multiplyF(money, MathUtil.subtractF(1, transFee)), NumberUtils.pareFloat(priceEdit.getText().toString())) + ""));
                    numEdit.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(3)});
                }
                min = 0;
                usefulNum.setText("可用：" + money + "  USDT");
            } else {//卖
                max = haveNum;
                min = 0;
                usefulNum.setText("可用：" + haveNum + "  " + currencyType);
                numEdit.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(3)});
            }
        }
        changeSeekBar();
    }

    /**
     * 计算总交易额
     */
    public void calculateTotalPrice() {

        if (orderType == SHIJIA) {
            if (mActivity.status == 0) { //买
                if (TextUtils.isEmpty(numEdit.getText().toString())) {
                    transactionTotal.setText("交易额：" + "0.0000" + "  USDT");
                } else {
                    transactionTotal.setText("交易额：" + numEdit.getText().toString() + "  USDT");
                }
                transactionTotal.setVisibility(View.VISIBLE);
            } else { //卖
                transactionTotal.setText("交易额：" + "---" + "  USDT");
                transactionTotal.setVisibility(View.GONE);
            }
        } else {
            float price = 0;
            float num = 0;
            price = NumberUtils.pareFloat(priceEdit.getText().toString());
            num = NumberUtils.pareFloat(numEdit.getText().toString());
            float pre = price * num;
            float total = MathUtil.multiplyF(pre, 1 + transFee);
            transactionTotal.setVisibility(View.VISIBLE);
            transactionTotal.setText("交易额：" + priceFormat.format(total) + "  USDT");
        }

    }


    public void selectBuySell() {
        if (mActivity.status == 0) {
            transactionSelectBuy.setChecked(true);
        } else {
            transactionSelectSell.setChecked(true);
        }
    }

    /**
     * 检查登录状态
     */
    public void checkLogin() {
        if (Constants.mIsLogin) {
            historyAll.setVisibility(View.VISIBLE);
            //当前委托
            ClickUtil.click(historyAll, () -> EntrustActivity.start(mActivity, mActivity.mMobile));
            if (mActivity.status == 0) { //买
                submitOrder.setText("买入" + currencyType);
                submitOrder.setBackgroundColor(getResources().getColor(R.color.market_down));
            } else { //卖
                submitOrder.setText("卖出" + currencyType);
                submitOrder.setBackgroundColor(getResources().getColor(R.color.market_up));
            }
            //提交订单
            ClickUtil.click(submitOrder, () -> {
                String price = "";
                if (orderType == XIANJIA) { //限价
                    price = priceEdit.getText().toString();

                    if (TextUtils.isEmpty(price)) {
                        ToastUtil.getInstance(mActivity).showMessage("请输入价格");
                        return;
                    }

                    if (TextUtils.isEmpty(numEdit.getText().toString())) {
                        ToastUtil.getInstance(mActivity).showMessage("请输入数量");
                        return;
                    }

                    if (mActivity.status == 0) { //买

                        mPresenter.submit(mActivity.mMobile, code, price, numEdit.getText().toString(), "", "", 1, 1);

                    } else { //卖
                        mPresenter.submit(mActivity.mMobile, code, price, numEdit.getText().toString(), "", "", 2, 1);
                    }
                } else { //市价
                    if (TextUtils.isEmpty(numEdit.getText().toString())) {
                        ToastUtil.getInstance(mActivity).showMessage("请输入数量");
                        return;
                    }
                    if (mActivity.status == 0) { //买
                        mPresenter.submit(mActivity.mMobile, code, "", "", numEdit.getText().toString(), "", 1, 2);
                    } else { //卖
                        mPresenter.submit(mActivity.mMobile, code, "", "", "", numEdit.getText().toString(), 2, 2);
                    }
                }
            });
        } else {
            submitOrder.setText("登录");
            historyAll.setVisibility(View.GONE);
            ClickUtil.click(submitOrder, () -> {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                intent.putExtra("from", "transaction");
                startActivityForResult(intent, Constants.TRANSACTION);
            });
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TRANSACTION && resultCode == Activity.RESULT_OK) {
            upDate();
        }
    }
}
