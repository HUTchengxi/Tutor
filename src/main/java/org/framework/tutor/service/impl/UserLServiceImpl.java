package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserLog;
import org.framework.tutor.mapper.UserLMapper;
import org.framework.tutor.service.UserLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登录记录服务层实现类
 * @author chengxi
 */
@Component
public class UserLServiceImpl implements UserLService {

    @Autowired
    private UserLMapper userLMapper;

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

        return userLMapper.saveUserlog(username, logcity, ip, logsystem);
    }

    /**
     * 获取指定用户的登录记录
     * @param username
     * @return
     */
    @Override
    public List<UserLog> getUserlog(String username) {

        return userLMapper.getUserlog(username);
    }
}
