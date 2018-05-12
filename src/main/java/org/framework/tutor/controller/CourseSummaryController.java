package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseSummaryApi;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.service.CourseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yinjimin
 * @Description: 课程概述
 * @date 2018年04月15日
 */
@RestController
@RequestMapping("/coursesummary_con")
public class CourseSummaryController {

    @Autowired
    private CourseSummaryApi courseSummaryApi;


    /**
     * @Description 获取指定课程的课程概述
     */
    @PostMapping("/getcoursesummaryinfo")
    public String getCourseSummaryInfo(@RequestParam Integer cid) throws IOException {

        return courseSummaryApi.getCourseSummaryInfo(cid);
    }


    /**
     * @Description 更新课程概述
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/updatecoursesummary")
    public String updateCourseSummary(@RequestParam Integer id, String title, String descript) throws IOException {

        return courseSummaryApi.updateCourseSummary(id, title, descript);
    }

}
