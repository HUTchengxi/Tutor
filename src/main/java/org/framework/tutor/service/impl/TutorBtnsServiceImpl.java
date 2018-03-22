package org.framework.tutor.service.impl;

import org.framework.tutor.domain.TutorBtns;
import org.framework.tutor.mapper.TutorBtnsMapper;
import org.framework.tutor.service.TutorBtnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 指定家教常用链接服务层实现类
 * @author chengxi
 */
@Component
public class TutorBtnsServiceImpl implements TutorBtnsService {

    @Autowired
    private TutorBtnsMapper tutorBtnsMapper;

    /**
     * 获取指定家教的所有常用链接数据
     * @param username
     * @return
     */
    @Override
    public List<TutorBtns> getBtnsList(String username) {

        return tutorBtnsMapper.getBtnsList(username);
    }
}
