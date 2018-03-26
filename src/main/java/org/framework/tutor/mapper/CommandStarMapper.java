package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CommandStar;

import java.util.List;

/**
 * 用户评论点赞数据访问层
 * @author chengxi
 */
@Mapper
public interface CommandStarMapper {

    /**
     * 获取当前用户的评论点赞数量
     * @param username
     * @param score
     * @return
     */
    @Select("select * from command_star where cmid=#{cmid} and score=#{score}")
    List<CommandStar> getCountByCmid(@Param("cmid") Integer cmid, @Param("score") Integer score);

    /**
     * 判断指定用户是否对指定课程进行评分
     * @param username
     * @param cmid
     * @return
     */
    @Select("select * from command_star where username=#{username} and cmid=#{cmid}")
    CommandStar getByUserAndCmid(@Param("username") String username, @Param("cmid") Integer cmid);

    /**
     * 进行评论的点赞与踩
     * @param username
     * @param cmid
     * @param score
     * @return
     */
    @Insert("insert into command_star(username, cmid, score) values(#{username}, #{cmid}, #{score})")
    Integer addMyStar(@Param("username") String username, @Param("cmid") Integer cmid, @Param("score") Integer score);
}