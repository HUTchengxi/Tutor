package org.framework.tutor.service;

import org.apache.ibatis.annotations.Param;
import org.framework.tutor.domain.CourseSummary;

import java.util.List;

public interface CourseSummaryService {
    
    /**  
     *    
     * @Description 获取指定课程的课程概述
     * @param [cid]    
     * @return java.util.List<org.framework.tutor.domain.CourseSummary>
     * @author yinjimin  
     * @date 2018/4/15
     */  
    List<CourseSummary> getCourseSummaryInfo(Integer cid);


    /**
     *
     * @Description 更新课程概述
     * @param [id, title, descript]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/15
     */
    Integer updateCourseSummary(Integer id, String title, String descript);

    void addCourseSummary(String username, Integer id, String sumTitle1, String sumDescript1);

    List<CourseSummary> getCourseSummary(Integer cid);
}
