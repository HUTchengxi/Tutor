package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description 帖子回答的评论数据实体类
 * @author yinjimin
 * @date 2018/4/10
 */
public class BbsCardAnswerCommand implements Serializable{

    private final static Long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private Integer cardid;

    private Integer aid;

    private Integer floor;

    private Integer repfloor;

    private String comment;

    private Date comtime;

    private Integer gcount;

    private Integer bcount;

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

    public Integer getCardid() {
        return cardid;
    }

    public void setCardid(Integer cardid) {
        this.cardid = cardid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getRepfloor() {
        return repfloor;
    }

    public void setRepfloor(Integer repfloor) {
        this.repfloor = repfloor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getComtime() {
        return comtime;
    }

    public void setComtime(Date comtime) {
        this.comtime = comtime;
    }

    public Integer getGcount() {
        return gcount;
    }

    public void setGcount(Integer gcount) {
        this.gcount = gcount;
    }

    public Integer getBcount() {
        return bcount;
    }

    public void setBcount(Integer bcount) {
        this.bcount = bcount;
    }
}