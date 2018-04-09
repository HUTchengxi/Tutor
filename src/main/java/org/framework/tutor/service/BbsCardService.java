package org.framework.tutor.service;

import org.framework.tutor.domain.BbsCard;

import java.util.List;

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


    /**
     *
     * @Description 关键字查询数据
     * @param [keyword]
     * @return java.util.List<org.framework.tutor.domain.BbsCard>
     * @author yinjimin
     * @date 2018/4/3
     */
    List<BbsCard> searchCard(String keyword);


    /**
     *
     * @Description 加载最多五条热门数据
     * @param []
     * @return java.util.List<org.framework.tutor.domain.BbsCard>
     * @author yinjimin
     * @date 2018/4/3
     */
    List<BbsCard> loadHotCard();


    /**
     *
     * @Description 获取对应的帖子详情数据
     * @param [id]
     * @return org.framework.tutor.domain.BbsCard
     * @author yinjimin
     * @date 2018/4/6
     */
    BbsCard getCardById(Integer id);


    /**
     *
     * @Description 收藏加1
     * @param [cardId]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    void addColCountByCardId(Integer cardId);


    /**
     *
     * @Description 收藏jian减1
     aram [cardId]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    void delColCountByCardId(Integer cardId);


    /**
     *
     * @Description 回答数加1
     * @param [cardId]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    void addComCountByCardId(Integer cardId);
}
