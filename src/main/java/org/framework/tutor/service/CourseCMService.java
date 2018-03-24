package org.framework.tutor.service;

import org.framework.tutor.domain.CourseCommand;

import java.util.List;

/**
 * 课程评论服务层接口
 * @author chengxi
 */
public interface CourseCMService {

    /**
     * 获取课程评论数据
     * @param cid
     * @param startpos
     * @return
     */
    List<CourseCommand> getCourseCommand(Integer cid, Integer startpos);

    /**
     * 获取课程神评数据
     * @param cid
     * @return
     */
    List<CourseCommand> getCourseCommandGod(Integer cid);

    /**
     * 获取指定用户对指定课程的评价数据
     * @param username
     * @param cid
     * @return
     */
    List<CourseCommand> getMyCommand(String username, Integer cid);

    /**
     * 发表用户评价
     * @param cid
     * @param command
     * @param username
     * @return
     */
    Integer subMyCommand(Integer cid, String command, Integer score, String username);

    /**
     * 获取指定用户的课程评论总数
     * @param username
     * @param now
     * @return
     */
    Integer getCommandCountNow(String username, String now);

    /**
     * 获取家教的课程今日评分平均值
     * @param username
     * @param now
     * @return
     */
    Double getScoreAvgNow(String username, String now);

    /**
     * 获取当前用户的课程评论数据
     * @param username
     * @return
     */
    List<CourseCommand> loadMyCommandInfo(String username);
}
