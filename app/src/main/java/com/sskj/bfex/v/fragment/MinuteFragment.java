package com.sskj.bfex.v.fragment;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.sskj.bfex.R;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.bean.KMAEntity;
import com.sskj.bfex.m.bean.bean.Stock;
import com.sskj.bfex.p.fragment.StockPresenter;
import com.sskj.bfex.v.activity.MarketActivity;
import com.sskj.bfex.v.base.BaseFragment;
import com.sskj.bfex.v.widget.mychart.CoupleChartGestureListener;
import com.sskj.bfex.v.widget.mychart.MyBottomMarkerView;
import com.sskj.bfex.v.widget.mychart.MyCombinedChart;
import com.sskj.bfex.v.widget.mychart.MyHMarkerView;
import com.sskj.bfex.v.widget.mychart.MyInfoMarkerView;
import com.sskj.bfex.v.widget.mychart.MyLeftMarkerView;
import com.sskj.bfex.v.widget.mychart.TimeValueFormatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * 分时Fragment
 *
 * @author Hey
 *         created at 2018/4/2 18:34
 */
public class MinuteFragment extends BaseFragment<MarketActivity, StockPresenter> {

    @BindView(R.id.lineChart)
    MyCombinedChart lineChart;
    @BindView(R.id.volumeChart)
    MyCombinedChart volumeChart;
    @BindView(R.id.market_ma5)
    TextView marketMa5;
    @BindView(R.id.market_ma15)
    TextView marketMa15;
    @BindView(R.id.market_ma20)
    TextView marketMa20;
    @BindView(R.id.ma_layout)
    LinearLayout maLayout;

    //商品编码
    String code;
    /**
     * K线周期(minute/minute5/minute15/minute30/minute60/day)
     */
    String goodsType;

    //行情最大值
    float high;
    //行情最小值
    float low;

    float highVolume;

    Unbinder unbinder;

    //记录最大最小缩放
    private float scaleMax = 1f, scaleMin = 1f;
    List<Entry> minuteList = new ArrayList<>();
    List<BarEntry> barEntries = new ArrayList<>();
    List<String> xVals = new ArrayList<>();
    List<CandleEntry> candleEntries = new ArrayList<>();

    ArrayList<Entry> ma5DataL = new ArrayList<>();
    ArrayList<Entry> ma10DataL = new ArrayList<>();
    ArrayList<Entry> ma20DataL = new ArrayList<>();


    private boolean isFirst = true;

    private boolean isMinute;

    private boolean update = false;

    private List<Disposable> mDisposable = new ArrayList<>();


    private boolean isHighlight = false;


    private DecimalFormat volumeFormat = new DecimalFormat("0.00");
    private DecimalFormat maFormat = new DecimalFormat("0.0000");
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            lineChart.setAutoScaleMinMaxEnabled(true);
            volumeChart.setAutoScaleMinMaxEnabled(true);

            lineChart.notifyDataSetChanged();
            volumeChart.notifyDataSetChanged();

