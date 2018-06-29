package com.sskj.bfex.m.bean;

/**
*
*交易币明细
*create by Hey at 2018/4/17 16:34
*/
public class TimeDetail {

    /**
     * code : CAR-T
     * name : 大健康
     * openPrice : 20.8373
     * closePrice : 20.2214
     * high : 21.4981
     * low : 19.5132
     * volume : 1872067.66
     * change : -0.6159
     * changePercentage : -2.96%
     */

    private String code;
    private String name;
    private String openPrice;
    private String closePrice;
    private String high;
    private String low;
    private String volume;
    private double change;
    private String changePercentage;
    private String buy;
    private String sell;

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(String changePercentage) {
        this.changePercentage = changePercentage;
    }
}
