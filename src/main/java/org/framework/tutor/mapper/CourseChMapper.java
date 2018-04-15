package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
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

    /**  
     *    
     * @Description 获取指定目录数据
     * @param [id]    
     * @return org.framework.tutor.domain.CourseChapter
     * @author yinjimin  
     * @date 2018/4/15
     */
    @Select("select * from course_chapter where id=#{id}")
    CourseChapter getById(@Param("id") Integer id);

    /**
     *
     * @Description 删除指定目录
     * @param [id]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Delete("delete from course_chapter where id=#{id}")
    void deleteChapter(@Param("id") Integer id);

    /**
     *
     * @Description 添加新的目录
     * @param [cid, title, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Insert("insert into course_chapter(cid, ord, title, descript) values(#{cid}, #{ord}, #{title}, #{descript})")
    void addChapter(@Param("cid") Integer cid, @Param("ord") Integer ord, @Param("title") String title, @Param("descript") String descript);

    /**
     *
     * @Description 更新目录
     * @param [id, title, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    @Update("update course_chapter set title=#{title} , descript=#{descript} where id=#{id}")
    void modChapter(@Param("id") Integer id, @Param("title") String title, @Param("descript") String descript);


    /**
     *
     * @Description 获取指定课程的最大ord
     * @param [cid]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/15
     */
    @Select("select max(ord) from course_chapter where cid=#{cid}")
    Integer getLastOrg(@Param("cid") Integer cid);
}
