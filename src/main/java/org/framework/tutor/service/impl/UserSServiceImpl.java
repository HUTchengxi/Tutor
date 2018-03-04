package org.framework.tutor.service.impl;

import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.mapper.UserSMapper;
import org.framework.tutor.service.UserSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户签到服务层实现类
 * @author chengxi
 */
@Component
public class UserSServiceImpl implements UserSService {

    @Autowired
    private UserSMapper userSMapper;

    /**
     * 获取用户的签到数据
     * @param username
     * @return
     */
    @Override
    public List<UserSign> getMySign(String username) {

        return userSMapper.getMySign(username);
    }

    /**
     * 获取用户当月的签到数据
     * @param username
     * @param monthstr
     * @return
     */
    @Override
    public List<UserSign> getMySignNow(String username, String monthstr) {

        return userSMapper.getMySignNow(username, monthstr);
    }

    /**
     * 进行签到打卡
     * @param username
     * @return
     */
    @Override
    public Integer addUsersign(String username) {

        return userSMapper.addUsersign(username);
    }

    /**
     * 打卡日榜
     * @return
     */
    @Override
    public List<UserSign> rankSignDay(String daystr, Integer startpos) {

        return userSMapper.getSignDay(daystr, startpos);
    }

    /**
     * 打卡总榜
     * @param startpos
     * @return
     */
    @Override
    public List<RankTemp> rankSignTotal(Integer startpos) {

        return userSMapper.getSignTotal(startpos);
    }
}
