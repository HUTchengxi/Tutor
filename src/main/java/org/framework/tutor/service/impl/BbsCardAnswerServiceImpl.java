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

import org.framework.tutor.mapper.BbsCardAnswerMapper;
import org.framework.tutor.service.BbsCardAnswerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yinjimin
 * @Description: 帖子答案服务层
 * @date 2018年04月05日
 */
public class BbsCardAnswerServiceImpl implements BbsCardAnswerService {

    @Autowired
    private BbsCardAnswerMapper bbsCardAnswerMapper;
}
