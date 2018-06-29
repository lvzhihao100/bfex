package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.adapter.BaseAdapter;
import com.sskj.bfex.common.adapter.ViewHolder;
import com.sskj.bfex.m.bean.RaiseDetail;
import com.sskj.bfex.m.bean.RaiseListBean;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.QiangChouPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.DateUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;
import com.sskj.bfex.v.widget.mychart.BubbleSeekBar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class QiangChouActivity extends BaseActivity<QiangChouPresenter> {

    @BindView(R.id.qc_tab)
    Toolbar qcTab;
    @BindView(R.id.qc_avaiable_sum)
    TextView qcAvaiableSum;
    @BindView(R.id.qc_price)
    TextView qcPrice;
    @BindView(R.id.qc_total_sum)
    TextView qcTotalSum;
    @BindView(R.id.qc_count_value)
    AppCompatEditText qcCountValue;
    @BindView(R.id.qc_seekbar)
    BubbleSeekBar qcSeekbar;
    @BindView(R.id.qc_submit)
    Button qcSubmit;
    @BindView(R.id.qc_history_all)
    TextView qcAllList;

    @BindView(R.id.qc_revcyclerView)
    RecyclerView qcRevcyclerView;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private BaseAdapter<RaiseListBean> adapter;

    private String code;

    private UserInfo mUserInfo;

    int max;
    int min;

    public final int REQUESTCODE = 121;


    public DecimalFormat intFormat = new DecimalFormat("0");
    private int buyMax;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_qiang_chou;
    }

    @Override
    public QiangChouPresenter getPresenter() {
        return new QiangChouPresenter();
    }

    @Override
    protected void initView() {
        qcTab.setTitle("抢筹");
        setSupportActionBar(qcTab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        qcRevcyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        qcRevcyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        qcRevcyclerView.setNestedScrollingEnabled(false);
        adapter = new BaseAdapter<RaiseListBean>(R.layout.recy_item_raise, new ArrayList<>(), qcRevcyclerView) {
            @Override
            public void bind(ViewHolder holder, RaiseListBean data) {
                holder.setText(R.id.tv_raise_type, data.cro_id);
                holder.setText(R.id.tv_num, data.cro_no);
                holder.setText(R.id.tv_time, DateUtil.getDateFormat(new Date(Long.parseLong(data.addtime) * 1000), DateUtil.PATTERN_J));
            }
        };
        adapter.setEmtyView();
        adapter.setEmptyText("还没有抢筹过哦");
        nestedScrollView.scrollTo(0, 0);
    }

    @Override
    protected void initData() {
        code = getIntent().getStringExtra("code");
        mUserInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
        qcAvaiableSum.setText(mUserInfo.wallone);
        mPresenter.getUserInfo(mUserInfo.mobile);
        mPresenter.getRaiseDetail(code);
        mPresenter.getRaiseList();
    }

    public void updateUserInfo(UserInfo data) {
        mUserInfo = data;
        qcAvaiableSum.setText(data.wallone);
    }

    public void setView(RaiseDetail raiseDetail) {
        qcPrice.setText(raiseDetail.getPrice());
        qcPrice.setEnabled(false);

        max = (int) Float.parseFloat(raiseDetail.getMax_limit());
        min = (int) Float.parseFloat(raiseDetail.getMin_limit());
        buyMax = (int) (Float.parseFloat(mUserInfo.wallone) / Float.parseFloat(raiseDetail.getPrice()));
        if (max == 0) {
            min = 0;
        }
        qcCountValue.setText(min + "");
        qcTotalSum.setText(caculateTotalSum());
        qcSeekbar.getConfigBuilder().max(max).build();
        qcSeekbar.getConfigBuilder().min(min).build();
        qcCountValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString().trim();
                int count = 0;
                if (TextUtils.isEmpty(text)) {
                    count = -1;
                } else {
                    count = Integer.parseInt(text);
                }
                if (count > max) {
                    if (count > buyMax) {
                        ToastUtil.showShort("账户余额不足");
                    } else {
                        ToastUtil.showShort("最大购买数量为：" + max);
                    }
                    count = max;
                    qcCountValue.setText(count + "");
                    qcCountValue.setSelection(String.valueOf(count).length());
                }
                if (count < min) {
                    count = min;
                    qcCountValue.setText(count + "");
                    qcCountValue.setSelection(String.valueOf(count).length());
                    ToastUtil.showShort("最小购买数量为：" + min);
                }
                qcSeekbar.setCurrentProgress(count);
                qcTotalSum.setText(caculateTotalSum());
            }
        });


        qcSeekbar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (fromUser) {
                    qcCountValue.setText(progress + "");
                    qcTotalSum.setText(caculateTotalSum());
                }

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        qcCountValue.setFocusable(true);
        qcCountValue.setFocusableInTouchMode(true);
        ClickUtil.click(qcSubmit, () -> {
            if (checkUser()) {
                Intent intent = new Intent(this, PayPwdInputActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }

        });

        ClickUtil.click(qcAllList, () -> {
            Intent intent = new Intent(this, RaiseListActivity.class);
            startActivity(intent);
        });

    }

    public void setData(List<RaiseListBean> data) {
        if (data.size() > 5) {
            adapter.setNewData(data.subList(0, 5));
        } else {
            adapter.setNewData(data);
        }
        qcRevcyclerView.setAdapter(adapter);
    }

    public void submitSuccess() {
        ToastUtil.showShort("抢筹成功");
        Intent intent = new Intent(this, RaiseListActivity.class);
        startActivity(intent);
        finish();
    }

    public void submitFail(String msg) {

        ToastUtil.showShort(msg);
    }


    private boolean checkUser() {
        if (Constants.mIsLogin) {
            switch (mUserInfo.status) {
                case 0:
                case 4:
                    ToastUtil.showShort("实名认证失败，请重新提交认证");
                    return false;

                case 1:
                    ToastUtil.showShort("请先完成实名认证");
                    return false;
                case 2:
                    ToastUtil.showShort("实名认证审核中，请耐心等待");
                    return false;
                case 3:
                    if (TextUtils.isEmpty(mUserInfo.tpwd)) {
                        ToastUtil.showShort("请先设置交易密码");
                        return false;
                    }

            }
            if (Float.parseFloat(qcCountValue.getText().toString()) > buyMax) {
                ToastUtil.showShort("金额不足");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    private String caculateTotalSum() {
        float count = Float.parseFloat(qcCountValue.getText().toString());
        float price = Float.parseFloat(qcPrice.getText().toString());
        return String.valueOf(count * price);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                mPresenter.submit(mUserInfo.mobile, code, qcCountValue.getText().toString());
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, String code, UserInfo userInfo) {
        Intent intent = new Intent(context, QiangChouActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("userInfo", userInfo);
        context.startActivity(intent);
    }


}
