package org.framework.tutor.service;

public interface BbsCardService {


    /**
     *
     * @Description 获取指定用户的帖子总数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/3/31
     */
    Integer getMyCardCount(String username);
}
