package org.framework.tutor.service.impl;

import org.framework.tutor.domain.UserMessage;
import org.framework.tutor.mapper.UserMSMapper;
import org.framework.tutor.service.UserMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户通知服务层实现类
 * @author chengxi
 */
@Component
public class UserMSServiceImpl implements UserMSService {

    @Autowired
    private UserMSMapper userMSMapper;

    /**
     * 获取指定用户的未读通知的总数量
     * @param username
     * @return
     */
    @Override
    public Integer getMyMessageCount(String username) {

        return userMSMapper.getMyMessageCount(username);
    }

    /**
     * 获取指定用户的通知数据(简单数据)
     * @return
     */
    @Override
    public List<UserMessage> getMyMessage(String username) {

        return userMSMapper.getMyMessage(username);
    }

    /**
     * 获取指定用户的指定管理员发送的未读通知总数量
     * @param suser
     * @param username
     * @return
     */
    @Override
    public Integer getNoMessageCount(String suser, String username) {

        return userMSMapper.getNoMessageCount(suser, username);
    }

    /**
     * 获取指定用户的指定管理员发送的通知数据(详细数据)
     * @param suser
     * @param username
     * @return
     */
    @Override
    public List<UserMessage> getMessageBySuser(String suser, String username) {

        return userMSMapper.getMessageBySuser(suser, username);
    }

    /**
     * 设置指定用户的指定管理员发送的通知数据为已读
     * @param suser
     * @param username
     * @return
     */
    @Override
    public Integer setMessageRead(String suser, String username) {

        return userMSMapper.setMessageRead(suser, username);
    }

    /**
     * 查看已读通知信息
     * @param suser
     * @param username
     * @param sta
     * @return
     */
    @Override
    public List<UserMessage> getReadMessage(String suser, String username) {

        return userMSMapper.getReadMessage(suser, username);
    }

    /**
     * 查看已读通知信息
     * @param suser
     * @param username
     * @param sta
     * @return
     */
    @Override
    public List<UserMessage> getUnreadMessage(String suser, String username) {

        return userMSMapper.getUnreadMessage(suser, username);
    }

    /**
     * 删除选中的通知数据
     * @param did
     * @return
     */
    @Override
    public Integer delMyMessage(Integer did, String username) {

        return userMSMapper.delMyMessage(did, username);
    }

    /**
     * 标记全部为已读
     * @param username
     * @return
     */
    @Override
    public Integer setAllStatus(String username) {

        return userMSMapper.setAllStatus(username);
    }

    @Override
    public List<UserMessage> getMessageListLimit(Integer identity, String title, String startTime, Integer offset, Integer pageSize) {
        return userMSMapper.getMessageListLimit(identity, title, startTime, offset, pageSize);
    }

    @Override
    public Integer getMessageCountLimit(Integer identity, String title, String startTime) {
        return userMSMapper.getMessageCountLimit(identity, title, startTime);
    }

    @Override
    public UserMessage getById(Integer id) {
        return userMSMapper.getById(id);
    }

    @Override
    public void seneMessage(Integer identity, String suser, String username, String title, String message) {
        userMSMapper.sendMessage(identity, suser, username, title, message);
    }
}
