package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;

public interface UserFeedbackApi {


    /**  
     * @Description 获取当前用户的所有反馈数据
     */
    String getMyFeedback();

    /**
     * @Description 用户删除：status设置为-1
     * @param id 反馈id
     */
    String removeMyFeedback(Integer id);

    /**  
     * @Description 新增用户反馈
     * @param info 反馈描述
     */
    String saveMyFeedback(String info);

    /**
     * @Description 获取用户的反馈数据（admin操作）
     * @param paramMap
     */
    String getUserFeedback(ParamMap paramMap);

    /**  
     * @Description 删除用户的反馈数据（admin操作）
     * @param id 反馈id
     */
    String removeUserFeedback(Integer id);

    /**
     * @Description 修改反馈数据的处理状态
     * @param paramMap
     */
    String modUserFeedbackStatus(ParamMap paramMap);
}
