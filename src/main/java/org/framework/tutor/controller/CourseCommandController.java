package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseCommandApi;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 课程评价控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursecommand_con")
public class CourseCommandController {

    @Autowired
    private CourseCommandApi courseCommandApi;

    /**
     * @Description 获取课程评论数据
     */
    @RequestMapping("/getcoursecommand")
    public String getCourseCommand(Integer cid, Integer startpos) throws IOException {

        return courseCommandApi.getCourseCommand(cid, startpos);
    }

    /**
     * @Description 获取课程神评
     */
    @RequestMapping("/getcommandgod")
    public String getCourseCommandGod(Integer cid) throws IOException {

        return courseCommandApi.getCourseCommandGod(cid);
    }

    /**
     * @Dscription 查看当前用户对指定课程的评价
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/selmycommand")
    public String selMyCommand(Integer cid) throws IOException {

        return courseCommandApi.selMyCommand(cid);
    }

    /**
     * @Description 发表用户评价
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/submycommand")
    public String subMyCommand(Integer cid, String command, Integer score) throws IOException {

        return courseCommandApi.subMyCommand(cid, command, score);
    }

    /**
     * @Description 获取当前登录家教的课程评论总数
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getcommandcount")
    public String getCommandCount() throws IOException {

        return courseCommandApi.getCommandCount();
    }

    /**
     * @Description 获取当前登录家教的课程评分平均值
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getscoreavg")
    public String getScoreAvg() throws IOException {

        return courseCommandApi.getScoreAvg();
    }

    /**
     * @Description 获取当前用户的课程评论数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/loadmycommandinfo")
    public String loadMyCommandInfo() throws IOException {

        return courseCommandApi.loadMyCommandInfo();
    }

    /**
     * @Description 获取课程评论列表
     */
    @RequestMapping("/getcommandlist")
    public String getCommandList(@RequestBody ParamMap paramMap) throws IOException {

        return courseCommandApi.getCommandList(paramMap);
    }

    /**
     * @Description 指定评论为神评
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/setcommandgodstate")
    public String setCommandGodstate(@RequestParam Integer id) throws IOException {

        return courseCommandApi.setCommandGodstate(id);
    }
}
