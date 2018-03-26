package org.framework.tutor.service;

import org.framework.tutor.domain.CommandStar;

import java.util.List;

/**
 * 用户评论点赞服务层接口
 * @author chengxi
 */

public interface CommandStarService {

    /**
     * 获取当前用户的点赞总数
     * @param username
     * @param score
     * @return
     */
    List<CommandStar> getCountByCmid(Integer cmid, Integer score);

    /**
     * 判断指定用户是否对指定课程进行评分
     * @param username
     * @param cmid
     * @return
     */
    CommandStar getByUserAndCmid(String username, Integer cmid);

    /**
     * 进行评论的点赞与踩
     * @param username
     * @param cmid
     * @param score
     * @return
     */
    Integer addMyStar(String username, Integer cmid, Integer score);
}
