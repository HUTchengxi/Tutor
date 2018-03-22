package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.TutorsysBtns;

/**
 * 所有家教常用链接数据控制层
 * @author chengxi
 */
@Mapper
public interface TutorsysBtnsMapper {

    /**
     * 获取指定id的链接数据
     * @param bid
     * @return
     */
    @Select("select * from tutorsys_btns where id=#{bid}")
    TutorsysBtns getById(@Param("bid") Integer bid);
}