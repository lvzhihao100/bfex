package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.MoneyValueFilter;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.WithdrawPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.EditChangeUtil;
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
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class WithdrawActivity extends BaseActivity<WithdrawPresenter> {

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_send_code)
    Button btSendCode;
    @BindView(R.id.et_check_code)
    EditText etCheckCode;
    @BindView(R.id.bt_withdraw)
    Button btWithdraw;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_max_num)
    TextView mTvMaxNum;
    private DisposableSubscriber<Long> disposableSubscriber;
    private Double minCoin = 0d;
    private Double maxCoin = 0d;
    private Double wallone;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public WithdrawPresenter getPresenter() {
        return new WithdrawPresenter();
    }

    @Override
    protected void onViewBinding() {

        ClickUtil.click(btSendCode, () -> {
            mPresenter.sendCode();
        });

        ClickUtil.click(ivAddress, () -> {
            startActivityForResult(new Intent(this, CashAddressListActivity.class), Constants.CASH_ADDRESS);
        });

        mTitle.setText("USDT提币");
        mToolBar.setRightButtonIcon(getResources().getDrawable(R.mipmap.icon_bill));
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        mToolBar.setRightButtonOnClickLinster(view -> startActivity(new Intent(mActivity, WithdrawRecordActivity.class)));
        ClickUtil.click(btWithdraw, () -> {
            if (TextUtils.isEmpty(etAddress.getText())) {
                ToastUtil.showShort("请输入提币地址");
                return;
            }
            if (TextUtils.isEmpty(etNumber.getText())) {
                ToastUtil.showShort("请输入数量");
                return;
            }
            Double aDouble = Double.valueOf(etNumber.getText().toString());
            if (aDouble < minCoin) {
                ToastUtil.showShort("亲~最少可以提" + minCoin + "个币哦");
                return;
            }

            if (aDouble > maxCoin) {
                ToastUtil.showShort("亲~最多可以提" + maxCoin + "个币哦");
                return;
            }

            if (aDouble > wallone) {
                ToastUtil.showShort("资金不足");
                return;
            }
            if (TextUtils.isEmpty(etPassword.getText())) {
                ToastUtil.showShort("请输入交易密码");
                return;
            }
            mPresenter.withdraw(etNumber.getText().toString().trim(),
                    etPassword.getText().toString().trim(),
                    etCheckCode.getText().toString().trim(),
                    etAddress.getText().toString().trim());
        });
        EditChangeUtil.onChange(etCheckCode, s -> {
            if (s.length() == 6) {
                mPresenter.checkCode(s.toString());
            }
        });
        etNumber.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(4)});
        mPresenter.getUserInfo();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == Constants.CASH_ADDRESS) {
            String address_url = data.getStringExtra("address_url");
            if (!TextUtils.isEmpty(address_url)) {
                etAddress.setText(address_url);
            }
        }
    }

    public void startCount() {
        btSendCode.setEnabled(false);
        disposableSubscriber = new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                int time = (int) (60 - aLong);
                btSendCode.setText(time + "s后重新发送");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void onComplete() {
                btSendCode.setText("再次获取验证码");
                btSendCode.setEnabled(true);
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

    public void setCodeOk() {
        btWithdraw.setEnabled(true);
    }

    public void updateUserInfo(UserInfo data) {
        minCoin = Double.valueOf(data.getTb_min());
        maxCoin = Double.valueOf(data.getTb_max());
        wallone = Double.valueOf(data.getWallone());
        mTvAccount.setText(data.getWallone() + " USDT");
        etNumber.setHint("最小提币数量" + data.getTb_min());
        mTvMaxNum.setText("可提现最大数量（USTD）: " + data.getTb_max());
        ClickUtil.click(tvAll, () -> {
            etNumber.setText(String.valueOf(maxCoin));
        });

    }
}
