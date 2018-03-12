package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 注册验证实体类
 * @author chengxi
 */
public class UserVali implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private Integer id;

    private String valicode;

    private Date regtime;

    private Integer status;

    private Integer resend;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValicode() {
        return valicode;
    }

    public void setValicode(String valicode) {
        this.valicode = valicode;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResend() {
        return resend;
    }

    public void setResend(Integer resend) {
        this.resend = resend;
    }

    @Override
    public String toString() {
        return "UserVali{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", valicode=" + valicode +
                ", regtime=" + regtime +
                ", status=" + status +
                ", resend=" + resend +
                '}';
    }
}