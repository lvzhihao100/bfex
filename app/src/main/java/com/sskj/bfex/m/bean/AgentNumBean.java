package com.sskj.bfex.m.bean;

/**
 * Created by lv on 18-6-12.
 */

public class AgentNumBean {

    /**
     * toDayCommission : 34.23
     * commissionTotal : 34.23
     * lowUsersCount : 2
     */

    private double toDayCommission;
    private double commissionTotal;
    private int lowUsersCount;

    public double getToDayCommission() {
        return toDayCommission;
    }

    public void setToDayCommission(double toDayCommission) {
        this.toDayCommission = toDayCommission;
    }

    public double getCommissionTotal() {
        return commissionTotal;
    }

    public void setCommissionTotal(double commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    public int getLowUsersCount() {
        return lowUsersCount;
    }

    public void setLowUsersCount(int lowUsersCount) {
        this.lowUsersCount = lowUsersCount;
    }
}
