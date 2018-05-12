package org.framework.tutor.controller;

import com.google.gson.Gson;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseDeleteRespApi;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.service.CourseDeleteRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description: 课程下线响应
 * @date 2018年04月21日
 */
@RestController
@RequestMapping("/coursedeleteresp_con")
public class CourseDeleteRespController {

    @Autowired
    private CourseDeleteRespApi courseDeleteRespApi;

    /**
     * @Description 更新状态
     */
    @RequireAuth(ident = "admin", type = "api")
    @PostMapping("/modreqstatus")
    public String modReqStatus(@RequestParam Integer id, @RequestParam Integer status, @RequestParam String respDesc) throws IOException {

        return courseDeleteRespApi.modReqStatus(id, status, respDesc);
    }
}
