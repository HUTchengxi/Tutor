package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CourseChapter;

import java.util.List;

/**
 * 课程章节目录服务层接口
 * @author chengxi
 */
@Mapper
public interface CourseChMapper {

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @return
     */
    @Select("select * from course_chapter where cid = #{cid} order by ord asc")
    List<CourseChapter> getCourseChapter(@Param("cid") Integer cid);
}
