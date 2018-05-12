package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.BbsCardAnswerCommandApi;
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
 * @Description: 帖子回答的评论控制类
 * @date 2018年04月10日
 */
@RestController
@RequestMapping("/bbscardanswercommand_con")
public class BbsCardAnswerCommandController {

    @Autowired
    private BbsCardAnswerCommandApi bbsCardAnswerCommandApi;

    /**
     *
     * @Description 每次获取五条评论数据
     * @param [aid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @RequestMapping("/getcommandlistbyaid")
    public String getCommandListByAid(@RequestParam Integer startpos, @RequestParam Integer aid) throws IOException {

        return bbsCardAnswerCommandApi.getCommandListByAid(startpos, aid);
    }


    /**
     *
     * @Description 发表评论
     * @param [cardid, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/publishcommand")
    public String publishCommand(@RequestParam Integer cardid, @RequestParam Integer aid, @RequestParam String answer, Integer repfloor) throws IOException {

        return bbsCardAnswerCommandApi.publishCommand(cardid, aid, answer, repfloor);
    }

    @RequireAuth(ident = "user", type = "api")
    @PostMapping("getmycommandcount")
    public String getMyCommandCount() throws IOException {

        return bbsCardAnswerCommandApi.getMyCommandCount();
    }

    /**
     *
     * @Description 获取当前用户的评论数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/14
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmycommandinfo")
    public String getMyCommandInfo() throws IOException {

        return bbsCardAnswerCommandApi.getMyCommandInfo();
    }
}
