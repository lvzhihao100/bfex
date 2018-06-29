package com.sskj.bfex.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/3.
 */

public class TransactionModel {


    private List<Item> buy;
    private List<Item> sell;
    private String newprice;

    public List<Item> getBuy() {
        return buy;
    }

    public void setBuy(List<Item> buy) {
        this.buy = buy;
    }

    public List<Item> getSell() {
        return sell;
    }

    public void setSell(List<Item> sell) {
        this.sell = sell;
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }

    public static class Item {

        public Item(String price, String totalSize) {
            this.price = price;
            this.totalSize = totalSize;
        }

        String price;
        String totalSize;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(String totalSize) {
            this.totalSize = totalSize;
        }
    }
}
