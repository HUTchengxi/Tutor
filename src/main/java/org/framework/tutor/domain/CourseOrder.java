package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户订单实体类
 * @author chengxi
 */
public class CourseOrder implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer cid;

    private Integer state;

    private Date otime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid(){return this.cid;}

    public void setCid(Integer cid){this.cid = cid;}

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getOtime() {
        return otime;
    }

    public void setOtime(Date otime) {
        this.otime = otime;
    }
}