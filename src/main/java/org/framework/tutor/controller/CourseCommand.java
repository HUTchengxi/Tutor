package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.CourseCMService;
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
 * 课程评价控制类
 */
@RestController
@RequestMapping("/coursecommand_con")
public class CourseCommand {

    @Autowired
    private CourseCMService courseCMService;

    @Autowired
    private UserMService userMService;

    /**
     * 获取课程评论数据
     * @param cid
     * @param response
     */
    @RequestMapping("/getcoursecommand")
    public void getCourseCommand(Integer cid, Integer startpos, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getCourseCommand(cid, startpos);
        int count = courseCommands.size();
        if(count == 0){
            res = "{\"count\": \""+courseCommands.size() + "\"}";
        }
        else {
            res = "{\"count\": \"" + courseCommands.size() + "\", ";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                UserMain userMain = userMService.getByUser(courseCommand.getUsername());
                res += "\"" + i + "\": ";
                String temp = "{\"ctime\": \"" + simpleDateFormat.format(courseCommand.getCtime()) + "\", " +
                        "\"info\": \"" + courseCommand.getInfo() + "\", " +
                        "\"id\": \"" + courseCommand.getId() + "\", " +
                        "\"repid\": \"" + courseCommand.getRepid() + "\", " +
                        "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                        "\"score\": \"" + courseCommand.getScore() + "\", " +
                        "\"username\": \"" + userMain.getNickname() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取课程神评
     * @param cid
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcommandgod")
    public void getCourseCommandGod(Integer cid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getCourseCommandGod(cid);
        int count = courseCommands.size();
        if(count == 0){
            res = "{\"count\": \""+courseCommands.size() + "\"}";
        }
        else {
            res = "{\"count\": \"" + courseCommands.size() + "\", ";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                UserMain userMain = userMService.getByUser(courseCommand.getUsername());
                res += "\"" + i + "\": ";
                String temp = "{\"ctime\": \"" + simpleDateFormat.format(courseCommand.getCtime()) + "\", " +
                        "\"info\": \"" + courseCommand.getInfo() + "\", " +
                        "\"id\": \"" + courseCommand.getId() + "\", " +
                        "\"repid\": \"" + courseCommand.getRepid() + "\", " +
                        "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                        "\"score\": \"" + courseCommand.getScore() + "\", " +
                        "\"username\": \"" + userMain.getNickname() + "\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length() - 2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 查看当前用户对指定课程的评价
     * @param cid
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/selmycommand")
    public void selMyCommand(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommand(username, cid);
            int count = courseCommands.size();
            if(count == 0){
                res = "{\"count\": \""+courseCommands.size() + "\"}";
            }
            else {
                res = "{\"count\": \"" + courseCommands.size() + "\", ";
                int i = 1;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                    UserMain userMain = userMService.getByUser(courseCommand.getUsername());
                    res += "\"" + i + "\": ";
                    String temp = "{\"ctime\": \"" + simpleDateFormat.format(courseCommand.getCtime()) + "\", " +
                            "\"info\": \"" + courseCommand.getInfo() + "\", " +
                            "\"id\": \"" + courseCommand.getId() + "\", " +
                            "\"repid\": \"" + courseCommand.getRepid() + "\", " +
                            "\"uimgsrc\": \"" + userMain.getImgsrc() + "\", " +
                            "\"score\": \"" + courseCommand.getScore() + "\", " +
                            "\"username\": \"" + userMain.getNickname() + "\"}, ";
                    res += temp;
                    i++;
                }
                res = res.substring(0, res.length() - 2);
                res += "}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.close();
    }

    /**
     * 发表用户评价
     * @param cid
     * @param command
     * @param request
     * @param response
     */
    @RequestMapping("/submycommand")
    public void subMyCommand(Integer cid, String command, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            Integer row = courseCMService.subMyCommand(cid, command, username);
            if(row == 1){
                res = "{\"status\": \"valid\"}";
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
