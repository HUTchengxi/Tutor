package org.framework.tutor.service;

import org.framework.tutor.domain.CourseLog;

import java.util.List;

/**
 * 课程记录服务层接口
 * @author chengxi
 */
public interface CourseLService {

    /**
     * 获取我的课程记录
     * @param username
     * @return
     */
    List<CourseLog> getUserlog(String username);

    /**
     * 删除指定的课程记录
     * @param id
     * @return
     */
    boolean delLog(Integer id);
}
