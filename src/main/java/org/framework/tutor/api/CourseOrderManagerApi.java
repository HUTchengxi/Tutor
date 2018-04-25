package org.framework.tutor.api;

import com.google.gson.Gson;
import org.framework.tutor.controller.CourseOrderManagerController;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.domain.CourseOrderManager;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.framework.tutor.service.CourseOrderManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
