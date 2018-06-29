package com.sskj.bfex.m.bean.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class OrderList extends BaseBean {

    public List<RestBean> rest;

    public static class RestBean {
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
        public String selltime;
        public String dayfee;
        public String tpath;

    }
}
