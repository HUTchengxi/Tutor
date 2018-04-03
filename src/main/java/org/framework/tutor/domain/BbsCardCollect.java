package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Description 用户帖子收藏实体类
 * @author yinjimin
 * @date 2018/4/1
 */
public class BbsCardCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private Integer cardid;

    private Date coltime;

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

    public Date getColtime() {
        return coltime;
    }

    public void setColtime(Date coltime) {
        this.coltime = coltime;
    }
}