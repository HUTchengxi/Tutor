package org.framework.tutor.service;

import org.framework.tutor.domain.UserMessageDelete;

public interface UserMessageDeleteService {

    UserMessageDelete checkIsDelete(Integer id, String username);
}
