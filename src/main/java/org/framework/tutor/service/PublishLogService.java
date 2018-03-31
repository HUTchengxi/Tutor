package org.framework.tutor.service;

import org.framework.tutor.domain.PublishLog;

import java.util.List;

/**
 * 版本发布记录服务层接口
 * @author chengxi
 */
public interface PublishLogService {

    /**
     * 获取最新的版本更新记录
     * @return
     */
    List<PublishLog> getLogNew();

    
    /**  
     *    
     * @Description 获取所有的版本更新记录
     * @param []    
     * @return java.util.List<org.framework.tutor.domain.PublishLog>
     * @author yinjimin  
     * @date 2018/3/31
     */  
    List<PublishLog> getLogAll();
}
