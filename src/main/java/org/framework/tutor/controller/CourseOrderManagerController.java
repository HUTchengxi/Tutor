package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseOrderManagerApi;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 已购订单管理表
 * @author yinjimin
 * @date 2018年04月18日
 */
@RestController
@RequestMapping("/courseordermanager_con")
public class CourseOrderManagerController {

    @Autowired
    private CourseOrderManagerApi courseOrderManagerApi;

    /**
     * @Description 获取指定家教课程订单列表
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getcourseorderlist")
    public String getCourseOrderList(@RequestBody ParamMap paramMap) throws IOException {

        return courseOrderManagerApi.getCourseOrderList(paramMap);
    }

    /**
     * @Description 获取订单详情数据
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/getorderdetail")
    public String getOrderDetail(@RequestParam String code) throws IOException {

        return courseOrderManagerApi.getOrderDetail(code);
    }

    /**
     *
     * @Description 更新家教处理状态
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/updatetutorstatus")
    public String updateTutorStatus(@RequestParam String code, @RequestParam Integer tutorStatus, @RequestParam String tutorInfo) throws IOException {

        return courseOrderManagerApi.updateTutorStatus(code, tutorStatus, tutorInfo);
    }

    /**
     * @Description 获取异常订单数据
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/geterrororderlist")
    public String getErrorOrderList(@RequestBody ParamMap paramMap) throws IOException {

        return courseOrderManagerApi.getErrorOrderList(paramMap);
    }

    /**
     * @Description 查看指定异常订单详情数据
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/geterrororderdetail")
    public String getErrorOrderDetail(@RequestParam String code) throws IOException {

        return courseOrderManagerApi.getErrorOrderDetail(code);
    }
}
