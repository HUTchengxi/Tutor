package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.CommandStarApi;
import org.framework.tutor.domain.CommandStar;
import org.framework.tutor.service.CommandStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class CommandStarApiImpl implements CommandStarApi {

    @Autowired
    private CommandStarService commandStarService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    //TODO：使用了Redis   保存[username].[cmid].commandstar    [cmid].commandgoodcount/commandbadcount
    @Override
    public String getMyCommandStar(Integer cmid) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(8);

        //获取当前用户的评论数据
        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append("."+cmid).append(".commandstar");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("status", redis.opsForValue().get(keyTemp.toString()));
        }else {
            CommandStar commandStar = commandStarService.getByUserAndCmid(username, cmid);
            if (commandStar == null) {
                resultMap.put("status", "uns");
                redis.opsForValue().set(keyTemp.toString(), "uns");
            } else {
                resultMap.put("status", commandStar.getScore());
                redis.opsForValue().set(keyTemp.toString(), commandStar.getScore().toString());
            }
        }

        //获取当前评论的所有点赞值
        Integer score = 1;
        keyTemp = new StringBuffer(cmid+"");
        keyTemp.append(".commandgoodcount");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("gcount", redis.opsForValue().get(keyTemp.toString()));
        }else {
            List<CommandStar> commandStarList = commandStarService.getCountByCmid(cmid, score);
            resultMap.put("gcount", commandStarList.size());
            redis.opsForValue().set(keyTemp.toString(), commandStarList.size()+"");
        }
        score = -1;
        keyTemp = new StringBuffer(cmid+"");
        keyTemp.append(".commandbadcount");
        if(redis.hasKey(keyTemp.toString())){
            resultMap.put("bcount", redis.opsForValue().get(keyTemp.toString()));
        }else {
            List<CommandStar> commandStarList1 = commandStarService.getCountByCmid(cmid, score);
            resultMap.put("bcount", commandStarList1.size());
            redis.opsForValue().set(keyTemp.toString(), commandStarList1.size()+"");
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用了Redis   更新[username].[cmid].commandstar    [cmid].commandgoodcount/commandbadcount
    @Override
    public String addMyStar(Integer score, Integer cmid) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已经打分
        CommandStar commandStar = commandStarService.getByUserAndCmid(username, cmid);
        if (commandStar != null) {
            resultMap.put("status", "stared");
        } else {
            Integer row = commandStarService.addMyStar(username, cmid, score);
            if (row == 1) {
                resultMap.put("status", "success");

                //更新[username].[cmid].commandstar缓存数据
                StringBuffer keyTemp = new StringBuffer(username);
                keyTemp.append("."+cmid).append(".commandstar");
                if(redis.hasKey(keyTemp.toString())){
                    redis.opsForValue().set(keyTemp.toString(), score.toString());
                }

                //更新[username].[cardid].commandgoodcount/commandbadcount缓存数据
                keyTemp = new StringBuffer(cmid+"");
                if(score == 1){
                    keyTemp.append(".commandgoodcount");
                }else{
                    keyTemp.append(".commandbadcount");
                }
                if(redis.hasKey(keyTemp.toString())){
                    Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
                    redis.opsForValue().set(keyTemp.toString(), count.toString());
                }
            } else {
                resultMap.put("status", "mysqlerr");
            }
        }

        return gson.toJson(resultMap);
    }
}