            lineChart.invalidate();
            volumeChart.invalidate();

        }
    };

    public MinuteFragment() {
        // Required empty public constructor
    }

    /**
     * @param code 币种编码
     * @return
     */
    public static MinuteFragment newInstance(String code, String goodsType) {
        MinuteFragment minuteFragment = new MinuteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("goodsType", goodsType);
        minuteFragment.setArguments(bundle);
        return minuteFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getString("code");
            goodsType = getArguments().getString("goodsType");
            if (goodsType.equals("minute")) {
                isMinute = true;
            } else {
                isMinute = false;
            }
        } else {
            throw new RuntimeException("You Should Use newInstance()");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_minute;
    }

    @Override
    public StockPresenter getPresenter() {
        return new StockPresenter();
    }

    @Override
    public void initData() {
        //0 获取五大币数据
        if (mActivity.type == Currency.BTC) {
            mPresenter.getStockInfo("goodsHistoryInfoGet", goodsType, code, 1, 2, false);
        } else if (mActivity.type == Currency.TIME) {
            //获取交易币数据
            mPresenter.getTime(goodsType, code, false);
        }
        if (isMinute) {
            time();
        }
    }

    @Override
    public void initView() {
        initLineChart();
        initVolumeChart();
    }


    public void initLineChart() {
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.getLegend().setEnabled(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setHighlightFullBarEnabled(false);
        lineChart.setNoDataText("");
        XAxis x = lineChart.getXAxis();
        x.setDrawAxisLine(true);
        x.setDrawGridLines(false);
        x.setTextColor(getResources().getColor(R.color.white));
        x.setSpaceMax(0);
        x.setLabelCount(3, true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis left = lineChart.getAxisRight();
        left.setEnabled(false);
        YAxis right = lineChart.getAxisLeft();
        right.setTextColor(Color.WHITE);
        right.setDrawAxisLine(true);
        right.setDrawGridLines(true);
        right.setDrawLabels(true);
        right.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        right.setLabelCount(3, true);
        right.enableGridDashedLine(10f, 10f, 0);
    }

    public void initVolumeChart() {
        Description description = new Description();
        description.setEnabled(false);
        volumeChart.setScaleEnabled(false);
        volumeChart.setDrawGridBackground(false);
        volumeChart.setDrawingCacheEnabled(true);
        volumeChart.setDescription(description);
        volumeChart.setAutoScaleMinMaxEnabled(true);
        volumeChart.setDoubleTapToZoomEnabled(false);
        volumeChart.setExtraRightOffset(30);
        volumeChart.getLegend().setEnabled(false);
        volumeChart.setNoDataText("");
        XAxis xAxis = volumeChart.getXAxis();
        xAxis.setEnabled(false);
        YAxis left = volumeChart.getAxisRight();
        left.setEnabled(false);
        YAxis right = volumeChart.getAxisLeft();
        right.setDrawGridLines(true);
        right.setDrawAxisLine(true);
        right.setTextColor(Color.WHITE);
        right.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        right.setLabelCount(3, true); //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        right.enableGridDashedLine(10f, 10f, 0f);
        right.setAxisMinimum(0);
    }


    public void setChart(List<Stock> stockList) {
        if (isVisible()) {
            minuteList.clear();
            barEntries.clear();
            candleEntries.clear();
            xVals.clear();
            highVolume = 0;
            Collections.reverse(stockList);
            high = stockList.get(0).getHighestPrice();
            low = stockList.get(0).getLowestPrice();
            if (isMinute) {
                maLayout.setVisibility(View.INVISIBLE);
                for (int i = 0; i < stockList.size(); i++) {
                    Stock k = stockList.get(i);
                    Entry entry = new Entry(i, k.getClosingPrice());

                    if (highVolume < k.getVolume()) {
                        highVolume = k.getVolume();
                    }
                    if (high < k.getClosingPrice()) {
                        high = k.getClosingPrice();
                    }
                    if (low > k.getClosingPrice()) {
                        low = k.getClosingPrice();
                    }

                    if (mActivity.type == Currency.TIME) {
                        xVals.add(k.getTimestamp() + "000");
                    } else {
                        String time[] = k.getTime().split(":");
                        xVals.add(time[0] + ":" + time[1]);
                    }
                    minuteList.add(entry);
                    BarEntry barEntry = new BarEntry(i, k.getVolume());
                    if (k.getClosingPrice() < k.getOpeningPrice()) {
                        barEntry.setColor(getResources().getColor(R.color.market_up));
                    } else {
                        barEntry.setColor(getResources().getColor(R.color.market_down));
                    }
                    barEntries.add(barEntry);
                }
                setLineChart();
                setVolumeChart();
            } else {
                maLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < stockList.size(); i++) {
                    Stock k = stockList.get(i);
                    CandleEntry candleEntry = new CandleEntry(i, k.getHighestPrice(), k.getLowestPrice(), k.getOpeningPrice(), k.getClosingPrice());
                    if (high < k.getClosingPrice()) {
                        high = k.getClosingPrice();
                    }
                    if (low > k.getClosingPrice()) {
                        low = k.getClosingPrice();
                    }
                    if (highVolume < k.getVolume()) {
                        highVolume = k.getVolume();
                    }
                    if ("day".equals(goodsType)) {
                        if (mActivity.type == Currency.TIME) {
                            xVals.add(k.getTimestamp() + "000");
                        } else {
                            xVals.add(k.getDate() + "");
                        }

                    } else {
                        if (mActivity.type == Currency.TIME) {
                            xVals.add(k.getTimestamp() + "000");
                        } else {
                            String time[] = k.getTime().split(":");
                            xVals.add(time[0] + ":" + time[1]);
                        }

                    }
                    candleEntries.add(candleEntry);
                    BarEntry barEntry = new BarEntry(i, k.getVolume());
                    if (k.getClosingPrice() < k.getOpeningPrice()) {
                        barEntry.setColor(getResources().getColor(R.color.market_down));
                    } else {
                        barEntry.setColor(getResources().getColor(R.color.market_up));
                    }
                    barEntries.add(barEntry);
                }
                MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.chart_markerview);
                MyInfoMarkerView infoMarkerView = new MyInfoMarkerView(getActivity(), stockList, goodsType, mActivity.type);
                MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getActivity(), R.layout.chart_markerview, goodsType, mActivity.type);
                lineChart.setMarker(leftMarkerView, bottomMarkerView, infoMarkerView, xVals);
                if (candleEntries.size() < 50) {
                    lineChart.getXAxis().setAxisMaximum(50);
                    volumeChart.getXAxis().setAxisMaximum(50);
                } else {
                    lineChart.getXAxis().setAxisMaximum(xVals.size() + 5);
                    volumeChart.getXAxis().setAxisMaximum(xVals.size() + 5);
                }
                setKLineChart();
                setVolumeChart();
            }

        }

    }


    public void setLineChart() {
        LineDataSet dataSet = new LineDataSet(minuteList, null);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        LineData lineData = new LineData(dataSet);
        CombinedData data = new CombinedData();
        data.setData(lineData);

        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.chart_markerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getActivity(), R.layout.chart_markerview, goodsType, mActivity.type);

        lineChart.setMarker(leftMarkerView, bottomMarkerView, xVals);
