package org.framework.tutor.service;

import org.framework.tutor.domain.UserLog;

import java.util.List;

/**
 * 用户登录记录表服务层接口
 * @author chengxi
 */
public interface UserLService {

    /**
     * 保存用户登录记录
     * @param username
     * @param logcity
     * @param ip
     * @param logsystem
     * @return
     */
    boolean saveUserlog(String username, String logcity, String ip, String logsystem);

    /**
     * 获取指定用户的登录记录
     * @param username
     * @return
     */
    List<UserLog> getUserlog(String username);
}
