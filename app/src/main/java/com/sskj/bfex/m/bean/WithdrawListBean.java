package com.sskj.bfex.m.bean;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 吕志豪 on 2018/04/24 11:04
 * updateName:(吕志豪)
 * updateTime:(11:04)
 * updateDesc:(修改内容)
 */

public class WithdrawListBean {

    /**
     * cash_id : 5
     * txnum : tx152455294371
     * userid : 7
     * account : 384117
     * username : 吕志豪
     * price : 10.0000
     * txfee : 0.1000
     * actual : 10
     * addtime : 1524552943
     * state : 1
     * memo : 提币
     * qiaobao_url : 1C5GRfFBAVp5veetXkz5RdaEVw6R7PVjSS
     * ti_id : null
     * tpath : null
     */

    private String cash_id;
    private String txnum;
    private String userid;
    private String account;
    private String username;
    private String price;
    private String txfee;
    private String actual;
    private String addtime;
    private String check_time;
    private String state;
    private String memo;
    private String qianbao_url;
    private String ti_id;
    private String tpath;

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getCash_id() {
        return cash_id;
    }

    public void setCash_id(String cash_id) {
        this.cash_id = cash_id;
    }

    public String getTxnum() {
        return txnum;
    }

    public void setTxnum(String txnum) {
        this.txnum = txnum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTxfee() {
        return txfee;
    }

    public void setTxfee(String txfee) {
        this.txfee = txfee;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getQianbao_url() {
        return qianbao_url;
    }

    public void setQianbao_url(String qianbao_url) {
        this.qianbao_url = qianbao_url;
    }

    public String getTi_id() {
        return ti_id;
    }

    public void setTi_id(String ti_id) {
        this.ti_id = ti_id;
    }

    public String getTpath() {
        return tpath;
    }

    public void setTpath(String tpath) {
        this.tpath = tpath;
    }
}
