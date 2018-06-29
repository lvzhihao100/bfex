package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * create by Hey at 2018/5/18 14:27
 */

public class MyCandleStickChartRenderer extends CandleStickChartRenderer {

    private float[] mShadowBuffers = new float[8];
    private float[] mBodyBuffers = new float[4];
    private float[] mRangeBuffers = new float[4];
    private float[] mOpenBuffers = new float[4];
    private float[] mCloseBuffers = new float[4];


    private float hLength = Utils.convertDpToPixel(10f); //横线长15dp

    private float vLength = Utils.convertDpToPixel(15f); //竖线长10dp

    private float textX = Utils.convertDpToPixel(2f); //文本x坐标偏移量

    private float textY = Utils.convertDpToPixel(3f); //文本y偏移量


    private boolean isShowHLPoint = true; //是否显示最高点和最低点标识,默认显示

    private float textSize = 10f; //文字大小

    private int screenWidth;

    private int minTextColor = Color.BLUE;
    private int maxTextColor = Color.BLUE;

    private float arrowSize = 7;  //箭头大小px

    public MyCandleStickChartRenderer(CandleDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler, int screenWidth) {
        super(chart, animator, viewPortHandler);
        this.screenWidth = screenWidth;
    }


    @Override
    protected void drawDataSet(Canvas c, ICandleDataSet dataSet) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        float phaseY = mAnimator.getPhaseY();
        float barSpace = dataSet.getBarSpace();
        boolean showCandleBar = dataSet.getShowCandleBar();

        mXBounds.set(mChart, dataSet);

        mRenderPaint.setStrokeWidth(dataSet.getShadowWidth());

