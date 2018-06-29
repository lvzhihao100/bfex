package com.sskj.bfex.v.fragment;

import com.sskj.bfex.R;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.base.BaseFragment;

/**
 * Created by Administrator on 2018/4/13 0013.
 */

public class ActivityFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_act;
    }

    @Override
    public BasePresenter getPresenter() {
        return new BasePresenter();
    }
}
