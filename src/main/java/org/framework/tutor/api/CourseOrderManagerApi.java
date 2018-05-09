package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseOrderManagerApi {

    /**
     * @param [paramMap, request, response]
     * @return void
     * @Description 获取指定家教课程订单列表
     * @author yinjimin
     * @date 2018/4/18
     */
    public void getCourseOrderList(ParamMap paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取订单详情数据
     * @param [code, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    public void getOrderDetail(String code, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 更新家教处理状态
     * @param [tutorStatus, tutorInfo, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    public void updateTutorStatus(String code, Integer tutorStatus, String tutorInfo, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取异常订单数据
     * @param [paramMap, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    public void getErrorOrderList(ParamMap paramMap, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 查看指定异常订单详情数据
     * @param [code, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    public void getErrorOrderDetail(String code, HttpServletResponse response) throws IOException;
}
