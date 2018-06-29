package com.sskj.bfex.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class NoticeList {

    /**
     * id : 4
     * title : 26日晚上10:00至27日早上6:00进行系统维护
     * create_time : ["2018-04-10","13:56"]
     */

    private String id;
    private String title;
    private List<String> create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCreate_time() {
        return create_time;
    }

    public void setCreate_time(List<String> create_time) {
        this.create_time = create_time;
    }
}
