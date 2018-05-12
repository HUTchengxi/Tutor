package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BbsCardApi {

    /**
     * @Description 获取当前登录用户的帖子发表总数
     */
    public String getMyCardCount() throws IOException;


    /**
     * @Description 指定用户发表讨论
     * @param title: 标题（判断是否重复）
     * @param imgsrc：发表的背景图片
     * @param descript：发表的讨论内容
     */
    public String publishCard(String title, String imgsrc, String descript) throws IOException ;


    /**
     * @Description 获取指定关键字的帖子数据
     * @param keyword: 关键字
     */
    public String searchCard(String keyword) throws IOException;


    /**
     * @Description 加载最新五条热门帖子
     */
    public String loadHotCard() throws IOException;

    /**
     * @Description 获取对应问题数据
     * @param cardId: 帖子问题id
     */
    public String getCardById(String cardId) throws IOException;

    /**  
     * @Description 访问量加1
     * @param cardid: 帖子id
     */
    public String addViscount(Integer cardid) throws IOException;

    /**
     * @Description 获取当前用户发表的帖子数据
     */
    public String getMyCardInfo() throws IOException;
}
