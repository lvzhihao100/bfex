package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.activity.PwdForgetPresenter;
import com.sskj.bfex.utils.CheckUtil;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PwdForgetActivity extends BaseActivity<PwdForgetPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.bt_next_step)
    Button btNextStep;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pwd_forget;
    }

    @Override
    public PwdForgetPresenter getPresenter() {
        return new PwdForgetPresenter();
    }

    @Override
    protected void onViewBinding() {
        ClickUtil.click(ivBack, () -> onBackPressed());
        ClickUtil.click(btNextStep, () -> {
            if (CheckUtil.isMobile(etPhone.getText().toString())) {
                mPresenter.sendSms(etPhone.getText().toString());
            } else {
                ToastUtil.showShort("请填写正确的手机号");
            }
        });
        ClickUtil.click(ivDelete, () -> {
            etPhone.setText("");
            ivDelete.setVisibility(View.GONE);
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ivDelete.setVisibility(View.VISIBLE);
                    if (CheckUtil.isMobile(etPhone.getText().toString())) {
                        btNextStep.setEnabled(true);
                    } else {
                        btNextStep.setEnabled(false);
                    }
                } else {
                    ivDelete.setVisibility(View.GONE);
                }

            }
        });
    }

    public void checkCode(String mobile) {
        Intent intent = new Intent(this, CodeCheckDialogActivity.class);
        intent.putExtra(Constants.PHONE, mobile);
        startActivity(intent);
    }

}
