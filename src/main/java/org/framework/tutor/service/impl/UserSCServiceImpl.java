package org.framework.tutor.service.impl;

import main.java.org.framework.tutor.domain.UserSecret;
import org.framework.tutor.mapper.UserSCMapper;
import org.framework.tutor.service.UserSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户密保服务层实现类
 * @author chengxi
 */
@Component
public class UserSCServiceImpl implements UserSCService {

    @Autowired
    private UserSCMapper userSCMapper;

    /**
     * 获取指定用户的密保数据
     * @param username
     * @return
     */
    @Override
    public List<UserSecret> getSecretInfoByUsername(String username) {

        return userSCMapper.getSecretInfoByUsername(username);
    }

    /**
     * 删除指定用户的密保数据
     * @param username
     * @return
     */
    @Override
    public boolean delUserSecret(String username) {

        return userSCMapper.delUserSecret(username);
    }

    /**
     * 为指定用户添加密保数据
     * @param question
     * @param answer
     * @param username
     * @return
     */
    @Override
    public Integer addUserSecret(String question, String answer, String username) {

        return userSCMapper.addUserSecret(question, answer, username);
    }

    /**
     * 校验用户名的密保答案是否正确
     * @param username
     * @param queone
     * @param ansone
     * @return
     */
    @Override
    public boolean checkSecret(String username, String queone, String ansone) {

        if (userSCMapper.checkSecret(username, queone, ansone) != null) {
            return true;
        }
        return false;
    }
}
