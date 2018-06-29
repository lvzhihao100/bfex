package com.sskj.bfex.m.bean;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 吕志豪 on 2018/04/25 16:06
 * updateName:(吕志豪)
 * updateTime:(16:06)
 * updateDesc:(修改内容)
 */

public class LevelBean {
    String code;
    String leverage;
    String num_min;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public String getNum_min() {
        return num_min;
    }

    public void setNum_min(String num_min) {
        this.num_min = num_min;
    }
}
