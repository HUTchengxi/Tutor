package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.CommonImgsrc;

import java.util.List;

/**
 *
 * @Description 通用图片数据访问层
 * @author yinjimin
 * @date 2018/4/1
 */
@Mapper
public interface CommonImgsrcMapper {
    
    /**  
     *    
     * @Description 获取所有图片数据
     * @param []    
     * @return java.util.List<org.framework.tutor.domain.CommonImgsrc>
     * @author yinjimin  
     * @date 2018/4/1
     */
    @Select("select * from common_imgsrc")
    List<CommonImgsrc> getAll();
}