package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 版本发布记录实体类
 * @author chengxi
 */
public class PublishLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer typeid;

    private String pversion;

    private Date ptime;

    private String descript;

    public Integer getId(){return id;}

    public void setId(Integer id){this.id = id;}

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getPversion(){return this.pversion;}

    public void setPversion(String pversion){this.pversion = pversion;}

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }
}