package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.CommandStarApi;
import org.framework.tutor.domain.CommandStar;
import org.framework.tutor.service.CommandStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 用户评论点赞控制器
 * @author chengxi
 */
@RestController
@RequestMapping("/commandstar_con")
public class CommandStarController {

    @Autowired
    private CommandStarApi commandStarApi;

    /**
     * 获取指定用户的指定课程评论的点赞数据
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/getmycommandstar")
    public String getMyCommandStar( Integer cmid) throws IOException {

        return commandStarApi.getMyCommandStar(cmid);
    }

    /**
     * 实现评论的点赞与踩
     */
    @RequireAuth(ident = "user", type = "api")
    @RequestMapping("/addmystar")
    public String addMyStar(Integer score, Integer cmid) throws IOException {

        return commandStarApi.addMyStar(score, cmid);
    }
}
