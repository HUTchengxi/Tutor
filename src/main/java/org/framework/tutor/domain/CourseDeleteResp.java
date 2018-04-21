package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

public class CourseDeleteResp implements Serializable {

    private static final Long serialVersionUID  = 1L;

    private Integer id;

    private Integer reqid;

    private Integer status;

    private String response;

    private Date resptime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReqid() {
        return reqid;
    }

    public void setReqid(Integer reqid) {
        this.reqid = reqid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public Date getResptime() {
        return resptime;
    }

    public void setResptime(Date resptime) {
        this.resptime = resptime;
    }

    @Override
    public String toString() {
        return "CourseDeleteResp{" +
                "id=" + id +
                ", reqid=" + reqid +
                ", status=" + status +
                ", response='" + response + '\'' +
                ", resptime=" + resptime +
                '}';
    }
}