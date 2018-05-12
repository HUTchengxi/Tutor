package org.framework.tutor.service.impl;

import org.framework.tutor.domain.BbsCardAnswer;
import org.framework.tutor.mapper.BbsCardAnswerMapper;
import org.framework.tutor.service.BbsCardAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 帖子答案服务层
 * @date 2018年04月05日
 */
@Component
public class BbsCardAnswerServiceImpl implements BbsCardAnswerService {

    @Autowired
    private BbsCardAnswerMapper bbsCardAnswerMapper;

    @Override
    public List<BbsCardAnswer> getByCardid(Integer cardId) {
        return bbsCardAnswerMapper.getByCardid(cardId);
    }

    @Override
    public BbsCardAnswer checkIsExistAnswer(Integer cardId, String username) {
        return bbsCardAnswerMapper.checkIsExistAnswer(cardId, username);
    }

    @Override
    public void addAnswer(Integer cardId, String username, String answer) {
        bbsCardAnswerMapper.addAnswer(cardId, username, answer);
    }

    @Override
    public void addGcount(Integer aid) {
        bbsCardAnswerMapper.addGcount(aid);
    }

    @Override
    public void addBcount(Integer aid) {
        bbsCardAnswerMapper.addBcount(aid);
    }

    @Override
    public void addComcount(Integer aid) {
        bbsCardAnswerMapper.addComcount(aid);
    }

    @Override
    public Integer getMyAnswerCount(String username) {
        return bbsCardAnswerMapper.getMyAnswerCount(username);
    }

    @Override
    public List<BbsCardAnswer> getmyAnswerInfo(String username) {
        return bbsCardAnswerMapper.getMyAnswerInfo(username);
    }
}
