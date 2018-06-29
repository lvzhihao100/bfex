package com.sskj.bfex.v.widget.mychart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.sskj.bfex.R;
import com.sskj.bfex.m.bean.Currency;
import com.sskj.bfex.m.bean.bean.Stock;
import com.sskj.bfex.utils.MathUtil;
import com.sskj.bfex.utils.NumberUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @author Hey
 *         created at 2018/4/10 13:23
 */
public class MyInfoMarkerView extends MarkerView {
    Currency type;
    private TextView tv_time, tv_open, tv_close, tv_high, tv_low, tv_volume;
    private String time, price, change, range, volume;
    private List<Stock> mData;
    private Stock entry;
    String goodsType;
    DecimalFormat df = new DecimalFormat("0.00");
    DecimalFormat df1 = new DecimalFormat("0.0000");
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public MyInfoMarkerView(Context context, List<Stock> data, String goodsType, Currency type) {
        super(context, R.layout.chart_marker_info);
        tv_time = findViewById(R.id.marker_info_time);
        tv_open = findViewById(R.id.marker_info_open);
        tv_close = findViewById(R.id.marker_info_close);
        tv_high = findViewById(R.id.marker_info_high);
        tv_low = findViewById(R.id.marker_info_low);
        tv_volume = findViewById(R.id.marker_info_volume);
        this.goodsType = goodsType;
        this.type = type;
        mData = data;
    }


    public void setIndex(int index) {
        entry = mData.get(index);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        if (this.goodsType.equals("day")) {
            if (type == Currency.TIME) {
                tv_time.setText("时间：" + dayFormat.format(entry.getTimestamp() * 1000));
            } else {
                tv_time.setText("时间：" + dayFormat.format(Long.parseLong(entry.getDate())));
            }
        } else {
            if (type == Currency.TIME) {
                tv_time.setText("时间：" + timeFormat.format(entry.getTimestamp() * 1000));
            } else {
                String[] time = entry.getTime().split(":");
                tv_time.setText("时间：" + time[0] + ":" + time[1]);
            }

        }
        tv_open.setText("开盘：" + NumberUtils.format(entry.getOpeningPrice(), "0.0000"));
        tv_close.setText("收盘：" + NumberUtils.format(entry.getClosingPrice(), "0.0000"));
        tv_high.setText("最高：" + NumberUtils.format(entry.getHighestPrice(), "0.0000"));
        tv_low.setText("最低：" + NumberUtils.format(entry.getLowestPrice(), "0.0000"));
//        tv_change.setText("闭盘：" + df1.format((entry.getClosingPrice() - entry.getOpeningPrice())) + "");
//        tv_range.setText("幅度：" + df.format(calculateChangeRate(entry)) + "%");
        tv_volume.setText("成交：" + entry.getVolume());
    }

    public double calculateChangeRate(Stock stock) {
        float price = stock.getClosingPrice();
        float openPrice = stock.getOpeningPrice();
        return MathUtil.divide((price - openPrice), openPrice, 4) * 100;
    }

}
