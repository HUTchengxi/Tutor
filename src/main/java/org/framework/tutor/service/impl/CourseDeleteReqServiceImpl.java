/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseDeleteReq;
import org.framework.tutor.mapper.CourseDeleteReqMapper;
import org.framework.tutor.service.CourseDeleteReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
