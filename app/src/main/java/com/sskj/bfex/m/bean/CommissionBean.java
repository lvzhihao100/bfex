package com.sskj.bfex.m.bean;

/**
 * Created by lv on 18-6-12.
 */

public class CommissionBean {

    /**
     * id : 9
     * stockUserId : 4
     * stockUserUpId : 1
     * userId : 3
     * yyId : 2
     * entrustNum : 15287091174262I3w4yA
     * stockCode : 00001
     * stockName : 长和
     * entrustCount : 2000
     * handNum : 4
     * username : 张卫星
     * account : 13015582378
     * userReturnFee : 177.87
     * userReturnRatio : 20.0
     * yyReturnFee : 88.94
     * yyReturnRatio : 30.0
     * usdFee : 889.35
     * createTime : 2018-06-11 17:25:17
     * timestamp : 1528709117000
     */

    private int id;
    private int stockUserId;
    private int stockUserUpId;
    private int userId;
    private int yyId;
    private String entrustNum;
    private String stockCode;
    private String stockName;
    private int entrustCount;
    private int handNum;
    private String username;
    private String account;
    private double userReturnFee;
    private double userReturnRatio;
    private double yyReturnFee;
    private double yyReturnRatio;
    private double usdFee;
    private String createTime;
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockUserId() {
        return stockUserId;
    }

    public void setStockUserId(int stockUserId) {
        this.stockUserId = stockUserId;
    }

    public int getStockUserUpId() {
        return stockUserUpId;
    }

    public void setStockUserUpId(int stockUserUpId) {
        this.stockUserUpId = stockUserUpId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getYyId() {
        return yyId;
    }

    public void setYyId(int yyId) {
        this.yyId = yyId;
    }

    public String getEntrustNum() {
        return entrustNum;
    }

    public void setEntrustNum(String entrustNum) {
        this.entrustNum = entrustNum;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getEntrustCount() {
        return entrustCount;
    }

    public void setEntrustCount(int entrustCount) {
        this.entrustCount = entrustCount;
    }

    public int getHandNum() {
        return handNum;
    }

    public void setHandNum(int handNum) {
        this.handNum = handNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getUserReturnFee() {
        return userReturnFee;
    }

    public void setUserReturnFee(double userReturnFee) {
        this.userReturnFee = userReturnFee;
    }

    public double getUserReturnRatio() {
        return userReturnRatio;
    }

    public void setUserReturnRatio(double userReturnRatio) {
        this.userReturnRatio = userReturnRatio;
    }

    public double getYyReturnFee() {
        return yyReturnFee;
    }

    public void setYyReturnFee(double yyReturnFee) {
        this.yyReturnFee = yyReturnFee;
    }

    public double getYyReturnRatio() {
        return yyReturnRatio;
    }

    public void setYyReturnRatio(double yyReturnRatio) {
        this.yyReturnRatio = yyReturnRatio;
    }

    public double getUsdFee() {
        return usdFee;
    }

    public void setUsdFee(double usdFee) {
        this.usdFee = usdFee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
