package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.BbsCardCollect;

/**  
 *    
 * @Description 用户帖子收藏数据访问层
 * @author yinjimin
 * @date 2018/4/1
 */  
@Mapper
public interface BbsCardCollectMapper {

    /**
     *
     * @Description 获取指定用户的帖子评论数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/1
     */
    @Select("select count(*) from bbs_card_collect where username=#{username}")
    Integer getMyCollectCount(@Param("username") String username);


    /**
     *
     * @Description 获取课程/用户名对应的数据
     * @param [cardId, username]
     * @return org.framework.tutor.domain.BbsCardCollect
     * @author yinjimin
     * @date 2018/4/8
     */
    @Select("select * from bbs_card_collect where cardid=#{cardId} and username=#{username}")
    BbsCardCollect checkCollectStatus(@Param("cardId") Integer cardId, @Param("username") String username);


    /**
     *
     * @Description 指定用户收藏问题
     * @param [cardId, username]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Insert("insert into bbs_card_collect(username, cardid) values(#{username}, #{cardId})")
    void collectCard(@Param("cardId") Integer cardId, @Param("username") String username);


    /**
     *
     * @Description 取消问题的收藏
     * @param [cardId, username]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Delete("delete from bbs_card_collect where cardid=#{cardId} and username=#{username}")
    void uncollectCard(@Param("cardId") Integer cardId, @Param("username") String username);


    /**
     *
     * @Description 获取当前用户收藏的帖子数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.domain.BbsCardCollect>
     * @author yinjimin
     * @date 2018/4/13
     */
    @Select("select * from bbs_card_collect where username=#{username} order by coltime desc")
    List<BbsCardCollect> getMyCollectInfo(@Param("username") String username);

}