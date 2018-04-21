/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.entity;

import java.util.Date;

/**
 * @author yinjimin
 * @Description: bootstrap-table传递参数封装类
 * @date 2018年04月18日
 */
public class ParamMap {

    private Integer pageNo;

    private Integer pageSize;

    private String courseName;

    private Date startTime;

    private String username;

    private String tutorName;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public ParamMap(){

    }

    @Override
    public String toString() {
        return "ParamMap{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", courseName='" + courseName + '\'' +
                ", startTime=" + startTime +
                ", username='" + username + '\'' +
                ", tutorName='" + tutorName + '\'' +
                '}';
    }
}
