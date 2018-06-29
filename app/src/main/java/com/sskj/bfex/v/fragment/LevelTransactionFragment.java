package com.sskj.bfex.v.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.MoneyValueFilter;
import com.sskj.bfex.common.adapter.MyBaseAdapter;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.StockBean;
import com.sskj.bfex.m.bean.Time;
import com.sskj.bfex.m.bean.TimeData;
import com.sskj.bfex.m.bean.TradeDealBean;
import com.sskj.bfex.m.bean.bean.NewStock;
import com.sskj.bfex.p.fragment.LevelTransactionFragmentPst;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.EditChangeUtil;
import com.sskj.bfex.utils.TipUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.activity.LoginActivity;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.activity.MarketActivity;
import com.sskj.bfex.v.activity.PayPwdSettingActivity;
import com.sskj.bfex.v.activity.TradeActivity;
import com.sskj.bfex.v.activity.VerifyIdentActivity;
import com.sskj.bfex.v.base.BaseFragment;
import com.sskj.bfex.v.widget.mychart.BubbleSeekBar;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;


/**
 * 杠杆交易
 * create by Hey at 2018/4/26 9:14
 */

public class LevelTransactionFragment extends BaseFragment<MainActivity, LevelTransactionFragmentPst> implements OnRefreshListener {

