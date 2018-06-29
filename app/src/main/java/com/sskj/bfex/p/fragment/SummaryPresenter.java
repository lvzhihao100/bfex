package com.sskj.bfex.p.fragment;

import android.content.res.AssetManager;

import com.sskj.bfex.m.bean.Summary;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.utils.GsonUtil;
import com.sskj.bfex.v.fragment.SummaryFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class SummaryPresenter extends BasePresenter<SummaryFragment> {

    public void getData(){
        StringBuilder builder =new StringBuilder();
        AssetManager assetManager=getContext().getAssets();
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(assetManager.open("summary.json"),"utf-8"));
            String text;
            while ((text=bufferedReader.readLine())!=null){
                builder.append(text);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String s=builder.toString();
        List<Summary> data=  GsonUtil.jsonToList(s, Summary.class);
        mView.setData(data);
    }

}
