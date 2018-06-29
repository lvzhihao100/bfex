package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.yoojia.inputs.AndroidNextInputs;
import com.github.yoojia.inputs.StaticScheme;
import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.ApplyAgentPersonPresenter;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lv on 18-6-11.
 */

public class ApplyAgentPersonActivity extends BaseActivity<ApplyAgentPersonPresenter> {
    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView titleText;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code_get)
    TextView tvCodeGet;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_jigou)
    TextView tvJigou;
    @BindView(R.id.tv_name)
    TextView tvName;
    private CountDownTimer countDownTimer;

    @Override
    protected void initView() {
        toolBar.setLeftButtonOnClickLinster((v) -> onBackPressed());
        titleText.setText("申请经纪人");
//        tvJigou.setText(PreferencesUtils.getString(this, Constant.OID));
//        tvName.setText(PreferencesUtils.getString(this, Constant.NAME));
        ClickUtil.click(tvCodeGet, () -> {
            tvCodeGet.setEnabled(false);
            countDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvCodeGet.setText(millisUntilFinished / 1000 + "秒再次获取");
                }

                @Override
                public void onFinish() {
                    tvCodeGet.setEnabled(true);
                    tvCodeGet.setText("获取验证码");
                }
            }.start();
            mPresenter.sendCode();
        });
        AndroidNextInputs input = new AndroidNextInputs()
                .add(etCode, StaticScheme.Required());
        ClickUtil.click(btSubmit, () -> {
            String jigouCode = tvJigou.getText().toString().trim();
            String code = etCode.getText().toString().trim();
            String name = tvName.getText().toString().trim();
            if (input.test()) {
                mPresenter.applyAgent(jigouCode, name, code);
            }
        });
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, ApplyAgentPersonActivity.class);
        context.startActivity(starter);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_agent_person;
    }

    @Override
    public ApplyAgentPersonPresenter getPresenter() {
        return new ApplyAgentPersonPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
