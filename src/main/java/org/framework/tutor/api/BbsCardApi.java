package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BbsCardApi {

    /**
     *
     * @Description 获取当前登录用户的帖子发表总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/3/31
     */
    public void getMyCardCount(HttpServletRequest request, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 指定用户发表讨论
     * @param [title, imgsrc, descript, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/1
     */
    public void publishCard(String title, String imgsrc, String descript, HttpServletRequest request, HttpServletResponse response)
            throws IOException ;


    /**
     *
     * @Description 获取指定关键字的帖子数据
     * @param [keyword, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/3
     */
    public void searchCard(String keyword, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 加载最新五条热门帖子
     * @param [response]
     * @return void
     * @author yinjimin
     * @date 2018/4/3
     */
    public void loadHotCard(HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取对应问题数据
     * @param [cardId, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/6
     */
    public void getCardById(String cardId, HttpServletResponse response) throws IOException;

    public void addViscount(Integer cardid, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取当前用户发表的帖子数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/13
     */
    public void getMyCardInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
