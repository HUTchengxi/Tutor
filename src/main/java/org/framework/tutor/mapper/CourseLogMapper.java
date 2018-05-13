package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.CourseLog;

import java.util.List;

/**
 * 课程记录表数据访问层
 * @author chengxi
 */
@Mapper
public interface CourseLogMapper {

    /**
     * 获取我的课程记录
     * @param username
     * @return
     */
    @Select("select * from course_log where username = #{username} order by id desc")
    List<CourseLog> getUserlog(@Param("username") String username);

    /**
     * 删除指定的课程记录
     * @param id
     * @return
     */
    @Delete("delete from course_log where id = #{id}")
    boolean delLog(@Param("id") Integer id);

    @Insert("insert into course_log(cid, username) values(#{cid}, #{username})")
    void addLog(@Param("cid") Integer cid, @Param("username") String username);

    @Select("select count(*) from course_log where username=#{username}")
    Integer getUserlogCount(@Param("username") String username);

    @Select("select * from course_log where username=#{username} order by id asc limit 1")
    CourseLog getFirseLog(@Param("username") String username);
}
