package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.view.View;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

public class CommonActivity extends BaseActivity {


    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_common;
    }

    @Override
    public BasePresenter getPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void onViewBinding() {
        toolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
    }

    public void buttonClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }


}
