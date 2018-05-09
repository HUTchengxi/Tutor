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

import org.framework.tutor.domain.UserFeedback;
import org.framework.tutor.mapper.UserFeedbackMapper;
import org.framework.tutor.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年05月09日
 */
@Component
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackMapper userFeedbackMapper;

    @Override
    public List<UserFeedback> getMyFeedback(String username) {
        return userFeedbackMapper.getMyFeedback(username);
    }

    @Override
    public UserFeedback getByUserAndId(Integer id, String username) {
        return userFeedbackMapper.getByUserAndId(id, username);
    }

    @Override
    public Integer modMyFeedbackStatus(Integer id, Integer status) {
        return userFeedbackMapper.modMyFeedbackStatus(id, status);
    }

    @Override
    public Integer saveMyFeedback(String username, String info) {
        return userFeedbackMapper.saveMyFeedback(username, info);
    }

    @Override
    public List<UserFeedback> getUserFeedback(String username, Integer status, Integer offset, Integer pageSize) {
        return userFeedbackMapper.getUserFeedback(username, status, offset, pageSize);
    }

    @Override
    public List<UserFeedback> getUserFeedback(String username, Integer offset, Integer pageSize) {
        return userFeedbackMapper.getUserFeedbackNoStatus(username, offset, pageSize);
    }

    @Override
    public Integer getUserFeedbackCount(String username, Integer status) {
        return userFeedbackMapper.getUserFeedbackCount(username, status);
    }

    @Override
    public Integer getUserFeedbackCount(String username) {
        return userFeedbackMapper.getUserFeedbackCountNoStatus(username);
    }

    @Override
    public Integer removeUserFeedback(Integer id) {
        return userFeedbackMapper.removeUserFeedback(id);
    }
}
