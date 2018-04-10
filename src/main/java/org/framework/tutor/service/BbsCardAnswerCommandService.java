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
    Integer getCurrentFloor(Integer cardid);

    void publishCommand(String username, Integer cardid, Integer aid, String answer, Integer floor, Integer repfloor);
}
