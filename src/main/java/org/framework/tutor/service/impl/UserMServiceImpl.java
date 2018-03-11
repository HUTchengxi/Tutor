package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserMain;
import org.framework.tutor.mapper.UserMMapper;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户主表服务层实现类
 * @author chengxi
 */
@Component
public class UserMServiceImpl implements UserMService {

    @Autowired
    private UserMMapper userMMapper;

    /**
     * 判断指定用户名是否存在
     * @param username
     * @return
     */
    @Override
    public boolean userExist(String username) {

        if(userMMapper.getByUsername(username) == null){
            return false;
        }
        return true;
    }

    /**
     * 判断用户名和密码是否对应
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean passCheck(String username, String password) {

        if(userMMapper.getByUserPass(username, password) == null){
            return false;
        }
        return true;
    }

    /**
     * 根据用户名获取并返回对应的UserMain
     * @param username
     * @return
     */
    @Override
    public UserMain getByUser(String username) {

        return userMMapper.getByUsername(username);
    }

    /**
     * 判断昵称是否已经被使用过
     * @param nickname
     * @return
     */
    @Override
    public boolean NickExist(String nickname) {
        return userMMapper.getByNickname(nickname) != null;
    }

    /**
     * 暂不验证的方式进行注册
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @return
     */
    @Override
    public boolean registerNoCheck(Integer identity, String username, String password, String nickname) {

        if(userMMapper.addUser(identity, username, password, nickname) == 1)
            return true;
        return false;
    }

    /**
     * 更换指定用户的头像
     * @param username
     * @param imgsrc
     * @return
     */
    @Override
    public boolean modImgsrcByUser(String username, String imgsrc) {

        return userMMapper.modImgsrcByUser(username, imgsrc);
    }

    /**
     * 更改用户的个人信息
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     * @return
     */
    @Override
    public boolean modUserinfo(String username, String nickname, Integer sex, Integer age, String info) {

        return userMMapper.modUserinfo(username, nickname, sex, age, info);
    }

    /**
     * 判断邮箱是否已经被注册
     * @param email
     * @return
     */
    @Override
    public boolean emailExist(String email) {

        if(userMMapper.emailExist(email) != null){
            return true;
        }
        return false;
    }

    /**
     * 进行邮箱注册
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @param email
     * @return
     */
    @Override
    public boolean registerByEmail(Integer identity, String username, String password, String nickname, String email) {

        return userMMapper.registerByEmail(identity, username, password, nickname, email);
    }
}
