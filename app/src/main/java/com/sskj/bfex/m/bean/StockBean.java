package com.sskj.bfex.m.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class StockBean implements Serializable {
    /**
     * 是否是抢筹单子
     */
    public boolean isQiangchou=false;
    /**
     * 最新价格
     */
    public String newprice;
    /**
     * 产品名称
     */
    public String shopname;
    /**
     * 产品名称
     */
    public String code;
    /**
     * 手数
     */
    public String buynum;
    /**
     * 1: 市价   ；   2 ： 限价
     */
    public int type;
    /**
     * 1 ：买涨  ；  2 ：买跌
     */
    public int otype;

}
