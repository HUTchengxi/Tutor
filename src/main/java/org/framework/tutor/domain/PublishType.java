package org.framework.tutor.domain;

import java.io.Serializable;

/**
 * 发布版本类型实体类
 * @author chengxi
 */
public class PublishType implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

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
}