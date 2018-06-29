package com.sskj.bfex.p.base;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.sskj.bfex.LoadingDialog;
import com.sskj.bfex.R;
import com.sskj.bfex.v.base.IBaseView;
import com.sskj.bfex.v.widget.mychart.ProgressDialogs;

import java.lang.ref.WeakReference;

/**
 eated by Lee on 2018/1/25 0025.
 */
public class BasePresenter<VIEW extends IBaseView> {
    public VIEW mView;
    private WeakReference<VIEW> mReference;
    public Context mContext;
    private ProgressDialogs mProgressDialogs;

    private LoadingDialog dialog;

    public Context getContext(){
        return mContext;
    }

    LoadingDialog.Builder builder;
    /**
     * 绑定View
     * @param view
     * @param context
     */
    public void attachView(VIEW view, Context context) {
        mReference = new WeakReference<VIEW>(view);
        mView = mReference.get();
        mContext = context;
        mProgressDialogs = new ProgressDialogs(mContext);
        builder = new LoadingDialog.Builder(mContext);
        builder.loadText(R.string.loading);
        builder.cancelable(true);
        builder.canceledOnTouchOutside(false);

    }

    public void showLoading(){
        dialog = builder.show();
    }

    public void hideLoading(){
        if (dialog != null){
//            mProgressDialogs.closeDialog();
            dialog.dismiss();
        }
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        if (mReference != null){
            mReference.clear();
        }
    }
    public void cancelRequest(){
        OkGo.getInstance().cancelTag(this);
    }

}
