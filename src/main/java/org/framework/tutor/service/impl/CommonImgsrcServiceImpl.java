package org.framework.tutor.service.impl;

import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.mapper.CommonImgsrcMapper;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yinjimin
 * @Description: 通用图片数据服务层实现类
 * @date 2018年04月01日
 */
@Component
public class CommonImgsrcServiceImpl implements CommonImgsrcService {

    @Autowired
    private CommonImgsrcMapper commonImgsrcMapper;

    @Override
    public List<CommonImgsrc> getAll() {

        return commonImgsrcMapper.getAll();
    }
}
