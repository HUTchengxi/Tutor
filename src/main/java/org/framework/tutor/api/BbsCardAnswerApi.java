package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BbsCardAnswerApi {

    /**
     *
     * @Description 获取对应的帖子答案数据
     * @param [cardId, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/7
     */
    public void getCardAnswerByCardid(Integer cardId, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 判断当前用户是否已回答问题
     * @param [cardId, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    public void checkUserCommand(Integer cardId, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 添加回答
     * @param [cardId, answer, request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    public void addAnswer(Integer cardId, String answer, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取用户回答总数
     * @param [request, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/12
     */
    public void getMyAnswerCount(HttpServletRequest request, HttpServletResponse response) throws IOException;


    public void getMyAnswerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
