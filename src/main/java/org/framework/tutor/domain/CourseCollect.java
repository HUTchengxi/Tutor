package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程收藏实体类
 * @author chengxi
 */
public class CourseCollect implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private String cname;
    private String username;
    private Date coltime;
    private String descript;
    private String cimgsrc;

    public CourseCollect() {
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getColtime() {
        return coltime;
    }

    public void setColtime(Date coltime) {
        this.coltime = coltime;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getCimgsrc() {
        return cimgsrc;
    }

    public void setCimgsrc(String cimgsrc) {
        this.cimgsrc = cimgsrc;
    }

    @Override
    public String toString() {
        return "CourseCollect{" +
                "id=" + id +
                ", cid=" + cid +
                ", cname='" + cname + '\'' +
                ", username='" + username + '\'' +
                ", coltime=" + coltime +
                ", descript='" + descript + '\'' +
                ", cimgsrc='" + cimgsrc + '\'' +
                '}';
    }
}
