package com.sskj.bfex.v.fragment;


import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.Summary;
import com.sskj.bfex.m.bean.Time;
import com.sskj.bfex.p.fragment.SummaryPresenter;
import com.sskj.bfex.v.activity.MarketActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * 币种简介
 *
 * @author Hey
 *         created at 2018/4/10 17:18
 */
public class SummaryFragment extends BaseFragment<MarketActivity, SummaryPresenter> {

    @BindView(R.id.summary_name)
    TextView summaryName;
    @BindView(R.id.summary_crateTime)
    TextView summaryCrateTime;
    @BindView(R.id.summary_create_num)
    TextView summaryCreateNum;
    @BindView(R.id.summary_num)
    TextView summaryNum;
    @BindView(R.id.summary_price)
    TextView summaryPrice;
    @BindView(R.id.summary_white_paper)
    TextView summaryWhitePaper;
    @BindView(R.id.summary_website)
    TextView summaryWebsite;
    @BindView(R.id.summary_block)
    TextView summaryBlock;

    @BindView(R.id.summary_content)
    AppCompatTextView summaryContent;
    String code;
    private Time time;

    public SummaryFragment() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_summary;
    }

    @Override
    public SummaryPresenter getPresenter() {
        return new SummaryPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        code = getArguments().getString("code");
        if (mActivity.type == Currency.TIME) {
            time = (Time) getArguments().getSerializable("time");
            setData(time);
        } else {
            mPresenter.getData();
        }
    }

    public void setData(Time data) {
        setText(summaryName, data.getPname());
        setText(summaryCrateTime, data.getFxtime());
        setText(summaryCreateNum, data.getFxnum());
        setText(summaryNum, data.getFxnum());
        setText(summaryPrice, data.getFxprice());
        setText(summaryWhitePaper, data.getFxbook());
        setText(summaryWebsite, data.getFxweb());
        setText(summaryBlock, data.getFxweb());
        setText(summaryContent, data.getMemo());
        return;
    }


    public void setData(List<Summary> data) {
        for (Summary summary : data) {
            if (summary.getPname().equals(code)) {
                summaryName.setText(summary.getMark());
                summaryCrateTime.setText(summary.getFxtime());
                summaryCreateNum.setText(summary.getFxnum());
                summaryNum.setText(summary.getFxall());
                summaryPrice.setText(summary.getFxprice());
                summaryWhitePaper.setText(summary.getFxbook());
                summaryWebsite.setText(summary.getFxweb());
                summaryBlock.setText(summary.getFxqukuai());
                setText(summaryContent, summary.getMemo());
                return;
            }
        }
    }

    /**
     * @param code 币种编号
     * @param time type为1时传入
     * @return
     */
    public static SummaryFragment newInstance(String code, Time time) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putSerializable("time", time);
        fragment.setArguments(bundle);
        return fragment;
    }

}
