package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.mapper.CourseCommandMapper;
import org.framework.tutor.service.CourseCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程评价服务层实现类
 * @author chengxi
 */
@Component
public class CourseCommandServiceImpl implements CourseCommandService {

    @Autowired
    private CourseCommandMapper courseCommandMapper;

    /**
     * 获取课程评论数据
     * @param cid
     * @param startpos
     * @return
     */
    @Override
    public List<CourseCommand> getCourseCommand(Integer cid, Integer startpos) {

        return courseCommandMapper.getCourseCommand(cid, startpos);
    }

    /**
     * 获取课程神评数据
     * @param cid
     * @return
     */
    @Override
    public List<CourseCommand> getCourseCommandGod(Integer cid) {

        return courseCommandMapper.getCourseCommandGod(cid);
    }

    /**
     * 获取指定用户对指定课程的评价数据
     * @param username
     * @param cid
     * @return
     */
    @Override
    public List<CourseCommand> getMyCommand(String username, Integer cid) {

        return courseCommandMapper.getMyCommand(username, cid);
    }

    /**
     * 发表用户评价
     * @param cid
     * @param command
     * @param username
     * @return
     */
    @Override
    public Integer subMyCommand(Integer cid, String command, Integer score, String username) {

        return courseCommandMapper.subMyCommand(cid, command, score, username);
    }

    /**
     * 获取指定用户的课程今日评论总数
     * @param username
     * @param now
     * @return
     */
    @Override
    public Integer getCommandCountNow(String username, String now) {

        return courseCommandMapper.getCommandCountNow(username, now);
    }

    /**
     * 获取家教的课程今日评分平均值
     * @param username
     * @param now
     * @return
     */
    @Override
    public Double getScoreAvgNow(String username, String now) {

        return courseCommandMapper.getScoreAvgNow(username, now);
    }

    /**
     * 获取当前用户的课程评论数据
     * @param username
     * @return
     */
    @Override
    public List<CourseCommand> loadMyCommandInfo(String username) {

        return courseCommandMapper.loadMyCommandInfo(username);
    }

    @Override
    public Integer getMyPublishAvg(Integer id) {
        return courseCommandMapper.getMyPublishAvg(id);
    }

    @Override
    public List<CourseCommand> getMyCommandList(String courseId, Integer offset, Integer pageSize) {
        return courseCommandMapper.getMyCommandList(courseId, offset, pageSize);
    }

    @Override
    public Integer getCommandCountByIdlist(String courseId) {
        return courseCommandMapper.getCommandCountByIdlist(courseId);
    }

    @Override
    public CourseCommand getCommandById(Integer id) {
        return courseCommandMapper.getCommandById(id);
    }

    @Override
    public void setCommandGodstate(Integer id) {
        courseCommandMapper.setCommandGodstate(id);
    }

    @Override
    public Integer getGodCountById(Integer cid) {
        return courseCommandMapper.geGodCountById(cid);
    }

    @Override
    public void updateCommandStatus(Integer cid, Integer status) {
        courseCommandMapper.updateCommandStatus(cid, status);
    }
}
