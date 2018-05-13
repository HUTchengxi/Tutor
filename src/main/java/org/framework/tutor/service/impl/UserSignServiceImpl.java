package org.framework.tutor.service.impl;

import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.mapper.UserSignMapper;
import org.framework.tutor.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户签到服务层实现类
 * @author chengxi
 */
@Component
public class UserSignServiceImpl implements UserSignService {

    @Autowired
    private UserSignMapper userSignMapper;

    /**
     * 获取用户的签到数据
     * @param username
     * @return
     */
    @Override
    public List<UserSign> getMySign(String username) {

        return userSignMapper.getMySign(username);
    }

    /**
     * 获取用户当月的签到数据
     * @param username
     * @param monthstr
     * @return
     */
    @Override
    public List<UserSign> getMySignNow(String username, String monthstr) {

        return userSignMapper.getMySignNow(username, monthstr);
    }

    /**
     * 进行签到打卡
     * @param username
     * @return
     */
    @Override
    public Integer addUsersign(String username) {

        return userSignMapper.addUsersign(username);
    }

    /**
     * 打卡日榜
     * @return
     */
    @Override
    public List<UserSign> rankSignDay(String daystr, Integer startpos) {

        return userSignMapper.getSignDay(daystr, startpos);
    }

    /**
     * 打卡总榜
     * @param startpos
     * @return
     */
    @Override
    public List<RankTemp> rankSignTotal(Integer startpos) {

        return userSignMapper.getSignTotal(startpos);
    }
}
