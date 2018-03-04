package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户-课程订单实体类
 * @author  chengxi
 */
public class CourseOrder implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private String username;
    private Integer state;
    private Date otime;

    public CourseOrder(){

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getOtime() {
        return otime;
    }

    public void setOtime(Date otime) {
        this.otime = otime;
    }

    @Override
    public String toString() {
        return "CourseOrder{" +
                "id=" + id +
                ", cid=" + cid +
                ", username='" + username + '\'' +
                ", state=" + state +
                ", otime=" + otime +
                '}';
    }
}
