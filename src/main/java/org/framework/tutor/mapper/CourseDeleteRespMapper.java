package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.CourseDeleteResp;

@Mapper
public interface CourseDeleteRespMapper {

    @Select("select * from course_delete_resp where reqid=#{id}")
    CourseDeleteResp getByRid(@Param("id") Integer id);

    @Insert("insert into course_delete_Resp(reqid, status, response) values(#{id}, #{status},#{respDesc})")
    void insertResp(@Param("id") Integer id, @Param("status") Integer status, @Param("respDesc") String respDesc);

    @Update("update course_delete_resp set status=#{status}, response=#{respDesc} where reqid=#{id}")
    void updateResp(@Param("id") Integer id, @Param("status") Integer status, @Param("respDesc") String respDesc);
}