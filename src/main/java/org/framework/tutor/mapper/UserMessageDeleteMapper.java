package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.UserMessageDelete;

@Mapper
public interface UserMessageDeleteMapper {

    @Select("select * from user_message_delete where mid=#{mid} and username=#{username} limit 0,1")
    UserMessageDelete getStatus(@Param("mid") Integer id, @Param("username") String username);

    @Delete("delete from user_message_delete where status=1 and mid in (select um.id from user_message um where " +
            "um.username=#{username} and suser=#{suser})")
    void deleteRepeatRead(@Param("suser") String suser, @Param("username") String username);
}
