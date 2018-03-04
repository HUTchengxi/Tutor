package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程记录表实体类
 * @author chengxi
 */
public class CourseLog implements Serializable {

    private final static Long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private String cname;
    private String ctype;
    private Date logtime;

    public CourseLog(){

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

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    @Override
    public String toString() {
        return "CourseLog{" +
                "id=" + id +
                ", cid=" + cid +
                ", cname='" + cname + '\'' +
                ", ctype='" + ctype + '\'' +
                ", logtime='" + logtime + '\'' +
                '}';
    }
}
