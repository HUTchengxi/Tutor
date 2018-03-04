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
    Integer subMyCommand(Integer cid, String command, String username);
}
