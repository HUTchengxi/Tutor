package org.framework.tutor.service;

import org.framework.tutor.domain.CourseLog;

import java.util.List;

/**
 * 课程记录服务层接口
 * @author chengxi
 */
public interface CourseLogService {

    /**
     * 获取我的课程记录
     * @param username
     * @return
     */
    List<CourseLog> getUserlog(String username);

    /**
     * 删除指定的课程记录
     * @param id
     * @return
     */
    boolean delLog(Integer id);

    /**  
     * @Description 新增课程记录
     * @param cid 课程id
     * @param username
     */
    void addLog(Integer cid, String username);


    /**  
     * @Description 获取指定用户的课程浏览记录数据
     * @param username
     */  
    Integer getUserlogCount(String username);

    /**  
     * @Description 获取用户最早的一个课程记录
     * @param username
     */
    CourseLog getFirstLog(String username);
}
