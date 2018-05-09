package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.UserFeedback;

import java.util.List;

@Mapper
public interface UserFeedbackMapper {

    @Select("select * from user_feedback where username=#{username} and status<>-1 order by ptime desc")
    List<UserFeedback> getMyFeedback(@Param("username") String username);

    @Select("select * from user_feedback where id=#{id} and username=#{username} limit 0,1")
    UserFeedback getByUserAndId(@Param("id") Integer id, @Param("username") String username);

    @Update(("update user_feedback set status=#{status} where id=#{id}"))
    Integer modMyFeedbackStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Insert("insert into user_feedback(username, info) values(#{username}, #{info})")
    Integer saveMyFeedback(@Param("username") String username, @Param("info") String info);
}