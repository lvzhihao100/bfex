package com.sskj.bfex.adapter.slimadapter;


import android.support.v7.util.DiffUtil;

import java.util.List;


public final class SlimDiffUtil extends DiffUtil.Callback {

    private List<?> oldData;
    private List<?> newData;
    private Callback diffCallback;

    public SlimDiffUtil(List<?> oldData, List<?> newData, Callback diffCallback) {
        this.oldData = oldData;
        this.newData = newData;
        this.diffCallback = diffCallback;
    }

    @Override
    public int getOldListSize() {
        return oldData == null ? 0 : oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData == null ? 0 : newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return diffCallback.areItemsTheSame(oldData.get(oldItemPosition), newData.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return diffCallback.areContentsTheSame(oldData.get(oldItemPosition), newData.get(newItemPosition));
    }

    public interface Callback {
        boolean areItemsTheSame(Object oldItem, Object newItem);

        boolean areContentsTheSame(Object oldItem, Object newItem);
    }
}
