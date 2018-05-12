package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseDeleteReqApi;
import org.framework.tutor.domain.CourseDeleteReq;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrderManager;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseDeleteReqService;
import org.framework.tutor.service.CourseDeleteRespService;
import org.framework.tutor.service.CourseMainService;
import org.framework.tutor.service.CourseOrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class CourseDeleteReqApiImpl implements CourseDeleteReqApi {

    @Autowired
    private CourseDeleteReqService courseDeleteReqService;

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private CourseDeleteRespService courseDeleteRespService;

    @Autowired
    private CourseOrderManagerService courseOrderManagerService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String setMyCourseDeleteReq(Integer cid, String descript) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断课程是否为当前登录用户的
        CourseMain courseMain = courseMainService.getCourseById(cid);
        if(courseMain == null || !courseMain.getUsername().equals(username)){
            resultMap.put("status", "invalid");
        }
        else{
            //判断该课程是否已经提交
            CourseDeleteReq courseDeleteReq = courseDeleteReqService.getByCid(cid);
            if(courseDeleteReq == null){
                courseDeleteReqService.addCourseDeleteReq(cid, username, descript);
            }
            else{
                courseDeleteReqService.updateCourseDeleteReq(cid, descript);
            }
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getReqList(ParamMap paramMap) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>(1);
        Integer total = 0;

        Integer status = paramMap.getStatus();
        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        Integer offset = pageNo * pageSize;
        String courseName = paramMap.getCourseName();

        //查询所有的申请数据
        List<CourseDeleteReq> courseDeleteReqs = null;
        if(status == null || status == 0){
            courseDeleteReqs = courseDeleteReqService.getAllLimit(courseName, offset, pageSize);
            total = courseDeleteReqService.getAllCount(courseName);
        }else if(status == 1 || status == 2){
            courseDeleteReqs = courseDeleteReqService.getRespAllLimit(courseName, status, offset, pageSize);
            total = courseDeleteReqService.getRespAll(courseName, status);
        }
        if(courseDeleteReqs == null){
            resultMap.put("status", "invalid");
        }
        else if(courseDeleteReqs.size() == 0){
            resultMap.put("rows", rowList);
            resultMap.put("total", 0);
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(CourseDeleteReq courseDeleteReq: courseDeleteReqs){
                CourseDeleteResp courseDeleteResp = courseDeleteRespService.getByRid(courseDeleteReq.getId());
                CourseMain courseMain = courseMainService.getCourseById(courseDeleteReq.getCid());
                String respStatus = "";
                if(courseDeleteResp == null){
                    respStatus = "待处理";
                }else {
                    Integer statusTemp = courseDeleteResp.getStatus();
                    if (statusTemp == 0) {
                        respStatus = "待处理";
                    }else if(statusTemp == 1){
                        respStatus = "已同意";
                    }else{
                        respStatus = "已拒绝";
                    }
                }
                Map<String, Object> rowMap = new HashMap<>(1);
                rowMap.put("reqId", courseDeleteReq.getId());
                rowMap.put("courseName", courseMain.getName());
                rowMap.put("reqUser", courseDeleteReq.getUsername());
                rowMap.put("reqTime", simpleDateFormat.format(courseDeleteReq.getReqtime()));
                rowMap.put("respStatus", respStatus);
                rowMap.put("reqDesc", courseDeleteReq.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("rows", rowList);
            resultMap.put("total", total);
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getReqDetail(Integer reqid) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(3);

        CourseDeleteReq courseDeleteReq = courseDeleteReqService.getById(reqid);
        CourseMain courseMain = courseMainService.getCourseById(courseDeleteReq.getCid());
        List<CourseOrderManager> courseOrderManagers = courseOrderManagerService.getByReqid(reqid);
        resultMap.put("courseName", courseMain.getName());
        resultMap.put("reqDesc", courseDeleteReq.getDescript());
        resultMap.put("hasOrder", courseOrderManagers.size() == 0?"无": "有");

        return gson.toJson(resultMap);
    }
}
