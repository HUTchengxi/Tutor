package org.framework.tutor.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

/**
 * 邮箱操作工具类
 * @author chengxi
 */
public class EmailUtil {

    /**
     * 发送邮箱注册码
     * @param email
     * @param username
     * @return
     */
    public static String sendValiEmail(String email, String username) throws MessagingException {

        String valicode = null;

        Properties prop = new Properties();
        prop.put("mail.host", "smtp.163.com");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", true);
        //使用java发送邮件5步骤
        //1.创建sesssion
        Session session = Session.getInstance(prop);
        //开启session的调试模式，可以查看当前邮件发送状态
        session.setDebug(true);
        //2.通过session获取Transport对象（发送邮件的核心API）
        Transport ts = session.getTransport();
        //3.通过邮件用户名密码链接
        ts.connect("15616371583@163.com", "mygod23");


        //4.创建邮件
        //创建邮件对象
        MimeMessage mm = new MimeMessage(session);
        //设置发件人
        mm.setFrom(new InternetAddress("15616371583@163.com"));
        //设置收件人
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //设置抄送人
        mm.setRecipient(Message.RecipientType.CC, new InternetAddress(email));
        mm.setSubject("勤成家教网---邮箱验证链接");

        valicode = getUUID();
        mm.setContent("点击链接<a href='http://localhost:8080/forward_con/register_check?username="+username+"&valicode="+valicode+"'>http://localhost:8080/register_con/register_check</a>进行验证", "text/html;charset=utf-8");


        //5.发送电子邮件
        ts.sendMessage(mm, mm.getAllRecipients());
        return valicode;
    }

    /**
     * 随机生成16为code
     * @return
     */
    public static String getUUID(){

        UUID uuid = UUID.randomUUID();
        String valicode = uuid.toString().replaceAll("-", "").substring(0, 16);

        return valicode;
    }
}
