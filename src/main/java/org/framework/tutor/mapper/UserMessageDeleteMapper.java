package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.UserMessageDelete;

@Mapper
public interface UserMessageDeleteMapper {

    @Select("select * from user_message_delete where mid=#{mid} and username=#{username} limit 0,1")
    UserMessageDelete checkIsDelete(@Param("mid") Integer id, @Param("username") String username);
}
