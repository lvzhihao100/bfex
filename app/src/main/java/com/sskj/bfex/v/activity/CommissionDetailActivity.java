package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.TimePickerView;
import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.CommissionBean;
import com.sskj.bfex.m.bean.CommissionTitleBean;
import com.sskj.bfex.mvchelper.ModelRx2DataSource;
import com.sskj.bfex.p.activity.CommissionDetailsPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.NumberUtil;
import com.sskj.bfex.utils.TimeFormatUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lv on 18-6-7.
 * 佣金明细界面
 */

public class CommissionDetailActivity extends BaseActivity<CommissionDetailsPresenter> {
    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView titleText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coolRefreshView)
    CoolRefreshView coolRefreshView;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.all_money)
    TextView allMoney;
    @BindView(R.id.tv_commission_ratio)
    TextView tvCommissionRatio;
    private SlimAdapter slimAdapter;
    private boolean isStart;
    private TimePickerView startPicker;
    private MVCCoolHelper<List<CommissionBean>> mvcCoolHelper;

    @Override
    protected void initView() {
        titleText.setText("佣金明细");
        initTime();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slimAdapter = SlimAdapter.create().register(R.layout.item_commission, new SlimInjector<CommissionBean>() {
            @Override
            public void onInject(CommissionBean data, IViewInjector injector) {
                injector.text(R.id.tv_title, data.getUsername())
                        .text(R.id.tv_fee, NumberUtil.keepNoZore(data.getUsdFee()))
                        .text(R.id.tv_money, NumberUtil.keepNoZore(data.getUserReturnFee()))
                        .text(R.id.tv_time, data.getCreateTime());
            }
        }).attachTo(recyclerView).updateData(new ArrayList());
        ModelRx2DataSource<CommissionBean> dataSource =
                new ModelRx2DataSource<>((ModelRx2DataSource.OnLoadSource<CommissionBean>)
                        page -> mPresenter.loadData(page, tvStartTime.getText().toString(), tvEndTime.getText().toString()), 10);
        mvcCoolHelper = new MVCCoolHelper<>(coolRefreshView);
        mvcCoolHelper.setDataSource(dataSource);
        mvcCoolHelper.setAdapter(slimAdapter);
        mvcCoolHelper.refresh();
    }



    private void initTime() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_YEAR, -7);
        tvStartTime.setText(TimeFormatUtil.SF_FORMAT_B.format(instance.getTime()));
        tvEndTime.setText(TimeFormatUtil.SF_FORMAT_B.format(Calendar.getInstance().getTime()));
        ClickUtil.click(tvStartTime, () -> showTime(true));
        ClickUtil.click(tvEndTime, () -> showTime(false));
    }

    private void showTime(boolean isStart) {
        this.isStart = isStart;
        if (startPicker == null) {
            startPicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
            startPicker.setOnTimeSelectListener(date -> {
                if (this.isStart) {
                    tvStartTime.setText(TimeFormatUtil.limitMaxDate(date));
                } else {
                    tvEndTime.setText(TimeFormatUtil.limitMaxDate(date));
                }
                mvcCoolHelper.refresh();
            });
            startPicker.show();
        } else {
            try {
                Date parse = TimeFormatUtil.SF_FORMAT_B.parse(isStart ? tvStartTime.getText().toString() : tvEndTime.getText().toString());
                startPicker.setTime(parse);
                startPicker.show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CommissionDetailActivity.class);
        context.startActivity(starter);
    }

    public void updataUI(CommissionTitleBean data) {
        allMoney.setText("总佣金：" + data.getCommissionTotal());
        tvCommissionRatio.setText("手续费返佣比例：" + data.getConcessionRate() + "%");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commission_detail;
    }

    @Override
    public CommissionDetailsPresenter getPresenter() {
        return new CommissionDetailsPresenter();
    }
}
