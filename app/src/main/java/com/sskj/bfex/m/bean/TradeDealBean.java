package com.sskj.bfex.m.bean;

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

public class TradeDealBean {


        /**
         * tran_id : 2
         * tran_no : 79152280707911
         * userid : 1
         * account : 38411
         * username : yazl
         * pid : 3
         * pname : ETH/USDT
         * buyprice : 388.5418
         * buynum : 2
         * price : 19.4271
         * totalprice : 38.8542
         * otype : 1
         * addtime : 1522807079
         * stopwin : 0.00
         * stoploss : 0.00
         * sellprice : 388.5418
         * profit : 0.0000
         * sxfee : 0.0389
         * selltime : 1522824641
         * dayfee : 0.0000
         * tpath : 1,2
         */

        public String tran_id;
        public String tran_no;
        public String userid;
        public String account;
        public String username;
        public String pid;
        public String pname;
        public String buyprice;
        public String buynum;
        public String price;
        public String totalprice;
        public int otype;
        public long addtime;
        public String stopwin;
        public String stoploss;
        public String sellprice;
        public String profit;
        public String sxfee;
        public long selltime;
        public String dayfee;
        public String tpath;



        public String getTran_id() {
            return tran_id;
        }

        public void setTran_id(String tran_id) {
            this.tran_id = tran_id;
        }

        public String getTran_no() {
            return tran_no;
        }

        public void setTran_no(String tran_no) {
            this.tran_no = tran_no;
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
            return pname;
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

        public int getOtype() {
            return otype;
        }

        public void setOtype(int otype) {
            this.otype = otype;
        }

        public String getAddtime() {
            return DateUtil.getDateFormat(new Date(addtime * 1000), DateUtil.PATTERN_H);
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public String getStopwin() {
            return stopwin;
        }

        public void setStopwin(String stopwin) {
            this.stopwin = stopwin;
        }

        public String getStoploss() {
            return stoploss;
        }

        public void setStoploss(String stoploss) {
            this.stoploss = stoploss;
        }

        public String getSellprice() {
            return sellprice;
        }

        public void setSellprice(String sellprice) {
            this.sellprice = sellprice;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public String getSxfee() {
            return sxfee;
        }

        public void setSxfee(String sxfee) {
            this.sxfee = sxfee;
        }

        public String getSelltime() {
            return DateUtil.getDateFormat(new Date(selltime * 1000), DateUtil.PATTERN_H);
        }

        public void setSelltime(long selltime) {
            this.selltime = selltime;
        }

        public String getDayfee() {
            return dayfee;
        }

        public void setDayfee(String dayfee) {
            this.dayfee = dayfee;
        }

        public String getTpath() {
            return tpath;
        }

        public void setTpath(String tpath) {
            this.tpath = tpath;
        }

}
