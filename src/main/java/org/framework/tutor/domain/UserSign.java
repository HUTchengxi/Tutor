package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户签到实体类
 * @author chengxi
 */
public class UserSign implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private Date stime;

    public UserSign(){

    }

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
        this.username = username;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    @Override
    public String toString() {
        return "UserSign{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", stime=" + stime +
                '}';
    }
}
