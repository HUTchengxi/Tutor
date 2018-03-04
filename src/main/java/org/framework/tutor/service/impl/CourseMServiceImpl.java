package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.mapper.CourseMMapper;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程表服务层实现类
 * @author chengxi
 */
@Component
public class CourseMServiceImpl implements CourseMService {

    @Autowired
    private CourseMMapper courseMMapper;


    /**
     * 不限学科进行最新发布排序
     * @param stype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNew(Integer stype, Integer startpos) {

        return courseMMapper.getCourseListANew(stype, startpos);
    }

    /**
     * 不限学科进行最热搜索排序
     * @param stype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHot(Integer stype, Integer startpos) {

        return courseMMapper.getCourseListAHot(stype, startpos);
    }

    /**
     * 不限学科进行最多评论排序
     * @param stype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMore(Integer stype, Integer startpos) {

        return courseMMapper.getCourseListAMore(stype, startpos);
    }

    /**
     * 指定学科进行最新发布排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNew(Integer stype, String ctype, Integer startpos) {

        return courseMMapper.getCourseListNew(stype, ctype, startpos);
    }

    /**
     * 指定学科进行最热搜索排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHot(Integer stype, String ctype, Integer startpos) {

        return courseMMapper.getCourseListHot(stype, ctype, startpos);
    }

    /**
     * 指定学科进行最多评论排序
     * @param stype
     * @param ctype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMore(Integer stype, String ctype, Integer startpos) {

        return courseMMapper.getCourseListMore(stype, ctype, startpos);
    }

    /**
     * 获取所有科目类别
     * @param stype
     * @return
     */
    @Override
    public List<CourseMain> getCourseType(String stype) {

        return courseMMapper.getCourseType(stype);
    }

    /**
     * 获取关键字对应的所有课程数据
     * @param keyword
     * @return
     */
    @Override
    public List<CourseMain> courseSearch(String keyword) {

        return courseMMapper.courseSearch(keyword);
    }

    /**
     * 获取指定id的课程数据
     * @param id
     * @return
     */
    @Override
    public CourseMain getCourseById(Integer id) {

        return courseMMapper.getCourseById(id);
    }

    /**
     * 未指定主类别获取最热搜索课程数据
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHot(Integer startpos) {

        return courseMMapper.getCourseListAHotA(startpos);
    }

    /**
     * 未指定主类别获取最多评论课程数据
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMore(Integer startpos) {

        return courseMMapper.getCourseListAMoreA(startpos);
    }

    /**
     * 未指定主类别获取最新发布课程数据
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNew(Integer startpos) {

        return courseMMapper.getCourseListANewA(startpos);
    }

    /**
     * 获取科目类别的最新发布课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNewAC(String ctype, Integer startpos) {

        return courseMMapper.getCourseListNewAC(ctype, startpos);
    }

    /**
     * 获取科目类别的最热搜索课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHotAC(String ctype, Integer startpos) {

        return courseMMapper.getCourseListHotAC(ctype, startpos);
    }

    /**
     * 获取科目剋别的最多评论课程数据
     * @param ctype
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMoreac(String ctype, Integer startpos) {

        return courseMMapper.getCourseListMoreAC(ctype, startpos);
    }
}
