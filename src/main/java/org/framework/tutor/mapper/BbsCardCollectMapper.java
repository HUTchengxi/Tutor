package org.framework.tutor.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}