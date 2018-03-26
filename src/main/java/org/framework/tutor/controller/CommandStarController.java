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
        res += "\"gcount\": \""+commandStarList.size()+"\", ";
        res += "\"bcount\": \""+commandStarList1.size()+"\"}";

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 实现评论的点赞与踩
     * @param status
     * @param cmid
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/addmystar")
    public void addMyStar(Integer score, Integer cmid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        //判断是否登录
        if(username == null){
            res = "{\"status\": \"nologin\"}";
        }
        else {

            //判断是否已经打分
            CommandStar commandStar = commandStarService.getByUserAndCmid(username, cmid);
            if (commandStar != null) {
                res = "{\"status\": \"invalid\"}";
            } else {
                Integer row = commandStarService.addMyStar(username, cmid, score);
                if (row == 1) {
                    res = "{\"status\": \"success\"}";
                } else {
                    res = "{\"status\": \"mysqlerr\"}";
                }
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
