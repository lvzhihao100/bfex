package com.sskj.bfex.m.bean;


/**
 * Created by lv on 18-6-12.
 */

public class CommissionTitleBean<T> {

    /**
     * commissionTotal : null
     * concessionRate : 10.0
     * concessionList : {"pageNum":1,"pageSize":15,"size":0,"startRow":0,"endRow":0,"total":0,"pages":0,"list":[],"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[],"navigateFirstPage":0,"navigateLastPage":0,"firstPage":0,"lastPage":0}
     */

    private double commissionTotal;
    private double concessionRate;
    private PageData<T> concessionList;

    public double getCommissionTotal() {
        return commissionTotal;
    }

    public CommissionTitleBean setCommissionTotal(double commissionTotal) {
        this.commissionTotal = commissionTotal;
        return this;
    }

    public double getConcessionRate() {
        return concessionRate;
    }

    public void setConcessionRate(double concessionRate) {
        this.concessionRate = concessionRate;
    }

    public PageData<T> getConcessionList() {
        return concessionList;
    }

    public void setConcessionList(PageData<T> concessionList) {
        this.concessionList = concessionList;
    }

}
