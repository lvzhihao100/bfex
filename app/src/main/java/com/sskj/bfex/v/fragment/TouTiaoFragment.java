package com.sskj.bfex.v.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.m.bean.TouTiao;
import com.sskj.bfex.p.fragment.TouTiaoPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.activity.WebViewActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TouTiaoFragment extends BaseFragment<MainActivity, TouTiaoPresenter> {

    @BindView(R.id.toutiao_refresh)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.toutiao_recycler_view)
    RecyclerView toutiaoRecyclerView;

    private BaseAdapter<TouTiao> adapter;

    public TouTiaoFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_tou_tiao;
    }

    @Override
    public TouTiaoPresenter getPresenter() {
        return new TouTiaoPresenter();
    }


    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        toutiaoRecyclerView.setLayoutManager(linearLayoutManager);
        toutiaoRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity,DividerItemDecoration.VERTICAL));
        refreshLayout.setOnRefreshListener(refreshLayout1 ->{
                    refreshLayout.finishRefresh();
                    mPresenter.getData(10);
                });
        refreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));
        adapter = new BaseAdapter<TouTiao>(R.layout.recycler_item_toutiao, new ArrayList<>(), toutiaoRecyclerView) {
            @Override
            public void bind(ViewHolder holder, TouTiao item) {
                holder.setText(R.id.toutiao_title,item.getTitle());
                holder.setText(R.id.toutiao_from,item.getSrc());
                holder.setText(R.id.toutiao_time,item.getTime().get(0)+"  "+item.getTime().get(1));
                if (TextUtils.isEmpty(item.getPic())){
                    holder.getView(R.id.toutiao_img).setVisibility(View.GONE);
                }else{
                    holder.getView(R.id.toutiao_img).setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(item.getPic()).into((ImageView) holder.getView(R.id.toutiao_img));
                }
                ClickUtil.click(holder.getView(R.id.toutiao_root), () -> WebViewActivity.start(mActivity,item.getContent(),item.getTitle(),-1));
            }
        };
        adapter.setEmtyView();
    }

    @Override
    public void initData() {
        mPresenter.getData(10);
    }

    public void setData(List<TouTiao> data) {
        adapter.setNewData(data);
        toutiaoRecyclerView.setAdapter(adapter);
    }

    public static TouTiaoFragment newInstance() {
        TouTiaoFragment touTiaoFragment = new TouTiaoFragment();
        return touTiaoFragment;
    }


}
