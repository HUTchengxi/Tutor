package org.framework.tutor.service;

import org.framework.tutor.domain.TutorsysBtns;

/**
 * 所有家教常用链接服务层接口
 * @author chengxi
 */
public interface TutorsysBtnsService {

    /**
     * 获取指定的链接数据
     * @param bid
     * @return
     */
    TutorsysBtns getById(Integer bid);
}
