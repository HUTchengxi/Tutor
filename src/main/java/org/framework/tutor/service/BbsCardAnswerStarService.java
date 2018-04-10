package org.framework.tutor.service;

import org.framework.tutor.domain.BbsCardAnswerStar;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @Description 帖子回答star服务层接口
 * @author yinjimin
 * @date 2018/4/10
 */
public interface BbsCardAnswerStarService {

    /**
     *
     * @Description 校验当前用户是否star指定回答
     * @param [aid, username]
     * @return org.framework.tutor.domain.BbsCardAnswerStar
     * @author yinjimin
     * @date 2018/4/10
     */
    BbsCardAnswerStar checkUserStar(Integer aid, String username);


    /**
     *
     * @Description 当前用户star指定回答
     * @param [aid, username, score]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    void addUserStar(Integer aid, String username, Integer score);
}
