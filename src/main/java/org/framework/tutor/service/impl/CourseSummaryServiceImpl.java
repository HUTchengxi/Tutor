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

import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.mapper.CourseSummaryMapper;
import org.framework.tutor.service.CourseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 课程概述
 * @date 2018年04月15日
 */
@Component
public class CourseSummaryServiceImpl implements CourseSummaryService {

    @Autowired
    private CourseSummaryMapper courseSummaryMapper;

    @Override
    public List<CourseSummary> getCourseSummaryInfo(Integer cid) {
        return courseSummaryMapper.getCourseSummaryInfo(cid);
    }

    @Override
    public Integer updateCourseSummary(Integer id, String title, String descript) {
        return courseSummaryMapper.updateCourseSummary(id, title, descript);
    }

    @Override
    public void addCourseSummary(String username, Integer id, String sumTitle1, String sumDescript1) {
        courseSummaryMapper.addCourseSummary(username, id, sumTitle1, sumDescript1);
    }
}
