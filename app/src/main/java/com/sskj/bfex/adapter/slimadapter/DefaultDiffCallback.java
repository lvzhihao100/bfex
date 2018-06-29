package com.sskj.bfex.adapter.slimadapter;




public class DefaultDiffCallback implements SlimDiffUtil.Callback {
    @Override
    public boolean areItemsTheSame(Object oldItem, Object newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    public boolean areContentsTheSame(Object oldItem, Object newItem) {
        return true;
    }
}
