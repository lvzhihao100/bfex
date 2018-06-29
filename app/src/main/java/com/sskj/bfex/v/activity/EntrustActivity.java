package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.Entrust;
import com.sskj.bfex.p.activity.EntrustPresenter;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 当前委托列表
 */
public class EntrustActivity extends BaseActivity<EntrustPresenter> {

    @BindView(R.id.entrust_toolbar)
    ToolBarLayout entrustToolbar;
    @BindView(R.id.entrust_list)
    RecyclerView entrustList;
    @BindView(R.id.entrust_refresh)
    SmartRefreshLayout smartRefreshLayout;

    BaseAdapter<Entrust> adapter;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd");

    private String mobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_entrust;
    }

    @Override
    public EntrustPresenter getPresenter() {
        return new EntrustPresenter();
    }


    @Override
    protected void initView() {

        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(this));
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.getEntrustList(mobile));
        entrustToolbar.mRightButton.setTextColor(getResources().getColor(R.color.tabNormalColor));
        entrustToolbar.setLeftButtonOnClickLinster(view -> finish());
        entrustToolbar.setRightButtonOnClickLinster(view -> {
            EntrustHistoryActivity.start(this);
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        entrustList.setLayoutManager(layoutManager);
        entrustList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new BaseAdapter<Entrust>(R.layout.item_recycler_entrust, new ArrayList<>(), entrustList, true) {
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
    }

    @Override
    protected void initData() {
        mobile = getIntent().getStringExtra("mobile");
        mPresenter.getEntrustList(mobile);
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
            mPresenter.getEntrustList(mobile);
        }
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
     * @param mobile  手机号
     */
    public static void start(Context context, String mobile) {
        Intent intent = new Intent(context, EntrustActivity.class);
        intent.putExtra("mobile", mobile);
        context.startActivity(intent);
    }

    public void stopRefresh() {
        smartRefreshLayout.finishRefresh();
    }
}

