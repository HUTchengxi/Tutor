package org.framework.tutor.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.PublishLog;

/**
 * 版本更新记录数据控制层
 * @author chengxi
 */
@Mapper
public interface PublishLogMapper {


    /**
     * 获取最新版本的更新记录
     * @return
     */
    @Select("select * from publish_log where pversion=(select pversion from publish_log order by id desc limit 0,1);")
    List<PublishLog> getLogNew();
}