package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.BbsCardAnswerApi;
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
 * @Description: 帖子答案控制类
 * @date 2018年04月05日
 */
@RestController
@RequestMapping("/bbscardanswer_con")
public class BbsCardAnswerController {

    @Autowired
    private BbsCardAnswerApi bbsCardAnswerApi;

    /**
     * @Description 获取对应的帖子答案数据
     */
    @PostMapping("/getcardanswerbycardid")
    public String getCardAnswerByCardid(@RequestParam Integer cardId, HttpServletResponse response) throws IOException {

        return bbsCardAnswerApi.getCardAnswerByCardid(cardId);
    }

    /**
     * @Description 判断当前用户是否已回答问题
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/checkusercommand")
    public String checkUserCommand(@RequestParam Integer cardId) throws IOException {

        return bbsCardAnswerApi.checkUserCommand(cardId);
    }

    /**
     * @Description 添加回答
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/addanswer")
    public String addAnswer(@RequestParam Integer cardId, @RequestParam String answer) throws IOException {

        return bbsCardAnswerApi.addAnswer(cardId, answer);
    }

    /**
     * @Description 获取用户回答总数
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmyanswercount")
    public String getMyAnswerCount() throws IOException {

        return bbsCardAnswerApi.getMyAnswerCount();
    }

    /**  
     * @Description 获取当前登录用户的回答数据
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmyanswerinfo")
    public String getMyAnswerInfo() throws IOException {

        return bbsCardAnswerApi.getMyAnswerInfo();
    }
}
