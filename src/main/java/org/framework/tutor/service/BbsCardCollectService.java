package org.framework.tutor.service;

import org.framework.tutor.domain.BbsCardCollect;

import java.util.List;

/**
 *    
 * @Description 用户帖子收藏服务层接口
 * @author yinjimin
 * @date 2018/4/1
 */  
public interface BbsCardCollectService {

    /**
     *
     * @Description 获取指定用户的帖子收藏数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/1
     */
    Integer getMyCollectCount(String username);


    /**
     * @Description 获取课程/用户名对应的收藏数据
     * @param cardId
     * @param username
     */
     BbsCardCollect checkCollectStatus(Integer cardId, String username);


     /**
      *
      * @Description 指定用户收藏问题
      * @param [cardId, username]
      * @return void
      * @author yinjimin
      * @date 2018/4/8
      */
    void collectCard(Integer cardId, String username);


    /**
     *
     * @Description 取消问题的收藏
     * @param [cardId, username]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    void uncollectCard(Integer cardId, String username);

    
    /**  
     *    
     * @Description 获取当前用户收藏的帖子数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.domain.BbsCardCollect>
     * @author yinjimin  
     * @date 2018/4/13
     */  
    List<BbsCardCollect> getMyCollectInfo(String username);

}
