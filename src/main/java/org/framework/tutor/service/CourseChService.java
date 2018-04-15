package org.framework.tutor.service;

import org.framework.tutor.domain.CourseChapter;

import java.util.List;

public interface CourseChService {

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @return
     */
    List<CourseChapter> getCourseChapter(Integer cid);

    /**  
     *    
     * @Description 获取指定目录数据
     * @param [id]    
     * @return org.framework.tutor.domain.CourseChapter
     * @author yinjimin  
     * @date 2018/4/15
     */  
    CourseChapter getById(Integer id);

    /**
     *
     * @Descriptio0n 删除指定目录
     * @param [id]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    void deleteChapter(Integer id);


    /**
     *
     * @Description 添加新的目录
     * @param [cid, title, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    void addChapter(Integer cid, Integer ord, String title, String descript);


    /**
     *
     * @Description 更新目录
     * @param [id, title, descript]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    void modChapter(Integer id, String title, String descript);


    /**
     *
     * @Description 获取指定课程当前最大的ord
     * @param [cid]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/15
     */
    Integer getLastOrd(Integer cid);
}
