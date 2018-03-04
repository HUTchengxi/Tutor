package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.UserLog;

import java.util.List;

/**
 * 用户登录记录数据访问层
 * @author chengxi
 */
@Mapper
public interface UserLMapper {

    /**
     * 保存用户登录记录
     * @param username
     * @param logcity
     * @param ip
     * @param logsystem
     * @return
     */
    @Insert("insert into user_log(username, logcity, logip, logsys) values(#{username}, #{logcity}, #{ip}, #{logsystem})")
    boolean saveUserlog(@Param("username") String username, @Param("logcity") String logcity, @Param("ip") String ip,
                        @Param("logsystem") String logsystem);

    /**
     * 获取指定用户的登录记录
     * @param username
     * @return
     */
    @Select("select * from user_log where username = #{username} limit 0, 10")
    List<UserLog> getUserlog(@Param("username") String username);
}
