package com.sskj.bfex.m.bean;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class TradeInfoBean {
    /**
     * keyong_price : 7307078.1603
     * totaldeposit : 0
     * yingkui : 0
     * totalusdt : 7307078.1603
     * pingcang : 0.0000
     * risk : 0
     */
    private String keyong_price;
    private String totaldeposit;
    private String yingkui;
    private String totalusdt;
    private String pingcang;
    private String risk;

    public String getKeyong_price() {
        return keyong_price;
    }

    public void setKeyong_price(String keyong_price) {
        this.keyong_price = keyong_price;
    }

    public String getTotaldeposit() {
        return totaldeposit;
    }

    public void setTotaldeposit(String totaldeposit) {
        this.totaldeposit = totaldeposit;
    }

    public String getYingkui() {
        return yingkui;
    }

    public void setYingkui(String yingkui) {
        this.yingkui = yingkui;
    }

    public String getTotalusdt() {
        return totalusdt;
    }

    public void setTotalusdt(String totalusdt) {
        this.totalusdt = totalusdt;
    }

    public String getPingcang() {
        return pingcang;
    }

    public void setPingcang(String pingcang) {
        this.pingcang = pingcang;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }
}
