package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseChapterApi {

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @param response
     */
    public void getCourseChapter(Integer cid, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 删除指定目录
     * @param [id, response, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    public void deleteChapter(Integer id, HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     *
     * @Description 更新目录
     * @param [id, title, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    public void modChapter(Integer id, Integer cid, String title, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
