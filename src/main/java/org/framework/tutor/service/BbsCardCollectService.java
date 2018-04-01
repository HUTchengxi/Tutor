package org.framework.tutor.service;

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
}
