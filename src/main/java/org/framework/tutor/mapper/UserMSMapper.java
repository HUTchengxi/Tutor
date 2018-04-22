package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.UserMessage;

import java.util.List;

/**
 * 用户通知数据控制类
 * @author chengxi
 */
@Mapper
public interface UserMSMapper {

    /**
     * 获取指定用户当前未读通知总数量
     * @param username
     * @return
     */
    @Select("select count(*) from user_message um where (identity=0 || um.username=#{username}) and status=0 " +
            "and um.id not in (select mid from user_message_delete umd where umd.username=#{username})")
    Integer getMyMessageCount(@Param("username") String username);

    /**
     * 获取指定用户的通知数据(简单数据)
     * @param username
     * @return
     */
    @Select("select * from user_message um where um.username=#{username} || identity=0 and " +
            "um.id not in (select mid from user_message_delete umd where username=#{username}) order by stime desc")
    List<UserMessage> getMyMessage(@Param("username") String username);

    /**
     * 获取指定用户指定管理员的未读通知数量
     * @param suser
     * @param username
     * @return
     */
    @Select("select count(*) from user_message um where suser=#{suser} and (um.username=#{username} || identity=0) and status=0 " +
            "and um.id not in (select mid from user_message_delete umd where umd.username=#{username})")
    Integer getNoMessageCount(@Param("suser") String suser, @Param("username") String username);

    /**
     * 获取指定用户的指定管理员发送的通知数据(详细数据)
     * @param suser
     * @param username
     * @return
     */
    @Select("select * from user_message um where suser=#{suser} and (um.username=#{username} || identity=0) " +
            "and um.id not in (select mid from user_message_delete umd where umd.username=#{username}) order by stime asc")
    List<UserMessage> getMessageBySuser(@Param("suser") String suser, @Param("username") String username);

    /**
     * 设置指定用户的指定管理员的通知数据为已读
     * @param suser
     * @param username
     * @return
     */
    @Update("update user_message set status=1 where suser=#{suser} and (username=#{username} || identity=0)")
    Integer setMessageStatus(@Param("suser") String suser, @Param("username") String username);

    /**
     * 查看已读/未读通知数据
     * @param suser
     * @param username
     * @param sta
     * @return
     */
    @Select("select * from user_message um where suser=#{suser} and (um.username=#{username} || identity=0) and status=#{sta} " +
            "and um.id not in (select mid from user_message_delete where username=#{username})")
    List<UserMessage> getMessageByStatus(@Param("suser") String suser, @Param("username") String username, @Param("sta") Integer sta);

    /**
     * 删除选中的通知信息
     * @param did
     * @return
     */
    @Delete("delete from user_message where id=#{did}")
    Integer delMyMessage(@Param("did") Integer did);

    /**
     * 标记全部为已读
     * @param username
     * @return
     */
    @Update("update user_message um set status=1 where um.username=#{username} and um.id not in (" +
            "select mid from user_message_delete umd where umd.username=#{username})")
    Integer setAllStatus(@Param("username") String username);

    @Select("select * from user_message where identity=#{identity} and title like CONCAT('%',#{title},'%') and stime like CONCAT('%',#{stime},'%')" +
            " limit #{offset}, #{pagesize}")
    List<UserMessage> getMessageListLimit(@Param("identity") Integer identity, @Param("title") String title, @Param("stime") String startTime, @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select count(*) from user_message um where um.identity=#{identity} and um.title like CONCAT('%',#{title},'%') and um.stime like CONCAT('%',#{stime},'%') " +
            "and um.id not in (select mid from user_message_delete where username=#{username})")
    Integer getMessageCountLimit(@Param("identity") Integer identity, @Param("username") String username, @Param("title") String title, @Param("stime") String startTime);

    @Select("select * from user_message where id=#{id}")
    UserMessage getById(@Param("id") Integer id);

    @Insert("insert into user_message(identity, suser, username, title, descript) values(#{identity}, #{suser}, #{username}, #{title}, #{message})")
    Integer sendMessage(@Param("identity") Integer identity,@Param("suser") String suser, @Param("username") String username, @Param("title") String title, @Param("message") String message);

    @Select("select * from user_message where title=#{title} limit 0,1")
    UserMessage checkIsExistTitle(@Param("title") String title);
}
