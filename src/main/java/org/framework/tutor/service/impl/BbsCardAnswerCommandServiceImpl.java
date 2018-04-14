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

import org.framework.tutor.domain.BbsCardAnswerCommand;
import org.framework.tutor.mapper.BbsCardAnswerCommandMapper;
import org.framework.tutor.service.BbsCardAnswerCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 帖子回答的评论服务层实现类
 * @date 2018年04月10日
 */
@Component
public class BbsCardAnswerCommandServiceImpl implements BbsCardAnswerCommandService {

    @Autowired
    private BbsCardAnswerCommandMapper bbsCardAnswerCommandMapper;

    @Override
    public List<BbsCardAnswerCommand> getCommandListByAid(Integer aid, Integer startpos) {
        return bbsCardAnswerCommandMapper.getCommandListByAid(aid, startpos);
    }

    @Override
    public Integer getCurrentFloor(Integer cardid, Integer aid) {
        return bbsCardAnswerCommandMapper.getCurrentFloor(cardid, aid);
    }

    @Override
    public void publishCommand(String username, Integer cardid, Integer aid, String answer, Integer floor, Integer repfloor) {
        bbsCardAnswerCommandMapper.publishCommand(username, cardid, aid, answer, floor, repfloor);
    }

    @Override
    public Integer getComcountByUser(String username) {
        return bbsCardAnswerCommandMapper.getComCountByUser(username);
    }

    @Override
    public List<BbsCardAnswerCommand> getMyCommandInfo(String username) {
        return  bbsCardAnswerCommandMapper.getMyCommandInfo(username);
    }
}
