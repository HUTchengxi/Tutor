package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseLogAPi {

    /**
     * 获取我的课程记录
     */
    public String getLog() throws IOException;


    /**
     * 删除指定的课程记录
     * @param id 课程记录id
     */
    public String delLog(Integer id) throws IOException;
}
