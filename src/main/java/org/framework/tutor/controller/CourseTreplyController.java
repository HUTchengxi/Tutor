package org.framework.tutor.controller;

import org.framework.tutor.api.CourseTreplyApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     */
    @RequestMapping("/gettreply")
    public String getTreply(Integer cid, Integer cmid) throws IOException {
        return courseTreplyApi.getTreply(cid, cmid);
    }
}
