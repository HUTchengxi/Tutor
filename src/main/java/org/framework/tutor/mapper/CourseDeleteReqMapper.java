package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.CourseDeleteReq;

/**
 *
 * @Description 课程下线申请
 * @author yinjimin
 * @date 2018/4/15
 */
@Mapper
public interface CourseDeleteReqMapper {

    /**
     *
     * @Description 获取对应课程的申请数据
     * @param [cid]
     * @return org.framework.tutor.domain.CourseDeleteReq
     * @author yinjimin
     * @date 2018/4/15
     */
    @Select("select * from course_delete_req where cid=#{cid}")
    CourseDeleteReq getByCid(@Param("cid") Integer cid);

    /**
     *
     * @Description 添加申请
     * @param [cid, username, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Insert("insert into course_delete_req(cid, username, descript, reqcount) values(#{cid}, #{username}, #{descript}, 1)")
    void addCourseDeleteReq(@Param("cid") Integer cid, @Param("username") String username, @Param("descript") String descript);


    /**
     *
     * @Description 更新申请数据
     * @param [cid, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Update("update course_delete_req set descript=#{descript}, reqcount=reqcount+1 where cid=#{cid}")
    void updateCourseDeleteReq(@Param("cid") Integer cid, @Param("descript") String descript);
}