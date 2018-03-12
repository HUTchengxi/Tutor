package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.UserVali;

/**
 *  注册验证数据访问层
 *  @author chengxi
 */
@Mapper
public interface UserVMapper {

    /**
     * 添加注册队列
     * @param username
     * @param valicode
     * @param status
     */
    @Insert("insert into user_vali(username, valicode, status) values(#{username}, #{valicode}, #{status})")
    void addUserVali(@Param("username") String username, @Param("valicode") String valicode, @Param("status") Integer status);

    /**
     * 获取指定用户的验证码
     * @param username
     * @return
     */
    @Select("select valicode from user_vali where username=#{username}")
    String getCodeByUsername(@Param("username") String username);

    /**
     * 清除指定用户的未验证状态
     * @param username
     */
    @Delete("delete from user_vali where username=#{username}")
    void delStatus(@Param("username") String username);

    /**
     * 清空当天失效的验证数据
     * @param now
     */
    @Update("update user_vali set vstatus=1 where regtime > #{now}")
    void checkAll(@Param("now") String now);

    /**
     * 判断当前邮箱是否已经被验证
     * @param email
     * @return
     */
    @Select("select * from user_vali where username=#{email} ")
    UserVali checkEmailStatus(@Param("email") String email);

    /**
     * 更新邮箱注册码
     * @param email
     * @param valicode
     */
    @Update("update user_vali set valicode=#{valicode} where username=#{email}")
    void updateEmailCode(@Param("email") String email, @Param("valicode") String valicode);
}