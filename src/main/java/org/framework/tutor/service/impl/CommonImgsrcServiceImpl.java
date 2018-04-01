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

import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.mapper.CommonImgsrcMapper;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 通用图片数据服务层实现类
 * @date 2018年04月01日
 */
@Component
public class CommonImgsrcServiceImpl implements CommonImgsrcService {

    @Autowired
    private CommonImgsrcMapper commonImgsrcMapper;

    @Override
    public List<CommonImgsrc> getAll() {

        return commonImgsrcMapper.getAll();
    }
}
