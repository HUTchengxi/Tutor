package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程评价实体类
 * @author chengxi
 */
public class CourseCommand implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private String username;
    private Integer repid;
    private String info;
    private Date ctime;
    private Integer score;
    private Integer god;
    private Integer status;

    public Integer getGod() {
        return god;
    }

    public void setGod(Integer god) {
        this.god = god;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CourseCommand(){

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRepid() {
        return repid;
    }

    public void setRepid(Integer repid) {
        this.repid = repid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCtime() {
        return ctime;
    }

    @Override
    public String toString() {
        return "CourseCommand{" +
                "id=" + id +
                ", cid=" + cid +
                ", username='" + username + '\'' +
                ", repid=" + repid +
                ", info='" + info + '\'' +
                ", ctime=" + ctime +
                ", score=" + score +
                ", god=" + god +
                ", status=" + status +
                '}';
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getScore(){return this.score;}

    public void setScore(Integer score){this.score = score;}


}
