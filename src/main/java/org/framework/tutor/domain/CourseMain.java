package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程实体类
 * @author chengxi
 */
public class CourseMain implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String name;
    private String imgsrc;
    private Integer stype;
    private String ctype;
    private Integer jcount;
    private Integer hcount;
    private Integer ccount;
    private String descript;
    private Double price;
    private Date ptime;
    private Date stime;
    private Integer total;

    public CourseMain() {

    }

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
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public Integer getJcount() {
        return jcount;
    }

    public void setJcount(Integer jcount) {
        this.jcount = jcount;
    }

    public Integer getHcount() {
        return hcount;
    }

    public void setHcount(Integer hcount) {
        this.hcount = hcount;
    }

    public Integer getCcount() {
        return ccount;
    }

    public void setCcount(Integer ccount) {
        this.ccount = ccount;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Integer getTotal(){return this.total;}

    public void setTotal(Integer total){this.total = total;}

    @Override
    public String toString() {
        return "CourseMain{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", stype=" + stype +
                ", ctype='" + ctype + '\'' +
                ", jcount=" + jcount +
                ", hcount=" + hcount +
                ", ccount=" + ccount +
                ", descript='" + descript + '\'' +
                ", price=" + price +
                ", ptime=" + ptime +
                ", stime=" + stime +
                ", total=" + total +
                '}';
    }
}
