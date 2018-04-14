package org.framework.tutor.service;

import org.framework.tutor.domain.BbsCardAnswer;

import java.util.List;

public interface BbsCardAnswerService {

    /**
     *
     * @Description 获取对应的帖子答案数据
     * @param [cardId]
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswer>
     * @author yinjimin
     * @date 2018/4/7
     */
    List<BbsCardAnswer> getByCardid(Integer cardId);


    /**
     *
     * @Description 判断是否已经发了回答
     * @param [cardId, username, answer]
     * @return boolean
     * @author yinjimin
     * @date 2018/4/9
     */
    BbsCardAnswer checkIsExistAnswer(Integer cardId, String username);


    /**
     *
     * @Description 添加回答
     * @param [cardId, username, answer]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    void addAnswer(Integer cardId, String username, String answer);

    /**
     *
     * @Description 点赞加1
     * @param [aid]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    void addGcount(Integer aid);

    /**
     *
     * @Description 踩加1
     * @param [aid]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    void addBcount(Integer aid);


    /**
     *
     * @Description 对应的回答评论数加1
     * @param [aid]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    void addComcount(Integer aid);


    /**
     *
     * @Description 获取用户回答总数
     * @param [username]
     * @return java.lang.Integer
     * @author yinjimin
     * @date 2018/4/12
     */
    Integer getMyAnswerCount(String username);


    /**
     *
     * @Description 获取指定用户的回答数据
     * @param [username]
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswer>
     * @author yinjimin
     * @date 2018/4/13
     */
    List<BbsCardAnswer> getmyAnswerInfo(String username);
}
