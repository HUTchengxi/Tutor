package org.framework.tutor.service;

import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserSign;

import java.util.List;
import java.util.Map;

/**
 * 用户签到服务层接口
 * @author chengxi
 */
public interface UserSService {

    /**
     * 获取用户的签到数据
     * @param username
     * @return
     */
    List<UserSign> getMySign(String username);

    /**
     * 获取用户当月的签到数据
     * @param username
     * @param monthstr
     * @return
     */
    List<UserSign> getMySignNow(String username, String monthstr);

    /**
     * 进行签到打卡
     * @param username
     * @return
     */
    Integer addUsersign(String username);

    /**
     * 打卡日榜
     * @return
     */
    List<UserSign> rankSignDay(String daystr, Integer startpos);

    /**
     * 打卡总榜
     * @param startpos
     * @return
     */
    List<RankTemp> rankSignTotal(Integer startpos);
}
