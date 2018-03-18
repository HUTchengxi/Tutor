package org.framework.tutor.service;

import java.util.List;

/**
 * 用户密保服务层接口
 * @author chengxi
 */
public interface UserSCService {

    /**
     * 获取指定用户的密保数据
     * @param username
     * @return
     */
    List<main.java.org.framework.tutor.domain.UserSecret> getSecretInfoByUsername(String username);

    /**
     * 删除指定用户的密保数据
     * @param username
     * @return
     */
    boolean delUserSecret(String username);

    /**
     * 为指定用户添加密保数据
     * @param question
     * @param answer
     * @param username
     * @return
     */
    Integer addUserSecret(String question, String answer, String username);

    /**
     * 校验密保答案是否正确
     * @param username
     * @param queone
     * @param ansone
     * @return
     */
    boolean checkSecret(String username, String queone, String ansone);
}
