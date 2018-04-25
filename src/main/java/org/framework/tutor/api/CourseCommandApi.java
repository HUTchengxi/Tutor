package org.framework.tutor.api;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseCMService;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public interface CourseCommandApi {

    /**
     * 获取课程评论数据
     *
     * @param cid
     * @param response
     */
    public void getCourseCommand(Integer cid, Integer startpos, HttpServletResponse response) throws IOException;

    /**
     * 获取课程神评
     *
     * @param cid
     * @param response
     * @throws IOException
     */
    public void getCourseCommandGod(Integer cid, HttpServletResponse response) throws IOException;

    /**
     * 查看当前用户对指定课程的评价
     *
     * @param cid
     * @param request
     * @param response
     * @throws IOException
     */
    public void selMyCommand(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 发表用户评价
     *
     * @param cid
     * @param command
     * @param score
     * @param request
     * @param response
     */
    public void subMyCommand(Integer cid, String command, Integer score, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取当前登录家教的课程评论总数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取当前登录家教的课程评分平均值
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getScoreAvg(HttpServletRequest request, HttpServletResponse response) throws IOException ;

    /**
     * 获取当前用户的课程评论数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void loadMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * @param [paramMap, request, response]
     * @return void
     * @Description 获取课程评论列表
     * @author yinjimin
     * @date 2018/4/18
     */
    public void getCommandList(ParamMap paramMap, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 指定评论为神评
     * @param [id, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    public void setCommandGodstate(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
