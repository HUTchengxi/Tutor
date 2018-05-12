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

    @Override
    public List<CourseSummary> getCourseSummary(Integer cid) {
        return courseSummaryMapper.getCourseSummary(cid);
    }
}
