package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseCollect;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.service.CourseCService;
import org.framework.tutor.service.CourseMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface CourseCollectApi {

    /**
     * 获取我的课程收藏记录
     * @param request
     * @param response
     * @param startpos
     * @throws IOException
     */
    public void getMyCollect(HttpServletRequest request, HttpServletResponse response, Integer startpos) throws IOException;

    /**
     * 判断当前用户是否收藏了指定的课程
     * @param cid
     * @param request
     * @param response
     */
    public void checkUserCollect(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 收藏/取消收藏
     * @param cid
     * @param mod
     * @param descript
     * @param request
     * @param response
     * @throws IOException
     */
    public void modUserCollect(Integer cid, String mod, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取家教的今日课程收藏数量
     * @param request
     * @param response
     */
    public void getCollectCount(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
