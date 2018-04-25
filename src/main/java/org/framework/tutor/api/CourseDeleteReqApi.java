package org.framework.tutor.api;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseDeleteReq;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.domain.CourseMain;
import org.framework.tutor.domain.CourseOrderManager;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.CourseDeleteReqService;
import org.framework.tutor.service.CourseDeleteRespService;
import org.framework.tutor.service.CourseMService;
import org.framework.tutor.service.CourseOrderManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CourseDeleteReqApi {

    /**
     *
     * @Description 提交课程下线申请
     * @param [cid, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    public void setMyCourseDeleteReq(Integer cid, String descript, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取课程下线申请数据列表
     * @param [paramMap, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    public void getReqList(ParamMap paramMap, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取课程下线申请详情
     * @param [reqid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/21
     */
    public void getReqDetail(Integer reqid, HttpServletResponse response) throws IOException;
}
