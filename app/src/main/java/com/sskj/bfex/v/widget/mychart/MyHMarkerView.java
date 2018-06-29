package com.sskj.bfex.v.widget.mychart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.sskj.bfex.R;

import java.text.DecimalFormat;


/**
 * Created by loro on 2017/2/8.
 */
public class MyHMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private View markerTv;
    private LinearLayout layout;
    private float num;
    private DecimalFormat mFormat;
    public MyHMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat=new DecimalFormat("#0.00");
        View view= LayoutInflater.from(context).inflate(layoutResource,null);
        markerTv = view.findViewById(R.id.marker_img);
    }

    public void setData(float num){
        this.num=num;
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
    }

    public void setHeight(int width){
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) markerTv.getLayoutParams();
        params.height=width;
        markerTv.setLayoutParams(params);
    }

}
