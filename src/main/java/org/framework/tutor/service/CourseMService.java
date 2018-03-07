package org.framework.tutor.service;

import org.framework.tutor.domain.CourseMain;

import java.util.List;

/**
 * 课程表服务层接口
 * @author chengxi
 */
public interface CourseMService {

    /**
     * 不限学科进行最新发布排序
     * @param stype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNew(Integer stype, Integer startpos);

    /**
     * 不限学科进行最热搜索排序
     * @param stype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHot( Integer stype, Integer startpos);

    /**
     * 不限学科进行最多评论排序
     * @param stype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMore( Integer stype, Integer startpos);

    /**
     * 指定学科进行最新发布排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNew( Integer stype, String ctype, Integer startpos);

    /**
     * 指定学科进行最热搜索排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHot( Integer stype, String ctype, Integer startpos);

    /**
     * 指定学科进行最多评论排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMore( Integer stype, String ctype, Integer startpos);

    /**
     * 获取所有科目类别
     * @param stype
     * @return
     */
    List<CourseMain> getCourseType(String stype);

    /**
     * 获取关键子对应的所有课程数据
     * @param keyword
     * @return
     */
    List<CourseMain> courseSearch(String keyword);

    /**
     * 获取指定id的课程数据
     * @param id
     * @return
     */
    CourseMain getCourseById(Integer id);

    /**
     * 未指定主类别获取最热搜索课程数据
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHot(Integer startpos);

    /**
     * 未指定主类别获取最多评论课程数据
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMore(Integer startpos);

    /**
     * 未指定主类别获取最新发布课程数据
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNew(Integer startpos);

    /**
     * 获取科目类别的最新发布课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNewAC(String ctype, Integer startpos);

    /**
     * 获取科目类别的最热搜索课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHotAC(String ctype, Integer startpos);

    /**
     * 获取科目类别的最多评论课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMoreac(String ctype, Integer startpos);
}