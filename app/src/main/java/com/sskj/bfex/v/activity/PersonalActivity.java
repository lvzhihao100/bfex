package com.sskj.bfex.v.activity;

import android.text.TextUtils;
import android.widget.TextView;

import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.PersonalPresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 李岩
 *     e-mail : 465357793@qq.com
 *     time   : 2018/04/03
 *     desc   : 个人中心
 *     version: 1.0
 * </pre>
 */
public class PersonalActivity extends BaseActivity<PersonalPresenter> {


    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.personal_mobile)
    TextView personalMobile;
    @BindView(R.id.personal_trabcaction_id)
    TextView personalTrabcactionId;
    @BindView(R.id.personal_name)
    TextView personalName;
    @BindView(R.id.personal_ident)
    TextView personalIdent;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personal;
    }

    @Override
    public PersonalPresenter getPresenter() {
        return new PersonalPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("个人信息");
        String mobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        if (!TextUtils.isEmpty(mobile)) {
            mPresenter.request(mobile);
        }
        toolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
    }

    public void updateUserTitle(UserInfo user) {
        personalMobile.setText(String.format(getString(R.string.personal_mobile), TextUtils.isEmpty(user.mobile) ? "" : user.mobile));
        personalTrabcactionId.setText(String.format(getString(R.string.personal_trabcaction_id), TextUtils.isEmpty(user.account)? "" : user.account));
        personalName.setText(String.format(getString(R.string.personal_name), TextUtils.isEmpty(user.realname) ? "" : user.realname));
        personalIdent.setText(String.format(getString(R.string.personal_ident), TextUtils.isEmpty(user.idcard) ? "" : user.idcard));
    }
}
