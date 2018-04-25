package org.framework.tutor.api;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CourseMainApi {

    /**
     * 加载课程数据
     *
     * @param stype
     * @param ctype
     * @param sort
     * @param startpos
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCourseList(Integer stype, String ctype, String sort, Integer startpos, Integer status, String keyword,
                              HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * @param [response]
     * @return void
     * @Description 获取所有科目类别
     * @author yinjimin
     * @date 2018/4/15
     */
    public void getAllCourseType(HttpServletResponse response) throws IOException;

    /**
     * 获取指定主类别的所有科目类别
     *
     * @param stype
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCourseType(String stype, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 关键字获取所有对应的课程数据
     *
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    public void courseSearch(String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取指定的课程的数据
     *
     * @param id
     * @param response
     * @throws IOException
     */
    public void getCourseById(Integer id, HttpServletResponse response) throws IOException;

    /**
     * 获取所搜索的课程数量，便于实现分页
     *
     * @param stype
     * @param ctype
     * @param status
     * @param keyword
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCourseCount(Integer stype, String ctype, Integer status, String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前家教的所有发布数据
     * @author yinjimin
     * @date 2018/4/14
     */
    public void getMyPublish(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * @param [name, stype, ctype, imgsrc, total, jcount, price, sumTitle1, sumTitle2, sumTitle3,
     *               sumDescript1, sumDescript2, sumDescript3, chapTitle, chaDescript, request, response]
     * @return void
     * @Description 发布课程
     * @author yinjimin
     * @date 2018/4/15
     */
    public void publishNewCourse(String name, Integer stype, String ctype, String descript,
                                 MultipartFile imgsrc, Integer total, Integer jcount,
                                 Double price, String sumTitle1, String sumTitle2,
                                 String sumTitle3, String sumDescript1, String sumDescript2,
                                 String sumDescript3, String chapTitle, String chapDescript,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 获取课程概述
     * @param
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/25
     */
    public @ResponseBody String getCourseSummary(Integer cid);
}
