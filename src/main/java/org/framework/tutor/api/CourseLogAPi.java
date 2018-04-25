package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseLog;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseLService;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

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
