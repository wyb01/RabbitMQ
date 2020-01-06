package com.itlaoqi.rabbit.exchange;

import java.io.Serializable;
import java.util.Date;

/**
 * description: 新闻
 * @author: wyb
 * @createTime: 2020-01-05 13:29:24
 */
public class News implements Serializable{

    private String source;   //新闻来源
    private String title;
    private Date createTime;
    private String content;

    public News(String source, String title, Date createTime, String content) {
        this.source = source;
        this.title = title;
        this.createTime = createTime;
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
