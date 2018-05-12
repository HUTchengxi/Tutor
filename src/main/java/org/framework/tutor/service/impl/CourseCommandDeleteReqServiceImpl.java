package org.framework.tutor.service.impl;

import org.framework.tutor.mapper.CourseCommandDeleteReqMapper;
import org.framework.tutor.service.CourseCommandDeleteReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yinjimin
 * @Description: 评论删除待审
 * @date 2018年04月18日
 */
@Component("courseCommandDeleteReqService")
public class CourseCommandDeleteReqServiceImpl implements CourseCommandDeleteReqService {

    @Autowired
    private CourseCommandDeleteReqMapper courseCommandDeleteReqMapper;

    @Override
    public void addCommandDeleteReq(String username, Integer cid, String info) {
        courseCommandDeleteReqMapper.addCommandDeleteReq(username, cid, info);
    }
}
