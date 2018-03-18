package org.framework.tutor.mapper;

import java.util.List;
import main.java.org.framework.tutor.domain.UserSecret;
import org.apache.ibatis.annotations.*;

/**
 * 用户密保数据访问层
 * @author chengxi
 */
@Mapper
public interface UserSCMapper {

    /**
     * 获取指定用户的密保数据
     * @param username
     * @return
     */
    @Select("select * from user_secret where username=#{username}")
    List<main.java.org.framework.tutor.domain.UserSecret> getSecretInfoByUsername(@Param("username") String username);

    /**
     * 删除指定用户的密保数据
     * @param username
     * @return
     */
    @Delete("delete from user_secret where username=#{username}")
    boolean delUserSecret(@Param("username") String username);

    /**
     * 为指定用户添加密保数据
     * @param question
     * @param answer
     * @param username
     * @return
     */
    @Insert("insert into user_secret(username, question, answer) values(#{username}, #{question}, #{answer})")
    Integer addUserSecret(@Param("question") String question, @Param("answer") String answer, @Param("username") String username);

    /**
     * 校验用户的密保问题是否正确
     * @param username
     * @param queone
     * @param ansone
     * @return
     */
    @Select("select * from user_secret where username=#{username} and question=#{queone} and answer=#{ansone}")
    UserSecret checkSecret(@Param("username") String username, @Param("queone") String queone, @Param("ansone") String ansone);
}