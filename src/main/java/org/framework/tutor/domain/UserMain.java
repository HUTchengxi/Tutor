package org.framework.tutor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表实体类
 * @author chengxi
 */
public class UserMain implements Serializable{

    private final static long serialVersionUID = 1L;
    private Integer id;
    private Integer identity;
    private String username;
    private String password;
    private String nickname;
    private Integer sex;
    private Integer age;
    private String telephone;
    private String email;
    private String info;
    private String imgsrc;
    private Date regtime;

    public UserMain(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    @Override
    public String toString() {
        return "UserMain{" +
                "id=" + id +
                ", identity=" + identity +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", info='" + info + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", regtime=" + regtime +
                '}';
    }
}
