package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseChapter;
import org.framework.tutor.mapper.CourseChapterMapper;
import org.framework.tutor.service.CourseChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程章节目录服务层实现类
 * @author chengxi
 */
@Component
public class CourseChapterServiceImpl implements CourseChapterService {

    @Autowired
    private CourseChapterMapper courseChapterMapper;

    /**
     * 获取指定课程章节目录数据
     * @param cid
     * @return
     */
    @Override
    public List<CourseChapter> getCourseChapter(Integer cid) {

        return courseChapterMapper.getCourseChapter(cid);
    }

    @Override
    public CourseChapter getById(Integer id) {
        return courseChapterMapper.getById(id);
    }

    @Override
    public void deleteChapter(Integer id) {
        courseChapterMapper.deleteChapter(id);
    }

    @Override
    public void addChapter(Integer cid, Integer ord, String title, String descript) {
        courseChapterMapper.addChapter(cid, ord, title, descript);
    }

    @Override
    public void modChapter(Integer id, String title, String descript) {
        courseChapterMapper.modChapter(id, title, descript);
    }

    @Override
    public Integer getLastOrd(Integer cid) {
        return courseChapterMapper.getLastOrg(cid);
    }
}
