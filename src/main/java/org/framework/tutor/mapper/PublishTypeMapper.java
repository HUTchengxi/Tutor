package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.PublishType;

/**
 * 版本类型数据控制层
 * @author chengxi
 */
@Mapper
public interface PublishTypeMapper {

    /**
     * 获取对应id的版本类型数据
     * @param typeid
     * @return
     */
    @Select("select * from publish_type where id=#{typeid}")
    PublishType getById(@Param("typeid") Integer typeid);
}