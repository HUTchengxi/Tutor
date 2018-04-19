package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.framework.tutor.domain.CourseOrderManager;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseOrderManagerMapper {


    @Select("select count(*) from course_order_manager where oid in (select co.id from course_order co where co.cid in (" +
            "select cm.id from course_main cm where cm.username=#{username}))")
    Integer getCountByOidList(@Param("username") String username);

    @Select("select * from course_order_manager where oid in (select co.id from course_order co where co.cid in (" +
            "select cm.id from course_main cm where cm.username=#{username})) limit #{offset}, #{pageSize}")
    List<CourseOrderManager> getByOidList(@Param("username") String username, @Param("offset") int offset, @Param("pageSize") Integer pageSize);

    @Select("select * from course_order_manager where oid in (select co.id from course_order co where co.cid in (" +
            "select cm.id from course_main cm where cm.username=#{username} and cm.name like CONCAT('%', #{courseName}, '%'))) limit #{offset}, #{pageSize}")
    List<CourseOrderManager> getByUserAndName(@Param("username") String username, @Param("courseName") String courseName, @Param("offset") int offset, @Param("pageSize") Integer pageSize);

    @Select("select * from course_order_manager where code=#{code}")
    CourseOrderManager getByCode(@Param("code") String code);

    @Update("update course_order_manager set tutorinfo=#{tutorinfo}, tutorstatus=#{tutorstatus} where code=#{code}")
    void updateTutorStatus(@Param("code") String code, @Param("tutorstatus") Integer tutorStatus, @Param("tutorinfo") String tutorInfo);
}