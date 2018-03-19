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

    /**
     * 判断邮箱是否已经被注册
     * @param email
     * @return
     */
    boolean emailExist(String email);

    /**
     * 进行邮箱注册
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @param email
     */
    boolean registerByEmail(Integer identity, String username, String password, String nickname, String email);

    /**
     * 获取用户名和邮箱对应的用户
     * @param username
     * @param email
     * @return
     */
    UserMain getByUserAndEmail(String username, String email);

    /**
     * 修改用户的密码
     * @param username
     * @param newpass
     * @return
     */
    Integer modPassword(String username, String newpass);

    /**
     * 邮箱解除绑定
     * @param username
     * @return
     */
    Integer unbindEmail(String username);

    /**
     * 绑定手机号码
     * @param username
     * @param email
     */
    void bindPhone(String username, String email);

    /**
     * 绑定邮箱
     * @param username
     * @param email
     */
    void bindEmail(String username, String email);

    /**
     * 判断手机号码是否已经被注册
     * @param phone
     * @return
     */
    Boolean phoneExist(String phone);

    /**
     * 修改指定用户的id状态
     * @param username
     * @param identity
     */
    void setIdentity(String username, Integer identity);

    /**
     * 手机号码解除绑定
     * @param username
     * @return
     */
    Integer unbindPhone(String username);

    /**
     * 获取用户名和手机号码对应的用户
     * @param username
     * @param phone
     * @return
     */
    UserMain getByUserAndPhone(String username, String phone);

    /**
     * 手机号码注册用户
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @param telephone
     * @return
     */
    Boolean registerByPhone(Integer identity, String username, String password, String nickname, String telephone);
}
