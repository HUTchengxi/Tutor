package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.BbsCardCollectApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.BbsCardCollect;
import org.framework.tutor.service.BbsCardCollectService;
import org.framework.tutor.service.BbsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class BbsCardCollectApiImpl implements BbsCardCollectApi {

    @Autowired
    private BbsCardCollectService bbsCardCollectService;

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    //TODO：使用了Redis    保存[username].collectcount
    @Override
    public String getMyCollectCount() throws IOException {

        HttpSession sessions = request.getSession();
        String username = (String) sessions.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        Integer count = null;
        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append(".collectcount");
        if (redis.hasKey(keyTemp.toString())) {
            count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString()));
        } else {
            count = bbsCardCollectService.getMyCollectCount(username);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }
        resultMap.put("count", count);

        return gson.toJson(resultMap);
    }


    //TODO：使用了Redis    保存[username].[cardid].collectstatus
    @Override
    public String checkCollectStatus(Integer cardId) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            resultMap.put("status", "none");
        } else {
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append("."+cardId).append(".collectstatus");
            if (redis.hasKey(keyTemp.toString())) {
                resultMap.put("status", redis.opsForValue().get(keyTemp.toString()));
            } else if (bbsCardCollectService.checkCollectStatus(cardId, username) != null) {
                resultMap.put("status", "col");
                redis.opsForValue().set(keyTemp.toString(), "col");
            } else {
                resultMap.put("status", "uncol");
                redis.opsForValue().set(keyTemp.toString(), "uncol");
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用了Redis    更新 [username].collectcount 和  [username].[cardid].collectstatus
    @Override
    public String collectCard(Integer cardId) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已收藏
        if (bbsCardCollectService.checkCollectStatus(cardId, username) != null) {
            resultMap.put("status", "none");
        } else {
            bbsCardCollectService.collectCard(cardId, username);
            bbsCardService.addColCountByCardId(cardId);
            resultMap.put("status", "col");

            //更新[username].collectcount缓存数据
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".collectcount");
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }

            //更新[username].[cardid].collectstatus缓存数据
            keyTemp = new StringBuffer(username);
            keyTemp.append("."+cardId).append(".collectstatus");
            if(redis.hasKey(keyTemp.toString())){
                redis.opsForValue().set(keyTemp.toString(), "col");
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：使用了Redis    更新 [username].collectcount 和  [username].[cardid].collectstatus
    @Override
    public String uncollectCard(Integer cardId) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断是否已收藏
        if (bbsCardCollectService.checkCollectStatus(cardId, username) == null) {
            resultMap.put("status", "none");
        } else {
            bbsCardCollectService.uncollectCard(cardId, username);
            bbsCardService.delColCountByCardId(cardId);
            resultMap.put("status", "uncol");

            //更新[username].collectcount缓存数据
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".collectcount");
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) - 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }

            //更新[username].[cardid].collectstatus缓存数据
            keyTemp = new StringBuffer(username);
            keyTemp.append("."+cardId).append(".collectstatus");
            if(redis.hasKey(keyTemp.toString())){
                redis.opsForValue().set(keyTemp.toString(), "uncol");
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO：后续可以考虑使用redis，目前基于值的复杂性暂时不考虑
    @Override
    public String getMyCollectInfo() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> resultList = new ArrayList<>();

        List<BbsCardCollect> bbsCardList = bbsCardCollectService.getMyCollectInfo(username);
        if (bbsCardList.size() == 0) {
            resultMap.put("status", "none");
        } else {
            SimpleDateFormat ysdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCardCollect bbsCardCollect : bbsCardList) {
                BbsCard bbsCard = bbsCardService.getCardById(bbsCardCollect.getCardid());
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("id", bbsCard.getId());
                rowMap.put("crtime", ysdf.format(bbsCard.getCrttime()));
                rowMap.put("coltime", ysdf.format(bbsCardCollect.getColtime()));
                rowMap.put("title", bbsCard.getTitle());
                rowMap.put("comcount", bbsCard.getComcount());
                rowMap.put("viscount", bbsCard.getViscount());
                rowMap.put("colcount", bbsCard.getColcount());
                rowMap.put("descript", bbsCard.getDescript());
                resultList.add(rowMap);
            }
            resultMap.put("list", resultList);
        }

        return gson.toJson(resultMap);
    }
}
