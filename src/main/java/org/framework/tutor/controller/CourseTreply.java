package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.CourseTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 课程家教老师评价回复控制类
 * @author chegnxi
 */
@RestController
@RequestMapping("/coursetreply_con")
public class CourseTreply {

    @Autowired
    private CourseTService courseTService;

    /**
     * 获取对应用户的指定课程的家教回复数据
     * @param cid
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/gettreply")
    public void getTreply(Integer cid, Integer cmid, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        org.framework.tutor.domain.CourseTreply courseTreply = courseTService.getCourseTreply(cid, cmid);

        if(courseTreply == null){
            res = "{\"info\": \"null\"}";
        }
        else {
            res = "{\"info\": \"" + courseTreply.getInfo() + "\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
