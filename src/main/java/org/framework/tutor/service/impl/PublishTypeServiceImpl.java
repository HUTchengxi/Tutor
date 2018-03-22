package org.framework.tutor.service.impl;

import org.framework.tutor.domain.PublishType;
import org.framework.tutor.mapper.PublishTypeMapper;
import org.framework.tutor.service.PublishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 版本类型服务层实现类
 * @author chengxi
 */
@Component
public class PublishTypeServiceImpl implements PublishTypeService {

    @Autowired
    private PublishTypeMapper publishTypeMapper;

    /**
     * 通过id获取对应的版本类型数据
     * @param typeid
     * @return
     */
    @Override
    public PublishType getById(Integer typeid) {

        return publishTypeMapper.getById(typeid);
    }
}
