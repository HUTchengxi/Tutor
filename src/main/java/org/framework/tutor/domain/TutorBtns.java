package org.framework.tutor.domain;

import java.io.Serializable;

/**
 * 指定家教常用链接实体类
 * @author chengxi
 */
public class TutorBtns implements Serializable{

    private static final long serialVerionUID = 1L;

    private Integer id;

    private String tname;

    private Integer bid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }
}