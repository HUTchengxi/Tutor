package org.framework.tutor.mapper;


import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.CourseCommand;

import java.util.List;

/**
 * 课程评论数据访问层
 * @author chengxi
 */
@Mapper
public interface CourseCommandMapper {

    /**
     * 获取课程评论数据
     * @param cid
     * @param startpos
     * @return
     */
    @Select("select * from course_command where cid=#{cid} order by ctime desc limit #{startpos}, 5")
    List<CourseCommand> getCourseCommand(@Param("cid") Integer cid, @Param("startpos") Integer startpos);

    /**
     * 获取课程神评数据
     * @param cid
     * @return
     */
    @Select("select * from course_command where cid=#{cid} and god=1 order by ctime asc")
    List<CourseCommand> getCourseCommandGod(@Param("cid") Integer cid);

    /**
     * 获取指定用户对指定课程的评价数据
     * @param username
     * @param cid
     * @return
     */
    @Select("select * from course_command where username=#{username} and cid=#{cid}")
    List<CourseCommand> getMyCommand(@Param("username") String username, @Param("cid") Integer cid);

    /**
     * 发表用户评价
     * @param cid
     * @param command
     * @param score
     * @param username
     * @return
     */
    @Insert("insert into course_command(cid,info,score,username) values(#{cid},#{command},#{score},#{username})")
    Integer subMyCommand(@Param("cid") Integer cid, @Param("command") String command, @Param("score") Integer score, @Param("username") String username);

    /**
     * 获取指定用户的今日课程评论总数
     * @param username
     * @param now
     * @return
     */
    @Select("select count(*) from course_command where cid in (select id from course_main where username=#{username} and ctime like CONCAT('%', #{now}, '%'))")
    Integer getCommandCountNow(@Param("username") String username, @Param("now") String now);

    /**
     * 获取家教的课程今日评分平均值
     * @param username
     * @param now
     * @return
     */
    @Select("select avg(score) from course_command where cid in (select id from course_main where username=#{username} and ctime like CONCAT('%', #{now}, '%'))")
    Double getScoreAvgNow(@Param("username") String username, @Param("now") String now);

    /**
     * 获取当前用户的课程评论数据
     * @param username
     * @return
     */
    @Select("select * from course_command where username=#{username}")
    List<CourseCommand> loadMyCommandInfo(@Param("username") String username);

    
    /**  
     *    
     * @Description 获取指定课程的评分平均值
     * @param [id]    
     * @return java.lang.Integer
     * @author yinjimin  
     * @date 2018/4/14
     */
    @Select("select avg(score) from course_command where cid=#{cid}")
    Integer getMyPublishAvg(@Param("cid") Integer id);

    @Select("select * from course_command where cid in (#{courseId}) limit #{offset}, #{pageSize}")
    List<CourseCommand> getMyCommandList(@Param("courseId") String courseId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("select count(*) from course_command where cid in (#{courseId})")
    Integer getCommandCountByIdlist(@Param("courseId") String courseId);

    @Select("select * from course_command where id=#{id}")
    CourseCommand getCommandById(@Param("id") Integer id);

    @Update("update course_command set god=1 where id=#{id}")
    void setCommandGodstate(@Param("id") Integer id);

    @Select("select count(*) from course_command where cid=#{cid} and god=1")
    Integer geGodCountById(@Param("cid") Integer cid);

    @Update("update course_command set status = #{status} where id=#{id}")
    void updateCommandStatus(@Param("id") Integer cid, @Param("status") Integer status);
}
