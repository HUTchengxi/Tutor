package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.mapper.CourseLogMapper;
import org.framework.tutor.service.CourseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程记录表服务层实现类
 * @author chengxi
 */

@Component
public class CourseLogServiceImpl implements CourseLogService {

    @Autowired
    private CourseLogMapper courseLogMapper;

    /**
     * 获取我的课程记录
     * @param username
     * @return
     */
    @Override
    public List<CourseLog> getUserlog(String username) {

        return courseLogMapper.getUserlog(username);
    }

    /**
     * 删除指定的课程记录
     * @param id
     * @return
     */
    @Override
    public boolean delLog(Integer id) {

        return courseLogMapper.delLog(id);
    }

    @Override
    public void addLog(Integer cid, String username) {
        courseLogMapper.addLog(cid, username);
    }

    @Override
    public Integer getUserlogCount(String username) {
        return courseLogMapper.getUserlogCount(username);
    }

    @Override
    public CourseLog getFirstLog(String username) {
        return courseLogMapper.getFirseLog(username);
    }
}
