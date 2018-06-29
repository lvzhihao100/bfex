package com.sskj.bfex.v.fragment;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.m.bean.NoticeList;
import com.sskj.bfex.p.fragment.NoticePresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.activity.WebViewActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 平台公告
 * create by Hey at 2018/4/16 13:42
 */
public class NoticeFragment extends BaseFragment<MainActivity, NoticePresenter> {


    @BindView(R.id.notice_recyclerView)
    RecyclerView noticeRecyclerView;
    @BindView(R.id.notice_refresh)
    SmartRefreshLayout refreshLayout;

    private BaseAdapter<NoticeList> adapter;

    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    public NoticePresenter getPresenter() {
        return new NoticePresenter();
    }


    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        noticeRecyclerView.setLayoutManager(linearLayoutManager);
        noticeRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(refreshLayout1 -> mPresenter.getData(10));
        refreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));
        adapter = new BaseAdapter<NoticeList>(R.layout.recycler_item_notice, new ArrayList<>(), noticeRecyclerView) {
            @Override
            public void bind(ViewHolder holder, NoticeList item) {
                holder.setText(R.id.notice_title, item.getTitle());
                holder.setText(R.id.notice_time, item.getCreate_time().get(0) + "  " + item.getCreate_time().get(1));
                ClickUtil.click(holder.getView(R.id.notice_root), () -> WebViewActivity.start(mActivity, "", item.getTitle(), Integer.parseInt(item.getId())));
            }
        };
        adapter.setEmtyView();

    }

    @Override
    public void initData() {
        mPresenter.getData(10);
    }

    public void setData(List<NoticeList> data) {
        adapter.setNewData(data);
        noticeRecyclerView.setAdapter(adapter);
    }

    public static NoticeFragment newInstance() {
        NoticeFragment noticeFragment = new NoticeFragment();
        return noticeFragment;
    }

    public void stopRefresh(){
        refreshLayout.finishRefresh();
    }
}
