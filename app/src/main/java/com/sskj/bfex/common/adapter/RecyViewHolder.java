package com.sskj.bfex.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyViewHolder extends ViewHolder {

	private View convertView;
	private SparseArray<View> views;
	private Context context;

	public RecyViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        convertView = itemView;
        views = new SparseArray<View>();
    }

    public static RecyViewHolder get(Context context, ViewGroup viewGroup, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        return new RecyViewHolder(context, itemView);
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public RecyViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public String getText(int viewId) {
        TextView tv = getView(viewId);
        return tv.getText().toString();
    }

    public RecyViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }

    public RecyViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }


    public RecyViewHolder setViewVisiable(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public RecyViewHolder setViewBackgroundResource(int viewId, int resId) {
        getView(viewId).setBackgroundResource(resId);
        return this;
    }

    public RecyViewHolder setOnclickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
