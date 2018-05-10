package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.BbsCardApi;
import org.framework.tutor.domain.BbsCard;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.service.BbsCardService;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class BbsCardApiImpl implements BbsCardApi {

    @Autowired
    private BbsCardService bbsCardService;

    @Autowired
    private UserMainService userMainService;

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前登录用户的帖子发表总数
     * @author yinjimin
     * @date 2018/3/31
     */
    @Override
    public void getMyCardCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        PrintWriter writer = response.getWriter();

        Integer count = bbsCardService.getMyCardCount(username);
        resultMap.put("count", count);

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param [title, imgsrc, descript, request, response]
     * @return void
     * @Description 指定用户发表讨论
     * @author yinjimin
     * @date 2018/4/1
     */
    @Override
    public void publishCard(String title, String imgsrc, String descript, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter writer = response.getWriter();
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
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param [keyword, response]
     * @return void
     * @Description 获取指定关键字的帖子数据
     * @author yinjimin
     * @date 2018/4/3
     */
    @Override
    public void searchCard(String keyword, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }


    /**
     * @param [response]
     * @return void
     * @Description 加载最新五条热门帖子
     * @author yinjimin
     * @date 2018/4/3
     */
    @Override
    public void loadHotCard(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [cardId, response]
     * @return void
     * @Description 获取对应问题数据
     * @author yinjimin
     * @date 2018/4/6
     */
    @Override
    public void getCardById(String cardId, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
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
            writer.print(gson.toJson(resultMap));
            writer.flush();
            writer.close();
        }
    }

    @Override
    public void addViscount(Integer cardid, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * @param [request, response]
     * @return void
     * @Description 获取当前用户发表的帖子数据
     * @author yinjimin
     * @date 2018/4/13
     */
    @Override
    public void getMyCardInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
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

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
