package com.sskj.bfex.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class TouTiao {

    /**
     * title : 外媒：中国华信计划剥离非核心资产 估值最高达437亿
     * time : ["2018-04-12","19:25"]
     * src :
     * category : finance
     * pic :
     * content : <p class="art_p">据彭博报道，据不愿具名的知情人士称，按照目前的处置方案，中国华信能源有限公司的能源、油气相关业务之外的非核心资产将被剥离。</p><p class="art_p">该名人士表示......
     * url : http://finance.sina.cn/chanjing/gsxw/2018-04-03/detail-ifysuuxz9458592.d.html?cre=tianyi&mod=wfin&loc=12&r=25&doct=0&rfunc=100&tj=none&tr=25
     * weburl : http://finance.sina.com.cn/chanjing/gsnews/2018-04-03/doc-ifysuuxz9458592.shtml
     */

    private String title;
    private String src;
    private String category;
    private String pic;
    private String content;
    private String url;
    private String weburl;
    private List<String> time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
