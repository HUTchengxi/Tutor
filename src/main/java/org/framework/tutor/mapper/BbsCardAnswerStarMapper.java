package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.framework.tutor.domain.BbsCardAnswerStar;

/**
 *
 * @Description 帖子回答star数据访问层
 * @author yinjimin
 * @date 2018/4/10
 */
@Mapper
public interface BbsCardAnswerStarMapper {

    /**
     *
     * @Description 判断当前用户是否star指定回答
     * @param [aid, username]
     * @return org.framework.tutor.domain.BbsCardAnswerStar
     * @author yinjimin
     * @date 2018/4/10
     */
    @Select("select * from bbs_card_answer_star where aid=#{aid} and username=#{username}")
    BbsCardAnswerStar checkUserStar(@Param("aid") Integer aid, @Param("username") String username);


    /**
     *
     * @Description 当前用户star指定回答
     * @param [aid, username, score]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Insert("insert into bbs_card_answer_star(aid, username, score) values(#{aid}, #{username}, #{score})")
    void addUserStar(@Param("aid") Integer aid, @Param("username") String username, @Param("score") Integer score);
}