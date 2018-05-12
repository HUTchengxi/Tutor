package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.BbsCardCollectApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.service.BbsCardCollectService;
import org.framework.tutor.service.BbsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yinjimin
 * @Description: 用户帖子收藏控制类
 * @date 2018年04月01日
 */
@RestController
@RequestMapping("/bbscardcollect_con")
public class BbsCardCollectController {

    @Autowired
    private BbsCardCollectApi bbsCardCollectApi;
    
    /**  
     * @Description 获取当前用户的收藏总数
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycollectcount")
    public String getMyCollectCount() throws IOException {

        return bbsCardCollectApi.getMyCollectCount();
    }

    /**
     * @Description 判断当前用户是否已收藏
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/checkcollectstatus")
    public String checkCollectStatus(@RequestParam Integer cardId) throws IOException {

        return bbsCardCollectApi.checkCollectStatus(cardId);
    }


    /**
     * @Description 收藏问题
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/collectcard")
    public String collectCard(@RequestParam Integer cardId) throws IOException {

        return bbsCardCollectApi.collectCard(cardId);
    }

    /**
     * @Description 取消收藏问题
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/uncollectcard")
    public String uncollectCard(@RequestParam Integer cardId) throws IOException {

        return bbsCardCollectApi.uncollectCard(cardId);
    }


    /**
     * @Description 获取当前用户收藏的帖子数据
     */
    @RequireAuth(ident = "user", type = "api")
    @PostMapping("/getmycollectinfo")
    public String getMyCollectInfo() throws IOException {

        return bbsCardCollectApi.getMyCollectInfo();
    }
}
