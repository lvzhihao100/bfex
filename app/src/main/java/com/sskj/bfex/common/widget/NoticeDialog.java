package com.sskj.bfex.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.utils.ClickUtil;

/**
*
*create by Hey at 2018/4/24 10:55
*/

public class NoticeDialog extends Dialog {

    TextView content;
    Button mButton;
    private Builder mBuilder;

    public NoticeDialog(Builder builder) {
        super(builder.mContext, R.style.custom_dialog);
        mBuilder = builder;
        setCancelable(mBuilder.mCancelable);
        setCanceledOnTouchOutside(mBuilder.mCanceledOnTouchOutside);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notice);
        initView();
    }

    private void initView() {
        content=findViewById(R.id.dialog_content);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        mButton=findViewById(R.id.dialog_know);
        ClickUtil.click(mButton,()->{
            dismiss();
        });
    }

    @Override
    public void show() {
        super.show();
        content.setText(mBuilder.mLoadText);
    }


    public Button getButton(){
        return mButton;
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

        public NoticeDialog build() {
            return new NoticeDialog(this);
        }

        public NoticeDialog show() {
            NoticeDialog dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
