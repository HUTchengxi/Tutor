package org.framework.tutor.domain;

import java.io.Serializable;

/**
 *
 * @Description 课程概述
 * @author yinjimin
 * @date 2018/4/15
 */
public class CourseSummary implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private Integer cid;

    private String title;

    private String descript;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }
}