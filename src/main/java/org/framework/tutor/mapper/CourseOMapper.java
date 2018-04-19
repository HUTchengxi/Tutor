package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.CourseOrder;

import java.util.List;

/**
 * 课程订单数据访问层
 * @author chengxi
 */
@Mapper
public interface CourseOMapper {

    /**
     * 获取课程订购人数
     * @param cid
     * @return
     */
    @Select("select count(*) from course_order where cid=#{cid} and state=1")
    Integer getOrderCount(@Param("cid") Integer cid);

    /**
     * 获取指定用户的对应课程订购信息
     * @param username
     * @param cid
     * @return
     */
    @Select("select * from course_order where username=#{username} and cid=#{cid}")
    CourseOrder getUserOrder(@Param("username") String username, @Param("cid") Integer cid);

    /**
     * 修改用户订单状态
     * @param username
     * @param cid
     * @param state
     * @return
     */
    @Update("update course_order set state=#{state} where username=#{username} and cid=#{cid}")
    boolean modUserState(@Param("username") String username, @Param("cid") Integer cid, @Param("state") Integer state);

    /**
     * 加入购物车
     * @param username
     * @param cid
     * @param state
     * @return
     */
    @Insert("insert into course_order(cid, username, state) values(#{cid}, #{username}, #{state})")
    boolean addUserOrder(@Param("username") String username, @Param("cid") Integer cid, @Param("state") Integer state);

    /**
     * 获取用户的订单/购物车数据
     * @param username
     * @param status
     * @param startpos
     * @return
     */
    @Select("select * from course_order where username=#{username} and state=#{status} order by otime asc limit #{startpos}, 5")
    List<CourseOrder> getMyOrder(@Param("username") String username, @Param("status") Integer status, @Param("startpos") Integer startpos);

    /**
     * 删除指定用户的购物车物品
     * @param id
     * @param username
     * @return
     */
    @Delete("delete from course_order where id=#{id} and username=#{username}")
    int delMyCart(@Param("id") Integer id, @Param("username") String username);

    /**
     * 获取指定用户的购物车物品总数
     * @param username
     * @return
     */
    @Select("select count(*) from course_order where username=#{username}")
    Integer getMyCartCount(@Param("username") String username);

    /**
     * 将指定订单放入回收站
     * @param oid
     * @return
     */
    @Update("update course_order set state=4 where id=#{oid}")
    Integer setInCycle(@Param("oid") Integer oid);

    /**
     * 根据订单号和用户名获取订单数据
     */
    @Select("select * from course_order where username=#{username} and id=#{oid}")
    CourseOrder getByIdAndUser(@Param("username") String username, @Param("oid") Integer oid);

    /**
     * 获取家教的课程订单总数
     * @param username
     * @param now
     * @return
     */
    @Select("select count(*) from course_order where cid in (select id from course_main where username=#{username}) and otime like CONCAT('%', #{now}, '%')")
    Integer getOrderCountNow(@Param("username") String username, @Param("now") String now);

    /**
     * 根据订单状态获取指定用户的订单数据
     * @param cid
     * @param username
     * @param state
     * @return
     */
    @Select("select * from course_order where cid=#{cid} and username=#{username} and state=#{state}")
    CourseOrder getByUserAndState(@Param("cid") Integer cid, @Param("username") String username, @Param("state") Integer state);

    /**
     *
     * @Description 获取指定课程的订单数据
     * @param [id]
     * @return java.util.List<org.framework.tutor.domain.CourseOrder>
     * @author yinjimin
     * @date 2018/4/14
     */
    @Select("select * from course_order where cid=#{id}")
    List<CourseOrder> getMyCourseOrderCount(@Param("id") Integer id);

    /**
     *
     * @Description 获取所有指定课程id对应的订单数据
     * @param [courseId]
     * @return java.util.List<org.framework.tutor.domain.CourseOrder>
     * @author yinjimin
     * @date 2018/4/18
     */
    @Select("select * from course_order where cid in (select id from course_main cm where cm.username=#{username})")
    List<CourseOrder> getMyOrderListByIdList(@Param("username") String username);

    /**
     *
     * @Description 获取指定数据
     * @param [oid]
     * @return org.framework.tutor.domain.CourseOrder
     * @author yinjimin
     * @date 2018/4/18
     */
    @Select("select * from course_order where id=#{oid}")
    CourseOrder getById(@Param("oid") Integer oid);
}
