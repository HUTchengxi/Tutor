package org.framework.tutor.service;

import org.framework.tutor.domain.UserVali;

/**
 * 注册验证服务层接口
 * @author chengxi
 */
public interface UserVService {

    /**
     * 添加注册队列
     * @param username
     * @param valicode
     * @param status
     */
    void addUserVali(String username, String valicode, Integer status);

    /**
     * 获取指定用户注册时的注册码
     * @param username
     * @return
     */
    String getCodeByUsername(String username);

    /**
     * 清除未验证状态
     * @param username
     */
    void delStatus(String username);

    /**
     * 清空当天失效的验证数据
     */
    void checkAll(String now);

    /**
     * 判断当前邮箱是否已经被验证
     * @param email
     * @return
     */
    UserVali checkEmailStatus(String email);

    /**
     * 更新邮箱注册码
     * @param email
     * @param valicode
     */
    void updateEmailCode(String email, String valicode);
}
