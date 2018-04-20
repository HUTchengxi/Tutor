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

import org.framework.tutor.mapper.SysEmailManageMapper;
import org.framework.tutor.service.SysEmailManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月20日
 */
@Service
public class SysEmailMessageServiceImpl implements SysEmailManageService {

    @Autowired
    private SysEmailManageMapper sysEmailManageMapper;

    @Override
    public void sendEmail(String send, String address, String theme, String email, Integer status) {
        sysEmailManageMapper.sendEmail(send, address, theme, email, status);
    }
}
