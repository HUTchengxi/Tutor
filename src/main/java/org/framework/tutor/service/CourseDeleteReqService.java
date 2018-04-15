package org.framework.tutor.service;

import org.framework.tutor.domain.CourseDeleteReq;

public interface CourseDeleteReqService {

    /**
     *
     * @Description 获取对应的课程的申请数据
     * @param [cid]
     * @return org.framework.tutor.domain.CourseDeleteReq
     * @author yinjimin
     * @date 2018/4/15
     */
    CourseDeleteReq getByCid(Integer cid);

    /**
     *
     * @Description 添加申请
     * @param [cid, username, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    void addCourseDeleteReq(Integer cid, String username, String descript);


    /**
     *
     * @Description 更新申请数据
     * @param [cid, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    void updateCourseDeleteReq(Integer cid, String descript);
}
