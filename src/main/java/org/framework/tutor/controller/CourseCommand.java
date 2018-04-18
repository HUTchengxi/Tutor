package org.framework.tutor.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseCMService;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.Kernel;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private CourseOService courseOService;

    @Autowired
    private CourseMService courseMService;

    /**
     * 获取课程评论数据
     *
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
        if (count == 0) {
            res = "{\"count\": \"" + courseCommands.size() + "\"}";
        } else {
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
     *
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
        if (count == 0) {
            res = "{\"count\": \"" + courseCommands.size() + "\"}";
        } else {
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
     *
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

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {

            //判断当前用户是否购买了该课程  cid  username start=1
            Integer state = 1;
            if (courseOService.getByUserAndState(cid, username, state) == null) {
                res = "{\"status\": \"nobuy\"}";
            } else {

                List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommand(username, cid);
                int count = courseCommands.size();
                if (count == 0) {
                    res = "{\"count\": \"" + courseCommands.size() + "\"}";
                } else {
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
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.close();
    }

    /**
     * 发表用户评价
     *
     * @param cid
     * @param command
     * @param score
     * @param request
     * @param response
     */
    @RequestMapping("/submycommand")
    public void subMyCommand(Integer cid, String command, Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            Integer row = courseCMService.subMyCommand(cid, command, score, username);
            if (row == 1) {
                res = "{\"status\": \"valid\"}";
            } else {
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前登录家教的课程评论总数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getcommandcount")
    public void getCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        res = "{\"count\": \"" + courseCMService.getCommandCountNow(username, now) + "\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前登录家教的课程评分平均值
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getscoreavg")
    public void getScoreAvg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        res = "{\"count\": \"" + courseCMService.getScoreAvgNow(username, now) + "\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前用户的课程评论数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/loadmycommandinfo")
    public void loadMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.loadMyCommandInfo(username);
        if (courseCommands.size() == 0) {
            res = "{\"count\": \"0\"}";
        } else {
            res = "{";
            int i = 1;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                CourseMain courseMain = courseMService.getCourseById(courseCommand.getCid());
                res += "\"" + i + "\": ";
                String temp = "{\"ctime\": \"" + simpleDateFormat.format(courseCommand.getCtime()) + "\", " +
                        "\"info\": \"" + courseCommand.getInfo() + "\", " +
                        "\"cid\": \"" + courseCommand.getCid() + "\", " +
                        "\"imgsrc\": \"" + courseMain.getImgsrc() + "\", " +
                        "\"score\": \"" + courseCommand.getScore() + "\"}, ";
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
     * @param [paramMap, request, response]
     * @return void
     * @Description 获取课程评论列表
     * @author yinjimin
     * @date 2018/4/18
     */
    @RequestMapping("/getcommandlist")
    public void getCommandList(@RequestBody ParamMap paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> rowMap = new HashMap<>(1);

        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        String courseName = paramMap.getCourseName();

        if (pageNo == null || pageSize == null) {
            rowMap.put("status", "paramMiss");
            rowMap.put("rows", rowList);
        } else {

            if (courseName == null || "".equals(courseName)) {

                List<CourseMain> courseMains = courseMService.getMyCourseList(username);
                if (courseMains.size() == 0) {
                    rowMap.put("status", "courseNone");
                    rowMap.put("rows", rowList);
                } else {
                    StringBuffer courseIds = new StringBuffer("");
                    for (CourseMain courseMain : courseMains) {
                        courseIds.append(courseMain.getId());
                        courseIds.append(",");
                    }
                    String courseId = courseIds.substring(0, courseIds.length() - 1).toString();
                    List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommandList(courseId, pageNo * pageSize, pageSize);
                    if (courseCommands.size() == 0) {
                        rowMap.put("status", "commandNone");
                        rowMap.put("rows", rowList);
                    } else {
                        //获取所有数据总数
                        Integer count = courseCMService.getCommandCountByIdlist(courseId);
                        rowMap.put("total", count);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                            resultMap = new HashMap<>(1);
                            CourseMain courseMain = courseMService.getCourseById(courseCommand.getCid());
                            Integer stype = courseMain.getStype();
                            StringBuffer courseStype = new StringBuffer("");
                            if (stype == 1) {
                                courseStype.append("小学-");
                            } else if (stype == 2) {
                                courseStype.append("初中-");
                            } else if (stype == 3) {
                                courseStype.append("高中-");
                            } else {
                                courseStype.append("其他-");
                            }
                            resultMap.put("commandId", courseCommand.getId());
                            resultMap.put("courseName", courseMain.getName());
                            resultMap.put("courseType", courseStype + courseMain.getCtype());
                            resultMap.put("commandUser", courseCommand.getUsername());
                            resultMap.put("commandDesc", courseCommand.getInfo());
                            resultMap.put("commandTime", simpleDateFormat.format(courseCommand.getCtime()));
                            resultMap.put("godStatus", courseCommand.getGod());
                            resultMap.put("commandStatus", courseCommand.getStatus());
                            rowList.add(resultMap);
                        }
                        rowMap.put("rows", rowList);
                    }
                }
            } else {
                List<CourseMain> courseMains = courseMService.getByCoursename(courseName);
                if (courseMains.size() == 0) {
                    rowMap.put("status", "courseNone");
                    rowMap.put("rows", rowList);
                } else {
                    StringBuffer courseIds = new StringBuffer("");
                    for (CourseMain courseMain : courseMains) {
                        courseIds.append(courseMain.getId());
                        courseIds.append(",");
                    }
                    String courseId = courseIds.substring(0, courseIds.length() - 1).toString();
                    List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCMService.getMyCommandList(courseId, pageNo * pageSize, pageSize);
                    if (courseCommands.size() == 0) {
                        rowMap.put("status", "commandNone");
                        rowMap.put("rows", rowList);
                    } else {
                        //获取所有数据总数
                        Integer count = courseCMService.getCommandCountByIdlist(courseId);
                        rowMap.put("total", count);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                            resultMap = new HashMap<>(1);
                            CourseMain courseMain = courseMService.getCourseById(courseCommand.getCid());
                            Integer stype = courseMain.getStype();
                            StringBuffer courseStype = new StringBuffer("");
                            if (stype == 1) {
                                courseStype.append("小学-");
                            } else if (stype == 2) {
                                courseStype.append("初中-");
                            } else if (stype == 3) {
                                courseStype.append("高中-");
                            } else {
                                courseStype.append("其他-");
                            }
                            resultMap.put("commandId", courseCommand.getId());
                            resultMap.put("courseName", courseMain.getName());
                            resultMap.put("courseType", courseStype + courseMain.getCtype());
                            resultMap.put("commandUser", courseCommand.getUsername());
                            resultMap.put("commandDesc", courseCommand.getInfo());
                            resultMap.put("commandTime", simpleDateFormat.format(courseCommand.getCtime()));
                            resultMap.put("godStatus", courseCommand.getGod());
                            resultMap.put("commandStatus", courseCommand.getStatus());
                            rowList.add(resultMap);
                        }
                        rowMap.put("rows", rowList);
                    }
                }
            }
        }

        writer.print(gson.toJson(rowMap));
        writer.flush();
        writer.close();
    }

    /**
     *
     * @Description 指定评论为神评
     * @param [id, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    @PostMapping("/setcommandgodstate")
    public void setCommandGodstate(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        //获取评论对应的课程所属username
        org.framework.tutor.domain.CourseCommand courseCommand = courseCMService.getCommandById(id);
        if(courseCommand == null || !courseCommand.getUsername().equals(username)){
            resultMap.put("status", "invalid");
        } else{

            //判断当前课程神评是否已经为三个了
            Integer count = courseCMService.getGodCountById(courseCommand.getCid());
            if(count >= 3){
                resultMap.put("status", "full");
            }else {
                //设置神评
                courseCMService.setCommandGodstate(id);
                resultMap.put("status", "valid");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
