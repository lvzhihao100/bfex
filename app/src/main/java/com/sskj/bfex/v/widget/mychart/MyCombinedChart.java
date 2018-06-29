package com.sskj.bfex.v.widget.mychart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;

import java.util.List;

/**
 * Created by loro on 2017/2/8.
 */
public class MyCombinedChart extends CombinedChart {
    private MyLeftMarkerView myMarkerViewLeft;
    private MyHMarkerView myMarkerViewH;
    private MyBottomMarkerView myBottomMarkerView;
    private MyInfoMarkerView myInfoMarkerView;
    private List<String> xVals;
    private boolean mDrawMarkerViews = true;
    private PointF point = new PointF();
    private String tag;

    public MyCombinedChart(Context context) {
        super(context);
    }

    public MyCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void init() {
        super.init();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mRenderer = new CombinedChartRenderer(this, mAnimator, mViewPortHandler, metrics.widthPixels);
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyHMarkerView markerH, List<String> xVals) {
        this.myMarkerViewLeft = markerLeft;
        this.myMarkerViewH = markerH;
        this.xVals = xVals;
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerH, List<String> xVals) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerH;
        this.xVals = xVals;
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, MyInfoMarkerView myInfoMarkerView, List<String> xVals) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerBottom;
        this.myInfoMarkerView = myInfoMarkerView;
        this.xVals = xVals;
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, MyHMarkerView markerH, List<String> xVals) {
        this.myMarkerViewLeft = markerLeft;
        this.myBottomMarkerView = markerBottom;
        this.myMarkerViewH = markerH;
        this.xVals = xVals;
    }


    public void setMarker(MyLeftMarkerView marker, MyInfoMarkerView infoMarkerView, List<String> xVals) {
        this.myMarkerViewLeft = marker;
        this.xVals = xVals;
        this.myInfoMarkerView = infoMarkerView;
    }


    public void setMarker(MyBottomMarkerView marker, List<String> xVals) {
        this.myBottomMarkerView = marker;
        this.xVals = xVals;
    }

    public void setMarker(MyLeftMarkerView marker) {
        this.myMarkerViewLeft = marker;
        this.xVals = xVals;
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        if (!mDrawMarkerViews || !valuesToHighlight())
            return;
        for (int i = 0; i < mIndicesToHighlight.length; i++) {
            Highlight highlight = mIndicesToHighlight[i];
            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
            // make sure entry not null
            float[] pos = getMarkerPosition(highlight);
            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            if (null != myMarkerViewH) {
                myMarkerViewH.refreshContent(e, mIndicesToHighlight[i]);
                int height = (int) mViewPortHandler.contentHeight();
                myMarkerViewH.setHeight(height);
                myMarkerViewH.draw(canvas, pos[0], mViewPortHandler.contentTop());
            }

            if (null != myMarkerViewLeft) {
                //修改标记值
                float yValForHighlight = mIndicesToHighlight[i].getY();
                myMarkerViewLeft.setData(yValForHighlight);
                myMarkerViewLeft.refreshContent(e, mIndicesToHighlight[i]);
                myMarkerViewLeft.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
                        myMarkerViewLeft.getMeasuredHeight());
                myMarkerViewLeft.draw(canvas, 0 - mViewPortHandler.offsetLeft() - getAxisLeft().getXOffset() - getAxisLeft().getMinWidth() - getExtraLeftOffset(), highlight.getDrawY() - myMarkerViewLeft.getMeasuredHeight() / 2);

            }

            if (null != myInfoMarkerView) {
                myInfoMarkerView.setIndex((int) highlight.getX());
                myInfoMarkerView.refreshContent(e, highlight);
                myInfoMarkerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                myInfoMarkerView.layout(0, 0, myInfoMarkerView.getMeasuredWidth(), myInfoMarkerView.getMeasuredHeight());
                WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                int width = windowManager.getDefaultDisplay().getWidth();
                int x;
                if (pos[0] < width / 2) {
                    x = width - myInfoMarkerView.getWidth();
                } else {
                    x = 0;
                }
                myInfoMarkerView.draw(canvas, x, 0);

            }

            if (null != myBottomMarkerView) {
                myBottomMarkerView.setData(xVals.get((int) highlight.getX()));
                myBottomMarkerView.refreshContent(e, highlight);
                myBottomMarkerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                myBottomMarkerView.layout(0, 0, myBottomMarkerView.getMeasuredWidth(),
                        myBottomMarkerView.getMeasuredHeight());
                myBottomMarkerView.draw(canvas, pos[0] - myBottomMarkerView.getWidth() / 2, mViewPortHandler.contentBottom());
            }

        }
    }

    private MyLeftMarkerView getLeftMarkerView() {

        return myMarkerViewLeft;
    }

    public MyBottomMarkerView getBottomMarkerView() {
        return myBottomMarkerView;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }
}
