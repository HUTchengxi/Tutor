package org.framework.tutor.service;

import org.framework.tutor.domain.CourseCommand;

import java.util.List;

/**
 * 课程评论服务层接口
 * @author chengxi
 */
public interface CourseCommandService {

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

    /**
     *
     * @Description 获取指定课程的评分平均值
     * @param [id]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/14
     */
    Integer getMyPublishAvg(Integer id);

    /**
     *
     * @Description 查询当前家教的课程评论数据
     * @param [courseId]
     * @return java.util.List<org.framework.tutor.domain.CourseCommand>
     * @author yinjimin
     * @date 2018/4/18
     */
    List<CourseCommand> getMyCommandList(String courseId, Integer offset, Integer pageSize);

    /**
     *
     * @Description 获取指定家教的课程评论总数
     * @param [courseId]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/18
     */
    Integer getCommandCountByIdlist(String courseId);

    /**  
     *    
     * @Description 通过id获取对应的评论数据
     * @param to    
     * @return org.framework.tutor.domain.CourseCommand
     * @author yinjimin  
     * @date 2018/4/18
     */  
    CourseCommand getCommandById(Integer id);

    /**
     *
     * @Description 设置神评
     * @param [id]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    void setCommandGodstate(Integer id);

    /**
     *
     * @Description 获取指定课程的神评数量
     * @param [cid]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/18
     */
    Integer getGodCountById(Integer cid);

    /**
     *
     * @Description 更新对应的评论的status状态
     * @param [cid, status]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    void updateCommandStatus(Integer cid, Integer status);
}
