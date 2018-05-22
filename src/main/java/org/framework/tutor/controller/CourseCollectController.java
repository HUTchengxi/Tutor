package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CourseCollectApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 课程收藏控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/coursecollect_con")
public class CourseCollectController {

    @Autowired
    private CourseCollectApi courseCollectApi;

    /**
     * 获取我的课程收藏记录
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycollect.json")
    public String getMyCollect(@RequestParam Integer startpos) throws IOException {

        return courseCollectApi.getMyCollect(startpos);
    }

    /**
     * 判断当前用户是否收藏了指定的课程
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/checkusercollect.json")
    public String checkUserCollect(@RequestParam Integer cid) throws IOException {

        return courseCollectApi.checkUserCollect(cid);
    }

    /**
     * 收藏/取消收藏
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/modusercollect.json")
    public String modUserCollect(@RequestParam Integer cid, @RequestParam String mod, String descript) throws IOException {

        return courseCollectApi.modUserCollect(cid, mod, descript);
    }

    /**
     * 获取家教的今日课程收藏数量
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getcollectcount.json")
    public String getCollectCount() throws IOException {

        return courseCollectApi.getCollectCount();
    }
}
