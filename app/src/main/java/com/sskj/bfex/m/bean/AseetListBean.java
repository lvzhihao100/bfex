package com.sskj.bfex.m.bean;

import java.util.List;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 吕志豪 on 2018/04/21 18:48
 * updateName:(吕志豪)
 * updateTime:(18:48)
 * updateDesc:(修改内容)
 */

public class AseetListBean {

    /**
     * asset : []
     * wallone : 0.0000
     */

    public String wallone;
    public List<AseetBean> asset;
    public static class AseetBean{
       public String mark;
       public String usable;
       public String frost;
       public String uptime;
       public String pid;
       public String pname;

    }
}
