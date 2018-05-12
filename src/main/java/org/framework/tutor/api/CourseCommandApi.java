package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseCommandApi {

    /**
     * @Description 获取课程评论数据
     * @param cid 课程id
     * @param startpos 开始位置
     */
    public String getCourseCommand(Integer cid, Integer startpos) throws IOException;

    /**
     * @Description 获取课程神评
     * @param cid 课程id
     */
    public String getCourseCommandGod(Integer cid) throws IOException;

    /**
     * @Description 查看当前用户对指定课程的评价
     * @param cid 课程id
     */
    public String selMyCommand(Integer cid) throws IOException;

    /**
     * @Description 发表用户评价
     * @param cid 课程id
     * @param command 用户评价
     * @param score 用户评分
     */
    public String subMyCommand(Integer cid, String command, Integer score) throws IOException;

    /**
     * @Description 获取当前登录家教的课程评论总数
     */
    public String getCommandCount() throws IOException;

    /**
     * @Descrption 获取当前登录家教的课程评分平均值
     */
    public String getScoreAvg() throws IOException ;

    /**
     * 获取当前用户的课程评论数据
     */
    public String loadMyCommandInfo() throws IOException;

    /**
     * @Description 获取课程评论列表
     * @param paramMap
     */
    public String getCommandList(ParamMap paramMap) throws IOException;

    /**
     * @Description 指定评论为神评
     * @param id 评论id
     */
    public String setCommandGodstate(Integer id) throws IOException;
}
