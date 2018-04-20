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

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月20日
 */
public class EmailParam {

    private Integer id;

    private String send;

    private String theme;

    private String email;

    private String formatEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormatEmail() {
        return formatEmail;
    }

    public void setFormatEmail(String formatEmail) {
        this.formatEmail = formatEmail;
    }

    @Override
    public String toString() {
        return "EmailParam{" +
                "id=" + id +
                ", send='" + send + '\'' +
                ", theme='" + theme + '\'' +
                ", email='" + email + '\'' +
                ", formatEmail='" + formatEmail + '\'' +
                '}';
    }
}
