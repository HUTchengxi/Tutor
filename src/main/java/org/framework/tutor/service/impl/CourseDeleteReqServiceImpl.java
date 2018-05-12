package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseDeleteReq;
import org.framework.tutor.mapper.CourseDeleteReqMapper;
import org.framework.tutor.service.CourseDeleteReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 课程下线申请
 * @date 2018年04月15日
 */
@Component
public class CourseDeleteReqServiceImpl implements CourseDeleteReqService {

    @Autowired
    private CourseDeleteReqMapper courseDeleteReqMapper;

    @Override
    public CourseDeleteReq getByCid(Integer cid) {
        return courseDeleteReqMapper.getByCid(cid);
    }

    @Override
    public void addCourseDeleteReq(Integer cid, String username, String descript) {
        courseDeleteReqMapper.addCourseDeleteReq(cid, username, descript);
    }

    @Override
    public void updateCourseDeleteReq(Integer cid, String descript) {
        courseDeleteReqMapper.updateCourseDeleteReq(cid, descript);
    }

    @Override
    public List<CourseDeleteReq> getAllLimit(String courName, Integer offset, Integer pageSize) {
        return courseDeleteReqMapper.getAllLimit(courName, offset, pageSize);
    }

    @Override
    public Integer getAllCount(String courName) {
        return courseDeleteReqMapper.getAllCount(courName);
    }

    @Override
    public List<CourseDeleteReq> getRespAllLimit(String courName, Integer status, Integer offset, Integer pageSize) {
        return courseDeleteReqMapper.getRepsAllLimit(courName, status, offset, pageSize);
    }

    @Override
    public Integer getRespAll(String courName, Integer status) {
        return courseDeleteReqMapper.getRespAll(courName, status);
    }

    @Override
    public CourseDeleteReq getById(Integer reqid) {
        return courseDeleteReqMapper.getById(reqid);
    }
}
