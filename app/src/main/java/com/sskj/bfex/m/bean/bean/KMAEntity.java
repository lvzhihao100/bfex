package com.sskj.bfex.m.bean.bean;

import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.NaN;

/**
 * Created by loro on 2017/3/7.
 */

public class KMAEntity {
    private List<Float> MAs;

    /**
     * 得到已N日为单位的均值
     *
     * @param kLineBeen
     * @param n         几日均值
     */
    public KMAEntity(List<CandleEntry> kLineBeen, int n) {
        this(kLineBeen, n, NaN);
    }

    /**
     * 得到已N日为单位的均值
     *
     * @param kLineBeen
     * @param n         几日均值
     * @param defult    不足N日时的默认值
     */
    public KMAEntity(List<CandleEntry> kLineBeen, int n, float defult) {
        MAs = new ArrayList<Float>();
        float ma = 0.0f;
        int index = n - 1;
        if (kLineBeen != null && kLineBeen.size() > 0) {
            for (int i = 0; i < kLineBeen.size(); i++) {
                if (i >= index) {
                    ma = getSum(i - index, i, kLineBeen) / n;
                } else {
                    ma = (kLineBeen.get(i).getOpen() + kLineBeen.get(i).getClose()) / 2;
                }
                MAs.add(ma);
            }
        }
    }


    private static float getSum(Integer a, Integer b, List<CandleEntry> datas) {
        float sum = 0;
        for (int i = a; i <= b; i++) {
            sum += datas.get(i).getClose();
        }
        return sum;
    }

    public static float getLastMA(List<CandleEntry> datas, int n) {
        if (null != datas && datas.size() > 0) {
            int count = datas.size() - 1;
            int index = n - 1;
            if (datas.size() >= n) {
                return getSum(count - index, count, datas) / n;
            }
            return NaN;
        }
        return NaN;
    }

    public List<Float> getMAs() {
        return MAs;
    }

}
