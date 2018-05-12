package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseOrderManagerApi {

    /**
     * @Description 获取指定家教课程订单列表
     * @param paramMap
     */
    public String getCourseOrderList(ParamMap paramMap) throws IOException;

    /**
     * @Description 获取订单详情数据
     * @param code 订单编号
     */
    public String getOrderDetail(String code) throws IOException;

    /**
     * @Description 更新家教处理状态
     * @param code 订单编号
     * @param tutorStatus 处理状态
     * @param tutorInfo 更新备注
     */
    public String updateTutorStatus(String code, Integer tutorStatus, String tutorInfo) throws IOException;

    /**
     * @Description 获取异常订单数据
     * @param paramMap
     */
    public String getErrorOrderList(ParamMap paramMap) throws IOException;

    /**
     * @Description 查看指定异常订单详情数据
     * @param code 订单编号
     */
    public String getErrorOrderDetail(String code) throws IOException;
}
