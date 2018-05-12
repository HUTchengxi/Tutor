package org.framework.tutor.entity;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月20日
 */
public class EmailParam {

    private Integer id;

    private String send;

    private String theme;

    private String email;

    private String formatEmail;

    private Integer emailStatus;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private Integer pageNo;

    private Integer pageSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormatEmail() {
        return formatEmail;
    }

    public void setFormatEmail(String formatEmail) {
        this.formatEmail = formatEmail;
    }

    @Override
    public String toString() {
        return "EmailParam{" +
                "id=" + id +
                ", send='" + send + '\'' +
                ", theme='" + theme + '\'' +
                ", email='" + email + '\'' +
                ", formatEmail='" + formatEmail + '\'' +
                '}';
    }
}
