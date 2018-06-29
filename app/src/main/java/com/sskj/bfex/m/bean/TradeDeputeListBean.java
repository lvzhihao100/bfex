package com.sskj.bfex.m.bean;

import android.text.TextUtils;

import com.sskj.bfex.utils.DateUtil;

import java.util.Date;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TradeDeputeListBean {

    /**
     * en_id : 38
     * en_no : 56152284307317
     * userid : 1
     * account : 38411
     * username : yazl
     * pid : 4
     * pname : ETC/USDT
     * buyprice : 14.3782
     * buynum : 3
     * price : 0.7189
     * totalprice : 2.1567
     * otype : 2
     * addtime : 1522843073
     * sxfee : 0.0022
     * dealtime : null
     * state : 1
     * deposit : 2.1567
     * tpath : 1,2
     */

    private String en_id;
    private String en_no;
    private String userid;
    private String account;
    private String username;
    private String pid;
    private String pname;
    private String buyprice;
    private String buynum;
    private String price;
    private String totalprice;
    private String otype;
    private long addtime;
    private String sxfee;
    private long dealtime;
    private String state;
    private String deposit;
    private String tpath;

    public String getEn_id() {
        return en_id;
    }

    public void setEn_id(String en_id) {
        this.en_id = en_id;
    }

    public String getEn_no() {
        return en_no;
    }

    public void setEn_no(String en_no) {
        this.en_no = en_no;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return TextUtils.isEmpty(pname)?"--/--":pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(String buyprice) {
        this.buyprice = buyprice;
    }

    public String getBuynum() {
        return buynum;
    }

    public void setBuynum(String buynum) {
        this.buynum = buynum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getAddtime() {
        return  DateUtil.getDateFormat(new Date(addtime*1000),DateUtil.PATTERN_H);
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getSxfee() {
        return sxfee;
    }

    public void setSxfee(String sxfee) {
        this.sxfee = sxfee;
    }

    public String getDealtime() {
        return DateUtil.getDateFormat(new Date(dealtime*1000),DateUtil.PATTERN_H);
    }

    public void setDealtime(long dealtime) {
        this.dealtime = dealtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getTpath() {
        return tpath;
    }

    public void setTpath(String tpath) {
        this.tpath = tpath;
    }
}
