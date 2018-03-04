package org.framework.tutor.domain;

import java.io.Serializable;

/**
 * 家教老师评价回复实体类
 * @author chengxi
 */
public class CourseTreply implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private Integer cmid;
    private String tname;
    private String info;

    public CourseTreply(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCmid() {
        return cmid;
    }

    public void setCmid(Integer cmid) {
        this.cmid = cmid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CourseTreply{" +
                "id=" + id +
                ", cid=" + cid +
                ", cmid=" + cmid +
                ", tname='" + tname + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
