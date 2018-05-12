package org.framework.tutor.api;

import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserMessageApi {

    /**
     * 获取我的未读通知的数量
     */
    public String getMyMessageCount() throws IOException;

    /**
     * 获取我的通知数据(简单数据)
     */
    public String getMyMessage() throws IOException;

    /**
     * 获取指定管理员发送的我的通知数据
     * @param suser 管理员username
     */
    public String getMessageByUser(String suser) throws IOException;

    /**
     * 通知阅读完毕之后的读状态的更改
     * @param suser 管理员username
     */
    public String setMessageStatus(String suser) throws IOException;

    /**
     * 获取未读/已读消息
     * @param suser 管理员username
     * @param status 消息状态
     * @throws IOException
     */
    public String getMessageByStatus(String suser, String status) throws IOException;

    /**
     * 删除所选通知数据
     * @param did 通知id
     */
    public String delMyMessage(Integer did) throws IOException;

    /**
     * 标记全部为已读
     */
    public String setAllStatus() throws IOException;

    /**
     * @Description 管理员身份获取通知数据列表
     * @param paramMap
     */
    public String getMessageList(ParamMap paramMap) throws IOException;

    /**
     * @Description 管理员身份查看通知详情
     * @param id 通知详情
     */
    public String getMessageDetail(Integer id) throws IOException;

    /**
     * @Description 发送通知
     * @param emailParam
     */
    public String sendMessage(EmailParam emailParam) throws IOException;
}
