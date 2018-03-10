package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CourseMain;

import java.util.List;

/**
 * 课程表数据访问层接口
 * @author chengxi
 */
@Mapper
public interface CourseMMapper {

    /**
     * 指定学科进行最新发布排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where stype = #{stype} and ctype = #{ctype} order by ptime desc limit #{startpos},8")
    List<CourseMain> getCourseListNew( @Param("stype") Integer stype, @Param("ctype") String ctype, @Param("startpos") Integer startpos);

    /**
     * 不限学科进行最新发布排序
     * @param stype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where stype=#{stype} order by ptime desc limit #{startpos},8")
    List<CourseMain> getCourseListANew( @Param("stype") Integer stype, @Param("startpos") Integer startpos);

    /**
     * 不限学科进行最热搜索排序
     * @param stype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where stype=#{stype} order by hcount desc limit #{startpos},8")
    List<CourseMain> getCourseListAHot( @Param("stype") Integer stype, @Param("startpos") Integer startpos);

    /**
     * 不限学科进行最多评论排序
     * @param stype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where stype=#{stype} order by ccount desc limit #{startpos},8")
    List<CourseMain> getCourseListAMore( @Param("stype") Integer stype, @Param("startpos") Integer startpos);

    /**
     * 指定学科进行最热搜索排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where stype=#{stype} and ctype=#{ctype} order by hcount desc limit #{startpos},8")
    List<CourseMain> getCourseListHot( @Param("stype") Integer stype, @Param("ctype") String ctype, @Param("startpos") Integer startpos);

    /**
     * 指定学科进行最多评论排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where stype=#{stype} and ctype=#{ctype} order by ccount desc limit #{startpos},8")
    List<CourseMain> getCourseListMore( @Param("stype") Integer stype, @Param("ctype") String ctype, @Param("startpos") Integer startpos);

    /**
     * 获取所有的科目类别
     * @param stype
     * @return
     */
    @Select("select distinct ctype from course_main where stype=#{stype}")
    List<CourseMain> getCourseType(@Param("stype") String stype);

    /**
     * 获取关键字对应的所有课程数据
     * @param keyword
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%')  ")
    List<CourseMain> courseSearch(@Param("keyword")String keyword);

    /**
     * 获取指定id的课程数据
     * @param id
     * @return
     */
    @Select("select * from course_main where id = #{id}")
    CourseMain getCourseById(@Param("id") Integer id);

    /**
     * 未指定主类别获取最新发布课程数据
     * @param startpos
     * @return
     */
    @Select("select * from course_main order by ptime desc limit #{startpos}, 8")
    List<CourseMain> getCourseListANewA(@Param("startpos") Integer startpos);

    /**
     * 未指定主类别获取最热搜索课程数据
     * @param startpos
     * @return
     */
    @Select("select * from course_main order by hcount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListAHotA(@Param("startpos") Integer startpos);

    /**
     * 未指定主类别获取最多评论课程数据
     * @param startpos
     * @return
     */
    @Select("select * from course_main order by ccount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListAMoreA(@Param("startpos") Integer startpos);

    /**
     * 获取科目类别的最热搜索课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype = #{ctype} order by hcount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListHotAC(@Param("ctype") String ctype, @Param("startpos") Integer startpos);

    /**
     * 获取科目类别的最新发布课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype = #{ctype} order by ptime desc limit #{startpos}, 8")
    List<CourseMain> getCourseListNewAC(@Param("ctype") String ctype, @Param("startpos") Integer startpos);

    /**
     * 获取科目类别的最多评论课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype = #{ctype} order by ccount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListMoreAC(@Param("ctype") String ctype, @Param("startpos") Integer startpos);

    /**
     * 未指定主类别获取最新发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%') order by ptime desc limit #{startpos}, 8")
    List<CourseMain> getCourseListNewKW(@Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 指定主类别获取最新发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%') and stype=#{stype} order by ptime desc limit #{startpos}, 8")
    List<CourseMain> getCourseListNewSKW(@Param("stype") Integer stype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 未指定主类别获取最热发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%') order by hcount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListHotKW(@Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 指定主类别获取最热发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%') and stype=#{stype} order by hcount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListHotSKW(@Param("stype") Integer stype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 未指定主类别获取最多发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%') order by ccount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListMoreKW(@Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 指定主类别获取最多发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where name like CONCAT('%', #{keyword}, '%') and stype=#{stype} order by ccount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListMoreSKW(@Param("stype") Integer stype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 未指定科目类别获取最新发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype=#{ctype} and name like CONCAT('%', #{keyword}, '%') order by ptime desc limit #{startpos}, 8")
    List<CourseMain> getCourseListNewACK(@Param("ctype") String ctype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 指定科目类别获取最新发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype=#{ctype} and stype=#{stype} and name like CONCAT('%', #{keyword}, '%') order by ptime desc limit #{startpos}, 8")
    List<CourseMain> getCourseListNewCKW(@Param("stype") Integer stype, @Param("ctype") String ctype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 未指定科目类别获取最热发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype=#{ctype} and name like CONCAT('%', #{keyword}, '%') order by hcount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListHotACK(@Param("ctype") String ctype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 指定科目类别获取最热发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype=#{ctype} and stype=#{stype} and name like CONCAT('%', #{keyword}, '%') order by hcount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListHotCKW(@Param("stype") Integer stype, @Param("ctype") String ctype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 未指定科目类别获取最多发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype=#{ctype} and name like CONCAT('%', #{keyword}, '%') order by ccount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListMoreACK(@Param("ctype") String ctype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);

    /**
     * 指定科目类别获取最多发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Select("select * from course_main where ctype=#{ctype} and stype=#{stype} and name like CONCAT('%', #{keyword}, '%') order by ccount desc limit #{startpos}, 8")
    List<CourseMain> getCourseListMoreCKW(@Param("stype") Integer stype, @Param("ctype") String ctype, @Param("keyword") String keyword, @Param("startpos") Integer startpos);
}
