package com.sskj.bfex.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class PublicData {

    /**
     * qrc : /Uploads/temp/2018/04-19/5ad8840bbcac047404.jpg
     * dh : 17739251253
     * qq : 2632471567
     * wx : wulianlian8
     * slide : ["/Uploads/slide/2018/04-20/5ad998cc6781987246.jpg","/Uploads/slide/2018/04-20/5ad998cfb918732424.jpg","/Uploads/slide/2018/04-20/5ad998d1c034391831.jpg"]
     * logo : /Uploads/logo/2018/04-19/5ad884a508ed613665.png
     * email : wslywgs@163.com
     * tpgg : ×××市×××名单
     张三  李四  王五
     联系电话 1234567890
     电话 9876543210
     */

    private String qrc;
    private String dh;
    private String qq;
    private String wx;
    private String logo;
    private String email;
    private String tpgg;
    private List<String> slide;

    public String getQrc() {
        return qrc;
    }

    public void setQrc(String qrc) {
        this.qrc = qrc;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTpgg() {
        return tpgg;
    }

    public void setTpgg(String tpgg) {
        this.tpgg = tpgg;
    }

    public List<String> getSlide() {
        return slide;
    }

    public void setSlide(List<String> slide) {
        this.slide = slide;
    }
}
