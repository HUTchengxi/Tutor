package org.framework.tutor.api;

import org.framework.tutor.entity.EmailParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SysEmailManagerApi {

    /**
     * @Description 发送邮件
     * @param emailParam
     */
    public String sendEmail(EmailParam emailParam) throws IOException, MessagingException;

    /**
     * @Description 发送邮件
     * @param emailParam
     */
    public String saveEmail(EmailParam emailParam) throws IOException, MessagingException;

    /**
     * @Description 获取邮箱列表
     * @param emailParam
     */
    public String getEmailList(EmailParam emailParam) throws IOException;

    /**
     * @Description 获取对应的邮件详情
     * @param id
     */
    public String getEmailDetail(Integer id) throws IOException;

    /**
     * @Description 删除指定邮件
     * @param id 邮件id
     */
    public String deleteEmail(Integer id) throws IOException;

    /**  
     * @Description 获取指定邮件的修改数据
     * @param id email的id    
     */
    public String getModinfoById(Integer id) throws IOException;
}
