package com.sskj.bfex.m.bean;

import java.io.Serializable;

/**
*
*抢筹币
*create by Hey at 2018/4/17 16:35
*/
public class Raise implements Serializable {

        /**
         * pname : 虚拟现实
         * mark : MR
         * sellnum : 0.00
         * retnum : 1000
         * cirnum : 1000000
         * price : 1000.00
         * add_time : 1523412660
         * end_time : 1523413200
         * state : 1
         * per : 0.1%
         */

        private String pname;
        private String mark;
        private String sellnum;
        private String retnum;
        private String cirnum;
        private String price;
        private long add_time;
        private long end_time;
        private int state;
        private String per;

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

    public String getSellnum() {
        return sellnum;
    }

    public void setSellnum(String sellnum) {
        this.sellnum = sellnum;
    }

    public String getRetnum() {
        return retnum;
    }

    public void setRetnum(String retnum) {
        this.retnum = retnum;
    }

    public String getCirnum() {
        return cirnum;
    }

    public void setCirnum(String cirnum) {
        this.cirnum = cirnum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getAdd_time() {
        return add_time*1000;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public long getEnd_time() {
        return end_time*1000;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }
}
