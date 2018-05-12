package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseLogAPi;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseLogService;
import org.framework.tutor.service.CourseMainService;
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
public class CourseLogApiImpl implements CourseLogAPi {

    @Autowired
    private CourseLogService courseLogService;

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private HttpServletRequest request;


    //TODO：后续考虑使用redis
    @Override
    public String getLog() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        //获取课程记录
        List<CourseLog> courseLogs = courseLogService.getUserlog(username);
        if (courseLogs.size() == 0) {
            resultMap.put("status", "ok");
            resultMap.put("len", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseLog courseLog : courseLogs) {
                CourseMain courseMain = courseMainService.getCourseById(courseLog.getCid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("logtime", simpleDateFormat.format(courseLog.getLogtime()));
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("id", courseLog.getId());
                rowMap.put("cid", courseLog.getCid());
                rowMap.put("cname", courseMain.getName());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String delLog(Integer id) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (!courseLogService.delLog(id)) {
            resultMap.put("status", "mysqlerr");
        } else {
            resultMap.put("status", "ok");
        }

        return gson.toJson(resultMap);
    }
}
