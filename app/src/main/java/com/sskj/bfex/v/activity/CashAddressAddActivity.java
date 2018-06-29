package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.CashPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : 李岩
 *     e-mail : 465357793@qq.com
 *     time   : 2018/04/03
 *     desc   : 添加提现地址
 *     version: 1.0
 * </pre>
 */
public class CashAddressAddActivity extends BaseActivity<CashPresenter> {

    @BindView(R.id.cash_addreess_add_submit)
    Button mSubmit;
    @BindView(R.id.cash_addreess_add_input)
    EditText mAddressInput;
    @BindView(R.id.cash_addreess_add_remark)
    EditText mAddressRemarkInput;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.qr_scan)
    ImageView MIvQRScan;

    private TextView.OnEditorActionListener dealEditorAction = (v, actionId, event) -> {
        //以下方法防止两次发送请求
        if (actionId == EditorInfo.IME_ACTION_SEND ||
                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_UP://"你点了软键盘'回车'按钮"
                    //						//隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                default:
                    return true;
            }
        }
        return false;
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cash_address_add;
    }

    @Override
    public CashPresenter getPresenter() {
        return new CashPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("USDT钱包地址管理");
        mAddressRemarkInput.setOnEditorActionListener(dealEditorAction);
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());

        ClickUtil.click(MIvQRScan, () -> {
            startActivityForResult(new Intent(mActivity, QuickMarkActivity.class), Constants.SACN_QR);
        });
        ClickUtil.click(mSubmit, () -> {
            String address = mAddressInput.getText().toString().trim();
            if (TextUtils.isEmpty(address)){
                ToastUtil.showShort("请输入钱包地址");
            }else {
                mPresenter.requestAddAddress(address, mAddressRemarkInput.getText().toString(), UserUtil.getAccount());
            }
        });
    }


    public void onResponseCashAddressAddSuccess() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.SACN_QR){
            String scan_result = data.getStringExtra("scan_result");
            mAddressInput.setText(TextUtils.isEmpty(scan_result) ? "" : scan_result);
        }
    }
}
