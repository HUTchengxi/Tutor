package org.framework.tutor.mapper;

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
}