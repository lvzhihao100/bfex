package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.vcedittext.VerificationCodeEditText;
import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.activity.CodeCheckPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/02
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CodeCheckActivity extends BaseActivity<CodeCheckPresenter> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.etVerificationCode)
    VerificationCodeEditText etVerificationCode;
    @BindView(R.id.tv_time_count)
    TextView tvTimeCount;
    @BindView(R.id.bt_next_step)
    Button btNextStep;
    private DisposableSubscriber<Long> disposableSubscriber;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_code_check;
    }

    @Override
    public CodeCheckPresenter getPresenter() {
        return new CodeCheckPresenter();
    }

    @Override
    protected void onViewBinding() {
        String mobile = getIntent().getStringExtra(Constants.PHONE);
        startCount();
        ClickUtil.click(btNextStep, () -> {
            if (etVerificationCode.getText().length() == 6) {
                mPresenter.checkCode(mobile, etVerificationCode.getText().toString());
            } else {
                ToastUtil.showShort("请输入正确的验证码");
            }
        });
        ClickUtil.click(tvTimeCount, () -> {
            mPresenter.sendCode(mobile);
        });
        ClickUtil.click(ivBack, this::onBackPressed);
    }

    public void setPwd(String account) {
        Intent intent = new Intent(this, PwdSetActivity.class);
        intent.putExtra(Constants.PHONE, account);
        intent.putExtra(Constants.CODE, etVerificationCode.getText().toString());
        startActivity(intent);
    }

    public void startCount() {
        tvTimeCount.setEnabled(false);
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                tvTimeCount.setText(String.format("%d秒后重新发送", time));
                tvTimeCount.setTextColor(getResources().getColor(R.color.colorDarkGreen));
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onComplete() {
                tvTimeCount.setText("再次获取验证码");
                tvTimeCount.setEnabled(true);
                tvTimeCount.setTextColor(getResources().getColor(R.color.colorTextGreen));
                if (!disposableSubscriber.isDisposed()) {
                    disposableSubscriber.dispose();
                    disposableSubscriber = null;
                }

            }
        };
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6) {
                    btNextStep.setEnabled(true);
                } else {
                    btNextStep.setEnabled(false);
                }
            }
        });

        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);
    }


}
