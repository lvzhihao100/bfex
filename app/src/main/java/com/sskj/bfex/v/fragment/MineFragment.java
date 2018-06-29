package com.sskj.bfex.v.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.R;
import com.sskj.bfex.common.App;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.common.rxbus2.RxBus;
import com.sskj.bfex.common.rxbus2.Subscribe;
import com.sskj.bfex.common.rxbus2.ThreadMode;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.UserInfo;
import com.sskj.bfex.p.activity.MinePresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.TipUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.AboutUsActivity;
import com.sskj.bfex.v.activity.AssetActivity;
import com.sskj.bfex.v.activity.AssetManageActivity;
import com.sskj.bfex.v.activity.InforMationActivity;
import com.sskj.bfex.v.activity.InviteFriendActivity;
import com.sskj.bfex.v.activity.LoginActivity;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.activity.PersonalActivity;
import com.sskj.bfex.v.activity.SecurityCenterActivity;
import com.sskj.bfex.v.activity.WithdrawActivity;
import com.sskj.bfex.v.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * Created by Lee on 2018/2/28 0028.
 */

public class MineFragment extends BaseFragment<MainActivity, MinePresenter> {

    @BindView(R.id.mine_user_balance)
    TextView mMineUserBalance;
    @BindView(R.id.mine_property_type)
    TextView mMinePropertyType;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_uid)
    TextView tvUid;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    Unbinder unbinder;
    private MaterialDialog tip;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public MinePresenter getPresenter() {
        return new MinePresenter();
    }

    @Subscribe(code = Constants.USER_LOGIN, threadMode = ThreadMode.MAIN)
    public void userLogin() {
        mActivity.mMobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        Constants.mIsLogin = (boolean) SPUtils.get(MyAppLication.getApplication(), Constants.SP_LOGIN_STATUS, false);
        mPresenter.requestUserInfo(mActivity.mMobile);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (Constants.mIsLogin) {
                if (!TextUtils.isEmpty(mActivity.mMobile)) {
                    mPresenter.requestUserInfo(mActivity.mMobile);
                }
            }
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPreView();
        RxBus.getDefault().register(this);
        mActivity.mMobile = (String) SPUtils.get(MyAppLication.getApplication(), Constants.SP_Mobile, "");
        if (TextUtils.isEmpty(mActivity.mMobile) || !Constants.mIsLogin) {
            logout();
        } else {
            if (mActivity.mUserInfo != null) {

                loginSuccess(mActivity.mUserInfo);
            }
        }

        mToolBar.setRightButtonOnClickLinster(v -> {//退出
            if (tip == null) {
                tip = TipUtil.getTip(getActivity(), "确认要退出吗？", () -> {
                    SPUtils.clear(mActivity);
                    logout();
                });
            }
            tip.show();
        });

    }

    private void initPreView() {
        initItem(R.id.include_grzx, "个人中心", R.mipmap.icon_me_personal);
        initItem(R.id.include_aqzx, "安全中心", R.mipmap.icon_security_center);
        initItem(R.id.include_zcgl, "资产管理", R.mipmap.icon_recharge, 0);
//        initItem(R.id.include_zhcz, "账户充值", R.mipmap.icon_recharge);
//        initItem(R.id.include_zhtx, "账户提现", R.mipmap.icon_cash);
        initItem(R.id.include_tbdz, "提币地址", R.mipmap.icon_address_gray);
        initItem(R.id.include_yqhy, "经纪人", R.mipmap.icon_invite_friend);
        initItem(R.id.include_hqzx, "行情资讯", R.mipmap.icon_invite_friend);
        initItem(R.id.include_gywm, "关于我们", R.mipmap.icon_about_us);
        ClickUtil.click(getView().findViewById(R.id.include_grzx), () -> {//个人中心
            if (!TextUtils.isEmpty(mActivity.mMobile)) {
                startActivity(new Intent(mActivity, PersonalActivity.class));
            } else {
                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });
        ClickUtil.click(getView().findViewById(R.id.include_aqzx), () -> {//安全中心
            if (!TextUtils.isEmpty(mActivity.mMobile)) {
                startActivity(new Intent(mActivity, SecurityCenterActivity.class));
            } else {
                startActivity(new Intent(mActivity, LoginActivity.class));

            }
        });

//        ClickUtil.click(getView().findViewById(R.id.include_zhcz), () -> {//
//            if (!TextUtils.isEmpty(mActivity.mMobile)) {
//                startActivity(new Intent(mActivity, RechargeActivity.class));
//            } else {
//                startActivity(new Intent(mActivity, LoginActivity.class));
//            }
//        });
        ClickUtil.click(getView().findViewById(R.id.include_zcgl), () -> {//资产管理
            startActivity(new Intent(mActivity, AssetManageActivity.class));
//            if (!TextUtils.isEmpty(mActivity.mMobile)) {
//            } else {
//                startActivity(new Intent(mActivity, LoginActivity.class));
//            }
        });

        ClickUtil.click(getView().findViewById(R.id.include_tbdz), () -> {//推荐地址
            if (!TextUtils.isEmpty(mActivity.mMobile)) {
                if (mActivity.mUserInfo.status == 3) {
                    if (mActivity.mUserInfo.tpwd != null && mActivity.mUserInfo.tpwd.equals("1")) {
                        startActivity(new Intent(mActivity, WithdrawActivity.class));
                    } else {
                        ToastUtil.showShort("请先设置支付密码");
                        startActivity(new Intent(mActivity, SecurityCenterActivity.class));
                    }
                } else {
                    ToastUtil.showShort("请先完成实名认证");
                    startActivity(new Intent(mActivity, SecurityCenterActivity.class));
                }
            } else {
                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });


//        /**
//         * 提币地址
//         */
//        ClickUtil.click(getView().findViewById(R.id.include_zhtx), () -> {
//
//            if (!TextUtils.isEmpty(mActivity.mMobile)) { //登录
//
//            } else {
//                startActivity(new Intent(mActivity, LoginActivity.class));
//            }
//        });
        ClickUtil.click(getView().findViewById(R.id.include_yqhy), () -> {//经纪人
            if (!TextUtils.isEmpty(mActivity.mMobile)) {
                startActivity(new Intent(mActivity, InviteFriendActivity.class));
            } else {
                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });
        ClickUtil.click(getView().findViewById(R.id.include_hqzx), () -> {//行情资讯
            startActivity(new Intent(mActivity, InforMationActivity.class));
//            if (!TextUtils.isEmpty(mActivity.mMobile)) {
//            } else {
//                startActivity(new Intent(mActivity, LoginActivity.class));
//            }
        });
        ClickUtil.click(getView().findViewById(R.id.include_gywm), () -> {//关于我们
            startActivity(new Intent(mActivity, AboutUsActivity.class));
        });
        ClickUtil.click(mMineUserBalance, () -> {//账户资产
            if (Constants.mIsLogin) {
                AssetActivity.start(getActivity());
            }
        });
    }


    private void initItem(int itemId, String title, int icon) {
        ((TextView) getView().findViewById(itemId).findViewById(R.id.tv_title)).setText(title);
        ((ImageView) getView().findViewById(itemId).findViewById(R.id.iv_title)).setImageResource(icon);
    }

    private void initItem(int itemId, String title, int icon, int num) {
        ((TextView) getView().findViewById(itemId).findViewById(R.id.tv_title)).setText(title);
        ((ImageView) getView().findViewById(itemId).findViewById(R.id.iv_title)).setImageResource(icon);
        ((TextView) getView().findViewById(itemId).findViewById(R.id.tv_dot)).setText(num + "");
        ((TextView) getView().findViewById(itemId).findViewById(R.id.tv_dot)).setVisibility(num > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    public void updateUserTitle(UserInfo user) {
        SPUtils.putBean(mActivity, Constants.SP_USER_INFO, user);
        loginSuccess(user);
    }

    private void loginSuccess(UserInfo user) {
        Constants.mIsLogin = true;
        mMinePropertyType.setVisibility(View.VISIBLE);
        mMineUserBalance.setVisibility(View.VISIBLE);
        mToolBar.showRightImgView();
        String start = mActivity.mMobile.substring(0, 3);
        String end = mActivity.mMobile.substring(7, 11);
        tvAccount.setText(start + "****" + end);
        tvAccount.setEnabled(false);
        tvUid.setText(String.format(getString(R.string.user_id), user.account));
        tvUid.setTextSize(TypedValue.COMPLEX_UNIT_PT, 36);
        tvUid.setTextColor(getResources().getColor(R.color.colorDarkGreen));
        mMineUserBalance.setText(user.wallone);
        mMinePropertyType.setText("账户资产（USDT）");
    }

    @Subscribe(code = Constants.USER_LOGOUT, threadMode = ThreadMode.MAIN)
    private void logout() {
        SPUtils.clear(App.INSTANCE);
        Constants.mIsLogin = false;
        tvAccount.setText("请登录");
        tvUid.setText(getResources().getText(R.string.welcome));
        tvAccount.setEnabled(true);
        mActivity.mMobile = "";
        mActivity.mUserInfo = null;
        mToolBar.hideRightImgView();
        tvUid.setTextSize(TypedValue.COMPLEX_UNIT_PT, 24);
        tvUid.setTextColor(getResources().getColor(R.color.colorHintLine));
        ClickUtil.click(tvAccount, () -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        mMineUserBalance.setText("--");
        mMinePropertyType.setText("账户资产（USTD）");
    }


    @Override
    public void onDestroy() {
        RxBus.getDefault().unregister(this);
        super.onDestroy();


    }

}
