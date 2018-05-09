package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
