package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseOrderManagerApi;
import org.framework.tutor.controller.CourseOrderManagerController;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrder;
import org.framework.tutor.domain.CourseOrderManager;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseMainService;
import org.framework.tutor.service.CourseOrderService;
import org.framework.tutor.service.CourseOrderManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class CourseOrderManagerApiImpl implements CourseOrderManagerApi {

    private static final Logger LOG = LoggerFactory.getLogger(CourseOrderManagerController.class);

    @Autowired
    private CourseOrderManagerService courseOrderManagerService;

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private HttpServletRequest request;

    //TODO：后续考虑使用redis
    @Override
    public String getCourseOrderList(ParamMap paramMap) throws IOException {

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
                        CourseOrder courseOrder = courseOrderService.getById(courseOrderManager.getOid());
                        CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
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
                        CourseOrder courseOrder = courseOrderService.getById(courseOrderManager.getOid());
                        CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
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

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getOrderDetail(String code) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //判断对应的订单编号的课程是否属于当前登录用户
        Integer count =  courseMainService.checkOrderBelongs(username, code);
        if(count == 0){
            resultMap.put("status", "invalid");
        }else{
            //获取对应的订单管理数据详情
            CourseOrderManager courseOrderManager = courseOrderManagerService.getByCode(code);
            CourseOrder courseOrder = courseOrderService.getById(courseOrderManager.getOid());
            CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
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
        return gson.toJson(resultMap);
    }


    @Override
    public String updateTutorStatus(String code, Integer tutorStatus, String tutorInfo) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        //判断对应的订单编号的课程是否属于当前登录用户
        Integer count =  courseMainService.checkOrderBelongs(username, code);
        if(count == 0){
            resultMap.put("status", "invalid");
        }else{
            //修改对应的订单状态
            courseOrderManagerService.updateTutorStatus(code, tutorStatus, tutorInfo);
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getErrorOrderList(ParamMap paramMap) throws IOException {

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
                CourseOrder courseOrder = courseOrderService.getById(courseOrderManager.getOid());
                CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
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

        return gson.toJson(rowMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getErrorOrderDetail(String code) throws IOException {

        Gson gson = new Gson();
        CourseOrderManager courseOrderManager = courseOrderManagerService.getByCode(code);
        CourseOrder courseOrder = courseOrderService.getById(courseOrderManager.getOid());
        CourseMain courseMain = courseMainService.getCourseById(courseOrder.getCid());
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

        return gson.toJson(tempMap);
    }
}
