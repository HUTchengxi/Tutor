package org.framework.tutor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}