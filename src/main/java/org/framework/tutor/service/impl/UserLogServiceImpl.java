package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserLog;
import org.framework.tutor.mapper.UserLogMapper;
import org.framework.tutor.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户登录记录服务层实现类
 * @author chengxi
 */
@Component
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;

    /**
     * 保存用户登录记录
     * @param username
     * @param logcity
     * @param ip
     * @param logsystem
     * @return
     */
    @Override
    public boolean saveUserlog(String username, String logcity, String ip, String logsystem) {

        return userLogMapper.saveUserlog(username, logcity, ip, logsystem);
    }

    /**
     * 获取指定用户的登录记录
     * @param username
     * @return
     */
    @Override
    public List<UserLog> getUserlog(String username) {

        return userLogMapper.getUserlog(username);
    }
}
