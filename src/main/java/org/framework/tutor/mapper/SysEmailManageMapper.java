package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.SysEmailManage;

@Mapper
public interface SysEmailManageMapper {

    @Insert("insert into sys_email_manage(sendto, theme, email, status, address) values(#{send}, #{theme}, #{email}, #{status}, #{address})")
    void sendEmail(@Param("send") String send, @Param("address") String address, @Param("theme") String theme,
                   @Param("email") String email, @Param("status") Integer status);

    @Select("select * from sys_email_manage where id=#{id}")
    SysEmailManage getById(@Param("id") Integer emailId);

    @Select("select id from sys_email_manage order by id desc limit 0,1")
    Integer getLastId();

    @Update("update sys_email_manage set theme=#{theme}, email=#{email} where id=#{id}")
    void updateEmail(@Param("id") Integer emailId, @Param("theme") String theme, @Param("email") String email);

    @Select("select * from sys_email_manage limit #{offset}, #{pagesize}")
    List<SysEmailManage> getAllEmailListLimit(@Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select count(*) from sys_email_manage")
    Integer getAllEmailCount();

    @Select("select * from sys_email_manage where status=#{status} limit #{offset}, #{pagesize}")
    List<SysEmailManage> getEmailListByStatusLimit(@Param("status") Integer emailStatus, @Param("offset") Integer offset, @Param("pagesize") Integer pageSize);

    @Select("select count(*) from sys_email_manage where status=#{status}")
    Integer getEmailCountByStatus(@Param("status") Integer emailStatus);

    @Delete("delete from sys_email_manage where id=#{id}")
    void deleteEmail(@Param("id") Integer id);
}