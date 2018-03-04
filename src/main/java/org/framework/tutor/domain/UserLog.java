package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录记录状态实体类
 * @author chengxi
 */
public class UserLog implements Serializable{

    private final static Long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private Date logtime;
    private String logcity;
    private String logip;
    private String logsys;

    public UserLog() {
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public Date getLogtime(){
        return logtime;
    }
    public void setLogtime(Date logtime){
        this.logtime = logtime;
    }

    public String getLogcity(){
        return logcity;
    }
    public void setLogcity(String logcity){
        this.logcity = logcity;
    }

    public String getLogip(){
        return logip;
    }
    public void setLogip(String logip){
        this.logip = logip;
    }

    public String getLogsys(){
        return logsys;
    }
    public void setLogsys(String logsys){
        this.logsys = logsys;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", logtime=" + logtime +
                ", logcity='" + logcity + '\'' +
                ", logip='" + logip + '\'' +
                ", logsys='" + logsys + '\'' +
                '}';
    }
}