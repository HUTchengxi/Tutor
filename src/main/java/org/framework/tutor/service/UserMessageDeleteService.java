package org.framework.tutor.service;

import org.framework.tutor.domain.UserMessageDelete;

public interface UserMessageDeleteService {

    UserMessageDelete getStatus(Integer id, String username);

    void deleteRepeatRead(String suser, String username);
}
