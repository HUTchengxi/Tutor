package org.framework.tutor.domain;

import java.io.Serializable;

/**
 * 所有家教常用链接实体类
 * @author chengxi
 */
public class TutorsysBtns implements Serializable{

    private final static long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String url;

    private Integer ord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }
}