package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.BbsCardAnswerCommand;

/**  
 *    
 * @Description 帖子回答的评论数据访问层
 * @author yinjimin
 * @date 2018/4/10
 */
@Mapper
public interface BbsCardAnswerCommandMapper {

    /**
     *
     * @Description 每次获取五条评论
     * @param [aid, startpos]
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswerCommand>
     * @author yinjimin
     * @date 2018/4/10
     */
    @Select("select * from bbs_card_answer_command where aid=#{aid} order by floor asc limit #{startpos}, 5")
    List<BbsCardAnswerCommand> getCommandListByAid(@Param("aid") Integer aid, @Param("startpos") Integer startpos);


    /**
     *
     * @Description 获取指定回答评论最高楼层
     * @param [cardid]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/10
     */
    @Select("select floor from bbs_card_answer_command where cardid=#{cardid} and aid=#{aid} order by floor desc limit 0,1")
    Integer getCurrentFloor(@Param("cardid") Integer cardid, @Param("aid") Integer aid);

    /**
     *
     * @Description 新增回答的评论数据
     * @param [username, cardid, answer, floor, repfloor]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Insert("insert into bbs_card_answer_command(username, cardid, aid, comment, floor, repfloor) values(#{username}, #{cardid}, #{aid}, #{answer}, #{floor}, #{repfloor})")
    void publishCommand(@Param("username") String username, @Param("cardid") Integer cardid, @Param("aid") Integer aid, @Param("answer") String answer, @Param("floor") Integer floor, @Param("repfloor") Integer repfloor);


    /**
     *
     * @Description 获取用户的评论总数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/12
     */
    @Select("select count(*) from bbs_card_answer_command where username=#{username}")
    Integer getComCountByUser(@Param("username") String username);


    /**
     *
     * @Description 获取指定用户的评论数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswerCommand>
     * @author yinjimin
     * @date 2018/4/14
     */
    @Select("select * from bbs_card_answer_command where username=#{username} order by comtime desc")
    List<BbsCardAnswerCommand> getMyCommandInfo(@Param("username") String username);
}