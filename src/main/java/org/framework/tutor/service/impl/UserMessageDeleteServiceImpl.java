package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserMessageDelete;
import org.framework.tutor.mapper.UserMessageDeleteMapper;
import org.framework.tutor.service.UserMessageDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月22日
 */
@Component
public class UserMessageDeleteServiceImpl implements UserMessageDeleteService {

    @Autowired
    private UserMessageDeleteMapper userMessageDeleteMapper;

    @Override
    public UserMessageDelete getStatus(Integer id, String username) {
        return userMessageDeleteMapper.getStatus(id, username);
    }

    @Override
    public void deleteRepeatRead(String suser, String username) {
        userMessageDeleteMapper.deleteRepeatRead(suser, username);
    }
}
