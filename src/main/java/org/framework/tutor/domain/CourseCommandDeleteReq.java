package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description 评论删除待审
 * @author yinjimin
 * @date 2018/4/18
 */
public class CourseCommandDeleteReq implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;

    private String reqer;

    private Integer cid;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;

    private Date reqtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReqer() {
        return reqer;
    }

    public void setReqer(String reqer) {
        this.reqer = reqer == null ? null : reqer.trim();
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
}