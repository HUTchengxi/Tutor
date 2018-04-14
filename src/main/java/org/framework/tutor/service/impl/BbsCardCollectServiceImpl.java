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

import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.mapper.BbsCardCollectMapper;
import org.framework.tutor.service.BbsCardCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public BbsCardCollect checkCollectStatus(Integer cardId, String username) {
        return bbsCardCollectMapper.checkCollectStatus(cardId, username);
    }

    @Override
    public void collectCard(Integer cardId, String username) {
        bbsCardCollectMapper.collectCard(cardId, username);
    }

    @Override
    public void uncollectCard(Integer cardId, String username) {
        bbsCardCollectMapper.uncollectCard(cardId, username);
    }

    @Override
    public List<BbsCardCollect> getMyCollectInfo(String username) {
        return bbsCardCollectMapper.getMyCollectInfo(username);
    }
}
