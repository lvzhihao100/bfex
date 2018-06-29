package com.sskj.bfex.common.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sskj.bfex.R;

import java.util.List;

/**
 * @author Hey
 *         created at 2018/4/13 13:37
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, ViewHolder> {

    public BaseAdapter(int layoutResId, @Nullable List<T> data, RecyclerView recyclerView) {
        super(layoutResId, data);
        this.bindToRecyclerView(recyclerView);
    }

    public BaseAdapter(int layoutResId, @Nullable List<T> data, RecyclerView recyclerView, boolean addEmptyView) {
        super(layoutResId, data);
        this.bindToRecyclerView(recyclerView);
        if (addEmptyView) {
            setEmtyView();
        }
    }

    @Override
    protected void convert(ViewHolder helper, T item) {
        bind(helper, item);
    }

    public abstract void bind(ViewHolder holder, T item);


    public void setEmtyView() {
        this.setEmptyView(R.layout.empty_view);
    }

    public void setEmtyView(int id) {
        this.setEmptyView(id);
    }

    public void setEmptyText(String text) {
        TextView textView = getEmptyView().findViewById(R.id.empty_text);
        textView.setText(text);
    }


}
