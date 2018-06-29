package com.sskj.bfex.v.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.sskj.bfex.MyAppLication;
import com.sskj.bfex.common.Constants;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.SPUtils;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.activity.MainActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;

/**
 * Created by Lee on 2018/1/25 0025.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    public T mPresenter;
    public static int themeIndex = 1;
    public BaseActivity mActivity;
    /**
     * 用户手机号
     */
    public String mMobile;

    private static Activity mCurrentActivity;// 对所有activity进行管理

    public static List<Activity> mActivities = new LinkedList<Activity>();

    private static long mPreTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initTheme();
        setContentView(getLayoutResId());
        mActivity = this;
        mPresenter = getPresenter();
        mPresenter.attachView(this, this);
        mMobile = (String) SPUtils.get(this, Constants.SP_Mobile, "");
        Constants.mIsLogin = (boolean) SPUtils.get(MyAppLication.getApplication(), Constants.SP_LOGIN_STATUS, false);
        onViewBinding();
        initView();
        initData();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    private void initTheme() {

    }

    /**
     * 初始化布局ID
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化当前View 的 presenter
     *
     * @return
     */
    public abstract T getPresenter();

    /**
     * 初始化
     */
    protected void onViewBinding() {
    }

    ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.cancelRequest();
        mPresenter.detachView();
        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                ToastUtil.showShort("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
    }


    /**
     * 退出应用
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public void setText(TextView view, String text) {
        if (view != null && text != null) {
            view.setText(text);
        }
    }
}
