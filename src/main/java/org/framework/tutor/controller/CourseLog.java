package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.CourseLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 课程记录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/courselog_con")
public class CourseLog {

    @Autowired
    private CourseLService courseLService;

    /**
     * 获取我的课程记录
     * @param request
     * @param response
     */
    @RequestMapping("/getlog")
    public void getLog(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            //获取课程记录
            List<org.framework.tutor.domain.CourseLog> courseLogs  = courseLService.getUserlog(username);
            if(courseLogs.size() == 0){
                res = "{\"status\": \"ok\", \"len\": \"0\"}";
            }
            else {
                res = "{";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.CourseLog courseLog : courseLogs) {
                    res += "\""+i+"\": ";
                    String temp = "{\"logtime\": \""+simpleDateFormat.format(courseLog.getLogtime())+"\", " +
                            "\"ctype\": \""+courseLog.getCtype()+"\", " +
                            "\"id\": \""+courseLog.getId()+"\", " +
                            "\"cname\": \""+courseLog.getCname()+"\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length()-2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }


    /**
     * 删除指定的课程记录
     * @param id
     * @param request
     * @param response
     */
    @RequestMapping("/dellog")
    public void delLog(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            if(!courseLService.delLog(id)){
                res = "{\"status\": \"mysqlerr\", \"msg\": \"I'm sorry\"}";
            }
            else{
                res = "{\"status\": \"ok\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
