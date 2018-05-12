package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.BbsCardApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.util.CommonUtil;
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
public class BbsCardApiImpl implements BbsCardApi {

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMainService userMainService;

    /**
     * @Description 直接使用SpringBoot集成的Redis进行操作
     */
    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private HttpServletRequest request;


    //TODO：使用了Redis   [username].cardcount
    @Override
    public String getMyCardCount() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        StringBuffer keyTemp = new StringBuffer(username);
        keyTemp.append(".cardcount");
        Integer count = null;
        if(redis.hasKey(keyTemp.toString())){
            count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString()));
        }else{
            count = bbsCardService.getMyCardCount(username);
            redis.opsForValue().set(keyTemp.toString(), count.toString());
        }
        resultMap.put("count", count);

        return gson.toJson(resultMap);
    }


    //TODO: 使用了Redis，发表之后redis中缓存的对应的用户帖子数加1
    @Override
    public String publishCard(String title, String imgsrc, String descript) throws IOException {

        Gson gson = new Gson();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Map<String, Object> resultMap = new HashMap<>(1);

        //判断标题是否已经被占用了
        if (bbsCardService.getByTitle(title) != null) {
            resultMap.put("status", "texist");
        } else {
            bbsCardService.publishCard(username, title, imgsrc, descript);
            resultMap.put("status", "valid");
            StringBuffer keyTemp = new StringBuffer(username);
            keyTemp.append(".cardcount");
            //更新缓存
            if(redis.hasKey(keyTemp.toString())){
                Integer count = Integer.valueOf(redis.opsForValue().get(keyTemp.toString())) + 1;
                redis.opsForValue().set(keyTemp.toString(), count.toString());
            }
        }

        return gson.toJson(resultMap);
    }


    //TODO: 可以使用redis，考虑到值的复杂性，后续加入
    @Override
    public String searchCard(String keyword) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Object> rowList = new ArrayList<>();

        //获取课程记录
        List<BbsCard> bbsCards = bbsCardService.searchCard(keyword);
        if (bbsCards.size() == 0) {
            resultMap.put("count", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCard bbsCard : bbsCards) {
                UserMain userMain = userMainService.getByUser(bbsCard.getUsername());
                Map<String, Object> rowMap = new HashMap<>(7);
                rowMap.put("crttime", simpleDateFormat.format(bbsCard.getCrttime()));
                rowMap.put("nickname", userMain.getNickname());
                rowMap.put("imgsrc", userMain.getImgsrc());
                rowMap.put("bimgsrc", bbsCard.getImgsrc());
                rowMap.put("id", bbsCard.getId());
                rowMap.put("descript", bbsCard.getDescript());
                rowMap.put("title", bbsCard.getTitle());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String loadHotCard() throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Object> rowList = new ArrayList<>();

        //获取课程记录
        List<BbsCard> bbsCards = bbsCardService.loadHotCard();
        if (bbsCards.size() == 0) {
            resultMap.put("count", 0);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCard bbsCard : bbsCards) {
                UserMain userMain = userMainService.getByUser(bbsCard.getUsername());
                Map<String, Object> rowMap = new HashMap<>(7);
                rowMap.put("crttime", simpleDateFormat.format(bbsCard.getCrttime()));
                rowMap.put("nickname", userMain.getNickname());
                rowMap.put("imgsrc", userMain.getImgsrc());
                rowMap.put("bimgsrc", bbsCard.getImgsrc());
                rowMap.put("id", bbsCard.getId());
                rowMap.put("descript", bbsCard.getDescript());
                rowMap.put("title", bbsCard.getTitle());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    //TODO: 可以使用redis，考虑到值的复杂性，后续加入
    @Override
    public String getCardById(String cardId) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(9);

        try {
            Integer id = Integer.parseInt(cardId);
            BbsCard bbsCard = bbsCardService.getCardById(id);
            if (bbsCard == null) {
                resultMap.put("status", "none");
            } else {
                UserMain userMain = userMainService.getByUser(bbsCard.getUsername());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                resultMap.put("crttime", simpleDateFormat.format(bbsCard.getCrttime()));
                resultMap.put("username", bbsCard.getUsername());
                resultMap.put("viscount", bbsCard.getViscount());
                resultMap.put("colcount", bbsCard.getColcount());
                resultMap.put("comcount", bbsCard.getComcount());
                resultMap.put("imgsrc", userMain.getImgsrc());
                resultMap.put("id", bbsCard.getId());
                resultMap.put("descript", bbsCard.getDescript());
                resultMap.put("title", bbsCard.getTitle());
            }
        } catch (Exception e) {
            resultMap.put("status", "sysexception");
            e.printStackTrace();
        } finally {
            return gson.toJson(resultMap);
        }
    }


    @Override
    public String addViscount(Integer cardid) throws IOException {

        String res = null;
        HttpSession session = request.getSession();
        String curIp = (String) session.getAttribute("ip");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        //获取真实ip
        String ip = CommonUtil.getIpAddr(request);
        //同一ip同一文章目前只加一次
        if (curIp != null && curIp.equals(ip) && CommonUtil.isExistCardid(cardid)) {
            resultMap.put("status", "added");
        } else {
            bbsCardService.addViscountByCardId(cardid);
            CommonUtil.addCardList(cardid);
            session.setAttribute("ip", ip);
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }


    //TODO: 可以使用redis，考虑到值的复杂性，后续加入
    @Override
    public String getMyCardInfo() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        List<Object> rowList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>(4);

        List<BbsCard> bbsCardList = bbsCardService.getMyCardInfo(username);
        if (bbsCardList.size() == 0) {
            resultMap.put("status", "none");
        } else {
            SimpleDateFormat ysdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (BbsCard bbsCard : bbsCardList) {
                Map<String, Object> rowMap = new HashMap<>(16);
                rowMap.put("id", bbsCard.getId());
                rowMap.put("crttime", ysdf.format(bbsCard.getCrttime()));
                rowMap.put("title", bbsCard.getTitle());
                rowMap.put("comcount", bbsCard.getComcount());
                rowMap.put("viscount", bbsCard.getViscount());
                rowMap.put("colcount", bbsCard.getColcount());
                rowMap.put("descript", bbsCard.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }
}
