package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description 帖子答案实体类
 * @author yinjimin
 * @date 2018/4/5
 */
public class BbsCardAnswer implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;

    private Integer cardid;

    private String username;

    private Date crtime;

    private Integer gcount;

    private Integer bcount;

    private Integer comcount;

    private String answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardid() {
        return cardid;
    }

    public void setCardid(Integer cardid) {
        this.cardid = cardid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
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

    public Integer getComcount(){return comcount;}

    public void setComcount(Integer comcount){this.comcount = comcount;}

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}