    private static String TAG = "LevelTransactionFragment";
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.transaction_lsit)
    ListView mTransactionList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefresh;

    private HeaderViewHolder mHeaderViewHolder;
    private MyBaseAdapter<TradeDealBean> mAdapter;
    private View mItem_foot;
    /**
     * 价格类型 ： 1: 市价   ；   2 ： 限价
     * 默认 限价
     */
    private int mType = 2;
    /**
     * 下单需要的数据
     */
    private StockBean mStockBean;
    /**
     * 当前股票的当前价格
     */
    private double mCurrentPriceD;
    DecimalFormat mFormat = new DecimalFormat("0.0000");
    DateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd");
    /**
     * 计算的交易额
     */
    private double mSumPrice;
    private String name;
    private float price;
    private String code = "";
    private double maxNum; //可输入的最大数量
    private double minNum; //可输入的最小数量
    private int minDealNum; //可交易的最小数量
    private MaterialDialog customTip;
    private String unit = "USTD";
    private double leverage = 20; //杠杆
    private double shouPer; //手续费
    private String baoPer = "100%";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_level_transaction;
    }

    @Override
    public LevelTransactionFragmentPst getPresenter() {
        return new LevelTransactionFragmentPst();
    }

    //    public int status = 0;//0 买入，1 卖出，
    public boolean isLimitPrice = false; //是否市价 默认非市价
    private String wallet = "0.00";


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!TextUtils.isEmpty(UserUtil.getMobile())) {
                mPresenter.getUserInfo();
                mPresenter.getLeveRange();
                mPresenter.requestOrderList(code);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(UserUtil.getMobile())) {
            mPresenter.getUserInfo();
            mPresenter.getLeveRange();
            mPresenter.requestOrderList(code);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSmartRefresh.setRefreshHeader(new BezierCircleHeader(getActivity()));
        mSmartRefresh.setEnableLoadMore(false);
        mSmartRefresh.setOnRefreshListener(this);
        mToolBar.setRightButtonIcon(getResources().getDrawable(R.mipmap.icon_a_nor));
        mToolBar.setRightButtonOnClickLinster(view12 -> { //跳转股票详情
            switch (mActivity.mCurrency) {
                case TIME:
                    MarketActivity.start(getActivity(), code, name, mActivity.mCurrency, (Time) mActivity.mCurrentStock, 2);
                    break;
                case BTC:
                    MarketActivity.start(getActivity(), code, name, mActivity.mCurrency, null, 2);
                    break;
            }
        });

        mToolBar.setLeftButtonOnClickLinster(view1 -> mActivity.openDrawerLayout());

        View item_head = LayoutInflater.from(mActivity).inflate(R.layout.layout_head_view, null);
        mItem_foot = LayoutInflater.from(mActivity).inflate(R.layout.layout_foot_view, null);

        mHeaderViewHolder = new HeaderViewHolder(item_head);
        mTransactionList.addHeaderView(item_head);
        mAdapter = new MyBaseAdapter<TradeDealBean>(new MyBaseAdapter.initView<TradeDealBean>() {
            @Override
            public View initItenView(int position, View convertView, ViewGroup parent, TradeDealBean obj) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_transaction_order_layout, null);
                }
                TextView itemPrice = convertView.findViewById(R.id.item_price);
                TextView itemPriceValue = convertView.findViewById(R.id.item_price_value);
                TextView itemFinalPrice = convertView.findViewById(R.id.item_final_price);
                TextView itemFinalPriceVlaue = convertView.findViewById(R.id.item_final_price_value);
                TextView itemCount = convertView.findViewById(R.id.item_count);
                TextView itemCountVlaue = convertView.findViewById(R.id.item_count_value);
                TextView itemTitle = convertView.findViewById(R.id.item_title);
                TextView itemType = convertView.findViewById(R.id.item_type);

                if (obj.getOtype() == 1) {
                    itemType.setText("做多");
                    itemType.setTextColor(Color.parseColor("#01c485"));
                } else {
                    itemType.setTextColor(Color.parseColor("#eb7147"));
                    itemType.setText("做空");
                }
                itemPrice.setText(getString(R.string.transaction_order_item_price));
                itemFinalPrice.setText(getString(R.string.transaction_order_item_final_price));
                itemCount.setText(getString(R.string.transaction_order_item_count));

                itemPriceValue.setText(obj.buyprice);

                itemCountVlaue.setText(obj.buynum);
                itemFinalPriceVlaue.setText(obj.sellprice);
                itemTitle.setText(obj.pname + "  " + dateFormat.format(obj.selltime*1000));

                return convertView;
            }
        });

        mTransactionList.setAdapter(mAdapter);
        mStockBean = new StockBean();
        upDate();
    }

    public void upDate() {
        updateUserTitle();
        updateCoinData();
        if (mActivity.mCurrentStock != null) {
            mActivity.mTransactionFragmentUp = true;
            updateSaleAndSold();
            mHeaderViewHolder.mTransctionPriceTextView.setVisibility(isLimitPrice ? View.VISIBLE : View.GONE);
            mHeaderViewHolder.rlEditPrice.setVisibility(isLimitPrice ? View.GONE : View.VISIBLE);
            mHeaderViewHolder.mTransctionPriceEditText.setText(String.valueOf(price));
            String type;
            if (name.contains("_")) {
                String[] split = name.split("_");
                unit = split[1];
                type = split[0];
            } else if (name.contains("/")) {
                String[] split = name.split("/");
                unit = split[1];
                type = split[0];
            } else {
                type = name;
            }

//            mHeaderViewHolder.mSpinner.setSelection(mType == 2 ? 0 : 1);
            mHeaderViewHolder.mTransactionType.setText(type);
            mToolBar.setTitle(name);
            mHeaderViewHolder.mTransctionSumEditText.getText().clear();
            mHeaderViewHolder.mTransactionSeekBar.setCurrentProgress(0f);
            updateCountText(0.0);
            mHeaderViewHolder.mTransactionSeekBar.getConfigBuilder().max((int) maxNum).build();
            mHeaderViewHolder.mTransactionSeekBar.getConfigBuilder().min((int) minNum).build();
        }


    }

    private void updateCountText(double money) {
//        mHeaderViewHolder.mTransactionCount.setText(getString(R.string.transaction_count) + mFormat.format(mSumPrice) + unit);
        mHeaderViewHolder.mTransactionCount.setText(getString(R.string.transaction_count) + " " + mFormat.format(money) + " " + unit);
    }

    private void updateSaleAndSold() {
        if (mActivity.status == 0) { //买入
            if (mHeaderViewHolder.mRadioGroup.getCheckedRadioButtonId() != R.id.price_type_a) {
                mHeaderViewHolder.mRadioGroup.check(R.id.price_type_a);
            }
            mHeaderViewHolder.mTransactionInput.setVisibility(View.VISIBLE);
            mHeaderViewHolder.mTransactionInput.setText("买入（做多）");
            mHeaderViewHolder.mTransactionInput.setBackgroundColor(getResources().getColor(R.color.market_down));
            mHeaderViewHolder.mTransactionOutput.setVisibility(View.GONE);
            mHeaderViewHolder.mTransctionSumEditText.setHint("购买数量最小为" + minDealNum);
        } else { //卖出
            if (mHeaderViewHolder.mRadioGroup.getCheckedRadioButtonId() != R.id.price_type_b) {
                mHeaderViewHolder.mRadioGroup.check(R.id.price_type_b);
            }
            mHeaderViewHolder.mTransactionInput.setVisibility(View.GONE);
            mHeaderViewHolder.mTransactionOutput.setVisibility(View.VISIBLE);
            mHeaderViewHolder.mTransactionOutput.setText("卖出（做空）");
            mHeaderViewHolder.mTransactionOutput.setBackgroundColor(getResources().getColor(R.color.market_up));
            mHeaderViewHolder.mTransctionSumEditText.setHint("卖出数量");
        }
    }


    private void updateCoinData() {
        if (mActivity.mCurrentStock instanceof NewStock) { //五大币
            NewStock stock = (NewStock) mActivity.mCurrentStock;
            name = stock.getName().replace("_", "/");
            switchPrice(stock.getBuy(), stock.getSell());
            code = stock.getCode();
            mPresenter.getLeveRange();
            mHeaderViewHolder.mTransctionPriceTextView.setText(stock.getPrice());
        } else if (mActivity.mCurrentStock instanceof Time) { //time币
            Time mCurrentStock = (Time) mActivity.mCurrentStock;
            leverage = Double.valueOf(mCurrentStock.getLeverage());
//            baoPer = Double.valueOf(mCurrentStock.getBond_rate().replace("%", "")) / 100;
            shouPer = Double.valueOf(mCurrentStock.getTrans_fee().replace("%", "")) / 100;
            name = mCurrentStock.getPname().contains("/USDT") ? mCurrentStock.getPname() : (mCurrentStock.getPname() + "/USDT");
//            mCurrentPriceD = price = Float.parseFloat(mCurrentStock.getPrice());
            switchPrice(TextUtils.isEmpty(mCurrentStock.getBuy()) ? mCurrentStock.getPrice() : mCurrentStock.getBuy(), TextUtils.isEmpty(mCurrentStock.getSell()) ? mCurrentStock.getPrice() : mCurrentStock.getSell());//TODO  字段没有确认
            code = mCurrentStock.getMark();
            calcuMostNum();
            minDealNum = mCurrentStock.getNum_min();
            if (minDealNum == 0) {
                minDealNum = 1;
            }
            minNum = 0;
            mHeaderViewHolder.mTransctionPriceTextView.setText(mCurrentStock.getPrice());
        }
        mPresenter.requestOrderList(code);
    }

    /**
     * 切换显示价格
     *
     * @param buyPrice  买价
     * @param sellPrice 卖价
     */

    private void switchPrice(String buyPrice, String sellPrice) {
        if (mActivity.status == 0) { //买入
            mCurrentPriceD = price = Float.parseFloat(sellPrice);
        } else {
            mCurrentPriceD = price = Float.parseFloat(buyPrice);
        }
    }

    private void updateUserTitle() {
        if (Constants.mIsLogin) { //有登陆
            if (mHeaderViewHolder != null) {
                mHeaderViewHolder.mTransactionItemBalance.setText(wallet);
            }
        } else { //没登录
            if (mHeaderViewHolder != null) {
                mHeaderViewHolder.mTransactionItemBalance.setText("0.000");
                mHeaderViewHolder.mTransactionItemBalance.setText("0.000");
                if (mTransactionList.getFooterViewsCount() <= 0) {
                    mTransactionList.addFooterView(mItem_foot);
                }
                mAdapter.clear();
            }
        }
    }

    /**
     * 用户交易订单列表获取成功
     *
     * @param rest
     */
    public void onOrderListResponseSuccess(List<TradeDealBean> rest) {
        mSmartRefresh.finishRefresh();
        if (rest == null) {
            if (mTransactionList.getFooterViewsCount() <= 0) {
                mTransactionList.addFooterView(mItem_foot);
            }
            mAdapter.setList(new ArrayList<>());

        } else {
            Flowable.fromIterable(rest)
                    .take(5)
                    .toList()
                    .subscribe((tradeDealBeans, throwable) -> {
                        mTransactionList.removeFooterView(mItem_foot);
                        mAdapter.setList(tradeDealBeans);
                    });
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mPresenter.requestOrderList(code);
        mPresenter.getLeveRange();
        mPresenter.getUserInfo();
    }

    public void updateWallet(String wallet) {
        this.wallet = wallet;
        upDate();
    }

    public void onCreateOrderSuccess() {
        startActivity(new Intent(getActivity(), TradeActivity.class));
    }

    public void updateLevel(List<TimeData.TimeDataBean> timeData) {
        Flowable.fromIterable(timeData)
                .filter(timeDataBean -> timeDataBean.getPname().equals(name.replace("_", "/")))
                .take(1)
                .subscribe(timeDataBean -> {
                    leverage = Double.valueOf(timeDataBean.getLeverage());
                    baoPer = timeDataBean.getTrans_ware();
                    shouPer = Double.valueOf(timeDataBean.getTrans_fee().replace("%", "")) / 100;
                    calcuMostNum();
                    minNum = 0;
                    minDealNum = timeDataBean.getNum_min();
                    if (minDealNum == 0) {
                        minDealNum = 1;
                    }
                    mHeaderViewHolder.mTransactionSeekBar.getConfigBuilder().max((int) maxNum).build();
                    mHeaderViewHolder.mTransactionSeekBar.getConfigBuilder().min((int) minNum).build();
                    if (mActivity.status == 0) {
                        mHeaderViewHolder.mTransctionSumEditText.setHint("购买数量最小为" + minDealNum);
                    } else {
                        mHeaderViewHolder.mTransctionSumEditText.setHint("卖出数量");
                    }
                });
    }


    public class HeaderViewHolder implements RadioGroup.OnCheckedChangeListener {
        @BindView(R.id.transaction_item_balance)
        TextView mTransactionItemBalance;
        @BindView(R.id.risk_ratio)
        TextView mRiskRatio;
        @BindView(R.id.transction_price_editText)
        EditText mTransctionPriceEditText;
        @BindView(R.id.transction_price_textView)
        TextView mTransctionPriceTextView;
        @BindView(R.id.transction_increase_price)
        TextView mTransctionIncreasePrice;
        @BindView(R.id.transction_decrease_price)
        TextView mTransctionDecreasePrice;
        @BindView(R.id.transaction_approximate)
        TextView mTransactionApproximate;
        @BindView(R.id.transaction_count)
        TextView mTransactionCount;
        @BindView(R.id.transction_sum_editText)
        EditText mTransctionSumEditText;
        @BindView(R.id.transaction_type)
        TextView mTransactionType;
        @BindView(R.id.transaction_user_balance)
        TextView mTransactionUserBalance;
        @BindView(R.id.transcation_seekbar)
        BubbleSeekBar mTransactionSeekBar;
        @BindView(R.id.transaction_input)
        TextView mTransactionInput;
        @BindView(R.id.transaction_output)
        TextView mTransactionOutput;
        @BindView(R.id.transaction_history_all)
        TextView mTransactionHistoryAll;
        @BindView(R.id.radioGroup)
        RadioGroup mRadioGroup;
        @BindView(R.id.price_type_a)
        RadioButton mRadioBTTypeA;
        @BindView(R.id.price_type_b)
        RadioButton mRadioBTTypeB;
        @BindView(R.id.tv_detail_title_type)
        TextView tvDetailTitleType;
        @BindView(R.id.spinner)
        Spinner mSpinner;
        @BindView(R.id.rlEditPrice)
        RelativeLayout rlEditPrice;

        public HeaderViewHolder(View headerView) {
            ButterKnife.bind(this, headerView);
            mRadioGroup.setOnCheckedChangeListener(this);
            if (mActivity.mUserInfo != null) {
                mTransactionItemBalance.setText(mActivity.mUserInfo.wallone);
            }
            ClickUtil.click(mTransactionHistoryAll, () -> { //查看全部交易记录
                if (Constants.mIsLogin) {
                    startActivity(new Intent(mActivity, TradeActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
            });
            mTransactionSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                    if (fromUser) {
                        double num = progress;
                        if (num <= minNum) {
                            num = minNum;

                        } else if (num >= maxNum) {
                            num = maxNum;
                        }
                        mTransctionSumEditText.setText(((int) num) + "");
                    }
                }

                @Override
                public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                }

                @Override
                public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                }
            });

            EditChangeUtil.onChange(mTransctionPriceEditText, s -> {
                mCurrentPriceD = (Double.parseDouble(TextUtils.isEmpty(s.toString()) ? "0" : s.toString()));
                if (TextUtils.isEmpty(s.toString()) || Double.isInfinite(maxNum) || Double.isNaN(maxNum)) {
                    maxNum = 0;
                } else {
                    calcuMostNum();
                }
                mHeaderViewHolder.mTransactionSeekBar.getConfigBuilder().max((int) maxNum).build();
                if (!TextUtils.isEmpty(mTransctionSumEditText.getText())) {
                    //交易额 计算公式 : 最新价*购买数量/20
                    double valueD = Double.valueOf(mTransctionSumEditText.getText().toString().trim());

                    mSumPrice = calcuAllPrice(mCurrentPriceD, valueD);
                    updateCountText(mSumPrice);
//                    mTransactionCount.setText(getString(R.string.transaction_count) + mFormat.format(mSumPrice) + unit);
                }
            });

//            mTransctionSumEditText.setOnFocusChangeListener((view, b) -> {
//                if (b) {
//                    String currentPrice = null;
//                    if (!isLimitPrice) {
//                        currentPrice = mTransctionPriceEditText.getText().toString().trim();
//
//                    } else {
//                        currentPrice = price + "";
//                    }
//                    if (!TextUtils.isEmpty(currentPrice)) {
//                        mCurrentPriceD = Double.valueOf(currentPrice);
//                        maxNum = Double.parseDouble(wallet) * 20 / mCurrentPriceD;
//                        mHeaderViewHolder.mTransactionSeekBar.getConfigBuilder().max((int) maxNum).build();
//                    }
//                }
//            });
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        isLimitPrice = false;
                        mType = 2;
                        mCurrentPriceD = TextUtils.isEmpty(mHeaderViewHolder.mTransctionPriceEditText.getText()) ? 0 : Double.parseDouble(mHeaderViewHolder.mTransctionPriceEditText.getText().toString().trim());
                    } else {
                        isLimitPrice = true;
                        mCurrentPriceD = price;
                        mType = 1;
                    }
                    upDate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            mTransctionPriceEditText.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(4)});
            EditChangeUtil.onChange(mTransctionSumEditText, editable -> {
                if (!TextUtils.isEmpty(editable.toString())) {
                    double valueD = formatDouble(editable);
                    if (valueD > maxNum) { //大于最大数量
                        editable.clear();
                        editable.append((int) maxNum + "");
                        mTransactionSeekBar.setCurrentProgress((int) maxNum);
                    } else if (valueD < minNum) { //小于最小数量
                        editable.clear();
                        editable.append((int) minNum + "");
                        mTransactionSeekBar.setCurrentProgress((int) minNum);
                    } else { //数量正常
                        //交易额 计算公式 : 最新价*购买数量/20
                        mSumPrice = calcuAllPrice(mCurrentPriceD, valueD);
                        updateCountText(mSumPrice);
//                        mTransactionCount.setText(getString(R.string.transaction_count) + mFormat.format(mSumPrice) + unit);
                        mTransactionSeekBar.setCurrentProgress((int) valueD);
                    }
                } else { //数量为空，使用最小数量
                    mSumPrice = calcuAllPrice(mCurrentPriceD, minNum);
                    mTransactionCount.setText(getString(R.string.transaction_count) + mFormat.format(mSumPrice) + unit);
                    mTransactionSeekBar.setCurrentProgress((int) minNum);
                }
            });
            ClickUtil.click(mTransactionInput, () -> { //买入（买涨）
                mStockBean.otype = 1;
                placeOrder();
            });
            ClickUtil.click(mTransactionOutput, () -> { //卖出（买跌）
                mStockBean.otype = 2;
                placeOrder();
            });
            ClickUtil.click(mRiskRatio, () -> {
                customTip = TipUtil.customTip(getActivity(), "爆仓说明", "当风险达到" + baoPer + "时，系统会自动强平", "我知道了");
            });


        }


        private void placeOrder() {
            if (Constants.mIsLogin) {
                Double userBalance = Double.valueOf(mActivity.mUserInfo.wallone);
                Double sumVlaue = mSumPrice;
                if (userBalance < sumVlaue) {
                    ToastUtil.showShort("账户余额不足！！");
                    return;
                }

                switch (mActivity.mUserInfo.status) {
                    case 0:
                    case 4:
                        ToastUtil.showShort("实名认证失败，请重新提交认证");
                        return;
                    case 1:
                        ToastUtil.showShort("请先完成实名认证");
                        startActivity(new Intent(mActivity, VerifyIdentActivity.class));
                        return;
                    case 2:
                        ToastUtil.showShort("实名认证审核中，请耐心等待");
                        return;
                    case 3:
                        mStockBean.buynum = mTransctionSumEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(mStockBean.buynum)) {
                            ToastUtil.showShort("请输入购买数量");
                            return;
                        }
                        if (Double.valueOf(mStockBean.buynum) < minDealNum) {
                            ToastUtil.showShort("购买数量最小为" + minDealNum);
                            return;
                        }
                        if (TextUtils.isEmpty(mActivity.mUserInfo.tpwd)) {
                            ToastUtil.showShort("请先设置交易密码");
                            startActivity(new Intent(mActivity, PayPwdSettingActivity.class));
                            return;
                        }
                        Double aDouble = Double.valueOf(mStockBean.buynum);
                        if (aDouble <= 0) {
                            ToastUtil.showShort("请输入购买数量");
                            return;
                        }
                        mStockBean.type = mType;
                        if (mType == 2) { //限价，选择用户输入的价格
                            if (TextUtils.isEmpty(mHeaderViewHolder.mTransctionPriceEditText.getText())) {
                                ToastUtil.showShort("请输入价格");
                                return;
                            } else if (Double.valueOf(mHeaderViewHolder.mTransctionPriceEditText.getText().toString()) <= 0) {
                                ToastUtil.showShort("价格不能为0");
                                return;
                            }
                            mStockBean.newprice = mTransctionPriceEditText.getText().toString().trim();
                        } else { //市价，选择股票最新的价格
                            mStockBean.newprice = mTransctionPriceTextView.getText().toString().trim();
                        }
                        if (mActivity.mCurrency == Currency.BTC) {
                            mStockBean.shopname = ((NewStock) mActivity.mCurrentStock).getCode();
                        } else {
                            mStockBean.shopname = ((Time) mActivity.mCurrentStock).getMark();
                        }
//                        getActivity().startActivityForResult(new Intent(getActivity(), PayPwdInputActivity.class).putExtra("stock_data", mStockBean), Constants.PAY_PWD_INPUT);
                        if (mActivity.mCurrency == Currency.TIME) {
                            mPresenter.createOrder(mStockBean, true);
                        } else {
                            mPresenter.createOrder(mStockBean, false);
                        }
                        return;
                }

            } else {
                ToastUtil.showShort("请先登录");
            }
        }


        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId) {
                case R.id.price_type_a://买入
                    mActivity.status = 0;
                    break;
                case R.id.price_type_b://卖出
                    mActivity.status = 1;
                    break;
            }
            upDate();
        }

        @OnClick({R.id.transction_decrease_price, R.id.transction_increase_price})
        public void onViewClick(View view) {
            switch (view.getId()) {
                case R.id.transction_decrease_price:
                    mCurrentPriceD = mCurrentPriceD * 10000 + 1;
                    String formata = mFormat.format(mCurrentPriceD / 10000);
                    mCurrentPriceD = Double.valueOf(formata);
                    mHeaderViewHolder.mTransctionPriceEditText.setText(formata);
                    break;
                case R.id.transction_increase_price:
                    mCurrentPriceD = mCurrentPriceD * 10000 - 1;
                    String formatb = mFormat.format(mCurrentPriceD / 10000);
                    mCurrentPriceD = Double.valueOf(formatb);
                    mHeaderViewHolder.mTransctionPriceEditText.setText(formatb);
                    break;
            }
        }

    }

    private double formatDouble(Editable editable) {
        String value = editable.toString().trim();
        if (!CheckUtil.isOnlyPointNumber(value)) { //限制输入两位小数
//
            if (value.indexOf(".") > 0) {
                if (value.length() - value.indexOf(".") > 2) {
                    editable.delete(value.indexOf(".") + 3, value.length());
                }
            }
        }
        value = editable.toString().trim();
        return Double.valueOf(value);
    }

    /**
     * 计算可以购买的最大数量
     * wallet 钱包总金额
     * mCurrentPriceD 当前价格
     * leverage 杠杆
     * shouPer 手续费率
     * 计算公式 (wallet * leverage - 手续费) / mCurrentPriceD
     */
    private void calcuMostNum() {
        maxNum = (Double.parseDouble(wallet)) / (mCurrentPriceD / leverage + mCurrentPriceD * shouPer);
        maxNum = Math.floor(maxNum);
        if (Double.isNaN(maxNum) || Double.isInfinite(maxNum)) {
            maxNum = 0;
        }
    }

    /**
     * 计算总价格
     * price 单价
     * num 购买数量
     * leverage 杠杆
     * shouPer 手续费率
     * 这里的数量 已经计算了手续费（ count = （wallet * 杠杆 - wallet * 手续费） / 单价 ）
     * 计算公式 (price * num) / leverage
     */
    private double calcuAllPrice(double price, double num) {
        return (price * num) / leverage + (price * num) * shouPer; //交易额 计算公式 : 最新价*购买数量/20
    }

    public static LevelTransactionFragment newInstance() {
        LevelTransactionFragment fragment = new LevelTransactionFragment();
        return fragment;
    }

}
