package org.framework.tutor.service;

import org.framework.tutor.domain.CommonImgsrc;

import java.util.List;

/**  
 *    
 * @Description 通用图片数据服务层接口
 * @author yinjimin
 * @date 2018/4/1
 */  
public interface CommonImgsrcService {
    
    /**  
     *    
     * @Description 获取所有的图片数据
     * @param []    
     * @return java.util.List<org.framework.tutor.domain.CommonImgsrc>
     * @author yinjimin  
     * @date 2018/4/1
     */  
    List<CommonImgsrc> getAll();
}
