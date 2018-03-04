package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.RankTemp;
import org.framework.tutor.domain.UserSign;

import java.util.List;
import java.util.Map;

/**
 * 用户签到数据访问层
 * @author chengxi
 */
@Mapper
public interface UserSMapper {

    /**
     * 获取用户的签到数据
     * @param username
     * @return
     */
    @Select("select * from user_sign where username=#{username}")
    List<UserSign> getMySign(@Param("username") String username);

    /**
     * 获取用户当月的签到数据
     * @param username
     * @param monthstr
     * @return
     */
    @Select("select * from user_sign where username=#{username} and stime like CONCAT('%', #{monthstr}, '%')")
    List<UserSign> getMySignNow(@Param("username") String username, @Param("monthstr") String monthstr);

    /**
     * 进行签到打卡
     * @param username
     * @return
     */
    @Insert("insert into user_sign(username) values(#{username})")
    Integer addUsersign(@Param("username") String username);

    /**
     * 打卡日榜
     * @return
     */
    @Select("select * from user_sign where stime like CONCAT('%', #{daystr}, '%') order by id asc limit #{startpos}, 10")
    List<UserSign> getSignDay(@Param("daystr") String daystr, @Param("startpos") Integer startpos);

    /**
     * 打卡总榜
     * @param startpos
     * @return
     */
    @Select("select username, count(*) as total from user_sign group by username order by total desc limit #{startpos}, 10")
    List<RankTemp> getSignTotal(@Param("startpos") Integer startpos);
}
