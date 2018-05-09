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

    @Select("select * from user_feedback where username like CONCAT(#{username},'%') and status=#{status} order by ptime desc " +
            "limit #{offset}, #{pagesize}")
    List<UserFeedback> getUserFeedback(@Param("username") String username, @Param("status") Integer status,
                                       @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);


    @Select("select * from user_feedback where username like CONCAT(#{username},'%') order by ptime desc " +
            "limit #{offset}, #{pagesize}")
    List<UserFeedback> getUserFeedbackNoStatus(@Param("username") String username, @Param("offset") Integer offset,
                                               @Param("pagesize") Integer pageSize);

    @Select("select count(*) from user_feedback where username like CONCAT(#{username}, '%') and status=#{status}")
    Integer getUserFeedbackCount(@Param("username") String username, @Param("status") Integer status);

    @Select("select count(*) from user_feedback where username like CONCAT(#{username}, '%')")
    Integer getUserFeedbackCountNoStatus(@Param("username") String username);

    @Delete("delete from user_feedback where id=#{id}")
    Integer removeUserFeedback(@Param("id") Integer id);
}