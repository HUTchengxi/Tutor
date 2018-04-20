package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysEmailManageMapper {

    @Insert("insert into sys_email_manage(sendto, theme, email, status, address) values(#{send}, #{theme}, #{email}, #{status}, #{address})")
    void sendEmail(@Param("send") String send, @Param("address") String address, @Param("theme") String theme,
                   @Param("email") String email, @Param("status") Integer status);
}