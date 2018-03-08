package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户通知表实体类
 * @author chengxi
 */
public class UserMessage implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private Integer identity;
    private String suser;
    private String username;
    private String title;
    private String descript;
    private Integer status;
    private Date stime;

    public UserMessage(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getSuser() {
        return suser;
    }

    public void setSuser(String suser) {
        this.suser = suser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public void setStatus(Integer status){this.status = status;}

    public Integer getStatus(){return this.status;}

    @Override
    public String toString() {
        return "UserMessage{" +
                "id=" + id +
                ", identity=" + identity +
                ", suser='" + suser + '\'' +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", text='" + descript + '\'' +
                ", stime=" + stime +
                ", status=" + status +
                '}';
    }
}
