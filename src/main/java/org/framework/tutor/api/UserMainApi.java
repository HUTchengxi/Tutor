package org.framework.tutor.api;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public interface UserMainApi {

    /**
     * 获取我的个人头像
     */
    public String getImgsrc() throws IOException;

    /**
     * 获取我的个人信息
     */
    public String getUserinfo() throws IOException;

    /**
     * 我帮你换修改我的头像
     * @param imgsrc 头像url
     */
    public String modImgsrc(String imgsrc) throws IOException;

    /**
     * 手动上传修改我的头像
     * @param imgfile 头像文件
     * @param oimgsrc 原来的头像url（删除替换）
     */
    public String modImgfile(MultipartFile imgfile, String oimgsrc) throws IOException ;

    /**
     * 修改我的个人信息
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     */
    public String modUserinfo(String username, String nickname, Integer sex, Integer age, String info) throws IOException;

    /**
     * 获取当前用户的绑定数据
     */
    public String getBindInfo() throws IOException;

    /**
     * 通过发送邮件找回密码
     * @param email
     * @param username
     */
    public String sendMail(String email, String username) throws IOException, MessagingException;

    /**
     * 根据手机号/邮箱来重设密码
     * @param username
     * @param email
     * @param phone
     * @param valicode
     * @param newpass
     * @param repass
     */
    public String modPass(String username, String email, String phone, String valicode, String newpass, String repass) throws IOException, NoSuchAlgorithmException;

    /**
     * 通过密保的方式进行找回密码
     * @param queone
     * @param ansone
     * @param quetwo
     * @param anstwo
     * @param quethree
     * @param ansthree
     * @param password
     * @param username
     * @throws IOException
     */
    public String modPassBySecret(String queone, String ansone,
                                String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username) throws IOException, NoSuchAlgorithmException;

    /**
     * 解除绑定
     * @param type
     * @param valicode
     */
    public String userUnbind(String type, String valicode) throws IOException;

    /**
     * 发送绑定的验证短码
     * @param type
     * @param email
     * @param phone
     */
    public String sendBindCode(String type, String email, String phone) throws IOException;

    /**
     * 进行邮箱/手机号码的绑定
     * @param type
     * @param email
     * @param valicode
     */
    public String userBind(String type, String email, String valicode) throws IOException;

    /**
     * 发送解除手机绑定的验证码
     *
     * @param phone
     * @param request
     * @param response
     * @throws IOException
     */
    public String sendUnbindPhone(String phone, String username) throws IOException;

    /**
     * 发送手机注册验证码
     * @param phone
     */
    public String sendRegisterBindCode(String phone) throws IOException;
}
