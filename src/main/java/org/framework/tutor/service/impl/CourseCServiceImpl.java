package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CourseCollect;
import org.framework.tutor.mapper.CourseCMapper;
import org.framework.tutor.service.CourseCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程收藏服务层实现类
 * @author chengxi
 */
@Component
public class CourseCServiceImpl implements CourseCService {

    @Autowired
    private CourseCMapper courseCMapper;

    /**
     * 获取我的课程收藏记录
     * @param username
     * @return
     */
    @Override
    public List<CourseCollect> getMyCollect(String username, Integer startpos) {

        return courseCMapper.getMyCollect(username, startpos);
    }

    /**
     * 取消指定课程的收藏
     * @param cid
     * @param username
     * @return
     */
    @Override
    public boolean unCollect(Integer cid, String username) {

        return courseCMapper.unCollect(cid, username);
    }

    /**
     * 获取指定用户及课程id的收藏信息
     * @param cid
     * @param username
     * @return
     */
    @Override
    public CourseCollect getCollect(Integer cid, String username) {

        return courseCMapper.getCollect(cid, username);
    }

    /**
     * 收藏指定课程
     * @param cid
     * @param username
     * @return
     */
    @Override
    public boolean Collect(Integer cid, String username, String descript) {

        return courseCMapper.Collect(cid, username, descript);
    }
}
