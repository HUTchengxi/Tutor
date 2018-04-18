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
    List<CourseMain> getCourseListMoreAC(String ctype, Integer startpos);

    /**
     * 未指定主类别获取最新发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNewKW(String keyword, Integer startpos);

    /**
     * 指定主类别获取最新发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNewKW(Integer stype, String keyword, Integer startpos);

    /**
     * 未指定主类别获取最热发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHotKW(String keyword, Integer startpos);

    /**
     * 指定主类别获取最热发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHotSKW(Integer stype, String keyword, Integer startpos);

    /**
     * 未指定主类别获取最多发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMoreKW(String keyword, Integer startpos);

    /**
     * 指定主类别获取最多发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @param stype
     * @return
     */
    List<CourseMain> getCourseListMoreSKW(Integer stype, String keyword, Integer startpos);

    /**
     * 未指定科目类别获取最新发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNewACK(String ctype, String keyword, Integer startpos);

    /**
     * 指定科目类别获取最新发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListNewKW(Integer stype, String ctype, String keyword, Integer startpos);

    /**
     * 未指定科目类别获取最热发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHotACK(String ctype, String keyword, Integer startpos);

    /**
     * 指定科目类别获取最热发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListHotKW(Integer stype, String ctype, String keyword, Integer startpos);

    /**
     * 未指定科目类别获取最多发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMoreACK(String ctype, String keyword, Integer startpos);

    /**
     * 指定科目类别获取最多发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    List<CourseMain> getCourseListMoreKW(Integer stype, String ctype, String keyword, Integer startpos);

    /**
     * 获取所有课程数
     * @return
     */
    Integer getCourseCount();

    /**
     * 关键字获取课程数
     * @param keyword
     * @return
     */
    Integer getCourseCountK(String keyword);

    /**
     * 科目类别获取课程数
     * @param stype
     * @return
     */
    Integer getCourseCountS(Integer stype);

    /**
     * 科目类别+关键字获取课程数
     * @param stype
     * @param keyword
     * @return
     */
    Integer getCourseCountSK(Integer stype, String keyword);

    /**
     * 课程主类别获取课程数
     * @param ctype
     * @return
     */
    Integer getCourseCountC(String ctype);

    /**
     * 课程主类别+关键字获取课程数
     * @param ctype
     * @param keyword
     * @return
     */
    Integer getCourseCountCK(String ctype, String keyword);

    /**
     * 课程主类别+科目类别获取课程数
     * @param ctype
     * @param stype
     * @return
     */
    Integer getCourseCountCS(String ctype, Integer stype);

    /**
     * 课程主类别+科目类别+关键字获取课程数
     * @param ctype
     * @param stype
     * @param keyword
     * @return
     */
    Integer getCourseCountCSK(String ctype, Integer stype, String keyword);


    /**
     *
     * @Description 获取指定用户的发布课程数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.domain.CourseMain>
     * @author yinjimin
     * @date 2018/4/14
     */
    List<CourseMain> getMyPublish(String username);

    /**
     *
     * @Description 获取所有科目类别
     * @param []
     * @return java.util.List<org.framework.tutor.domain.CourseMain>
     * @author yinjimin
     * @date 2018/4/15
     */
    List<CourseMain> getAllCourseType();


    /**
     *
     * @Description 发布课程
     * @param [username, name, originalFilename, stype, ctype, jcount, descript, price, total]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    void publishCourse(String username, String name, String originalFilename, Integer stype, String ctype, Integer jcount, String descript, Double price, Integer total);


    /**
     *
     * @Description 通过一连串数据获取课程
     * @param [username, name, stype, ctype]
     * @return org.framework.tutor.domain.CourseMain
     * @author yinjimin
     * @date 2018/4/15
     */
    CourseMain getByName(String username, String name, Integer stype, String ctype);

    /**
     *
     * @Description 判断课程名称是否已经存在
     * @param [name]
     * @return org.framework.tutor.domain.CourseMain
     * @author yinjimin
     * @date 2018/4/18
     */
    CourseMain checkIsexistName(String name);

    /**
     *
     * @Description 获取指定家教的课程数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.controller.CourseMain>
     * @author yinjimin
     * @date 2018/4/18
     */
    List<CourseMain> getMyCourseList(String username);

    List<CourseMain> getByCoursename(String courseName);
}
