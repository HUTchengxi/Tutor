package org.framework.tutor.api;

import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;

public interface UserFeedbackApi {

    String getMyFeedback(HttpServletRequest request);

    /**
     *
     * @Description 用户删除：status设置为-1
     * @param [id, request]
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/5/9
     */
    String removeMyFeedback(Integer id, HttpServletRequest request);

    String saveMyFeedback(String info, HttpServletRequest request);

    String getUserFeedback(ParamMap paramMap);

    String removeUserFeedback(Integer id);

    String modUserFeedbackStatus(ParamMap paramMap);
}
