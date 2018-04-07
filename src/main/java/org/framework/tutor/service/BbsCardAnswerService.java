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
}
