package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseCommandDeleteReqApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yinjimin
 * @Description: 评论删除待审
 * @date 2018年04月18日
 */
@RestController
@RequestMapping("/coursecommanddeletereq_con")
public class CourseCommandDeleteReqController {

    @Autowired
    private CourseCommandDeleteReqApi courseCommandDeleteReqApi;

    /**
     * @Description 提交评论删除申请
     */
    @RequireAuth(ident = "tutor", type = "api")
    @PostMapping("/addcommanddeletereq")
    public String addCommandDeleteReq(@RequestParam Integer cid, @RequestParam String info) throws IOException {

        return courseCommandDeleteReqApi.addCommandDeleteReq(cid, info);
    }
}
