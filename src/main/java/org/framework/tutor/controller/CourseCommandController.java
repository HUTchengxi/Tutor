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
 * 课程评价控制类
 */
@RestController
@RequestMapping("/coursecommand_con")
public class CourseCommandController {

    @Autowired
    private CourseCommandApi courseCommandApi;

    /**
     * 获取课程评论数据
     *
     * @param cid
     * @param response
     */
    @RequestMapping("/getcoursecommand")
    public void getCourseCommand(Integer cid, Integer startpos, HttpServletResponse response) throws IOException {

        courseCommandApi.getCourseCommand(cid, startpos, response);
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

        courseCommandApi.getCourseCommandGod(cid, response);
    }

    /**
     * 查看当前用户对指定课程的评价
     *
     * @param cid
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/selmycommand")
    public void selMyCommand(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCommandApi.selMyCommand(cid, request, response);
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
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/submycommand")
    public void subMyCommand(Integer cid, String command, Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCommandApi.subMyCommand(cid, command, score, request, response);
    }

    /**
     * 获取当前登录家教的课程评论总数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getcommandcount")
    public void getCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCommandApi.getCommandCount(request, response);
    }

    /**
     * 获取当前登录家教的课程评分平均值
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getscoreavg")
    public void getScoreAvg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCommandApi.getScoreAvg(request, response);
    }

    /**
     * 获取当前用户的课程评论数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/loadmycommandinfo")
    public void loadMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCommandApi.loadMyCommandInfo(request, response);
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

        courseCommandApi.getCommandList(paramMap, request, response);
    }

    /**
     *
     * @Description 指定评论为神评
     * @param [id, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/setcommandgodstate")
    public void setCommandGodstate(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        courseCommandApi.setCommandGodstate(id, request, response);
    }
}
