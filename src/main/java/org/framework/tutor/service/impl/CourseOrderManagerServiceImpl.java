package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseOrderManager;
import org.framework.tutor.mapper.CourseOrderManagerMapper;
import org.framework.tutor.service.CourseOrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 已购订单管理表
 * @date 2018年04月18日
 */
@Service("courseOrderManagerService")
public class CourseOrderManagerServiceImpl implements CourseOrderManagerService {

    @Autowired
    private CourseOrderManagerMapper courseOrderManagerMapper;

    @Override
    public Integer getCountByOidList(String orderId) {
        return courseOrderManagerMapper.getCountByOidList(orderId);
    }

    @Override
    public List<CourseOrderManager> getByOidList(String orderId, int offset, Integer pageSize) {
        return courseOrderManagerMapper.getByOidList(orderId, offset, pageSize);
    }

    @Override
    public List<CourseOrderManager> getByUserAndName(String username, String courseName, int offset, Integer pageSize) {
        return courseOrderManagerMapper.getByUserAndName(username, courseName, offset, pageSize);
    }

    @Override
    public CourseOrderManager getByCode(String code) {
        return courseOrderManagerMapper.getByCode(code);
    }

    @Override
    public void updateTutorStatus(String code, Integer tutorStatus, String tutorInfo) {
        courseOrderManagerMapper.updateTutorStatus(code, tutorStatus, tutorInfo);
    }

    @Override
    public List<CourseOrderManager> getAllErrsLimit(String courseName, Integer offset, Integer pageSize) {
        return courseOrderManagerMapper.getAllErrsLimit(courseName, offset, pageSize);
    }

    @Override
    public List<CourseOrderManager> getErrsByTutorAndCourse(String courseName, String tutorName, Integer offset, Integer pageSize) {
        return courseOrderManagerMapper.getErrsByTutorAndCourse(courseName, tutorName, offset, pageSize);
    }

    @Override
    public List<CourseOrderManager> getErrsByUserAndCourse(String courseName, String userName, Integer offset, Integer pageSize) {
        return courseOrderManagerMapper.getErrsByUserAndCourse(courseName, userName, offset, pageSize);
    }

    @Override
    public List<CourseOrderManager> getErrsByUserAndTutor(String courseName, String userName, String tutorName, Integer offset, Integer pageSize) {
        return courseOrderManagerMapper.getErrsByUserAndTutor(courseName, userName, tutorName, offset, pageSize);
    }

    @Override
    public Integer getAllErrs() {
        return courseOrderManagerMapper.getAllErrs();
    }

    @Override
    public List<CourseOrderManager> getByReqid(Integer reqid) {
        return courseOrderManagerMapper.getByReqid(reqid);
    }

    @Override
    public void addCourseOrderManager(String code, Integer oid, Integer tutorStatus, Integer userStatus, String userInfo, String tutorInfo, String tutorName) {
        courseOrderManagerMapper.addCourseOrderManager(code, oid, tutorStatus, userStatus, userInfo, tutorInfo, tutorName);
    }
}
