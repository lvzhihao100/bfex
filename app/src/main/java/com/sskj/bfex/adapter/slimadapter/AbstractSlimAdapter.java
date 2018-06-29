package com.sskj.bfex.adapter.slimadapter;

import android.support.v7.widget.RecyclerView;


abstract class AbstractSlimAdapter extends RecyclerView.Adapter<SlimViewHolder> {

    @Override
    public  void onBindViewHolder(SlimViewHolder holder, int position) {
        holder.bind(getItem(position ), position );
    }

    protected abstract Object getItem(int position);


}
