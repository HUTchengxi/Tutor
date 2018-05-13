package org.framework.tutor.service.impl;

import main.java.org.framework.tutor.domain.UserSecret;
import org.framework.tutor.mapper.UserSecretMapper;
import org.framework.tutor.service.UserSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户密保服务层实现类
 * @author chengxi
 */
@Component
public class UserSecretServiceImpl implements UserSecretService {

    @Autowired
    private UserSecretMapper userSecretMapper;

    /**
     * 获取指定用户的密保数据
     * @param username
     * @return
     */
    @Override
    public List<UserSecret> getSecretInfoByUsername(String username) {

        return userSecretMapper.getSecretInfoByUsername(username);
    }

    /**
     * 删除指定用户的密保数据
     * @param username
     * @return
     */
    @Override
    public boolean delUserSecret(String username) {

        return userSecretMapper.delUserSecret(username);
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

        return userSecretMapper.addUserSecret(question, answer, username);
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

        if (userSecretMapper.checkSecret(username, queone, ansone) != null) {
            return true;
        }
        return false;
    }
}
