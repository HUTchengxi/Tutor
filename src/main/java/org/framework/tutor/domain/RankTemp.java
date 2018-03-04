package org.framework.tutor.domain;

/**
 * 排行榜单临时用实体类
 * @author chengxi
 */
public class RankTemp {


    private String username;
    private Integer total;

    public RankTemp(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SignTemp{" +
                "username='" + username + '\'' +
                ", total=" + total +
                '}';
    }
}
