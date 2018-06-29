package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class MyLineCharRenderer extends LineChartRenderer {

    private float hLength = Utils.convertDpToPixel(15f); //横线长15dp

    private float vLength = Utils.convertDpToPixel(30f); //竖线长10dp

    float rect = Utils.convertDpToPixel(8f); //矩形高低差/2

    private float textX = Utils.convertDpToPixel(2f); //文本x坐标偏移量

    private float textY = Utils.convertDpToPixel(3f); //文本y偏移量


    private boolean isShowHLPoint = true; //是否显示最高点和最低点标识,默认显示

    private float textSize = 10f; //文字大小

    private int screenWidth;


    private int minTextColor = Color.WHITE;
    private int maxTextColor = Color.WHITE;

    public MyLineCharRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    public MyLineCharRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler, int screenWidth) {
        super(chart, animator, viewPortHandler);
        this.screenWidth = screenWidth;
    }

    @Override
    public void drawValues(Canvas c) {
        super.drawValues(c);
        if (isShowHLPoint) {
            LineDataSet dataSet = (LineDataSet) mChart.getLineData().getDataSetByIndex(0);
            Transformer transformer = mChart.getTransformer(dataSet.getAxisDependency());
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿画笔
            paint.setTextSize(Utils.convertDpToPixel(textSize)); //设置字体大小
            drawMinMaxPoint(dataSet, transformer, paint, c);
        }
    }

    private void drawMinMaxPoint(LineDataSet dataSet, Transformer transformer, Paint paint, Canvas c) {
        Entry[] entries = getMinMaxValues(dataSet.getValues());
        Entry minEntry = entries[0];
        Entry maxEntry = entries[1];
        //画最低点
        MPPointD minPoint = transformer.getPixelForValues(minEntry.getX(), minEntry.getY());
        float minX = (float) minPoint.x;
        float minY = (float) minPoint.y;
        paint.setColor(minTextColor);
        float minTextWidth = paint.measureText(String.valueOf(minEntry.getY()));
//        float rectLength = Utils.convertDpToPixel(String.valueOf(minEntry.getY()).length() * 1.7f); //矩形框长
        //画竖线
        c.drawLine(minX, minY, minX, minY + vLength, paint);
        if (minX > screenWidth - screenWidth / 3) { //向左
            c.drawLine(minX, minY + vLength, minX - hLength, minY + vLength, paint);
            c.drawText(String.valueOf(minEntry.getY()), minX - hLength - textX - minTextWidth, minY + vLength + textY, paint);
        } else {
            c.drawLine(minX, minY + vLength, minX + hLength, minY + vLength, paint);
            c.drawText(String.valueOf(minEntry.getY()), minX + hLength + textX + minTextWidth, minY + vLength + textY, paint);
        }
        //画最高点
        MPPointD maxPoint = transformer.getPixelForValues(maxEntry.getX(), maxEntry.getY());
        float maxX = (float) maxPoint.x;
        float maxY = (float) maxPoint.y;
        paint.setColor(maxTextColor);

        float maxTextWidth = paint.measureText(String.valueOf(maxEntry.getY()));

        //画竖线
        c.drawLine(maxX, maxY, maxX, maxY - vLength, paint);

        if (maxX > screenWidth - screenWidth / 3) { //向左
            c.drawLine(maxX, maxY - vLength, maxX - hLength, maxY - vLength, paint);
            c.drawText(String.valueOf(minEntry.getY()), maxX - hLength - textX - maxTextWidth, maxY - vLength - textY, paint);
        } else {
            c.drawLine(maxX, maxY - vLength, maxX + hLength, maxY - vLength, paint);
            c.drawText(String.valueOf(maxEntry.getY()), maxX + hLength + textX + maxTextWidth, maxY - vLength - textY, paint);
        }

    }

    /**
     * 获取最小值和最大值
     *
     * @param values data
     * @return min=entry[0] max=entry[1]
     */
    private Entry[] getMinMaxValues(List<Entry> values) {
        Entry minEntry = null;
        Entry maxEntry = null;
        if (values != null) {
            for (int i = 0; i < values.size(); i++) {
                Entry entry = values.get(i);
                if (i == 0) {
                    minEntry = entry;
                    maxEntry = entry;
                } else {
                    if (minEntry.getY() > entry.getY()) {
                        minEntry = entry;
                    }
                    if (maxEntry.getY() < entry.getY()) {
                        maxEntry = entry;
                    }
                }
            }
        }

        return new Entry[]{minEntry, maxEntry};
    }


    public MyLineCharRenderer setShowHLPoint(boolean showHLPoint) {
        isShowHLPoint = showHLPoint;
        return this;
    }

    public MyLineCharRenderer setMinTextColor(int minTextColor) {
        this.minTextColor = minTextColor;
        return this;
    }

    public MyLineCharRenderer setMaxTextColor(int maxTextColor) {
        this.maxTextColor = maxTextColor;
        return this;
    }


    public MyLineCharRenderer setHlength(float hLength) {
        this.hLength = hLength;
        return this;
    }

    public MyLineCharRenderer setVlength(float vLength) {
        this.vLength = vLength;
        return this;
    }

    public MyLineCharRenderer setRect(float rect) {
        this.rect = rect;
        return this;
    }

    public MyLineCharRenderer setTextX(float textX) {
        this.textX = textX;
        return this;
    }

    public MyLineCharRenderer setTextY(float textY) {
        this.textY = textY;
        return this;
    }

    public MyLineCharRenderer setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public MyLineCharRenderer setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
        return this;
    }
}
