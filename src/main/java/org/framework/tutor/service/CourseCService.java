package org.framework.tutor.service;

import org.framework.tutor.domain.CourseCollect;

import java.util.List;

/**
 * 课程收藏服务层接口
 * @author chengxi
 */
public interface CourseCService {

    /**
     * 获取我的课程收藏记录
     * @param username
     * @return
     */
    List<CourseCollect> getMyCollect(String username, Integer startpos);

    /**
     * 取消指定课程的收藏
     * @param cid
     * @param username
     * @return
     */
    boolean unCollect(Integer cid, String username);

    /**
     * 获取指定用户及课程id的收藏西信息
     * @param cid
     * @param username
     * @return
     */
    CourseCollect getCollect(Integer cid, String username);

    /**
     * 收藏课程
     * @param cid
     * @param username
     * @param descipt
     * @return
     */
    boolean Collect(Integer cid, String username, String descipt);
}
