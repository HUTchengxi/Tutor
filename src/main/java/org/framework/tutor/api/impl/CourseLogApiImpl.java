package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseLogAPi;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseLogService;
import org.framework.tutor.service.CourseMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    @Transactional
    public String addLog(Integer cid) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        try {

            //课程id是否有对应的课程
            CourseMain courseMain = courseMainService.getCourseById(cid);
            if(courseMain == null){
                resultMap.put("status", "nocourse");
            }else {

                //查询最新的十次数据 如果有相同的课程则删除掉  这里需要使用到事务
                List<CourseLog> courseLogs = courseLogService.getUserlog(username);
                if (courseLogs.size() > 0) {
                    for (CourseLog courseLog : courseLogs) {
                        if (cid.equals(courseLog.getCid())) {
                            courseLogService.delLog(courseLog.getId());
                        }
                    }
                }

                //新增浏览记录
                courseLogService.addLog(cid, username);

                //保证只保留最多十条数据
                //TODO：这里的代码后期考虑做个优化
                if (courseLogService.getUserlogCount(username) > 10) {
                    //获取最早的记录id
                    CourseLog courseLog = courseLogService.getFirstLog(username);
                    Integer id = courseLog.getId();
                    delLog(id);
                }
                resultMap.put("status", "valid");
            }
        }catch (Exception exception){
            exception.printStackTrace();
            resultMap.put("status", "sqlerr");
        }finally {
            return gson.toJson(resultMap);
        }
    }
}
