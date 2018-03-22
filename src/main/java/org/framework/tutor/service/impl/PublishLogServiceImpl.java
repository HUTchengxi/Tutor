package org.framework.tutor.service.impl;

import org.framework.tutor.domain.PublishLog;
import org.framework.tutor.mapper.PublishLogMapper;
import org.framework.tutor.service.PublishLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 版本发布记录服务层实现类
 * @author chengxi
 */
@Component
public class PublishLogServiceImpl implements PublishLogService {

    @Autowired
    private PublishLogMapper publishLogMapper;

    /**
     * 获取最新版本的更新记录
     * @return
     */
    @Override
    public List<PublishLog> getLogNew() {

        return publishLogMapper.getLogNew();
    }
}
