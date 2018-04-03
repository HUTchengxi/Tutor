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

import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.mapper.BbsCardMapper;
import org.framework.tutor.service.BbsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 论坛帖子服务层实现类
 * @date 2018年03月31日
 */
@Component
public class BbsCardServiceImpl implements BbsCardService {

    @Autowired
    private BbsCardMapper bbsCardMapper;


    @Override
    public Integer getMyCardCount(String username) {

        return bbsCardMapper.getMyCardCount(username);
    }


    @Override
    public BbsCard getByTitle(String title) {

        return bbsCardMapper.getByTitle(title);
    }


    @Override
    public void publishCard(String username, String title, String imgsrc, String descript) {

        bbsCardMapper.publishCard(username, title, imgsrc, descript);
    }

    @Override
    public List<BbsCard> searchCard(String keyword) {

        return bbsCardMapper.searchCard(keyword);
    }

    @Override
    public List<BbsCard> loadHotCard() {

        return bbsCardMapper.loadHotCard();
    }
}
