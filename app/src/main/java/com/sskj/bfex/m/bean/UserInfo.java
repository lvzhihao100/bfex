package com.sskj.bfex.m.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class UserInfo  implements Serializable {


    /**
     * account : 384117
     * idcard : 412824199203124753
     * mail :
     * mobile : 16603840418
     * realname : 吕志豪
     * reg_time : 1524315309
     * status : 3
     * wallone : 1009989.9000
     * tgno : TG7539035134251
     * tpwd : 1
     * crowd_num : 0
     * qd_status : 1
     * qd : 1
     * tb_fee : 0.1
     * tb_max : 10000
     * tb_min : 10
     */

    public String account;
    public String idcard;
    public String mail;
    public String mobile;
    public String realname;
    public String reg_time;
    public int status;
    public String wallone;
    public String tgno;
    public String tpwd;
    public int crowd_num;
    public int qd_status;
    public int qd;
    public String tb_fee;
    public String tb_max;
    public String tb_min;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWallone() {
        return wallone;
    }

    public void setWallone(String wallone) {
        this.wallone = wallone;
    }

    public String getTgno() {
        return tgno;
    }

    public void setTgno(String tgno) {
        this.tgno = tgno;
    }

    public String getTpwd() {
        return tpwd;
    }

    public void setTpwd(String tpwd) {
        this.tpwd = tpwd;
    }

    public int getCrowd_num() {
        return crowd_num;
    }

    public void setCrowd_num(int crowd_num) {
        this.crowd_num = crowd_num;
    }

    public int getQd_status() {
        return qd_status;
    }

    public void setQd_status(int qd_status) {
        this.qd_status = qd_status;
    }

    public int getQd() {
        return qd;
    }

    public void setQd(int qd) {
        this.qd = qd;
    }

    public String getTb_fee() {
        return tb_fee;
    }

    public void setTb_fee(String tb_fee) {
        this.tb_fee = tb_fee;
    }

    public String getTb_max() {
        return tb_max;
    }

    public void setTb_max(String tb_max) {
        this.tb_max = tb_max;
    }

    public String getTb_min() {
        return tb_min;
    }

    public void setTb_min(String tb_min) {
        this.tb_min = tb_min;
    }
}
