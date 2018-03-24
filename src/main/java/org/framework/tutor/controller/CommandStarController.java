package org.framework.tutor.controller;

import com.google.gson.JsonParser;
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
    private CommandStarService commandStarService;

    /**
     * 获取指定用户的指定课程评论的点赞数据
     * @param cmid
     * @param request
     * @param response
     */
    @RequestMapping("/getmycommandstar")
    public void getMyCommandStar( Integer cmid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        //获取当前用户的评论数据
        CommandStar commandStar = commandStarService.getByUserAndCmid(username, cmid);
        if(commandStar == null){
            res = "{\"status\": \"uns\", ";
        }
        else{
            res = "{\"status\": \""+commandStar.getScore()+"\", ";
        }

        //获取当前评论的所有点赞值
        Integer score = 1;
        List<CommandStar> commandStarList = commandStarService.getCountByCmid(cmid, score);
        score = -1;
        List<CommandStar> commandStarList1 = commandStarService.getCountByCmid(cmid, score);
        System.out.println(commandStarList.size());
        res += "\"gcount\": \""+commandStarList.size()+"\", ";
        res += "\"bcount\": \""+commandStarList1.size()+"\"}";

        System.out.println(res);
        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
