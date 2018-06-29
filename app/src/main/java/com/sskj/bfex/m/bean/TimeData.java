package com.sskj.bfex.m.bean;

import java.util.List;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 吕志豪 on 2018/04/19 18:05
 * updateName:(吕志豪)
 * updateTime:(18:05)
 * updateDesc:(修改内容)
 */

public class TimeData {

    private List<TimeDataBean> timeData;

    public List<TimeDataBean> getTimeData() {
        return timeData;
    }

    public void setTimeData(List<TimeDataBean> timeData) {
        this.timeData = timeData;
    }

    public static class TimeDataBean {
        /**
         * pname : BTC/USDT
         * mark : btc_usdt
         * leverage : 50
         * bond_rate : 5%
         * trans_fee : 0.1%
         */

        private String pname;
        private String mark;
        private String leverage;
        private String bond_rate;
        private String trans_fee;
        private int num_min;

        public String getTrans_ware() {
            return trans_ware;
        }

        public void setTrans_ware(String trans_ware) {
            this.trans_ware = trans_ware;
        }

        private String trans_ware;

        public int getNum_min() {
            return num_min;
        }

        public void setNum_min(int num_min) {
            this.num_min = num_min;
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

        public String getLeverage() {
            return leverage;
        }

        public void setLeverage(String leverage) {
            this.leverage = leverage;
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
}
