package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.*;
import org.framework.tutor.domain.BbsCardAnswer;

import java.util.List;

/**  
 *    
 * @Description 帖子答案数据访问层
 * @author yinjimin
 * @date 2018/4/5
 */
@Mapper
public interface BbsCardAnswerMapper {

    /**
     *
     * @Description 获取对应的帖子答案数据
     * @param [cardId]
     * @return java.util.List<org.framework.tutor.domain.BbsCardAnswer>
     * @author yinjimin
     * @date 2018/4/7
     */
    @Select("select * from bbs_card_answer where cardid=#{cardid} order by crtime desc")
    List<BbsCardAnswer> getByCardid(@Param("cardid") Integer cardId);


    /**
     *
     * @Description 获取用户对应帖子的回答
     * @param [cardId, username]
     * @return org.framework.tutor.domain.BbsCardAnswer
     * @author yinjimin
     * @date 2018/4/9
     */
    @Select("select * from bbs_card_answer where cardid=#{cardId} and username=#{username}")
    BbsCardAnswer checkIsExistAnswer(@Param("cardId") Integer cardId, @Param("username") String username);


    /**
     *
     * @Description 添加回答
     * @param [cardId, username, answer]
     * @return void
     * @author yinjimin
     * @date 2018/4/9
     */
    @Insert("insert into bbs_card_answer(cardId,username, answer) values(#{cardId}, #{username}, #{answer})")
    void addAnswer(@Param("cardId") Integer cardId, @Param("username") String username, @Param("answer") String answer);


    /**
     *
     * @Description 点赞加1
     * @param [aid]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Update("update bbs_card_answer set gcount = gcount+1 where id=#{id}")
    void addGcount(@Param("id") Integer aid);


    /**
     *
     * @Description 踩加1
     * @param [aid]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Update("update bbs_card_answer set bcount = bcount+1 where id=#{id}")
    void addBcount(@Param("id") Integer aid);


    /**
     *
     * @Description 对应的回答评论数加1
     * @param [aid]
     * @return void
     * @author yinjimin
     * @date 2018/4/10
     */
    @Update("update bbs_card_answer set comcount = comcount+1 where id=#{aid} ")
    void addComcount(@Param("aid") Integer aid);
}