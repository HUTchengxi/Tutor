package org.framework.tutor.service;

import org.framework.tutor.domain.CourseOrderManager;

import java.util.List;

public interface CourseOrderManagerService {

    /**
     *
     * @Description 获取所有oid对应的订单总数
     * @param [orderId]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/18
     */
    Integer getCountByOidList(String orderId);

    /**
     *
     * @Description 获取指定家教的所有课程对应的订单数据
     * @param [orderId, i, pageSize]
     * @return java.util.List<org.framework.tutor.domain.CourseOrderManager>
     * @author yinjimin
     * @date 2018/4/18
     */
    List<CourseOrderManager> getByOidList(String orderId, int i, Integer pageSize);

    /**
     *
     * @Description 获取指定家教的指定课程名称对应的订单数据
     * @param [username, courseName, i, pageSize]
     * @return java.util.List<org.framework.tutor.domain.CourseOrderManager>
     * @author yinjimin
     * @date 2018/4/19
     */
    List<CourseOrderManager> getByUserAndName(String username, String courseName, int i, Integer pageSize);

    /**
     *
     * @Description 通过code获取对应的订单
     * @param [code]
     * @return org.framework.tutor.domain.CourseOrderManager
     * @author yinjimin
     * @date 2018/4/19
     */
    CourseOrderManager getByCode(String code);

    /**
     *
     * @Description 更新家教处理状态
     * @param [code, tutorStatus, tutorInfo]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    void updateTutorStatus(String code, Integer tutorStatus, String tutorInfo);

    /**
     *
     * @Description 获取对应限制数量的所有异常订单数据
     * @param [courseName, offset, pageSize]
     * @return java.util.List<org.framework.tutor.domain.CourseOrderManager>
     * @author yinjimin
     * @date 2018/4/19
     */
    List<CourseOrderManager> getAllErrsLimit(String courseName, Integer offset, Integer pageSize);

    /**
     *
     * @Description 通过
     * @param [courseName, tutorName, offset, pageSize]
     * @return java.util.List<org.framework.tutor.domain.CourseOrderManager>
     * @author yinjimin
     * @date 2018/4/19
     */
    List<CourseOrderManager> getErrsByTutorAndCourse(String courseName, String tutorName, Integer offset, Integer pageSize);

    List<CourseOrderManager> getErrsByUserAndCourse(String courseName, String userName, Integer offset, Integer pageSize);

    List<CourseOrderManager> getErrsByUserAndTutor(String courseName, String userName, String tutorName, Integer offset, Integer pageSize);

    Integer getAllErrs();
}
