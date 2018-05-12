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
     * @Description 获取指定课程的课程概述
     * @param cid 课程id
     */
    public String getCourseSummaryInfo(Integer cid) throws IOException;


    /**
     * @Description 更新课程概述
     * @param id 课程id
     * @param title 课程概述标题
     * @param descript 课程概述
     */
    public String updateCourseSummary(Integer id, String title, String descript) throws IOException;
}