        // draw the body
        for (int j = mXBounds.min; j <= mXBounds.range + mXBounds.min; j++) {

            // get the entry
            CandleEntry e = dataSet.getEntryForIndex(j);

            if (e == null)
                continue;

            final float xPos = e.getX() + 0.5f; //修复显示一半的bug

            final float open = e.getOpen();
            final float close = e.getClose();
            final float high = e.getHigh();
            final float low = e.getLow();

            if (showCandleBar) {
                // calculate the shadow

                mShadowBuffers[0] = xPos;
                mShadowBuffers[2] = xPos;
                mShadowBuffers[4] = xPos;
                mShadowBuffers[6] = xPos;

                if (open > close) {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = open * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = close * phaseY;
                } else if (open < close) {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = close * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = open * phaseY;
                } else {
                    mShadowBuffers[1] = high * phaseY;
                    mShadowBuffers[3] = open * phaseY;
                    mShadowBuffers[5] = low * phaseY;
                    mShadowBuffers[7] = mShadowBuffers[3];
                }

                trans.pointValuesToPixel(mShadowBuffers);

                // draw the shadows

                if (dataSet.getShadowColorSameAsCandle()) {

                    if (open > close)
                        mRenderPaint.setColor(
                                dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        dataSet.getDecreasingColor()
                        );

                    else if (open < close)
                        mRenderPaint.setColor(
                                dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        dataSet.getIncreasingColor()
                        );

                    else
                        mRenderPaint.setColor(
                                dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE ?
                                        dataSet.getColor(j) :
                                        dataSet.getNeutralColor()
                        );

                } else {
                    mRenderPaint.setColor(
                            dataSet.getShadowColor() == ColorTemplate.COLOR_NONE ?
                                    dataSet.getColor(j) :
                                    dataSet.getShadowColor()
                    );
                }

                mRenderPaint.setStyle(Paint.Style.STROKE);

                c.drawLines(mShadowBuffers, mRenderPaint);

                // calculate the body

                mBodyBuffers[0] = xPos - 0.5f + barSpace;
                mBodyBuffers[1] = close * phaseY;
                mBodyBuffers[2] = (xPos + 0.5f - barSpace);
                mBodyBuffers[3] = open * phaseY;

                trans.pointValuesToPixel(mBodyBuffers);

                // draw body differently for increasing and decreasing entry
                if (open > close) { // decreasing

                    if (dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(dataSet.getDecreasingColor());
                    }

                    mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());

                    c.drawRect(
                            mBodyBuffers[0], mBodyBuffers[3],
                            mBodyBuffers[2], mBodyBuffers[1],
                            mRenderPaint);

                } else if (open < close) {

                    if (dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(dataSet.getIncreasingColor());
                    }

                    mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());

                    c.drawRect(
                            mBodyBuffers[0], mBodyBuffers[1],
                            mBodyBuffers[2], mBodyBuffers[3],
                            mRenderPaint);
                } else { // equal values

                    if (dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE) {
                        mRenderPaint.setColor(dataSet.getColor(j));
                    } else {
                        mRenderPaint.setColor(dataSet.getNeutralColor());
                    }

                    c.drawLine(
                            mBodyBuffers[0], mBodyBuffers[1],
                            mBodyBuffers[2], mBodyBuffers[3],
                            mRenderPaint);
                }
            } else {

                mRangeBuffers[0] = xPos;
                mRangeBuffers[1] = high * phaseY;
                mRangeBuffers[2] = xPos;
                mRangeBuffers[3] = low * phaseY;

                mOpenBuffers[0] = xPos - 0.5f + barSpace;
                mOpenBuffers[1] = open * phaseY;
                mOpenBuffers[2] = xPos;
                mOpenBuffers[3] = open * phaseY;

                mCloseBuffers[0] = xPos + 0.5f - barSpace;
                mCloseBuffers[1] = close * phaseY;
                mCloseBuffers[2] = xPos;
                mCloseBuffers[3] = close * phaseY;

                trans.pointValuesToPixel(mRangeBuffers);
                trans.pointValuesToPixel(mOpenBuffers);
                trans.pointValuesToPixel(mCloseBuffers);

                // draw the ranges
                int barColor;

                if (open > close)
                    barColor = dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : dataSet.getDecreasingColor();
                else if (open < close)
                    barColor = dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : dataSet.getIncreasingColor();
                else
                    barColor = dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE
                            ? dataSet.getColor(j)
                            : dataSet.getNeutralColor();

                mRenderPaint.setColor(barColor);
                c.drawLine(
                        mRangeBuffers[0], mRangeBuffers[1],
                        mRangeBuffers[2], mRangeBuffers[3],
                        mRenderPaint);
                c.drawLine(
                        mOpenBuffers[0], mOpenBuffers[1],
                        mOpenBuffers[2], mOpenBuffers[3],
                        mRenderPaint);
                c.drawLine(
                        mCloseBuffers[0], mCloseBuffers[1],
                        mCloseBuffers[2], mCloseBuffers[3],
                        mRenderPaint);
            }
        }
    }


    @Override
    public void drawValues(Canvas c) {
        super.drawValues(c);
        if (isShowHLPoint) {
            CandleDataSet dataSet = (CandleDataSet) mChart.getCandleData().getDataSetByIndex(0);
            Transformer transformer = mChart.getTransformer(dataSet.getAxisDependency());
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿画笔
            paint.setTextSize(Utils.convertDpToPixel(textSize)); //设置字体大小
            drawMinMaxPoint(dataSet, transformer, paint, c);
        }
    }


    /**
     * 绘制最高最低点
     *
     * @param dataSet     dateSet
     * @param transformer transformer
     * @param paint       paint
     * @param c           c
     */
    private void drawMinMaxPoint(CandleDataSet dataSet, Transformer transformer, Paint paint, Canvas c) {
        CandleEntry[] candleEntries = getMinMaxValues(dataSet.getValues());
        CandleEntry minEntry = candleEntries[0];
        CandleEntry maxEntry = candleEntries[1];
        drawHighEntry(c, dataSet, minEntry, paint, false);
        drawHighEntry(c, dataSet, maxEntry, paint, true);
    }


    private void drawHighEntry(Canvas c, ICandleDataSet dataSet, CandleEntry e, Paint paint, boolean max) {
        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
        float phaseY = mAnimator.getPhaseY();
        mRenderPaint.setStrokeWidth(dataSet.getShadowWidth());
        final float xPos = e.getX() + 0.5f;
        final float open = e.getOpen();
        final float close = e.getClose();
        final float high = e.getHigh();
        final float low = e.getLow();
        // calculate the body

        mShadowBuffers[0] = xPos;
        mShadowBuffers[2] = xPos;
        mShadowBuffers[4] = xPos;
        mShadowBuffers[6] = xPos;
        if (open > close) {
            mShadowBuffers[1] = high * phaseY;
            mShadowBuffers[3] = open * phaseY;
            mShadowBuffers[5] = low * phaseY;
            mShadowBuffers[7] = close * phaseY;
        } else if (open < close) {
            mShadowBuffers[1] = high * phaseY;
            mShadowBuffers[3] = close * phaseY;
            mShadowBuffers[5] = low * phaseY;
            mShadowBuffers[7] = open * phaseY;
        } else {
            mShadowBuffers[1] = high * phaseY;
            mShadowBuffers[3] = open * phaseY;
            mShadowBuffers[5] = low * phaseY;
            mShadowBuffers[7] = mShadowBuffers[3];
        }
        trans.pointValuesToPixel(mShadowBuffers);
        if (max) { //画高点
            float x = mShadowBuffers[0];
            float y = Math.min(mShadowBuffers[1], mShadowBuffers[3]);
            String text = String.valueOf(e.getHigh());
            float textWith = Utils.calcTextWidth(paint, text);
            float textHeight = Utils.calcTextHeight(paint, text);
            paint.setColor(maxTextColor);
            if (x > (screenWidth - screenWidth / 3)) { //向左
                x -= 5;
                y -= 5;
                c.drawLine(x, y, x - hLength, y, paint); //画横线
                c.drawLine(x, y, x - arrowSize, y - arrowSize, paint);
                c.drawLine(x, y, x - arrowSize, y + arrowSize, paint);
                c.drawText(text, x - hLength - textX - textWith, y + (textHeight / 2), paint); //画数值
            } else {
                x += 5;
                y -= 5;
                c.drawLine(x, y, x + hLength, y, paint); //画横线
                c.drawLine(x, y, x + arrowSize, y - arrowSize, paint);
                c.drawLine(x, y, x + arrowSize, y + arrowSize, paint);
                c.drawText(text, x + hLength + textX, y + (textHeight / 2), paint); //画数值
            }
        } else { //画低点
            float x = mShadowBuffers[0];
            float y = Math.max(mShadowBuffers[5], mShadowBuffers[7]);
            String text = String.valueOf(e.getLow());
            float textWith = Utils.calcTextWidth(paint, text);
            float textHeight = Utils.calcTextHeight(paint, text);
            paint.setColor(minTextColor);
            if (x > (screenWidth - screenWidth / 3)) { //向左
                x -= 5;
                y += 5;
                c.drawLine(x, y, x - hLength, y, paint); //画横线
                c.drawLine(x, y, x - arrowSize, y - arrowSize, paint);
                c.drawLine(x, y, x - arrowSize, y + arrowSize, paint);
                c.drawText(text, x - hLength - textX - textWith, y + (textHeight / 2), paint); //画数值
            } else {
                x += 5;
                y += 5;
                c.drawLine(x, y, x + hLength, y, paint); //画横线
                c.drawLine(x, y, x + arrowSize, y - arrowSize, paint);
                c.drawLine(x, y, x + arrowSize, y + arrowSize, paint);
                c.drawText(text, x + hLength + textX, y + (textHeight / 2), paint); //画数值
            }

        }
    }

    /**
     * 获取最小值和最大值
     *
     * @param values data
     * @return min=entry[0] max=entry[1]
     */
    private CandleEntry[] getMinMaxValues(List<CandleEntry> values) {
        CandleEntry minEntry = null;
        CandleEntry maxEntry = null;
        if (values != null) {
            for (int i = (int) mChart.getLowestVisibleX(); i < mChart.getHighestVisibleX(); i++) {
                if (values.size() > i) {
                    CandleEntry entry = values.get(i);
                    if ((int) mChart.getLowestVisibleX() == i) {
                        minEntry = entry;
                        maxEntry = entry;
                    } else {
                        if (minEntry.getLow() > entry.getLow()) {
                            minEntry = entry;
                        }
                        if (maxEntry.getHigh() < entry.getHigh()) {
                            maxEntry = entry;
                        }
                    }

                }
            }
        }

        return new CandleEntry[]{minEntry, maxEntry};
    }


    public MyCandleStickChartRenderer setShowHLPoint(boolean showHLPoint) {
        isShowHLPoint = showHLPoint;
        return this;
    }

    public MyCandleStickChartRenderer setMinTextColor(int minTextColor) {
        this.minTextColor = minTextColor;
        return this;
    }

    public MyCandleStickChartRenderer setMaxTextColor(int maxTextColor) {
        this.maxTextColor = maxTextColor;
        return this;
    }


    public MyCandleStickChartRenderer setHlength(float hLength) {
        this.hLength = hLength;
        return this;
    }

    public MyCandleStickChartRenderer setVlength(float vLength) {
        this.vLength = vLength;
        return this;
    }


    public MyCandleStickChartRenderer setTextX(float textX) {
        this.textX = textX;
        return this;
    }

    public MyCandleStickChartRenderer setTextY(float textY) {
        this.textY = textY;
        return this;
    }

    public MyCandleStickChartRenderer setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

}
