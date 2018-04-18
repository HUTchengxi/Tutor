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
