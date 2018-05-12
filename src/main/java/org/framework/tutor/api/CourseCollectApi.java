package org.framework.tutor.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CourseCollectApi {

    /**
     * @Description 获取我的课程收藏记录
     * @param startpos 开始位置
     */
    public String getMyCollect(Integer startpos) throws IOException;

    /**
     * @Description 判断当前用户是否收藏了指定的课程
     * @param cid 课程id
     */
    public String checkUserCollect(Integer cid) throws IOException;

    /**
     * @Description 收藏/取消收藏
     * @param cid 课程id
     * @param mod 操作（1/-1）
     * @param descript 收藏描述
     */
    public String modUserCollect(Integer cid, String mod, String descript) throws IOException;

    /**
     * @Description 获取家教的今日课程收藏数量
     */
    public String getCollectCount() throws IOException;
}
