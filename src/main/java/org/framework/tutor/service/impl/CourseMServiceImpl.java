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
    public List<CourseMain> getCourseListMoreAC(String ctype, Integer startpos) {

        return courseMMapper.getCourseListMoreAC(ctype, startpos);
    }

    /**
     * 未指定主类别获取最新发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNewKW(String keyword, Integer startpos) {

        return courseMMapper.getCourseListNewKW(keyword, startpos);
    }

    /**
     * 指定主类别获取最新发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNewKW(Integer stype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListNewSKW(stype, keyword, startpos);
    }

    /**
     * 未定主类别获取最热发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHotKW(String keyword, Integer startpos) {

        return courseMMapper.getCourseListHotKW(keyword, startpos);
    }

    /**
     * 指定主类别获取最热发布课程数据(关键字)
     * @param stype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHotSKW(Integer stype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListHotSKW(stype, keyword, startpos);
    }

    /**
     * 未指定主类别获取最多发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMoreKW(String keyword, Integer startpos) {

        return courseMMapper.getCourseListMoreKW(keyword, startpos);
    }

    /**
     * 指定主类别获取最多发布课程数据(关键字)
     * @param keyword
     * @param startpos
     * @param stype
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMoreSKW(Integer stype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListMoreSKW(stype, keyword, startpos);
    }

    /**
     * 未指定科目类别获取最新发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNewACK(String ctype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListNewACK(ctype, keyword, startpos);
    }

    /**
     * 指定科目类别获取最新发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListNewKW(Integer stype, String ctype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListNewCKW(stype, ctype, keyword, startpos);
    }

    /**
     * 未指定科目类别获取最热发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHotACK(String ctype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListHotACK(ctype, keyword, startpos);
    }

    /**
     * 指定科目类别获取最热发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListHotKW(Integer stype, String ctype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListHotCKW(stype, ctype, keyword, startpos);
    }

    /**
     * 未指定科目类别获取最多发布课程数据(关键字)
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMoreACK(String ctype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListMoreACK(ctype, keyword, startpos);
    }

    /**
     * 指定科目类别获取最热发布课程数据(关键字)
     * @param stype
     * @param ctype
     * @param keyword
     * @param startpos
     * @return
     */
    @Override
    public List<CourseMain> getCourseListMoreKW(Integer stype, String ctype, String keyword, Integer startpos) {

        return courseMMapper.getCourseListMoreCKW(stype, ctype, keyword, startpos);
    }

    /**
     * 获取所有课程数
     * @return
     */
    @Override
    public Integer getCourseCount() {

        return courseMMapper.getCourseCount();
    }

    /**
     * 关键字获取课程数
     * @param keyword
     * @return
     */
    @Override
    public Integer getCourseCountK(String keyword) {

        return courseMMapper.getCourseCountK(keyword);
    }

    /**
     * 科目类别获取课程数
     * @param stype
     * @return
     */
    @Override
    public Integer getCourseCountS(Integer stype) {

        return courseMMapper.getCourseCountS(stype);
    }

    /**
     * 关键字+科目类别获取课程数
     * @param stype
     * @param keyword
     * @return
     */
    @Override
    public Integer getCourseCountSK(Integer stype, String keyword) {

        return courseMMapper.getCourseCountSK(stype, keyword);
    }

    /**
     * 课程主类别获取课程数
     * @param ctype
     * @return
     */
    @Override
    public Integer getCourseCountC(String ctype) {

        return courseMMapper.getCourseCountC(ctype);
    }

    /**
     * 课程主类别+关键字获取课程数
     * @param ctype
     * @param keyword
     * @return
     */
    @Override
    public Integer getCourseCountCK(String ctype, String keyword) {

        return courseMMapper.getCourseCountCK(ctype, keyword);
    }

    /**
     * 课程主类别+科目类别获取课程数
     * @param ctype
     * @param stype
     * @return
     */
    @Override
    public Integer getCourseCountCS(String ctype, Integer stype) {

        return courseMMapper.getCourseCountCS(ctype, stype);
    }

    /**
     * 课程主类别+科目类别+关键字获取课程数
     * @param ctype
     * @param stype
     * @param keyword
     * @return
     */
    @Override
    public Integer getCourseCountCSK(String ctype, Integer stype, String keyword) {

        return courseMMapper.getCourseCountCSK(ctype, stype, keyword);
    }
}