//        lineChart.getAxisLeft().setAxisMaximum((float) (high * 1.01));
//        lineChart.getAxisLeft().setAxisMinimum((float) (low * 0.99));
        lineChart.setData(data);
        setValueFormatter();
//        lineChart.getXAxis().setAxisMaximum(xVals.size() + 1);
//        volumeChart.getXAxis().setAxisMaximum(xVals.size() + 1);

        setScale(lineChart);
        lineChart.invalidate();
        lineChart.moveViewToX(xVals.size() - 1);
    }


    public void setKLineChart() {
        CandleDataSet set = new CandleDataSet(candleEntries, "Data Set");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowColor(Color.DKGRAY);//影线颜色
        set.setShadowColorSameAsCandle(true);//影线颜色与实体一致
        set.setShadowWidth(0.7f);//影线
        set.setIncreasingColor(getResources().getColor(R.color.market_up));//涨
        set.setDecreasingColor(getResources().getColor(R.color.market_down));//跌
        set.setDecreasingPaintStyle(Paint.Style.FILL);//绿跌，空心
        set.setIncreasingPaintStyle(Paint.Style.STROKE);// 红涨，实体
        set.setNeutralColor(Color.RED);//当天价格不涨不跌（一字线）颜色
        set.setHighlightLineWidth(1f);//选中蜡烛时的线宽
        set.setDrawValues(false);//在图表中的元素上面是否显示数值
        set.setLabel("label");//图表名称，可以通过mChart.getLegend().setEnable(true)显示在标注上
        set.setHighlightEnabled(false);

        CandleData candleData = new CandleData(set);
        KMAEntity kmaEntity5 = new KMAEntity(candleEntries, 5);
        KMAEntity kmaEntity10 = new KMAEntity(candleEntries, 10);
        KMAEntity kmaEntity20 = new KMAEntity(candleEntries, 20);
        int size = candleEntries.size();
        for (int i = 0; i < size; i++) {
            ma5DataL.add(new Entry(i, kmaEntity5.getMAs().get(i)));
            ma10DataL.add(new Entry(i, kmaEntity10.getMAs().get(i)));
            ma20DataL.add(new Entry(i, kmaEntity20.getMAs().get(i)));
        }


        ArrayList<ILineDataSet> sets = new ArrayList<>();
        /******此处修复如果显示的点的个数达不到MA均线的位置所有的点都从0开始计算最小值的问题******************************/
        sets.add(setMaLine(5, xVals, ma5DataL));
        sets.add(setMaLine(10, xVals, ma10DataL));
        sets.add(setMaLine(20, xVals, ma20DataL));

        LineData lineData = new LineData(sets);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(candleData);
        combinedData.setData(lineData);
        setValueFormatter();
        lineChart.setData(combinedData);
        setScale(lineChart);
        lineChart.invalidate();
        lineChart.moveViewToX(xVals.size() - 1);
        changeMaValues(xVals.size() - 1);
    }

    private void setValueFormatter() {
        if ("day".equals(goodsType)) {
            lineChart.getXAxis().setValueFormatter(new TimeValueFormatter(goodsType, xVals));
        } else {
            if (mActivity.type == Currency.TIME) {
                lineChart.getXAxis().setValueFormatter(new TimeValueFormatter(goodsType, xVals));
            } else {
                lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return xVals.size() < value ? "" : xVals.get((int) value);
                    }
                });
            }
        }

    }

    private void setVolumeChart() {
        BarDataSet set = new BarDataSet(barEntries, "成交量");
        set.setBarBorderWidth(1); //bar空隙
        set.setHighlightEnabled(true);
        set.setHighLightColor(getResources().getColor(R.color.yellow));
        set.setDrawValues(false);
        set.setColor(getResources().getColor(R.color.yellow));
        set.setHighlightEnabled(false);
        BarData data = new BarData(set);
        data.setBarWidth(1);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(data);
        volumeChart.setData(combinedData);
        volumeChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return volumeFormat(value);
            }
        });
        setScale(volumeChart);
        volumeChart.invalidate();
        volumeChart.moveViewToX(xVals.size() - 1);
        MyHMarkerView myHMarkerView = new MyHMarkerView(getActivity(), R.layout.chart_markerview_line);
        volumeChart.setMarker(null, null, myHMarkerView, xVals);
        setListener();
        setOffset();
        handler.sendEmptyMessageDelayed(0, 300);
    }


    /*设置量表对齐*/
    private void setOffset() {
        float candleHighWidth = lineChart.getViewPortHandler().offsetLeft(); //获取Y轴绘制宽度
        float volumeHighWidth = volumeChart.getViewPortHandler().offsetLeft(); //获取Y轴绘制宽度
        float minWidth;
        if (candleHighWidth < volumeHighWidth) {
            minWidth = volumeHighWidth;
        } else {
            minWidth = candleHighWidth;
        }
        lineChart.getAxisLeft().setMinWidth(Utils.convertPixelsToDp(minWidth));
        volumeChart.getAxisLeft().setMinWidth(Utils.convertPixelsToDp(minWidth));
        lineChart.setExtraRightOffset(28);
        volumeChart.setExtraRightOffset(28);
    }


    private void setListener() {
        lineChart.getData().setHighlightEnabled(false);
        volumeChart.getData().setHighlightEnabled(false);

        GestureDetector gestureListener = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                longPress(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                isHighlight = !isHighlight;
                setHighlight(isHighlight);
                if (isHighlight) {
                    highLight(e);
                }
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                highLight(e);
                return super.onSingleTapConfirmed(e);
            }
        });
        View.OnTouchListener onTouchListenerK = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mActivity.refreshLayout.requestDisallowInterceptTouchEvent(true);
                        if (isHighlight) {
                            highLight(event);
                        } else {

                            float max = lineChart.getHighestVisibleX();
                            changeMaValues((int) max);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mActivity.refreshLayout.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return gestureListener.onTouchEvent(event);
            }
        };

        // 将K线控的滑动事件传递给交易量控件
        lineChart.setOnChartGestureListener(new CoupleChartGestureListener(lineChart, new Chart[]{volumeChart}));
        // 将交易量控件的滑动事件传递给K线控件
        volumeChart.setOnChartGestureListener(new CoupleChartGestureListener(volumeChart, new Chart[]{lineChart}));
        lineChart.setOnTouchListener(onTouchListenerK);
        volumeChart.setOnTouchListener(onTouchListenerK);
    }


    private void setScale(MyCombinedChart combinedChart) {
        if (isFirst) {
            if (combinedChart != null) {
                final ViewPortHandler viewPortHandlerBar = combinedChart.getViewPortHandler();
                viewPortHandlerBar.setMinMaxScaleX(calculateMinScale(xVals.size()), calculateMaxScale(xVals.size()));
                viewPortHandlerBar.setMinMaxScaleY(1, 1);
            }
        } else {
            final ViewPortHandler viewPortHandlerBar = combinedChart.getViewPortHandler();
            viewPortHandlerBar.setMinMaxScaleX(scaleMin, scaleMax);
            viewPortHandlerBar.setMinMaxScaleY(1, 1);
        }
        isFirst = false;
    }


    private float calculateMaxScale(int count) {
        BigDecimal c = new BigDecimal(count);
        BigDecimal num = new BigDecimal(50);
        scaleMax = c.divide(num, 2).floatValue();
        if (scaleMax == 0) {
            scaleMax = 1;
        }
        return scaleMax;
    }

    private float calculateMinScale(int count) {
        BigDecimal c = new BigDecimal(count);
        BigDecimal num = new BigDecimal(60);
        scaleMin = c.divide(num, 0).floatValue();
        if (scaleMin < 1) {
            scaleMin = 1;
        }
        return scaleMin;

    }

    @NonNull
    private LineDataSet setMaLine(int ma, List<String> xVals, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + ma);
        lineDataSetMa.setDrawValues(false);
        if (ma == 5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
        } else if (ma == 10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
        } else if (ma == 20) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma20));
        }
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setHighlightEnabled(false);
        lineDataSetMa.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return lineDataSetMa;
    }


    private void time() {
        mDisposable.add(Flowable.interval(20, TimeUnit.SECONDS)
                .subscribe(a -> {
                    if (mActivity.type == Currency.BTC) {
                        mPresenter.getStockInfo("goodsHistoryInfoGet", goodsType, code, 1, 2, true);
                    } else if (mActivity.type == Currency.TIME) {
                        mPresenter.getTime(goodsType, code, true);
                    }
                }));
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }


    private void longPress(MotionEvent e) {
        isHighlight = true;
        setHighlight(isHighlight);
        highLight(e);
    }


    private void highLight(MotionEvent e) {
        Highlight highlight = lineChart.getHighlightByTouchPoint(e.getX(), e.getY());
        lineChart.highlightValue(highlight);
        Highlight highlight1 = volumeChart.getHighlightByTouchPoint(e.getX(), e.getY() + lineChart.getHeight());
        volumeChart.highlightValue(highlight1);
        Entry entry = lineChart.getEntryByTouchPoint(e.getX(), e.getY());
        if (entry != null) {
            changeMaValues((int) entry.getX());
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (lineChart != null) {
                lineChart.highlightValue(null);
            }
            if (volumeChart != null) {
                volumeChart.highlightValue(null);
            }
            if (mDisposable != null) {
                for (Disposable disposable : mDisposable) {
                    disposable.dispose();
                }
            }
        } else {
            if (isMinute) {
                time();
            }
        }
    }


    private void setHighlight(boolean isHighlight) {
        lineChart.getData().getCandleData().setHighlightEnabled(isHighlight);
        volumeChart.getData().setHighlightEnabled(isHighlight);
        lineChart.setDragEnabled(!isHighlight);
        volumeChart.setDragEnabled(!isHighlight);
        if (!isHighlight) {
            lineChart.highlightValue(null);
            volumeChart.highlightValue(null);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mDisposable != null) {
            for (Disposable disposable : mDisposable) {
                disposable.dispose();
            }
        }
    }


    public String volumeFormat(float volume) {
        if (volume > 1000) {
            return volumeFormat.format(volume / 1000) + "k";
        } else if (volume > 10000) {
            return volumeFormat.format(volume / 10000) + "w";
        }
        return volumeFormat.format(volume);
    }

    /**
     * 修改MA图例显示
     *
     * @param index
     */
    public void changeMaValues(int index) {
        if (ma5DataL.size() > index) {
            setText(marketMa5, maFormat.format(ma5DataL.get(index).getY()));
            setText(marketMa15, maFormat.format(ma10DataL.get(index).getY()));
            setText(marketMa20, maFormat.format(ma20DataL.get(index).getY()));
        }
    }
}
