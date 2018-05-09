package org.framework.tutor.api;

import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.entity.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserMessageApi {

    /**
     * 获取我的未读通知的数量
     *
     * @param request
     * @param response
     */
    public void getMyMessageCount(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取我的通知数据(简单数据)
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMyMessage(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取指定管理员发送的我的通知数据
     *
     * @param suser
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMessageByUser(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 通知阅读完毕之后的读状态的更改
     *
     * @param suser
     * @param request
     * @param response
     */
    public void setMessageStatus(String suser, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 获取未读/已读消息
     *
     * @param suser
     * @param status
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMessageByStatus(String suser, String status, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 删除所选通知数据
     *
     * @param did
     * @param request
     * @param response
     * @throws IOException
     */
    public void delMyMessage(Integer did, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 标记全部为已读
     *
     * @param request
     * @param response
     */
    public void setAllStatus(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * @param [paramMap, response]
     * @return void
     * @Description 管理员身份获取通知数据列表
     * @author yinjimin
     * @date 2018/4/22
     */
    public void getMessageList(ParamMap paramMap, HttpServletResponse response) throws IOException;

    /**
     * @param [id, response]
     * @return void
     * @Description 管理员身份查看通知详情
     * @author yinjimin
     * @date 2018/4/22
     */
    public void getMessageDetail(Integer id, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 发送通知
     * @param [emailParam, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/24
     */
    public void sendMessage(EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
