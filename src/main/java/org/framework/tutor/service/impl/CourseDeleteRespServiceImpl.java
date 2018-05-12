package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.mapper.CourseDeleteRespMapper;
import org.framework.tutor.service.CourseDeleteRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月21日
 */
@Service
public class CourseDeleteRespServiceImpl implements CourseDeleteRespService {

    @Autowired
    private CourseDeleteRespMapper courseDeleteRespMapper;

    @Override
    public CourseDeleteResp getByRid(Integer id) {
        return courseDeleteRespMapper.getByRid(id);
    }

    @Override
    public void insertResp(Integer id, Integer status, String respDesc) {
        courseDeleteRespMapper.insertResp(id, status, respDesc);
    }

    @Override
    public void updateResp(Integer id, Integer status, String respDesc) {
        courseDeleteRespMapper.updateResp(id, status, respDesc);
    }
}
