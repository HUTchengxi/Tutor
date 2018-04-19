package org.framework.tutor.domain;

import java.io.Serializable;

/**
 *
 * @Description 订购订单管理表
 * @author yinjimin
 * @date 2018/4/18
 */
public class CourseOrderManager implements Serializable {

    private final static Long serialVersionUID = 1L;

    private String code;

    private Integer id;

    private Integer oid;

    private String tutorname;

    public String getTutorname() {
        return tutorname;
    }

    public void setTutorname(String tutorname) {
        this.tutorname = tutorname;
    }

    private Integer tutorstatus;

    private Integer userstatus;

    private String userinfo;

    private String tutorinfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getTutorstatus() {
        return tutorstatus;
    }

    public void setTutorstatus(Integer tutorstatus) {
        this.tutorstatus = tutorstatus;
    }

    public String getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }

    public String getTutorinfo() {
        return tutorinfo;
    }

    public void setTutorinfo(String tutorinfo) {
        this.tutorinfo = tutorinfo;
    }

    public Integer getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(Integer userstatus) {
        this.userstatus = userstatus;
    }
}