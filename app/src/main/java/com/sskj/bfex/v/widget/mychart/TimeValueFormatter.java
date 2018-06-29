package com.sskj.bfex.v.widget.mychart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class TimeValueFormatter implements IAxisValueFormatter {


    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private String goodsType;
    private List<String> xVals;

    public TimeValueFormatter(String goodsType, List<String> xVals) {
        this.goodsType = goodsType;
        this.xVals = xVals;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (goodsType.equals("day")) {
            return value >= xVals.size() ? "" : dayFormat.format(Long.parseLong(xVals.get((int) value)));
        }
        return value >= xVals.size() ? "" : timeFormat.format(Long.parseLong(xVals.get((int) value)));
    }
}
