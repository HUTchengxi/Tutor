package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserVali;
import org.framework.tutor.mapper.UserValiMapper;
import org.framework.tutor.service.UserValiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注册验证服务层实现类
 * @author chengxi
 */
@Component
public class UserValiServiceImpl implements UserValiService {

    @Autowired
    private UserValiMapper userValiMapper;

    /**
     * 添加注册队列
     * @param username
     * @param valicode
     * @param status
     */
    @Override
    public void addUserVali(String username, String valicode, Integer status) {

        userValiMapper.addUserVali(username, valicode, status);
    }

    /**
     * 获取指定用户的验证码
     * @param username
     * @return
     */
    @Override
    public String getCodeByUsername(String username) {

        return userValiMapper.getCodeByUsername(username);
    }

    /**
     * 清除指定用户的为验证码
     * @param username
     */
    @Override
    public void delStatus(String username) {

        userValiMapper.delStatus(username);
    }

    /**
     * 清空当天失效的验证数据
     * @param now
     */
    @Override
    public void checkAll(String now) {

        userValiMapper.checkAll(now);
    }

    /**
     * 判断当前邮箱是否已经被验证
     * @param email
     * @return
     */
    @Override
    public UserVali checkEmailStatus(String email) {

        return userValiMapper.checkEmailStatus(email);
    }

    /**
     * 更新邮箱注册码
     * @param email
     * @param valicode
     */
    @Override
    public void updateEmailCode(String email, String valicode) {

        userValiMapper.updateEmailCode(email, valicode);
    }
}
