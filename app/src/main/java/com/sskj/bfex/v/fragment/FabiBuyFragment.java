package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.mvchelper.ModelRx2DataSource;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.p.fragment.FabiFragmentPresenter;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Flowable;

public class FabiBuyFragment extends BaseFragment<MainActivity, BasePresenter> {

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_money_arrow)
    ImageView ivMoneyArrow;
    @BindView(R.id.iv_pay_type_arrow)
    ImageView ivPayTypeArrow;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coolRefreshView)
    CoolRefreshView coolRefreshView;
    private SlimAdapter slimAdapter;

    public static FabiBuyFragment newInstance() {
        FabiBuyFragment fragment = new FabiBuyFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fabi_buy;
    }

    @Override
    public BasePresenter getPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        slimAdapter = SlimAdapter.create().register(R.layout.recy_item_fabi, new SlimInjector<String>() {
            @Override
            public void onInject(String data, IViewInjector injector) {

            }
        }).attachTo(recyclerView).updateData(new ArrayList());
        ModelRx2DataSource<String> dataSource =
                new ModelRx2DataSource<>((ModelRx2DataSource.OnLoadSource<String>)
                        page -> Flowable.range(0, 10)
                                .map(integer -> integer + "")
                                .toList()
                                .toFlowable(), 10);
        MVCCoolHelper<List<String>> mvcCoolHelper = new MVCCoolHelper<>(coolRefreshView);
        mvcCoolHelper.setDataSource(dataSource);
        mvcCoolHelper.setAdapter(slimAdapter);
        mvcCoolHelper.refresh();
    }
}
