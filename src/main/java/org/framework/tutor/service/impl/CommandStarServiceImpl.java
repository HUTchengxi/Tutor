package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CommandStar;
import org.framework.tutor.mapper.CommandStarMapper;
import org.framework.tutor.service.CommandStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户评论点赞服务层实现类
 * @author chengxi
 */
@Component
public class CommandStarServiceImpl implements CommandStarService {

    @Autowired
    private CommandStarMapper commandStarMapper;

    /**
     * 获取当前用户的点赞数量
     * @param username
     * @param score
     * @return
     */
    @Override
    public List<CommandStar> getCountByCmid(Integer cmid, Integer score) {

        return commandStarMapper.getCountByCmid(cmid, score);
    }

    /**
     * 判断指定用户是否对指定课程进行评分
     * @param username
     * @param cmid
     * @return
     */
    @Override
    public CommandStar getByUserAndCmid(String username, Integer cmid) {

        return commandStarMapper.getByUserAndCmid(username, cmid);
    }

    /**
     * 进行评论的点赞与踩
     * @param username
     * @param cmid
     * @param score
     * @return
     */
    @Override
    public Integer addMyStar(String username, Integer cmid, Integer score) {

        return commandStarMapper.addMyStar(username, cmid, score);
    }
}
