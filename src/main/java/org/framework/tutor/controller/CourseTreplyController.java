package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.api.CourseTreplyApi;
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
public class CourseTreplyController {

    @Autowired
    private CourseTreplyApi courseTreplyApi;

    /**
     * 获取对应用户的指定课程的家教回复数据
     * @param cid
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/gettreply")
    public void getTreply(Integer cid, Integer cmid, HttpServletResponse response) throws IOException {
        courseTreplyApi.getTreply(cid, cmid, response);
    }
}
