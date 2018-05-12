package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yinjimin
 * @Description: 帖子答案评论服务层
 * @date 2018年04月25日
 */
public interface BbsCardAnswerCommandApi {

    /**
     * @Description 每次获取五条评论数据
     * @param startpos: 开始位置
     * @param aid: 帖子回答id
     */
    public String getCommandListByAid(Integer startpos, Integer aid) throws IOException;


    /**
     * @Description 发表评论
     * @param cardid: 帖子id
     * @param aid: 帖子回答id
     * @param answer: 评论
     * @param repfloor: 回复的楼层
     */
    public String publishCommand(Integer cardid, Integer aid, String answer, Integer repfloor) throws IOException;

    /**  
     * @Description 获取当前用户的评论数
     */
    public String getMyCommandCount() throws IOException;

    /**
     * @Description 获取当前用户的评论数据
     */
    public String getMyCommandInfo() throws IOException;
}
