package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.BbsCardApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 论坛帖子控制类
 * @author yinjimin
 * @date 2018年03月31日
 */
@RestController
@RequestMapping("/bbscard_con")
public class BbsCardController {

    @Autowired
    private BbsCardApi bbsCardApi;

    /**
     * @Description 获取当前登录用户的帖子发表总数
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycardcount")
    public String getMyCardCount() throws IOException {

        return bbsCardApi.getMyCardCount();
    }


    /**  
     * @Description 指定用户发表讨论
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/publishCard")
    public String publishCard(String title, String imgsrc, String descript) throws IOException {

        return bbsCardApi.publishCard(title, imgsrc, descript);
    }


    /**
     * @Description 获取指定关键字的帖子数据
     */
    @PostMapping("/searchCard")
    public String searchCard(String keyword) throws IOException {

        return bbsCardApi.searchCard(keyword);
    }


    /**
     * @Description 加载最新五条热门帖子
     */
    @PostMapping("/loadhotcard")
    public String loadHotCard() throws IOException {

        return bbsCardApi.loadHotCard();
    }

    /**  
     * @Description 获取对应问题数据
     */
    @PostMapping("/getcardbyid")
    public String getCardById(@RequestParam String cardId) throws IOException {

        return bbsCardApi.getCardById(cardId);
    }

    /**
     * @Description 访问量加1
     */
    @PostMapping("/addviscount")
    public String addViscount(@RequestParam Integer cardid) throws IOException {

        return bbsCardApi.addViscount(cardid);
    }

    /**
     * @Description 获取当前用户发表的帖子数据
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmycardinfo")
    public String getMyCardInfo() throws IOException {

        return bbsCardApi.getMyCardInfo();
    }
}
