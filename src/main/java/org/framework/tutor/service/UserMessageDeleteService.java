package org.framework.tutor.service;

import org.framework.tutor.domain.UserMessageDelete;

public interface UserMessageDeleteService {
    
    /**  
     *    
     * @Description 添加删除数据
     * @param [did, username]    
     * @return java.lang.Integer
     * @author yinjimin  
     * @date 2018/4/22
     */  
    Integer addDelete(Integer did, String username);

    UserMessageDelete checkDeleteStatus(Integer id, String username);
}
