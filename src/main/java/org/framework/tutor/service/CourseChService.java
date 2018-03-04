package org.framework.tutor.service;

import org.framework.tutor.domain.CourseChapter;

import java.util.List;

public interface CourseChService {

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @return
     */
    List<CourseChapter> getCourseChapter(Integer cid);
}
