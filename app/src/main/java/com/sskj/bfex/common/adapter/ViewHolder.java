package com.sskj.bfex.common.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2018/4/27.
 */

public  class ViewHolder extends BaseViewHolder {

    public ViewHolder(View view) {
        super(view);
    }

    @Override
    public BaseViewHolder setText(int viewId, CharSequence value) {
        if (value!=null) {
            super.setText(viewId, value);
        }
        return this;
    }
}
