package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.UserMessageDelete;

@Mapper
public interface UserMessageDeleteMapper {

    @Insert("insert into user_message_delete(mid, username) values(#{mid}, #{username})")
    Integer addDelete(@Param("mid") Integer did, @Param("username") String username);

    @Select("select * from user_message_delete where id=#{mid} and username=#{username}")
    UserMessageDelete checkDeleteStatus(@Param("id") Integer id, @Param("username") String username);
}