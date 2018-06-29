package com.sskj.bfex.v.fragment;


import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.HeyTabLayout;
import com.sskj.bfex.p.fragment.InformationPresenter;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * create by Hey at 2018/4/16 13:34
 */

public class InformationFragment extends BaseFragment<MainActivity, InformationPresenter> {

    @BindView(R.id.info_tab)
    HeyTabLayout infoTab;

    private List<Fragment> fragments=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_infromation;
    }

    @Override
    public InformationPresenter getPresenter() {
        return new InformationPresenter();
    }


    @Override
    public void initView() {
        fragments.add(TouTiaoFragment.newInstance());
        fragments.add(NoticeFragment.newInstance());
        HeyTabLayout.TabAdapter adapter=new HeyTabLayout.TabAdapter(R.id.info_content,fragments,getChildFragmentManager());
        infoTab.setAdapter(adapter);
    }

    public static InformationFragment newInstance() {
        InformationFragment informationFragment = new InformationFragment();
        return informationFragment;
    }

}
