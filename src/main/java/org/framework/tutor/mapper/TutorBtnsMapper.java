package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.TutorBtns;

/**
 * 家教对应常用链接数据控制层
 * @author chengxi
 */
@Mapper
public interface TutorBtnsMapper {

    /**
     * 获取指定家教的所有常用链接数据
     * @param username
     * @return
     */
    @Select("select * from tutor_btns where tname=#{username} order by ord desc")
    List<TutorBtns> getBtnsList(@Param("username") String username);
}