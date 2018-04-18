package org.framework.tutor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.framework.tutor.domain.CourseCommandDeleteReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**  
 *    
 * @Description 评论删除待审
 * @author yinjimin
 * @date 2018/4/18
 */
@Mapper
public interface CourseCommandDeleteReqMapper {

    /**
     *
     * @Description 添加删除申请
     * @param [username, cid, info]
     * @return void
     * @author yinjimin
     * @date 2018/4/18
     */
    @Insert("insert into course_command_delete_req(reqer, cid, info) values(#{username}, #{cid}, #{info})")
    void addCommandDeleteReq(@Param("username") String username, @Param("cid") Integer cid, @Param("info") String info);
}