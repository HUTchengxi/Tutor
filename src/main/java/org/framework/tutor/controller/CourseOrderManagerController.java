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
package org.framework.tutor.controller;

import com.google.gson.Gson;
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
import org.springframework.web.bind.annotation.*;

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

/**
 * @Description: 已购订单管理表
 * @author yinjimin
 * @date 2018年04月18日
 */
@RestController
@RequestMapping("/courseordermanager_con")
public class CourseOrderManagerController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseOrderManagerController.class);

    @Autowired
    private CourseOrderManagerService courseOrderManagerService;

    @Autowired
    private CourseOService courseOService;

    @Autowired
    private CourseMService courseMService;

    /**
     * @param [paramMap, request, response]
     * @return void
     * @Description 获取指定家教课程订单列表
     * @author yinjimin
     * @date 2018/4/18
     */
    @PostMapping("/getcourseorderlist")
    public void getCourseOrderList(@RequestBody ParamMap paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> rowMap = new HashMap<>(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        String courseName = paramMap.getCourseName();

        if (pageNo == null || pageSize == null) {
            rowMap.put("status", "paramMiss");
            rowMap.put("rows", rowList);
        } else {
            //courseName为不为空
            if (courseName == null || courseName.equals("")) {

                List<CourseOrderManager> courseOrderManagers = courseOrderManagerService.getByOidList(username, pageNo * pageSize, pageSize);
                if (courseOrderManagers.size() == 0) {
                    rowMap.put("status", "orderManagerNone");
                    rowMap.put("rows", rowList);
                } else {
                    for (CourseOrderManager courseOrderManager : courseOrderManagers) {
                        CourseOrder courseOrder = courseOService.getById(courseOrderManager.getOid());
                        CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
                        String tutorStatus = "";
                        Integer tStatus = courseOrderManager.getTutorstatus();
                        if(tStatus == 0){
                            tutorStatus = "请确认接单";
                        }else if(tStatus == 1){
                            tutorStatus = "已经接单";
                        }else if(tStatus == 2){
                            tutorStatus = "正在教学";
                        }else if(tStatus == 3){
                            tutorStatus = "等待确认中";
                        }else if(tStatus == 4){
                            tutorStatus = "完成订单";
                        }else if(tStatus == -1){
                            tutorStatus = "申请撤销中";
                        }else{
                            tutorStatus = "复审申请中";
                        }
                        String userStatus = "";
                        Integer uStatus = courseOrderManager.getUserstatus();
                        //0初始状态，1正在听课，2听课完成，3完成订单，-1申请退款，-2订单异常复审申请
                        if(uStatus == 0){
                            userStatus = "等待确认";
                        }else if(uStatus == 1){
                            userStatus = "正在听课";
                        }else if(uStatus == 2){
                            userStatus = "听课完成";
                        }else if(uStatus == 3){
                            userStatus = "完成订单";
                        }else if(uStatus == -1) {
                            userStatus = "申请退款中";
                        }else{
                            userStatus = "复审申请中";
                        }
                        resultMap = new HashMap<>(1);
                        resultMap.put("courseName", courseMain.getName());
                        resultMap.put("orderTime", simpleDateFormat.format(courseOrder.getOtime()));
                        resultMap.put("orderCode", courseOrderManager.getCode());
                        resultMap.put("orderUser", courseOrder.getUsername());
                        resultMap.put("tutorInfo", courseOrderManager.getTutorinfo());
                        resultMap.put("userInfo", courseOrderManager.getUserinfo());
                        resultMap.put("tutorStatus", tutorStatus);
                        resultMap.put("userStatus", userStatus);
                        rowList.add(resultMap);
                    }
                    rowMap.put("rows", rowList);
                    //获取总数
                    Integer count = courseOrderManagerService.getCountByOidList(username);
                    rowMap.put("total", count);
                }
            } else {
                List<CourseOrderManager> courseOrderManagers = courseOrderManagerService.getByUserAndName(username, courseName, pageNo * pageSize, pageSize);
                if (courseOrderManagers.size() == 0) {
                    rowMap.put("status", "orderManagerNone");
                    rowMap.put("rows", rowList);
                } else {
                    for (CourseOrderManager courseOrderManager : courseOrderManagers) {
                        CourseOrder courseOrder = courseOService.getById(courseOrderManager.getOid());
                        CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
                        String tutorStatus = "";
                        Integer tStatus = courseOrderManager.getTutorstatus();
                        if(tStatus == 0){
                            tutorStatus = "请确认接单";
                        }else if(tStatus == 1){
                            tutorStatus = "已经接单";
                        }else if(tStatus == 2){
                            tutorStatus = "正在教学";
                        }else if(tStatus == 3){
                            tutorStatus = "等待确认中";
                        }else if(tStatus == 4){
                            tutorStatus = "完成订单";
                        }else if(tStatus == -1){
                            tutorStatus = "申请撤销中";
                        }else{
                            tutorStatus = "复审申请中";
                        }
                        String userStatus = "";
                        Integer uStatus = courseOrderManager.getUserstatus();
                        //0初始状态，1正在听课，2听课完成，3完成订单，-1申请退款，-2订单异常复审申请
                        if(uStatus == 0){
                            userStatus = "等待确认";
                        }else if(uStatus == 1){
                            userStatus = "正在听课";
                        }else if(uStatus == 2){
                            userStatus = "听课完成";
                        }else if(uStatus == 3){
                            userStatus = "完成订单";
                        }else if(uStatus == -1) {
                            userStatus = "申请退款中";
                        }else{
                            userStatus = "复审申请中";
                        }
                        resultMap = new HashMap<>(1);
                        resultMap.put("courseName", courseMain.getName());
                        resultMap.put("orderTime", simpleDateFormat.format(courseOrder.getOtime()));
                        resultMap.put("orderCode", courseOrderManager.getCode());
                        resultMap.put("orderUser", courseOrder.getUsername());
                        resultMap.put("tutorStatus", tutorStatus);
                        resultMap.put("userStatus", userStatus);
                        rowList.add(resultMap);
                    }
                    rowMap.put("rows", rowList);
                    //获取总数
                    Integer count = courseOrderManagerService.getCountByOidList(username);
                    rowMap.put("total", count);
                }
            }
        }

        writer.print(gson.toJson(rowMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 获取订单详情数据
     * @param [code, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @PostMapping("/getorderdetail")
    public void getOrderDetail(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //判断对应的订单编号的课程是否属于当前登录用户
        Integer count =  courseMService.checkOrderBelongs(username, code);
        if(count == 0){
            resultMap.put("status", "invalid");
        }else{
            //获取对应的订单管理数据详情
            CourseOrderManager courseOrderManager = courseOrderManagerService.getByCode(code);
            CourseOrder courseOrder = courseOService.getById(courseOrderManager.getOid());
            CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
            String tutorStatus = "";
            Integer tStatus = courseOrderManager.getTutorstatus();
            if(tStatus == 0){
                tutorStatus = "请确认接单";
            }else if(tStatus == 1){
                tutorStatus = "已经接单";
            }else if(tStatus == 2){
                tutorStatus = "正在教学";
            }else if(tStatus == 3){
                tutorStatus = "等待确认中";
            }else if(tStatus == 4){
                tutorStatus = "完成订单";
            }else if(tStatus == -1){
                tutorStatus = "申请撤销中";
            }else{
                tutorStatus = "复审申请中";
            }
            String userStatus = "";
            Integer uStatus = courseOrderManager.getUserstatus();
            //0初始状态，1正在听课，2听课完成，3完成订单，-1申请退款，-2订单异常复审申请
            if(uStatus == 0){
                userStatus = "等待确认";
            }else if(uStatus == 1){
                userStatus = "正在听课";
            }else if(uStatus == 2){
                userStatus = "听课完成";
            }else if(uStatus == 3){
                userStatus = "完成订单";
            }else if(uStatus == -1) {
                userStatus = "申请退款中";
            }else{
                userStatus = "复审申请中";
            }
            resultMap = new HashMap<>(1);
            resultMap.put("courseName", courseMain.getName());
            resultMap.put("orderTime", simpleDateFormat.format(courseOrder.getOtime()));
            resultMap.put("orderCode", courseOrderManager.getCode());
            resultMap.put("orderUser", courseOrder.getUsername());
            resultMap.put("tutorInfo", courseOrderManager.getTutorinfo());
            resultMap.put("userInfo", courseOrderManager.getUserinfo());
            resultMap.put("tutorStatus", tutorStatus);
            resultMap.put("userStatus", userStatus);
        }
        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 更新家教处理状态
     * @param [tutorStatus, tutorInfo, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @PostMapping("/updatetutorstatus")
    public void updateTutorStatus(@RequestParam String code, @RequestParam Integer tutorStatus, @RequestParam String tutorInfo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        //判断对应的订单编号的课程是否属于当前登录用户
        Integer count =  courseMService.checkOrderBelongs(username, code);
        if(count == 0){
            resultMap.put("status", "invalid");
        }else{
            //修改对应的订单状态
            courseOrderManagerService.updateTutorStatus(code, tutorStatus, tutorInfo);
            resultMap.put("status", "valid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 获取异常订单数据
     * @param [paramMap, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @PostMapping("/geterrororderlist")
    public void getErrorOrderList(@RequestBody ParamMap paramMap, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        List<Object> resultList = new ArrayList<>();
        Map<String, Object> rowMap = new HashMap<>();

        String userName = paramMap.getUsername();
        String tutorName = paramMap.getTutorName();
        String courseName = paramMap.getCourseName();
        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        Integer offset = pageNo * pageSize;

        List<CourseOrderManager> courseOrderManagers = null;
        if(userName == null || userName.equals("")){
            if(tutorName == null || tutorName.equals("")){
                //获取所有的异常订单数据
                courseOrderManagers = courseOrderManagerService.getAllErrsLimit(courseName, offset, pageSize);
            }else{
                //获取指定了家教的异常订单数据
                courseOrderManagers = courseOrderManagerService.getErrsByTutorAndCourse(courseName, tutorName, offset, pageSize);
            }
        }else{
            if(tutorName == null || tutorName.equals("")){
                //获取指定用户名的异常订单数据
                courseOrderManagers = courseOrderManagerService.getErrsByUserAndCourse(courseName, userName, offset, pageSize);
            }else{
                courseOrderManagers = courseOrderManagerService.getErrsByUserAndTutor(courseName, userName, tutorName, offset, pageSize);
            }
        }

        if(courseOrderManagers == null){
            rowMap.put("status", "invalid");
            rowMap.put("rows", resultList);
            rowMap.put("total", 0);
        }else if(courseOrderManagers.size() == 0){
            rowMap.put("status", "none");
            rowMap.put("rows", resultList);
            rowMap.put("total", 0);
        } else{
            for(CourseOrderManager courseOrderManager: courseOrderManagers){
                CourseOrder courseOrder = courseOService.getById(courseOrderManager.getOid());
                CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
                Map<String, Object> tempMap = new HashMap<>(1);
                String tutorStatus = "";
                Integer tStatus = courseOrderManager.getTutorstatus();
                if(tStatus == 0){
                    tutorStatus = "请确认接单";
                }else if(tStatus == 1){
                    tutorStatus = "已经接单";
                }else if(tStatus == 2){
                    tutorStatus = "正在教学";
                }else if(tStatus == 3){
                    tutorStatus = "等待确认中";
                }else if(tStatus == 4){
                    tutorStatus = "完成订单";
                }else if(tStatus == -1){
                    tutorStatus = "申请撤销中";
                }else{
                    tutorStatus = "复审申请中";
                }
                String userStatus = "";
                Integer uStatus = courseOrderManager.getUserstatus();
                //0初始状态，1正在听课，2听课完成，3完成订单，-1申请退款，-2订单异常复审申请
                if(uStatus == 0){
                    userStatus = "等待确认";
                }else if(uStatus == 1){
                    userStatus = "正在听课";
                }else if(uStatus == 2){
                    userStatus = "听课完成";
                }else if(uStatus == 3){
                    userStatus = "完成订单";
                }else if(uStatus == -1) {
                    userStatus = "申请退款中";
                }else{
                    userStatus = "复审申请中";
                }
                tempMap.put("orderCode", courseOrderManager.getCode());
                tempMap.put("userName", courseOrder.getUsername());
                tempMap.put("courseName", courseMain.getName());
                tempMap.put("tutorName", courseMain.getUsername());
                tempMap.put("userStatus", userStatus);
                tempMap.put("userInfo", courseOrderManager.getUserinfo());
                tempMap.put("tutorStatus", tutorStatus);
                tempMap.put("tutorInfo", courseOrderManager.getTutorinfo());
                resultList.add(tempMap);
            }
            rowMap.put("rows", resultList);
            Integer count = courseOrderManagerService.getAllErrs();
            rowMap.put("total", count);
        }

        writer.print(gson.toJson(rowMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 查看指定异常订单详情数据
     * @param [code, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @PostMapping("/geterrororderdetail")
    public void getErrorOrderDetail(@RequestParam String code, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        PrintWriter writer = response.getWriter();

        CourseOrderManager courseOrderManager = courseOrderManagerService.getByCode(code);
        CourseOrder courseOrder = courseOService.getById(courseOrderManager.getOid());
        CourseMain courseMain = courseMService.getCourseById(courseOrder.getCid());
        Map<String, Object> tempMap = new HashMap<>(1);
        String tutorStatus = "";
        Integer tStatus = courseOrderManager.getTutorstatus();
        if(tStatus == 0){
            tutorStatus = "请确认接单";
        }else if(tStatus == 1){
            tutorStatus = "已经接单";
        }else if(tStatus == 2){
            tutorStatus = "正在教学";
        }else if(tStatus == 3){
            tutorStatus = "等待确认中";
        }else if(tStatus == 4){
            tutorStatus = "完成订单";
        }else if(tStatus == -1){
            tutorStatus = "申请撤销中";
        }else{
            tutorStatus = "复审申请中";
        }
        String userStatus = "";
        Integer uStatus = courseOrderManager.getUserstatus();
        //0初始状态，1正在听课，2听课完成，3完成订单，-1申请退款，-2订单异常复审申请
        if(uStatus == 0){
            userStatus = "等待确认";
        }else if(uStatus == 1){
            userStatus = "正在听课";
        }else if(uStatus == 2){
            userStatus = "听课完成";
        }else if(uStatus == 3){
            userStatus = "完成订单";
        }else if(uStatus == -1) {
            userStatus = "申请退款中";
        }else{
            userStatus = "复审申请中";
        }
        tempMap.put("orderCode", courseOrderManager.getCode());
        tempMap.put("userName", courseOrder.getUsername());
        tempMap.put("courseName", courseMain.getName());
        tempMap.put("tutorName", courseMain.getUsername());
        tempMap.put("userStatus", userStatus);
        tempMap.put("userInfo", courseOrderManager.getUserinfo());
        tempMap.put("tutorStatus", tutorStatus);
        tempMap.put("tutorInfo", courseOrderManager.getTutorinfo());

        writer.print(gson.toJson(tempMap));
        writer.flush();
        writer.close();
    }
}
