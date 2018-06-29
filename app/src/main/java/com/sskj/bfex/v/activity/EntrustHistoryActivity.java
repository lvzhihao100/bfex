package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.common.AnimationUtil;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.CurrencyBean;
import com.sskj.bfex.m.bean.Entrust;
import com.sskj.bfex.p.activity.EntrustHistoryPresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 委托历史记录
 * create by Hey at 2018/5/3 8:56
 */
public class EntrustHistoryActivity extends BaseActivity<EntrustHistoryPresenter> {


    @BindView(R.id.entrust_history_toolbar)
    ToolBarLayout entrustHistoryToolbar;
    @BindView(R.id.entrust_history_list)
    RecyclerView entrustHistoryList;
    @BindView(R.id.entrust_history_filter)
    TextView entrustHistoryFilter;
    @BindView(R.id.entrust_history_title)
    RelativeLayout entrustHistoryTitle;
    @BindView(R.id.entrust_history_bg)
    View entrustHistoryBg;
    @BindView(R.id.entrust_history_refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.filter_currency)
    AppCompatSpinner filterCurrency;
    @BindView(R.id.filter_type)
    AppCompatSpinner filterType;
    @BindView(R.id.filter_status)
    AppCompatSpinner filterStatus;
    @BindView(R.id.filer_confirm)
    Button filerConfirm;
    @BindView(R.id.filter_layout)
    LinearLayout filterLayout;
    @BindView(R.id.filter_layout_root)
    LinearLayout filterLayoutRoot;

    private BaseAdapter<Entrust> adapter;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd");
    private String code = "";
    private String status = "4";
    private String type = "";
    private List<CurrencyBean> currencyBeans = new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_entrust_history;
    }

    @Override
    public EntrustHistoryPresenter getPresenter() {
        return new EntrustHistoryPresenter();
    }

    @Override
    protected void initView() {
        entrustHistoryToolbar.setLeftButtonOnClickLinster(view -> finish());
        refreshLayout.setRefreshHeader(new BezierCircleHeader(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.getEntrustList(mMobile, code, status, type + ""));

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        entrustHistoryList.setLayoutManager(layoutManager);
        entrustHistoryList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new BaseAdapter<Entrust>(R.layout.item_recycler_entrust_history, new ArrayList<>(), entrustHistoryList, true) {
            @Override
            public void bind(ViewHolder holder, Entrust item) {
                holder.setText(R.id.entrust_name, item.getPname())
                        .setText(R.id.entrust_type, item.getType() == 1 ? "买入" : "卖出")
                        .setText(R.id.entrust_time, dateFormat.format(Long.parseLong(item.getAdd_time()) * 1000))
                        .setText(R.id.entrust_prePrice, item.getWtprice())
                        .setText(R.id.entrust_totalPrice, item.getTotalprice())
                        .setText(R.id.entrust_realNum, item.getCjnum())
                        .setText(R.id.entrust_realPrice, item.getCjprice());
                if (item.getOtype() == 2 && item.getType() == 1) {  //市价买入
                    holder.setText(R.id.entrust_preNum_text, "交易额")
                            .setText(R.id.entrust_preNum, item.getTotalprice());
                } else { //其他
                    holder.setText(R.id.entrust_preNum_text, "委托量")
                            .setText(R.id.entrust_preNum, item.getWtnum());
                }
                TextView cancel = holder.getView(R.id.entrust_state);
                switch (item.getStatus()) {
                    case 0:
                        cancel.setText("撤销");
                        cancel.setOnClickListener(v -> mPresenter.cancelOrder(mActivity.mMobile, item.getOrders_id()));
                        cancel.setTextColor(getResources().getColor(R.color.blue_5d));
                        break;
                    case 1:
                        cancel.setText("交易中");
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
        entrustHistoryList.setAdapter(adapter);

        entrustHistoryFilter.setOnClickListener(view -> {
            if (filterLayoutRoot.getVisibility() == View.VISIBLE) {
                dismissFilter();
            } else {
                filterLayoutRoot.setVisibility(View.VISIBLE);
                filterLayoutRoot.startAnimation(AnimationUtil.createAlphaVisible());
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getCurrencyList();
        mPresenter.getEntrustList(mMobile, "", "4", "");
    }

    /**
     * 设置列表
     *
     * @param data 数据源
     */
    public void setData(List<Entrust> data) {
        adapter.setNewData(data);
    }


    /**
     * 启动
     *
     * @param context context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, EntrustHistoryActivity.class);
        context.startActivity(intent);
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
            mPresenter.getEntrustList(mMobile, code, status, type);
        }
    }

    public void initFilter() {
        ArrayList<String> items = new ArrayList<>();
        items.add("全部");
        for (CurrencyBean c : currencyBeans) {
            items.add(c.getPname());
        }
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        filterCurrency.setAdapter(currencyAdapter);
        Button confirm = findViewById(R.id.filer_confirm);
        confirm.setOnClickListener(v -> {
            if (filterCurrency.getSelectedItemPosition() == 0) {
                code = "";
            } else {
                code = currencyBeans.get(filterCurrency.getSelectedItemPosition() - 1).getMark();
            }

            if (filterType.getSelectedItemPosition() == 0) {

                type = "";
            } else {
                type = filterType.getSelectedItemPosition() + "";
            }
            switch (filterStatus.getSelectedItemPosition()) {
                case 0:
                    status = "4";
                    break;
                case 1:
                case 2:
                case 3:
                    status = filterStatus.getSelectedItemPosition() - 1 + "";
                    break;
                case 4:
                    status = "-1";
                    break;

            }
            mPresenter.getEntrustList(mMobile, code, status, type);
            dismissFilter();
        });
    }


    public void stopRefresh() {
        refreshLayout.finishRefresh();
    }

    public void setFilter(List<CurrencyBean> data) {
        currencyBeans = data;
        runOnUiThread(() -> initFilter());

    }

    private void dismissFilter() {
        filterLayoutRoot.startAnimation(AnimationUtil.createAlphaGone());
        filterLayoutRoot.postDelayed(() -> filterLayoutRoot.setVisibility(View.GONE), AnimationUtil.ANIMATION_OUT_TIME);
    }
}
