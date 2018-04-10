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

import org.framework.tutor.domain.BbsCardAnswer;
import org.framework.tutor.mapper.BbsCardAnswerMapper;
import org.framework.tutor.service.BbsCardAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 帖子答案服务层
 * @date 2018年04月05日
 */
@Component
public class BbsCardAnswerServiceImpl implements BbsCardAnswerService {

    @Autowired
    private BbsCardAnswerMapper bbsCardAnswerMapper;

    @Override
    public List<BbsCardAnswer> getByCardid(Integer cardId) {
        return bbsCardAnswerMapper.getByCardid(cardId);
    }

    @Override
    public BbsCardAnswer checkIsExistAnswer(Integer cardId, String username) {
        return bbsCardAnswerMapper.checkIsExistAnswer(cardId, username);
    }

    @Override
    public void addAnswer(Integer cardId, String username, String answer) {
        bbsCardAnswerMapper.addAnswer(cardId, username, answer);
    }

    @Override
    public void addGcount(Integer aid) {
        bbsCardAnswerMapper.addGcount(aid);
    }

    @Override
    public void addBcount(Integer aid) {
        bbsCardAnswerMapper.addBcount(aid);
    }
}
