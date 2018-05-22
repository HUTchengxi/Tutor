package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseCommandApi;
import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseCommandService;
import org.framework.tutor.service.CourseMainService;
import org.framework.tutor.service.CourseOrderService;
import org.framework.tutor.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CourseCommandApiImpl implements CourseCommandApi {

    @Autowired
    private CourseCommandService courseCommandService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private CourseMainService courseMainService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    //TODO: 后续考虑加入redis
    @Override
    public String getCourseCommand(Integer cid, Integer startpos) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<CourseCommand> courseCommands = courseCommandService.getCourseCommand(cid, startpos);
        int count = courseCommands.size();
        if (count == 0) {
            resultMap.put("count", courseCommands.size());
        } else {
            resultMap.put("count", courseCommands.size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                UserMain userMain = userMainService.getByUser(courseCommand.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                rowMap.put("info", courseCommand.getInfo());
                rowMap.put("id", courseCommand.getId());
                rowMap.put("repid", courseCommand.getRepid());
                rowMap.put("uimgsrc", userMain.getImgsrc());
                rowMap.put("score", courseCommand.getScore());
                rowMap.put("username", userMain.getNickname());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO: 后续考虑加入redis
    @Override
    public String getCourseCommandGod(Integer cid) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCommandService.getCourseCommandGod(cid);
        int count = courseCommands.size();
        if (count == 0) {
            resultMap.put("count", courseCommands.size());
        } else {
            resultMap.put("count", courseCommands.size());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                UserMain userMain = userMainService.getByUser(courseCommand.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                rowMap.put("info", courseCommand.getInfo());
                rowMap.put("id", courseCommand.getId());
                rowMap.put("repid", courseCommand.getRepid());
                rowMap.put("uimgsrc", userMain.getImgsrc());
                rowMap.put("username", userMain.getNickname());
                rowMap.put("score", courseCommand.getScore());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO: 后续考虑加入redis
    @Override
    public String selMyCommand(Integer cid) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);
        List<Object> rowList = new ArrayList<>();

        //判断当前用户是否购买了该课程  cid  username start=1
        Integer state = 1;
        if (courseOrderService.getByUserAndState(cid, username, state) == null) {
            resultMap.put("status", "nobuy");
        } else {
            List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCommandService.getMyCommand(username, cid);
            int count = courseCommands.size();
            if (count == 0) {
                resultMap.put("count", courseCommands.size());
            } else {
                resultMap.put("count", courseCommands.size());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                    UserMain userMain = userMainService.getByUser(courseCommand.getUsername());
                    Map<String, Object> rowMap = new HashMap<>(16);
                    rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                    rowMap.put("info", courseCommand.getInfo());
                    rowMap.put("id", courseCommand.getId());
                    rowMap.put("repid", courseCommand.getRepid());
                    rowMap.put("uimgsrc", userMain.getImgsrc());
                    rowMap.put("score", courseCommand.getScore());
                    rowMap.put("username", userMain.getNickname());
                    rowList.add(rowMap);
                }
                resultMap.put("list", rowList);
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用了redis    更新tutor.[username].coursecommandcount
    @Override
    public String subMyCommand(Integer cid, String command, Integer score) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            resultMap.put("status", "invalid");
        } else {
            Integer row = courseCommandService.subMyCommand(cid, command, score, username);
            if (row == 1) {
                resultMap.put("status", "valid");

                //更新tutor.[username].coursecommandcount
                CourseMain courseMain = courseMainService.getCourseById(cid);
                String tutor = courseMain.getUsername();
                StringBuffer keyTemp = new StringBuffer("tutor");
                keyTemp.append("."+tutor).append(".coursecommandcount");
                if(redis.hasKey(keyTemp.toString())){
                    Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
                    redis.opsForValue().set(keyTemp.toString(), count.toString());
                }
            } else {
                resultMap.put("status","mysqlerr");
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用了redis     保存tutor.[username].coursecommandcount
    @Override
    public String getCommandCount() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());

        StringBuffer keyTemp = new StringBuffer("tutor");
        keyTemp.append("."+username).append(".coursecommandcount");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("count", redis.opsForValue().get(keyTemp.toString()));
        }else {
            Integer count = courseCommandService.getCommandCountNow(username, now);
            resultMap.put("count", count);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getScoreAvg() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(new Date());
        Double scoreAvg = courseCommandService.getScoreAvgNow(username, now);
        resultMap.put("count", scoreAvg==null?"null": scoreAvg);
        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String loadMyCommandInfo() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCommandService.loadMyCommandInfo(username);
        if (courseCommands.size() == 0) {
            resultMap.put("count", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                CourseMain courseMain = courseMainService.getCourseById(courseCommand.getCid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("ctime", simpleDateFormat.format(courseCommand.getCtime()));
                rowMap.put("info", courseCommand.getInfo());
                rowMap.put("cid", courseCommand.getCid());
                rowMap.put("imgsrc", courseMain.getImgsrc());
                rowMap.put("score", courseCommand.getScore());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续考虑使用redis
    @Override
    public String getCommandList(ParamMap paramMap) throws IOException {

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

                List<CourseMain> courseMains = courseMainService.getMyCourseList(username);
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
                    List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCommandService.getMyCommandList(courseId, pageNo * pageSize, pageSize);
                    if (courseCommands.size() == 0) {
                        rowMap.put("status", "commandNone");
                        rowMap.put("rows", rowList);
                    } else {
                        //获取所有数据总数
                        Integer count = courseCommandService.getCommandCountByIdlist(courseId);
                        rowMap.put("total", count);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                            resultMap = new HashMap<>(1);
                            CourseMain courseMain = courseMainService.getCourseById(courseCommand.getCid());
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
                List<CourseMain> courseMains = courseMainService.getByCoursename(courseName);
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
                    List<org.framework.tutor.domain.CourseCommand> courseCommands = courseCommandService.getMyCommandList(courseId, pageNo * pageSize, pageSize);
                    if (courseCommands.size() == 0) {
                        rowMap.put("status", "commandNone");
                        rowMap.put("rows", rowList);
                    } else {
                        //获取所有数据总数
                        Integer count = courseCommandService.getCommandCountByIdlist(courseId);
                        rowMap.put("total", count);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (org.framework.tutor.domain.CourseCommand courseCommand : courseCommands) {
                            resultMap = new HashMap<>(1);
                            CourseMain courseMain = courseMainService.getCourseById(courseCommand.getCid());
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

        return gson.toJson(rowMap);
    }


    @Override
    public String setCommandGodstate(Integer id) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        //获取评论对应的课程所属username
        org.framework.tutor.domain.CourseCommand courseCommand = courseCommandService.getCommandById(id);
        if (courseCommand == null || !courseCommand.getUsername().equals(username)) {
            resultMap.put("status", "invalid");
        } else {

            //判断当前课程神评是否已经为三个了
            Integer count = courseCommandService.getGodCountById(courseCommand.getCid());
            if (count >= 3) {
                resultMap.put("status", "full");
            } else {
                //设置神评
                courseCommandService.setCommandGodstate(id);
                resultMap.put("status", "valid");
            }
        }

        return gson.toJson(resultMap);
    }
}
