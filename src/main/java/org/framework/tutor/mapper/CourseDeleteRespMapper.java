package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CourseDeleteResp;

@Mapper
public interface CourseDeleteRespMapper {

    @Select("select * from course_delete_resp where id=#{id}")
    CourseDeleteResp getById(@Param("id") Integer id);
}