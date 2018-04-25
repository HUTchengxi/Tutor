package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.CourseSummary;
import org.framework.tutor.service.CourseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public interface CourseSummaryApi {

    /**
     *
     * @Description 获取指定课程的课程概述
     * @param [cid, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    public void getCourseSummaryInfo(Integer cid, HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 更新课程概述
     * @param [id, title, descript, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/15
     */
    public void updateCourseSummary(Integer id, String title, String descript, HttpServletResponse response) throws IOException;
}
