package org.framework.tutor.service;

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
}
