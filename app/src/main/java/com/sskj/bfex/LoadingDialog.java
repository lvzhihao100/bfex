package com.sskj.bfex;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Administrator on 2018/4/9.
 */

public class LoadingDialog extends Dialog {

    AVLoadingIndicatorView loadingView;
    TextView loadingText;
    private Builder mBuilder;

    public LoadingDialog(Builder builder) {
        super(builder.mContext, R.style.custom_dialog);
        mBuilder = builder;
        setCancelable(mBuilder.mCancelable);
        setCanceledOnTouchOutside(mBuilder.mCanceledOnTouchOutside);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        initView();
    }

    private void initView() {
        loadingView = findViewById(R.id.loadingView);
        loadingText=findViewById(R.id.loadingText);
        setOnDismissListener(dialog -> {
            dialog.dismiss();
            loadingView.hide();
        });
    }

    @Override
    public void show() {
        super.show();
        loadingView.show();
        loadingText.setText(mBuilder.mLoadText);
    }

    public Builder getBuilder() {
        return mBuilder;
    }


    public static class Builder {

        private Context mContext;

        private int mDelay = 80;

        private CharSequence mLoadText;

        private boolean mCancelable = true;

        private boolean mCanceledOnTouchOutside = true;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder delay(int delay) {
            mDelay = delay;
            return this;
        }

        public Builder loadText(CharSequence loadText) {
            mLoadText = loadText;
            return this;
        }

        public Builder loadText(int resId) {
            mLoadText = mContext.getString(resId);
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            mCancelable = cancelable;
            mCanceledOnTouchOutside = cancelable;
            return this;
        }

        public Builder canceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public LoadingDialog build() {
            return new LoadingDialog(this);
        }

        public LoadingDialog show() {
            LoadingDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
