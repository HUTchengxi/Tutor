package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.BbsCard;

@Mapper
public interface BbsCardMapper {

    /**
     *
     * @Description 获取用户的帖子总数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/3/31
     */
    @Select("select count(*) from bbs_card where username=#{username}")
    Integer getMyCardCount(@Param("username") String username);


    /**
     *
     * @Description 获取标题对应的帖子
     * @param [title]
     * @return org.framework.tutor.domain.BbsCard
     * @author yinjimin
     * @date 2018/4/1
     */
    @Select("select * from bbs_card where title=#{title}")
    BbsCard getByTitle(@Param("title") String title);


    /**
     *
     * @Description 发表帖子
     * @param [username, title, imgsrc, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    @Insert("insert into bbs_card(username, title, descript, imgsrc) values(#{username}, #{title}, #{descript}, #{imgsrc}) order by id desc")
    void publishCard(@Param("username") String username, @Param("title") String title, @Param("imgsrc") String imgsrc, @Param("descript") String descript);

    /**
     *
     * @Description 关键字查询帖子
     * @param [keyword]
     * @return java.util.List<org.framework.tutor.domain.BbsCard>
     * @author yinjimin
     * @date 2018/4/3
     */
    @Select("select * from bbs_card where title like CONCAT('%', #{keyword}, '%') or descript like CONCAT('%', #{keyword}, '%')")
    List<BbsCard> searchCard(@Param("keyword") String keyword);


    /**
     *
     * @Description 加载最多五条热门数据
     * @param []
     * @return java.util.List<org.framework.tutor.domain.BbsCard>
     * @author yinjimin
     * @date 2018/4/3
     */
    @Select("select * from bbs_card order by viscount desc limit 0, 5")
    List<BbsCard> loadHotCard();


    /**
     *
     * @Description 获取对应的帖子数据
     * @param [id]
     * @return org.framework.tutor.domain.BbsCard
     * @author yinjimin
     * @date 2018/4/6
     */
    @Select("select * from bbs_card where id = #{id}")
    BbsCard getCardById(@Param("id") Integer id);

    /**
     *
     * @Description 对应课程收藏加1
     * @param [cardId]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Update("update bbs_card set colcount = colcount+1 where id=#{id}")
    void addColCountById(@Param("id") Integer cardId);


    /**
     *
     * @Description 对应课程收藏jian减1
     * @param [cardId]
     * @return void
     * @author yinjimin
     * @date 2018/4/8
     */
    @Update("update bbs_card set colcount = colcount-1 where id=#{id}")
    void delColCountById(@Param("id") Integer cardId);
}