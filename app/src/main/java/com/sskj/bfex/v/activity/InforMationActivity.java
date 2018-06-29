package com.sskj.bfex.v.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.HeyTabLayout;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.base.BaseActivity;
import com.sskj.bfex.v.fragment.NoticeFragment;
import com.sskj.bfex.v.fragment.TouTiaoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName：bfex
 * DESC: (类描述)
 * Created by 李岩 on 2018/6/29 0029
 * updateName:(修改人名称)
 * updateTime:(修改时间)
 * updateDesc:(修改内容)
 */
public class InforMationActivity extends BaseActivity {
    @BindView(R.id.asset_manage_tablayout)
    HeyTabLayout mAssetManageTablayout;
    @BindView(R.id.information_back)
    ImageView mInformationBack;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_infor_mation;
    }

    @Override
    public BasePresenter getPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mInformationBack.setOnClickListener(v -> finish());
        fragments.add(TouTiaoFragment.newInstance());
        fragments.add(NoticeFragment.newInstance());
        HeyTabLayout.TabAdapter adapter=new HeyTabLayout.TabAdapter(R.id.info_content,fragments,getSupportFragmentManager());
        mAssetManageTablayout.setAdapter(adapter);
    }
}
