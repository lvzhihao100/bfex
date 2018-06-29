package com.sskj.bfex.common.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public class MyBaseAdapter<T> extends BaseAdapter {
    private initView init;
    /** 子类需要使用这个属性，获取数据 */
    public List<T> list = new ArrayList<>();


    public MyBaseAdapter(initView<T> init) {
        this.init = init;
    }


    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public T getListBean(int position) {
        return list.get(position);
    }

    public void loadMore(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getDatas(){
        return list;
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return init.initItenView(position, convertView, parent, list.get(position));
    }

    public interface initView<T>{
        View initItenView(int position, View convertView, ViewGroup parent, T obj);
    }
}