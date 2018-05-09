package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseLogAPi {

    /**
     * 获取我的课程记录
     * @param request
     * @param response
     */
    public void getLog(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     * 删除指定的课程记录
     * @param id
     * @param request
     * @param response
     */
    public void delLog(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
