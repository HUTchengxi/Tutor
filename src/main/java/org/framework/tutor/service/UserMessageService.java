package org.framework.tutor.service;

import org.framework.tutor.domain.UserMessage;

import java.util.List;

/**
 * 用户通知服务层接口
 * @author chengxi
 */
public interface UserMessageService {

    /**
     * 获取指定用户的未读通知总数量
     * @param username
     * @return
     */
    Integer getMyMessageCount(String username);

    /**
     * 获取指定用户的通知数据(简单数据)
     * @return
     */
    List<UserMessage> getMyMessage(String username);

    /**
     * 获取指定用户指定管理员的未读通知数量
     * @param suser
     * @param username
     * @return
     */
    Integer getNoMessageCount(String suser, String username);

    /**
     * 获取指定用户的指定管理员发送的通知数据(详细数据)
     * @param suser
     * @param username
     * @return
     */
    List<UserMessage> getMessageBySuser(String suser, String username);

    /**
     * 设置指定用户的指定管理员通知数据为已读
     * @param suser
     * @param username
     * @return
     */
    Integer setMessageRead(String suser, String username);

    /**
     * 查看已读通知数据
     * @param suser
     * @param username
     * @param sta
     * @return
     */
    List<UserMessage> getReadMessage(String suser, String username);

    /**
     *
     * @Description 查看未读通知数据
     * @param [suser, username]
     * @return java.util.List<org.framework.tutor.domain.UserMessage>
     * @author yinjimin
     * @date 2018/4/23
     */
    List<UserMessage> getUnreadMessage(String suser, String username);

    /**
     * 删除指定的通知数据
     * @param did
     * @return
     */
    Integer delMyMessage(Integer did, String username);

    /**
     * 标记全部为已读
     * @param username
     * @return
     */
    Integer setAllStatus(String username);

    List<UserMessage> getMessageListLimit(Integer identity, String title, String startTime, Integer offset, Integer pageSize);

    Integer getMessageCountLimit(Integer identity, String title, String startTime);

    UserMessage getById(Integer id);

    void seneMessage(Integer identity, String suser, String username, String title, String message);
}
