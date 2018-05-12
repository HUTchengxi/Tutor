package org.framework.tutor.service.impl;

import org.framework.tutor.domain.BbsCardAnswerStar;
import org.framework.tutor.mapper.BbsCardAnswerStarMapper;
import org.framework.tutor.service.BbsCardAnswerStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yinjimin
 * @Description: 帖子答案star服务层实现类
 * @date 2018年04月10日
 */
@Component
public class BbsCardAnswerStarServiceImpl implements BbsCardAnswerStarService {

    @Autowired
    private BbsCardAnswerStarMapper bbsCardAnswerStarMapper;

    @Override
    public BbsCardAnswerStar checkUserStar(Integer aid, String username) {
        return bbsCardAnswerStarMapper.checkUserStar(aid, username);
    }

    @Override
    public void addUserStar(Integer aid, String username, Integer score) {
        bbsCardAnswerStarMapper.addUserStar(aid, username, score);
    }
}
