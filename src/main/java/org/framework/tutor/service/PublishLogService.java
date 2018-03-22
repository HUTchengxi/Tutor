package org.framework.tutor.service;

import org.framework.tutor.domain.PublishLog;

import java.util.List;

/**
 * 版本发布记录服务层接口
 * @author chengxi
 */
public interface PublishLogService {

    /**
     * 获取罪行的版本更新记录
     * @return
     */
    List<PublishLog> getLogNew();
}
