package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description 课程下线申请
 * @author yinjimin
 * @date 2018/4/15
 */
public class CourseDeleteReq implements Serializable{

    private final static Long serailVersionUID = 1L;

    private Integer id;

    private String username;

    private Integer cid;

    private Date reqtime;

    private String descript;

    public Integer getReqcount() {
        return reqcount;
    }

    public void setReqcount(Integer reqcount) {
        this.reqcount = reqcount;
    }

    private Integer reqcount;

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

    public Date getReqtime() {
        return reqtime;
    }

    public void setReqtime(Date reqtime) {
        this.reqtime = reqtime;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }
}