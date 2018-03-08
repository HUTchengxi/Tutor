package org.framework.tutor.service;

import org.framework.tutor.domain.UserMessage;

import java.util.List;

/**
 * 用户通知服务层接口
 * @author chengxi
 */
public interface UserMSService {

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
    Integer setMessageStatus(String suser, String username);

    /**
     * 查看已读/未读通知数据
     * @param suser
     * @param username
     * @param sta
     * @return
     */
    List<UserMessage> getMessageByStatus(String suser, String username, Integer sta);

    /**
     * 删除指定的通知数据
     * @param did
     * @return
     */
    Integer delMyMessage(Integer did);

    /**
     * 标记全部为已读
     * @param username
     * @return
     */
    Integer setAllStatus(String username);
}
