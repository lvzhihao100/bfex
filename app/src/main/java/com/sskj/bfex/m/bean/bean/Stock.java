package com.sskj.bfex.m.bean.bean;

/**
 * Created by Administrator on 2018/4/3.
 */

public class Stock {
    /**
     * id : 3212141
     * period : null
     * code : eth_usdt
     * volume : 46.0054
     * openingPrice : 399.7658
     * closingPrice : 400.4279
     * highestPrice : 401.0494
     * lowestPrice : 399.7658
     * date : 1523289600000
     * time : 13:43:00
     * createTime : null
     * status : 0
     * timestamp : 1523339027000
     */

    private int id;
    private String period;
    private String code;
    private String volume;
    private String openingPrice;
    private String closingPrice;
    private String highestPrice;
    private String lowestPrice;
    private String date;
    private String time;
    private String createTime;
    private int status;
    private long timestamp;

    //交易币
    private String pid;
    private String name;
    private String dateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getVolume() {
        return parse(volume);
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Float getOpeningPrice() {
        return parse(openingPrice);
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public Float getClosingPrice() {
        return parse(closingPrice);
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Float getHighestPrice() {
        return parse(highestPrice);
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Float getLowestPrice() {
        return parse(lowestPrice);
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    private Float parse(String s) {
        return Float.valueOf(s);
    }
}
