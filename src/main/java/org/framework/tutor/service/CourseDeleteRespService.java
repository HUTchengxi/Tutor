package org.framework.tutor.service;

import org.framework.tutor.domain.CourseDeleteResp;

public interface CourseDeleteRespService {
    
    /**  
     *    
     * @Description 获取对应的请求相应数据
     * @param [id]    
     * @return org.framework.tutor.domain.CourseDeleteResp
     * @author yinjimin  
     * @date 2018/4/21
     */  
    CourseDeleteResp getByRid(Integer id);

    void insertResp(Integer id, Integer status, String respDesc);

    void updateResp(Integer id, Integer status, String respDesc);
}
