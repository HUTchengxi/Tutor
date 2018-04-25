package org.framework.tutor.api;

import com.google.gson.Gson;
import org.framework.tutor.domain.SysEmailManage;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.service.SysEmailManageService;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
