package org.framework.tutor.service.impl;

import org.framework.tutor.domain.TutorsysBtns;
import org.framework.tutor.mapper.TutorsysBtnsMapper;
import org.framework.tutor.service.TutorsysBtnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 所有家教常用链接服务层实现类
 * @author chengxi
 */
@Component
public class TutorsysBtnsServiceImpl implements TutorsysBtnsService {

    @Autowired
    private TutorsysBtnsMapper tutorsysBtnsMapper;

    /**
     * 获取指定id的链接数据
     * @param bid
     * @return
     */
    @Override
    public TutorsysBtns getById(Integer bid) {

        return tutorsysBtnsMapper.getById(bid);
    }
}
