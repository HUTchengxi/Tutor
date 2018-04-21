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

    @Select("select * from course_order_manager where oid in (select co.id from course_order co where co.cid in (" +
            "select cm.id from course_main cm where cm.name like CONCAT('%',#{coursename}, '%'))) and (tutorstatus in (-1,-2) or userstatus in (-1,-2)) " +
            "limit #{offset}, #{pagesize}")
    List<CourseOrderManager> getAllErrsLimit(@Param("coursename") String courseName, @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select * from course_order_manager where oid in (select co.id from course_order co where co.cid in (" +
            "select cm.id from course_main cm where cm.name like CONCAT('%',#{coursename},'%') and cm.username=#{tutorname})) " +
            "and (tutorstatus in (-1,-2) or userstatus in (-1,-2)) limit #{offset}, #{pagesize}")
    List<CourseOrderManager> getErrsByTutorAndCourse(@Param("coursename") String courseName, @Param("tutorname") String tutorName, @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select * from course_order_manager where oid in (select co.id from course_order co where co.cid in(" +
            "select cm.id from course_main cm where cm.name like CONCAT('%', #{coursename}, '%')) and co.username=#{username}) " +
            "and (tutorstatus in (-1,-2) or userstatus in (-1,-2)) limit #{offset}, #{pagesize}")
    List<CourseOrderManager> getErrsByUserAndCourse(@Param("coursename") String courseName, @Param("username") String userName, @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select * from course_order_manager where oid in (select co.id from course_order co where co.cid in (" +
            "select cm.id from course_main cm where cm.name like CONCAT('%', #{coursename}, '%') and cm.username=#{tutorname}) and " +
            "co.username=#{username}) and (tutorstatus in (-1,-2) or userstatus in (-1,-2)) limit #{offset}, #{pagesize}")
    List<CourseOrderManager> getErrsByUserAndTutor(@Param("coursename") String courseName, @Param("username") String userName, @Param("tutorname") String tutorName, @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select count(*) from course_order_manager where tutorstatus in (-1,-2) or userstatus in (-1,-2)")
    Integer getAllErrs();

    @Select("select * from course_order_manager com where com.oid in (select co.id from course_order co where co.cid in (" +
            "select cdr.cid from course_delete_req cdr where cdr.id=#{reqid}))")
    List<CourseOrderManager> getByReqid(@Param("reqid") Integer reqid);
}