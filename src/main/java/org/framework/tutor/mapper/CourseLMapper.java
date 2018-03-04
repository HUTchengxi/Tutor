package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CourseLog;

import java.util.List;

/**
 * 课程记录表数据访问层
 * @author chengxi
 */
@Mapper
public interface CourseLMapper {

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
}
