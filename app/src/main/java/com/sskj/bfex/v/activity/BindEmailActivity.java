package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.BindEmailPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

public class BindEmailActivity extends BaseActivity<BindEmailPresenter> {

    @BindView(R.id.bind_email_edittext)
    EditText mBindEmailEdittext;

    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.bind_email_submit)
    Button mBindEmailSubmit;
    private String mobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bind_email;
    }

    @Override
    public BindEmailPresenter getPresenter() {
        return new BindEmailPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("绑定邮箱");
        mobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");

        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        ClickUtil.click(mBindEmailSubmit, () -> {
            String email = mBindEmailEdittext.getText().toString().trim();
            if (CheckUtil.isEmail(email)) {
                mPresenter.requestBindEmail(email, mobile);
            } else {
                ToastUtil.showShort("邮箱格式不正确，请重新输入");
            }
        });

        mBindEmailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CheckUtil.isEmail(s.toString())) {
                    mBindEmailSubmit.setEnabled(true);
                }else {
                    mBindEmailSubmit.setEnabled(false);
                }
            }
        });
    }


    public void onResponseSuccess() {
        setResult(RESULT_OK, new Intent().putExtra("resultData", mBindEmailEdittext.getText().toString()));
        finish();
    }

}
