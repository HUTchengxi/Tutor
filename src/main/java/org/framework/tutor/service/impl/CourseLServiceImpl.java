package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.mapper.CourseLMapper;
import org.framework.tutor.service.CourseLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程记录表服务层实现类
 * @author chengxi
 */

@Component
public class CourseLServiceImpl implements CourseLService{

    @Autowired
    private CourseLMapper courseLMapper;

    /**
     * 获取我的课程记录
     * @param username
     * @return
     */
    @Override
    public List<CourseLog> getUserlog(String username) {

        return courseLMapper.getUserlog(username);
    }

    /**
     * 删除指定的课程记录
     * @param id
     * @return
     */
    @Override
    public boolean delLog(Integer id) {

        return courseLMapper.delLog(id);
    }
}
