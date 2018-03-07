package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.UserMService;
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
 * 课程表控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursemain_con")
public class CourseMain {

    @Autowired
    private CourseMService courseMService;

    @Autowired
    private UserMService userMService;

    /**
     * 加载课程数据
     * @param stype
     * @param ctype
     * @param sort
     * @param startpos
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcourselist")
    public void getCourseList(Integer stype, String ctype, String sort, Integer startpos, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;
        List<org.framework.tutor.domain.CourseMain> courseMains = null;
        //默认：不限进行排序
        if(ctype.equals("all")){
            //时间最新排序
            if(sort.equals("new")){
                //未指定主类别
                if(stype == -1){
                    courseMains = courseMService.getCourseListNew(startpos);
                }
                else{
                    courseMains = courseMService.getCourseListNew( stype, startpos);
                }
            }
            //搜索最热排序
            else if(sort.equals("hot")){
                //未指定主类别
                if(stype == -1){
                    courseMains = courseMService.getCourseListHot(startpos);
                }
                else{
                    courseMains = courseMService.getCourseListHot( stype, startpos);
                }
            }
            //评论最多排序
            else{
                if(stype == -1){
                    courseMains = courseMService.getCourseListMore(startpos);
                }
                else{
                    courseMains = courseMService.getCourseListMore( stype, startpos);
                }
            }
        }
        //选择指定子类别的课程
        else{
            //时间最新排序
            if(sort.equals("new")){
                if(stype == -1){
                    courseMains = courseMService.getCourseListNewAC(ctype, startpos);
                }
                else {
                    courseMains = courseMService.getCourseListNew(stype, ctype, startpos);
                }
            }
            //搜索最热排序
            else if(sort.equals("hot")){
                if(stype == -1){
                    courseMains = courseMService.getCourseListHotAC(ctype, startpos);
                }
                else {
                    courseMains = courseMService.getCourseListHot(stype, ctype, startpos);
                }
            }
            //评论最多排序
            else{
                if(stype == -1){
                    courseMains = courseMService.getCourseListMoreac(ctype, startpos);
                }
                else {
                    courseMains = courseMService.getCourseListMore(stype, ctype, startpos);
                }
            }
        }
        if(courseMains.size() == 0){
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain: courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                res += "\""+i+"\": ";
                String temp = "{\"imgsrc\": \""+courseMain.getImgsrc()+"\", " +
                        "\"id\": \""+courseMain.getId()+"\", " +
                        "\"name\": \""+courseMain.getName()+"\", " +
                        "\"jcount\": \""+courseMain.getJcount()+"\", " +
                        "\"nickname\": \""+userMain.getNickname()+"\", " +
                        "\"price\": \""+courseMain.getPrice()+"\", " +
                        "\"uimgsrc\": \""+userMain.getImgsrc()+"\", " +
                        "\"descript\": \""+courseMain.getDescript()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }
        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取所有科目类别
     * @param stype
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursetype")
    public void getCourseType(String stype, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.getCourseType(stype);
        if(courseMains.size() == 0){
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        }
        else{
            res = "{";
            int i = 0;
            for (org.framework.tutor.domain.CourseMain courseMain: courseMains) {
                String temp = "\"ctype"+i+"\": \""+courseMain.getCtype()+"\", ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 关键字获取所有对应的课程数据
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/coursesearch")
    public void courseSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseMain> courseMains = courseMService.courseSearch(keyword);
        if(courseMains.size() == 0){
            res = "{\"status\": \"valid\", \"len\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseMain courseMain: courseMains) {
                UserMain userMain = userMService.getByUser(courseMain.getUsername());
                res += "\""+i+"\": ";
                String temp = "{\"imgsrc\": \""+courseMain.getImgsrc()+"\", " +
                        "\"id\": \""+courseMain.getId()+"\", " +
                        "\"name\": \""+courseMain.getName()+"\", " +
                        "\"jcount\": \""+courseMain.getJcount()+"\", " +
                        "\"nickname\": \""+userMain.getNickname()+"\", " +
                        "\"price\": \""+courseMain.getPrice()+"\", " +
                        "\"uimgsrc\": \""+userMain.getImgsrc()+"\", " +
                        "\"descript\": \""+courseMain.getDescript()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取指定的课程的数据
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcoursebyid")
    public void getCourseById(Integer id, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        org.framework.tutor.domain.CourseMain courseMain = courseMService.getCourseById(id);
        UserMain userMain = userMService.getByUser(courseMain.getUsername());
        res = "{\"imgsrc\": \""+courseMain.getImgsrc()+"\", " +
                "\"id\": \""+courseMain.getId()+"\", " +
                "\"stype\": \""+courseMain.getStype()+"\", " +
                "\"ctype\": \""+courseMain.getCtype()+"\", " +
                "\"name\": \""+courseMain.getName()+"\", " +
                "\"jcount\": \""+courseMain.getJcount()+"\", " +
                "\"nickname\": \""+userMain.getNickname()+"\", " +
                "\"info\": \""+userMain.getInfo()+"\", " +
                "\"price\": \""+courseMain.getPrice()+"\", " +
                "\"uimgsrc\": \""+userMain.getImgsrc()+"\", " +
                "\"total\": \""+courseMain.getTotal()+"\", " +
                "\"descript\": \""+courseMain.getDescript()+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}