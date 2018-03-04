package org.framework.tutor.domain;

import java.io.Serializable;

/**
 * 课程章节目录实体类
 * @author chengxi
 */
public class CourseChapter implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private Integer cid;
    private Integer ord;
    private String title;
    private String descript;

    public CourseChapter(){

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

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    @Override
    public String toString() {
        return "CourseChapter{" +
                "id=" + id +
                ", cid=" + cid +
                ", ord=" + ord +
                ", title='" + title + '\'' +
                ", descript='" + descript + '\'' +
                '}';
    }
}
