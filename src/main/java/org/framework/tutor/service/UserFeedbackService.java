package org.framework.tutor.service;

import org.framework.tutor.domain.UserFeedback;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserFeedbackService {

    List<UserFeedback> getMyFeedback(String username);

    UserFeedback getByUserAndId(Integer id, String username);

    Integer modMyFeedbackStatus(Integer id, Integer status);

    Integer saveMyFeedback(String username, String info);
}
