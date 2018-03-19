package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.domain.UserMessage;

import java.util.List;

/**
 * 用户主表数据访问层
 * @author chengxi
 */
@Mapper
public interface UserMMapper {

    /**
     * 根据用户名获取并返回对应的用户
     * @param username
     * @return
     */
    @Select("select * from user_main where username = #{username}")
    UserMain getByUsername(@Param("username") String username);

    /**
     * 根据用户名和密码获取并返回对应的用户
     * @param username
     * @param password
     * @return
     */
    @Select("select * from user_main where username= #{username} and password = #{password}")
    UserMain getByUserPass(@Param("username") String username, @Param("password") String password);

    /**
     * 根据昵称获取并返回对应的用户
     * @param nickname
     * @return
     */
    @Select("select * from user_main where nickname = #{nickname}")
    UserMain getByNickname(@Param("nickname") String nickname);

    /**
     * 添加/注册新用户
     * @param identity
     * @param username
     * @param password
     * @param nickname
     */
    @Insert("insert into user_main(identity,username, password, nickname) values(#{identity}, #{username},#{password},#{nickname})")
    Integer addUser(@Param("identity") Integer identity,@Param("username") String username,
                    @Param("password") String password, @Param("nickname") String nickname);

    /**
     * 更换指定用户的头像
     * @param username
     * @param imgsrc
     * @return
     */
    @Update("update user_main set imgsrc = #{imgsrc} where username = #{username}")
    boolean modImgsrcByUser(@Param("username") String username, @Param("imgsrc") String imgsrc);

    /**
     * 更改用户的个人信息
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     * @return
     */
    @Update("update user_main set nickname = #{nickname}, sex = #{sex}, age = #{age}, info = #{info} where username = #{username}")
    boolean modUserinfo(@Param("username") String username, @Param("nickname") String nickname, @Param("sex") Integer sex,
                        @Param("age") Integer age, @Param("info") String info);

    /**
     * 判断邮箱是否已经被注册
     * @param email
     * @return
     */
    @Select("select * from user_main where email=#{email}")
    UserMain emailExist(@Param("email") String email);

    /**
     * 邮箱注册用户
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @param email
     * @return
     */
    @Insert("insert into user_main(identity, username, password, nickname, email) values(#{identity}, #{username}, #{password}, #{nickname}, #{email})")
    boolean registerByEmail(@Param("identity") Integer identity, @Param("username") String username, @Param("password") String password,
                            @Param("nickname") String nickname, @Param("email") String email);

    /**
     * 通过用户名和邮箱获取对应的用户
     * @param username
     * @param email
     * @return
     */
    @Select("select * from user_main where username=#{username} and email=#{email}")
    UserMain getByUserAndEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 修改用户的密码
     * @param username
     * @param newpass
     * @return
     */
    @Update("update user_main set password=#{newpass} where username=#{username}")
    Integer modPassword(@Param("username") String username, @Param("newpass") String newpass);

    /**
     * 邮箱解除绑定
     * @param username
     * @return
     */
    @Update("update user_main set email=null where username=#{username}")
    Integer unbindEmail(@Param("username") String username);

    /**
     * 绑定手机号码
     * @param username
     * @param email
     */
    @Update("update user_main set telephone=#{email} where username=#{username}")
    void bindPhone(@Param("username") String username, @Param("email") String email);

    /**
     * 绑定邮箱
     * @param username
     * @param email
     */
    @Update("update user_main set email=#{email} where username=#{username}")
    void bindEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 判断手机号码是否已经被注册
     * @param phone
     * @return
     */
    @Select("select * from user_main where telephone=#{phone}")
    UserMain phoneExist(@Param("phone") String phone);

    /**
     * 修改指定用户的id状态
     * @param username
     * @param identity
     */
    @Update("update user_main set identity=#{identity} where username=#{username}")
    void setIdentity(@Param("username") String username, @Param("identity") Integer identity);

    /**
     * 手机号码解除绑定
     * @param username
     * @return
     */
    @Update("update user_main set telephone=null where username=#{username}")
    Integer unbindPhone(@Param("username") String username);

    /**
     * 获取用户名和手机号码对应的用户
     * @param username
     * @param phone
     * @return
     */
    @Select("select * from user_main where username=#{username} and telephone=#{phone}")
    UserMain getByUserAndPhone(@Param("username") String username, @Param("phone") String phone);

    /**
     * 手机号码注册用户
     * @param identity
     * @param username
     * @param password
     * @param nickname
     * @param telephone
     * @return
     */
    @Insert("insert into user_main(identity, username, password, nickname, telephone) values(#{identity}, #{username}, #{password}, #{nickname}, #{telephone})")
    Boolean registerByPhone(@Param("identity") Integer identity, @Param("username") String username, @Param("password") String password, @Param("nickname") String nickname, @Param("telephone") String telephone);
}
