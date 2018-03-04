package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.mapper.CourseCMMapper;
import org.framework.tutor.service.CourseCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程评价服务层实现类
 * @author chengxi
 */
@Component
public class CourseCMServiceImpl implements CourseCMService {

    @Autowired
    private CourseCMMapper courseCMMapper;

    /**
     * 获取课程评论数据
     * @param cid
     * @param startpos
     * @return
     */
    @Override
    public List<CourseCommand> getCourseCommand(Integer cid, Integer startpos) {

        return courseCMMapper.getCourseCommand(cid, startpos);
    }

    /**
     * 获取课程神评数据
     * @param cid
     * @return
     */
    @Override
    public List<CourseCommand> getCourseCommandGod(Integer cid) {

        return courseCMMapper.getCourseCommandGod(cid);
    }

    /**
     * 获取指定用户对指定课程的评价数据
     * @param username
     * @param cid
     * @return
     */
    @Override
    public List<CourseCommand> getMyCommand(String username, Integer cid) {

        return courseCMMapper.getMyCommand(username, cid);
    }

    /**
     * 发表用户评价
     * @param cid
     * @param command
     * @param username
     * @return
     */
    @Override
    public Integer subMyCommand(Integer cid, String command, String username) {

        return courseCMMapper.subMyCommand(cid, command, username);
    }
}
