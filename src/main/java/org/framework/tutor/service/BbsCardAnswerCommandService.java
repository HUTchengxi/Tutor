package org.framework.tutor.service;

import org.framework.tutor.domain.BbsCardAnswerCommand;

import java.util.List;

/**
 *
 * @Description 帖子回答的评论服务层接口
 * @author yinjimin
 * @date 2018/4/10
 */
public interface BbsCardAnswerCommandService {
    
    /**  
     *    
     * @Description 每次获取五条评论
     * @param [aid, startpos]    
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswerCommand>
     * @author yinjimin  
     * @date 2018/4/10
     */  
    List<BbsCardAnswerCommand> getCommandListByAid(Integer aid, Integer startpos);


    /**
     *
     * @Description 获取当前回答最高楼层
     * @param [cardid]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/10
     */
    Integer getCurrentFloor(Integer cardid, Integer aid);


    /**
     *
     * @Description 发布评论
     * @param [username, cardid, aid, answer, floor, repfloor]
     * @return void
     * @author yinjimin
     * @date 2018/4/12
     */
    void publishCommand(String username, Integer cardid, Integer aid, String answer, Integer floor, Integer repfloor);


    /**
     *
     * @Description 获取对应用户的评论总数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/12
     */
    Integer getComcountByUser(String username);


    /**
     *
     * @Description 获取指定用户的评论数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswerCommand>
     * @author yinjimin
     * @date 2018/4/14
     */
    List<BbsCardAnswerCommand> getMyCommandInfo(String username);
}
