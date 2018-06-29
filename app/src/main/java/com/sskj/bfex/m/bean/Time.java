package com.sskj.bfex.m.bean;

import java.io.Serializable;

/**
 * 交易币
 * create by Hey at 2018/4/17 16:35
 */
public class Time implements Serializable {

    /**
     * pid : 6
     * pname : 大健康
     * mark : CAR-T
     * price : 22.0181
     * leverage : 50
     * fxtime : 2018-04-18
     * fxnum : 0.00
     * fxprice : 0.00
     * fxweb : null
     * fxbook : null
     * memo : null
     * openPrice : 20.8373
     * closePrice : 20.2214
     * high : 21.4981
     * low : 19.5132
     * volume : 1872067.66
     * change : -0.6159
     * changePercentage : -2.96%
     * bond_rate : 5%
     * trans_fee : 0.1%
     */

    private String pid;
    private String pname;
    private String mark;
    private String price;
    private String buy;
    private String sell;
    private String leverage;
    private String fxtime;
    private String fxnum;
    private String fxprice;
    private String fxweb;
    private String fxbook;
    private String memo;
    private String openPrice;
    private String closePrice;
    private String high;
    private String low;
    private String volume;
    private double change;
    private String changePercentage;
    private String bond_rate;
    private String trans_fee;
    private int num_min;

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

    public int getNum_min() {
        return num_min;
    }

    public void setNum_min(int num_min) {
        this.num_min = num_min;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public String getFxtime() {
        return fxtime;
    }

    public void setFxtime(String fxtime) {
        this.fxtime = fxtime;
    }

    public String getFxnum() {
        return fxnum;
    }

    public void setFxnum(String fxnum) {
        this.fxnum = fxnum;
    }

    public String getFxprice() {
        return fxprice;
    }

    public void setFxprice(String fxprice) {
        this.fxprice = fxprice;
    }

    public String getFxweb() {
        return fxweb;
    }

    public void setFxweb(String fxweb) {
        this.fxweb = fxweb;
    }

    public String getFxbook() {
        return fxbook;
    }

    public void setFxbook(String fxbook) {
        this.fxbook = fxbook;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getBond_rate() {
        return bond_rate;
    }

    public void setBond_rate(String bond_rate) {
        this.bond_rate = bond_rate;
    }

    public String getTrans_fee() {
        return trans_fee;
    }

    public void setTrans_fee(String trans_fee) {
        this.trans_fee = trans_fee;
    }
}
