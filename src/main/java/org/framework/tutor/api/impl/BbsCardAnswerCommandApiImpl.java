package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.BbsCardAnswerCommandApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardAnswerCommand;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardAnswerCommandService;
import org.framework.tutor.service.BbsCardAnswerService;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BbsCardAnswerCommandApiImpl implements BbsCardAnswerCommandApi {

    @Autowired
    private BbsCardAnswerCommandService bbsCardAnswerCommandService;

    @Autowired
    private BbsCardAnswerService bbsCardAnswerService;

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    //TODO：后续可以考虑使用redis，目前基于值的复杂性暂时不考虑
    @Override
    public String getCommandListByAid(Integer startpos, Integer aid) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        startpos *= 5;
        List<BbsCardAnswerCommand> bbsCardAnswerCommands = bbsCardAnswerCommandService.getCommandListByAid(aid, startpos);

        int count = bbsCardAnswerCommands.size();
        if(count != 0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardAnswerCommand bbsCardAnswerCommand: bbsCardAnswerCommands) {
                UserMain userMain = userMainService.getByUser(bbsCardAnswerCommand.getUsername());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("comtime", simpleDateFormat.format(bbsCardAnswerCommand.getComtime()));
                rowMap.put("nickname", userMain.getNickname());
                rowMap.put("imgsrc", userMain.getImgsrc());
                rowMap.put("cardid", bbsCardAnswerCommand.getCardid());
                rowMap.put("floor", bbsCardAnswerCommand.getFloor());
                rowMap.put("repfloor", bbsCardAnswerCommand.getRepfloor());
                rowMap.put("id", bbsCardAnswerCommand.getId());
                rowMap.put("descript", bbsCardAnswerCommand.getComment());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }
        else{
            resultMap.put("status", "none");
        }

        return gson.toJson(resultMap);
    }


    //TODO：这里使用了Redis  更新[username].commandcount
    @Override
    public String publishCommand(Integer cardid, Integer aid, String answer, Integer repfloor) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer floor = bbsCardAnswerCommandService.getCurrentFloor(cardid, aid);
        if(floor == null){
            floor = 0;
        }
        floor += 1;
        bbsCardAnswerCommandService.publishCommand(username, cardid, aid, answer, floor, repfloor);
        bbsCardAnswerService.addComcount(aid);
        resultMap.put("status", "valid");

        //[username].commandcount缓存数据更新
        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append(".commandcount");
        if(redis.hasKey(keyTemp.toString())){
            Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }

        return gson.toJson(resultMap);
    }

    //TODO：这里使用了redis  保存[username].commandcount
    @Override
    public String getMyCommandCount() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append(".commandcount");
        Integer count = null;
        if(redis.hasKey(keyTemp.toString())){
            count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString()));
        }else {
            count = bbsCardAnswerCommandService.getComcountByUser(username);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }
        resultMap.put("count", count);

        return gson.toJson(resultMap);
    }

    /**
     *
     * @Description 获取当前用户的评论数据
     * @param [request, response]
     * @return String
     * @author yinjimin
     * @date 2018/4/14
     */
    //TODO：后续可以考虑使用redis，目前基于值的复杂性暂时不考虑
    @Override
    public String getMyCommandInfo() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<BbsCardAnswerCommand> bbsCardAnswerCommands = bbsCardAnswerCommandService.getMyCommandInfo(username);

        if(bbsCardAnswerCommands.size() == 0){
            resultMap.put("status", "none");
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardAnswerCommand bbsCardAnswerCommand: bbsCardAnswerCommands) {
                BbsCard bbsCard = bbsCardService.getCardById(bbsCardAnswerCommand.getCardid());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("comtime", simpleDateFormat.format(bbsCardAnswerCommand.getComtime()));
                rowMap.put("title", bbsCard.getTitle());
                rowMap.put("cid", bbsCard.getId());
                rowMap.put("floor", bbsCardAnswerCommand.getFloor());
                rowMap.put("repfloor", bbsCardAnswerCommand.getRepfloor());
                rowMap.put("comment", bbsCardAnswerCommand.getComment());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }
}
