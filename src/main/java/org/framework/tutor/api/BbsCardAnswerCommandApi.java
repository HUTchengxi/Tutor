package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BbsCardAnswerCommandApi {

    /**
     *
     * @Description 每次获取五条评论数据
     * @param [aid, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    public void getCommandListByAid(Integer startpos, Integer aid, HttpServletResponse response) throws IOException;


    /**
     *
     * @Description 发表评论
     * @param [cardid, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    public void publishCommand(Integer cardid, Integer aid, String answer, Integer repfloor, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void getMyCommandCount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取当前用户的评论数据
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/14
     */
    public void getMyCommandInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
