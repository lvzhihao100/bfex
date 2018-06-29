package com.sskj.bfex.v.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sskj.bfex.p.base.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by Lee on 2018/2/28 0028.
 */

public abstract class BaseViewPagerFragment<T extends BaseActivity, P extends BasePresenter> extends Fragment implements IBaseView{

    protected T mActivity;
    protected P mPresenter;
    private View inflate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflate==null) {
            inflate = inflater.inflate(getLayoutId(), null);
            mActivity = (T) getActivity();
            mPresenter = getPresenter();
            mPresenter.attachView(this, mActivity);
            ButterKnife.bind(this, inflate);
            initView();
            initData();
        }
        return inflate;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    public abstract int getLayoutId();

    public abstract P getPresenter();

    public  void initView(){}

    public  void initData(){}



}
