package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.CourseCollect;

import java.util.List;

/**
 * 课程收藏数据访问层
 * @author chengxi
 */
@Mapper
public interface CourseCMapper {

    /**
     * 获取我的课程收藏记录
     * @param username
     * @return
     */
    @Select("select * from course_collect where username = #{username} order by coltime desc limit #{startpos}, 3")
    List<CourseCollect> getMyCollect(@Param("username") String username, @Param("startpos") Integer startpos);

    /**
     * 取消指定课程的收藏
     * @param cid
     * @param username
     * @return
     */
    @Delete("delete from course_collect where username = #{username} and cid = #{cid}")
    boolean unCollect(@Param("cid") Integer cid, @Param("username") String username);

    /**
     * 获取指定用户及课程id的收藏信息
     * @param cid
     * @param username
     * @return
     */
    @Select("select * from course_collect where cid = #{cid} and username = #{username}")
    CourseCollect getCollect(@Param("cid") Integer cid, @Param("username") String username);

    /**
     * 收藏课程
     * @param cid
     * @param username
     * @param descript
     * @return
     */
    @Insert("insert into course_collect(cid, username, descript) values(#{cid}, #{username}, #{descript})")
    boolean Collect(@Param("cid") Integer cid, @Param("username") String username, @Param("descript") String descript);
}
