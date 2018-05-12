package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseDeleteReqApi;
import org.framework.tutor.entity.ParamMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yinjimin
 * @Description: 课程下线申请
 * @date 2018年04月15日
 */
@RestController
@RequestMapping("/coursedeletereq_con")
public class CourseDeleteReqController {

    @Autowired
    private CourseDeleteReqApi courseDeleteReqApi;

    /**
     * @Description 提交课程下线申请
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/setmycoursedeletereq")
    public String setMyCourseDeleteReq(@RequestParam Integer cid, @RequestParam String descript) throws IOException {

        return courseDeleteReqApi.setMyCourseDeleteReq(cid, descript);
    }

    /**
     * @Description 获取课程下线申请数据列表
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/getreqlist")
    public String getReqList(@RequestBody ParamMap paramMap) throws IOException {

        return courseDeleteReqApi.getReqList(paramMap);
    }

    /**
     * @Description 获取课程下线申请详情
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/getreqdetail")
    public String getReqDetail(@RequestParam Integer reqid) throws IOException {

        return courseDeleteReqApi.getReqDetail(reqid);
    }
}
