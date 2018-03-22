package org.framework.tutor.service;

import org.framework.tutor.domain.PublishType;

/**
 * 版本类型服务层接口
 * @author chengxi
 */
public interface PublishTypeService {

    /**
     * 通过id获取对应的版本类型数据
     * @param typeid
     * @return
     */
    PublishType getById(Integer typeid);
}
