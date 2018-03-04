package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CourseTreply;

/**
 * 家教课程评价回复数据访问层
 * @author chengxi
 */
@Mapper
public interface CourseTMapper {

    /**
     * 获取指定课程的对应用户评价的讲师回复
     * @param cid
     * @param cmid
     * @return
     */
    @Select("select * from course_treply where cid=#{cid} and cmid=#{cmid}")
    CourseTreply getCourseTreply(@Param("cid") Integer cid, @Param("cmid") Integer cmid);
}
