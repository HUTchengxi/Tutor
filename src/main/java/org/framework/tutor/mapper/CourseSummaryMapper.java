package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.framework.tutor.domain.CourseSummary;

import java.util.List;

/**
 *
 * @Description 课程概述
 * @author yinjimin
 * @date 2018/4/15
 */
@Mapper
public interface CourseSummaryMapper {

    /**
     *
     * @Description 获取指定课程的课程概述
     * @param [cid]
     * @return java.util.List<org.framework.tutor.domain.CourseSummary>
     * @author yinjimin
     * @date 2018/4/15
     */
    @Select("select * from course_summary where cid=#{cid}")
    List<CourseSummary> getCourseSummaryInfo(@Param("cid") Integer cid);


    /**
     *
     * @Description 更新课程概述
     * @param [id, title, descript]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/15
     */
    @Update("update course_summary set title=#{title}, descript=#{descript} where id=#{id}")
    Integer updateCourseSummary(@Param("id") Integer id, @Param("title") String title, @Param("descript") String descript);
}