package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseChapter;
import org.framework.tutor.mapper.CourseChMapper;
import org.framework.tutor.service.CourseChService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 课程章节目录服务层实现类
 * @author chengxi
 */
@Component
public class CourseChServiceImpl implements CourseChService {

    @Autowired
    private CourseChMapper courseChMapper;

    /**
     * 获取指定课程章节目录数据
     * @param cid
     * @return
     */
    @Override
    public List<CourseChapter> getCourseChapter(Integer cid) {

        return courseChMapper.getCourseChapter(cid);
    }
}
