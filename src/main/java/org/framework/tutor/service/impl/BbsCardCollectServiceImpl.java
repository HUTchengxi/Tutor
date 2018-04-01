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

import org.framework.tutor.mapper.BbsCardCollectMapper;
import org.framework.tutor.service.BbsCardCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yinjimin
 * @Description: 用户帖子收藏服务层实现类
 * @date 2018年04月01日
 */
@Component
public class BbsCardCollectServiceImpl implements BbsCardCollectService {

    @Autowired
    private BbsCardCollectMapper bbsCardCollectMapper;

    @Override
    public Integer getMyCollectCount(String username) {
        return bbsCardCollectMapper.getMyCollectCount(username);
    }
}
