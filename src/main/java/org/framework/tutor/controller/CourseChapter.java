package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.CourseChService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 课程章节目录控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursechapter_con")
public class CourseChapter {

    @Autowired
    private CourseChService courseChService;

    /**
     * 获取指定课程的章节目录数据
     * @param cid
     * @param response
     */
    @RequestMapping("/getcoursechapter")
    public void getCourseChapter(Integer cid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<org.framework.tutor.domain.CourseChapter> courseChapters = courseChService.getCourseChapter(cid);
        if(courseChapters.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (org.framework.tutor.domain.CourseChapter courseChapter: courseChapters) {
                res += "\""+i+"\": ";
                String temp = "{\"title\": \""+courseChapter.getTitle()+"\", " +
                        "\"descript\": \""+courseChapter.getDescript()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
