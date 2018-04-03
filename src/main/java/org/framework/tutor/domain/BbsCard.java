package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description 论坛帖子实体类
 * @author yinjimin
 * @date 2018/3/31
 */
public class BbsCard implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String title;

    private String descript;

    private String imgsrc;

    private Date crttime;

    private Integer viscount;

    private Integer comcount;

    private Integer colcount;

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
        this.username = username == null ? null : username.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc == null ? null : imgsrc.trim();
    }

    public Date getCrttime() {
        return crttime;
    }

    public void setCrttime(Date crttime) {
        this.crttime = crttime;
    }

    public Integer getViscount() {
        return viscount;
    }

    public void setViscount(Integer viscount) {
        this.viscount = viscount;
    }

    public Integer getComcount() {
        return comcount;
    }

    public void setComcount(Integer comcount) {
        this.comcount = comcount;
    }

    public Integer getColcount() {
        return colcount;
    }

    public void setColcount(Integer colcount) {
        this.colcount = colcount;
    }
}