package org.framework.tutor.service;

import org.framework.tutor.domain.BbsCard;

public interface BbsCardService {


    /**
     *
     * @Description 获取指定用户的帖子总数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/3/31
     */
    Integer getMyCardCount(String username);

    
    /**  
     *    
     * @Description 根据标题获取对应的帖子
     * @param [title]    
     * @return boolean
     * @author yinjimin  
     * @date 2018/4/1
     */  
    BbsCard getByTitle(String title);

    /**
     *
     * @Description 发表帖子
     * @param [username, title, imgsrc, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    void publishCard(String username, String title, String imgsrc, String descript);
}
