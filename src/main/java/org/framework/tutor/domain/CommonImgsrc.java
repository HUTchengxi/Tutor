package org.framework.tutor.domain;

import java.io.Serializable;

/**
 *
 * @Description 通用图片数据实体类
 * @author yinjimin
 * @date 2018/4/1
 */
public class CommonImgsrc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private String imgsrc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc == null ? null : imgsrc.trim();
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}