package com.sskj.bfex.m.bean;

/**
 * Created by Administrator on 2018/5/3.
 */

public class Entrust {


    /**
     * orders_id : 7
     * member_id : 10
     * account : 3841110
     * currency_id : 3
     * pname : ETH/USDT
     * wtprice : 757.38
     * cjprice : 0.0000
     * wtnum : 100.0000
     * cjnum : 0.0000
     * totalprice : 75.0000
     * fee : 0.0750
     * type : 2
     * add_time : 1525864207
     * trade_time : 0
     * status : 0
     * otype : 1
     * name : ETH/USDT
     */

    private String orders_id;
    private String member_id;
    private String account;
    private String currency_id;
    private String pname;
    private String wtprice;
    private String cjprice;
    private String wtnum;
    private String cjnum;
    private String totalprice;
    private String fee;
    private int type;
    private String add_time;
    private String trade_time;
    private int status;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOtype() {
        return otype;
    }

    public void setOtype(int otype) {
        this.otype = otype;
    }

    private int otype;
    private String name;

    public String getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getWtprice() {
        return wtprice;
    }

    public void setWtprice(String wtprice) {
        this.wtprice = wtprice;
    }

    public String getCjprice() {
        return cjprice;
    }

    public void setCjprice(String cjprice) {
        this.cjprice = cjprice;
    }

    public String getWtnum() {
        return wtnum;
    }

    public void setWtnum(String wtnum) {
        this.wtnum = wtnum;
    }

    public String getCjnum() {
        return cjnum;
    }

    public void setCjnum(String cjnum) {
        this.cjnum = cjnum;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
