package org.framework.tutor.api;

import org.framework.tutor.entity.EmailParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SysEmailManagerApi {

    /**
     * @param [emailParam, request, response]
     * @return void
     * @Description 发送邮件
     * @author yinjimin
     * @date 2018/4/20
     */
    public void sendEmail(EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException;

    /**
     * @param [emailParam, request, response]
     * @return void
     * @Description 发送邮件
     * @author yinjimin
     * @date 2018/4/20
     */
    public void saveEmail(EmailParam emailParam, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException;

    /**
     *
     * @Description 获取邮箱列表
     * @param [emailParam, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    public void getEmailList(EmailParam emailParam, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 获取对应的邮件详情
     * @param [id, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    public void getEmailDetail(Integer id, HttpServletResponse response) throws IOException;

    /**
     *
     * @Description 删除指定邮件
     * @param [id, response]
     * @return void
     * @author yinjimin
     * @date 2018/4/20
     */
    public void deleteEmail(Integer id, HttpServletResponse response) throws IOException;

    public void getModinfoById(Integer id, HttpServletResponse response) throws IOException;
}
