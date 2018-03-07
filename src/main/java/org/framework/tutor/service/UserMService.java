package org.framework.tutor.service;

import org.framework.tutor.domain.UserMain;

/**
 * 用户主表服务层接口
 * @author chengxi
 */
public interface UserMService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean userExist(String username);

    /**
     * 判断用户名和密码是否对应
     * @param username
     * @param password
     * @return
     */
    public boolean passCheck(String username, String password);

    /**
     * 通过用户名获取并返回对应的UserMain实体
     * @param username
     * @return
     */
    public UserMain getByUser(String username);

    /**
     * 判断昵称是否已经被使用过
     * @param nickname
     * @return
     */
    public boolean NickExist(String nickname);

    /**
     * 咱不验证的方式进行的注册
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @return
     */
    public boolean registerNoCheck(Integer identity, String username, String password, String nickname);

    /**
     * 更换指定用户的头像
     * @param username
     * @param imgsrc
     * @return
     */
    boolean modImgsrcByUser(String username, String imgsrc);

    /**
     * 更改用户的个人信息
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     * @return
     */
    boolean modUserinfo(String username, String nickname, Integer sex, Integer age, String info);
}