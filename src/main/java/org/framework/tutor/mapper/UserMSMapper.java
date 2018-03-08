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
    @Select("select count(*) from user_message where (identity=0 || username=#{username}) and status=0")
    Integer getMyMessageCount(@Param("username") String username);

    /**
     * 获取指定用户的通知数据(简单数据)
     * @param username
     * @return
     */
    @Select("select * from user_message where username=#{username} || identity=0 order by stime desc")
    List<UserMessage> getMyMessage(@Param("username") String username);

    /**
     * 获取指定用户指定管理员的未读通知数量
     * @param suser
     * @param username
     * @return
     */
    @Select("select count(*) from user_message where suser=#{suser} and (username=#{username} || identity=0) and status=0")
    Integer getNoMessageCount(@Param("suser") String suser, @Param("username") String username);

    /**
     * 获取指定用户的指定管理员发送的通知数据(详细数据)
     * @param suser
     * @param username
     * @return
     */
    @Select("select * from user_message where suser=#{suser} and (username=#{username} || identity=0) order by stime asc")
    List<UserMessage> getMessageBySuser(@Param("suser") String suser, @Param("username") String username);

    /**
     * 设置指定用户的指定管理员的通知数据为已读
     * @param suser
     * @param username
     * @return
     */
    @Update("update user_message set status=1 where suser=#{suser} and username=#{username}")
    Integer setMessageStatus(@Param("suser") String suser, @Param("username") String username);

    /**
     * 查看已读/未读通知数据
     * @param suser
     * @param username
     * @param sta
     * @return
     */
    @Select("select * from user_message where suser=#{suser} and username=#{username} and status=#{sta}")
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
    @Update("update user_message set status=1 where username=#{username}")
    Integer setAllStatus(@Param("username") String username);
}
