package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 帖子答案表服务层
 * @author yinjimin
 */
public interface BbsCardAnswerApi {

    /**
     * @Description 获取对应的帖子答案数据
     * @param cardId：帖子Id
     */
    public String getCardAnswerByCardid(Integer cardId) throws IOException;

    /**
     * @Description 判断当前用户是否已回答问题
     * @param cardId: 帖子id
     */
    public String checkUserCommand(Integer cardId) throws IOException;

    /**
     * @Description 添加回答
     * @param cardId: 帖子id
     * @param answer: 用户回答
     */
    public String addAnswer(Integer cardId, String answer) throws IOException;

    /**
     * @Description 获取用户回答总数
     * @param
     */
    public String getMyAnswerCount() throws IOException;

    /**  
     * @Description 获取用户的回答数据
     * @param
     */
    public String getMyAnswerInfo() throws IOException;
}
