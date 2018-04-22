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

import org.framework.tutor.domain.UserMessageDelete;
import org.framework.tutor.mapper.UserMessageDeleteMapper;
import org.framework.tutor.service.UserMessageDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月22日
 */
@Component
public class UserMessageDeleteServiceImpl implements UserMessageDeleteService {

    @Autowired
    private UserMessageDeleteMapper userMessageDeleteMapper;

    @Override
    public Integer addDelete(Integer did, String username) {
        return userMessageDeleteMapper.addDelete(did, username);
    }

    @Override
    public UserMessageDelete checkDeleteStatus(Integer id, String username) {
        return userMessageDeleteMapper.checkDeleteStatus(id, username);
    }
}
