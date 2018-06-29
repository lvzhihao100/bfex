package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.activity.CodeCheckDialogPresenter;
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

public class CodeCheckDialogActivity extends BaseActivity<CodeCheckDialogPresenter> {

    @BindView(R.id.tv_goto_register)
    TextView tvGotoRegister;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_phone_with_hide)
    TextView tvPhoneWithHide;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    private DisposableSubscriber<Long> disposableSubscriber;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dialog_code_check;
    }

    @Override
    public CodeCheckDialogPresenter getPresenter() {
        return new CodeCheckDialogPresenter();
    }

    private void setWindow() {
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;//设置对话框置顶显示
        win.setAttributes(lp);
    }

    @Override
    protected void onViewBinding() {
        setWindow();
        String originPhone = getIntent().getStringExtra(Constants.PHONE);
        String start = originPhone.substring(0, 3);
        String end = originPhone.substring(7, 11);
        String phone = start + "****" + end;
        tvPhoneWithHide.setText(phone);
        startCount();

        ClickUtil.click(btSubmit, () -> {
            if (!TextUtils.isEmpty(etCode.getText())) {
                mPresenter.checkCode(originPhone, etCode.getText().toString());
            } else {
                ToastUtil.showShort("请输入正确的验证码");
            }
        });
        ClickUtil.click(tvCount, () -> {
            mPresenter.sendCode(originPhone);
        });
        ClickUtil.click(tvCancel, () -> onBackPressed());
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    btSubmit.setEnabled(true);
                } else {
                    btSubmit.setEnabled(false);
                }
            }
        });
    }

    public void startCount() {
        tvCount.setEnabled(false);
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                tvCount.setText(time + "秒后重发送");
                tvCount.setTextColor(getResources().getColor(R.color.colorHintTextShow));
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onComplete() {
                tvCount.setText("再次获取验证码");
                tvCount.setEnabled(true);
                if (!disposableSubscriber.isDisposed()) {
                    disposableSubscriber.dispose();
                    disposableSubscriber = null;
                }

            }
        };
        Flowable.interval(0, 1, TimeUnit.SECONDS, Schedulers.newThread())
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableSubscriber);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableSubscriber != null && !disposableSubscriber.isDisposed()) {
            disposableSubscriber.dispose();
            disposableSubscriber = null;
        }
    }

    public void setPwd(String account) {
        Intent intent = new Intent(this, PwdResetActivity.class);
        intent.putExtra(Constants.PHONE, account);
        intent.putExtra(Constants.CODE, etCode.getText().toString());
        startActivity(intent);
    }
}
