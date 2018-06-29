package com.sskj.bfex.v.widget.mychart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.sskj.bfex.R;
import com.sskj.bfex.m.bean.Currency;

import java.text.SimpleDateFormat;


/**
 * Created by loro on 2017/2/8.
 */
public class MyBottomMarkerView extends MarkerView {
    private Currency type;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private String time;
    String goodsType;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");


    public MyBottomMarkerView(Context context, int layoutResource, String goodsType, Currency type) {
        super(context, layoutResource);
        markerTv = findViewById(R.id.markerView);
        markerTv.setTextSize(10);
        this.goodsType = goodsType;
        this.type = type;
    }

    public void setData(String time) {

        if (this.goodsType.equals("day")) {
            this.time = dayFormat.format(Long.parseLong(time));
        } else {
            if (type == Currency.TIME) {
                this.time = timeFormat.format(Long.parseLong(time));
            } else {
                this.time = time;
            }
        }

    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(time);
    }

}
