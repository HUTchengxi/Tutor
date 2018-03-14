package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * 用户个人信息控制类
 * @author chengxi
 */
@RestController
@RequestMapping("/usermain_con")
public class UserMain {

    @Autowired
    private UserMService userMService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 获取我的个人头像
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getimgsrc")
    public void getImgsrc(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;

        String username = (String) session.getAttribute("username");
        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            //服务层获取数据
            org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
            res = "{\"status\": \"valid\", \"imgsrc\": \""+userMain.getImgsrc()+"\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取我的个人信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getuserinfo")
    public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;

        String username = (String) session.getAttribute("username");
        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            //服务层获取数据
            org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
            res = "{\"status\": \"valid\", \"username\": \""+userMain.getUsername()+"\"" +
                    ", \"nickname\": \""+userMain.getNickname()+"\"" +
                    ", \"sex\": \""+(userMain.getSex()==1?"男":"女")+"\"" +
                    ", \"age\": \""+userMain.getAge()+"\"" +
                    ", \"info\": \""+userMain.getInfo()+"\" }";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 我帮你换修改我的头像
     * @param request
     * @param response
     * @param imgsrc
     */
    @RequestMapping("/modimgsrc")
    public void modImgsrc(HttpServletRequest request, HttpServletResponse response, String imgsrc) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String res = null;
        PrintWriter writer = response.getWriter();

        String username = (String) session.getAttribute("username");
        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            //服务层实现我的头像的修改
            if(userMService.modImgsrcByUser(username, imgsrc)){
                res = "{\"status\": \"modok\", \"imgsrc\": \""+imgsrc+"\"}";
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 手动上传修改我的头像
     * @param request
     * @param response
     * @param imgfile
     * @param oimgsrc
     */
    @RequestMapping("/modimgfile")
    public void modImgfile(HttpServletRequest request, HttpServletResponse response,
                           MultipartFile imgfile, String oimgsrc) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String res = null;
        PrintWriter writer = response.getWriter();

        String username = (String) session.getAttribute("username");
        if(username == null){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            String imgsrc = "/images/user/face/" + imgfile.getOriginalFilename();
            //上传头像到/images/user/face里
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                    new File("src/main/resources/static" + imgsrc)
            ));
            bos.write(imgfile.getBytes());
            bos.flush();
            bos.close();

            //服务层实现我的头像的修改
            if(userMService.modImgsrcByUser(username, imgsrc)){

                //然后删除原来的那张图片
                String oldimgsrc = "src/main/resources/static" + oimgsrc;
                File oldFile = new File(oldimgsrc);
                if(!oldFile.delete()){
                    res = "{\"status\": \"modok\", \"imgsrc\": \""+imgsrc+"\", \"others\": \"bad\"}";
                }
                else {
                    res = "{\"status\": \"modok\", \"imgsrc\": \"" + imgsrc + "\", \"others\": \"good\"}";
                }
            }
            else{
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 修改我的个人信息
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     * @param request
     * @param response
     */
    @RequestMapping("/moduserinfo")
    public void modUserinfo(String username, String nickname, Integer sex, Integer age, String info,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String rusername = (String) session.getAttribute("username");
        String res = null;

        if(rusername == null || !(rusername.equals(username))){
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        }
        else{
            if(!userMService.modUserinfo(username, nickname, sex, age, info)){
                res = "{\"status\": \"mysqlerr\", \"msg\": \"I'm sorry\"}";
            }
            else{
                res = "{\"status\": \"valid\"}";
                session.setAttribute("nickname", nickname);
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前用户的绑定数据
     * @param request
     * @param response
     */
    @RequestMapping("/getbindinfo")
    public void getBindInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if(username == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
            if(userMain == null){
                res = "{\"status\": \"invalid\"}";
            }
            else{
                res = "{\"tel\": \""+userMain.getTelephone()+"\", \"ema\": \""+userMain.getEmail()+"\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 通过发送邮件找回密码
     * @param email
     * @param username
     * @param response
     * @throws IOException
     */
    @RequestMapping("/forget_sendmail")
    public void sendMail(String email, String username, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        //判断用户名和邮箱是否对应
        org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndEmail(username, email);
        if(userMain == null){
            res = "{\"status\": \"invalid\"}";
        }
        else{
            //发送校验断码邮件
            String uuid = CommonUtil.getUUID().substring(0, 4);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject("勤成家教网----邮件找回密码验证钥匙");
            message.setText("您的验证短码是："+uuid);
            javaMailSender.send(message);

            //session保存短码
            request.getSession().setAttribute("valiemail", email);
            request.getSession().setAttribute("valicode", uuid);
            res = "{\"status\": \"ok\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }

    /**
     * 根据手机号/邮箱来重设密码
     * @param username
     * @param email
     * @param phone
     * @param valicode
     * @param newpass
     * @param repass
     * @param request
     * @param response
     */
    @RequestMapping("/forget_modpass")
    public void modPass(String username, String email, String phone, String valicode, String newpass, String repass,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String realvalicode = (String) session.getAttribute("valicode");
        String res = null;

        //进行密码的验证性
        if(!(newpass != null && newpass.length() >= 6 && newpass.length() <= 12 && newpass.equals(repass))){
            res = "{\"status\": \"invalid\"}";
        }
        else {
            //邮箱的方式进行密码找回
            if (email != null) {
                //判断验证断码是否正确
                String realemail = (String) session.getAttribute("valiemail");
                if (email.equals(realemail) && valicode != null && valicode.equals(realvalicode)) {

                    //判断邮箱和用户名是否对应
                    org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndEmail(username, email);
                    if(userMain == null){
                        res = "{\"status\": \"inerr\"}";
                    }
                    else {
                        //可以修改密码
                        Integer row = userMService.modPassword(username, newpass);
                        if (row == 1) {
                            res = "{\"status\": \"valid\"}";
                        } else {
                            res = "{\"status\": \"mysqlerr\"}";
                        }
                    }
                }
                else{
                    //判断邮箱是否为空或者长度不满足
                    if(!email.equals(realemail)){
                        res = "{\"status\": \"inerr\"}";
                    }
                    res = "{\"status\": \"invalid\"}";
                }
            }
            else if(phone != null){
                //判断验证断码是否正确
                String realphone = (String) session.getAttribute("valiphone");
                if (phone.equals(realphone) && valicode != null && valicode.equals(realvalicode)) {

                    //判断手机号码和用户名是否对应

                    //可以修改密码
                    Integer row = userMService.modPassword(username, newpass);
                    if(row == 1){
                        res = "{\"status\": \"valid\"}";
                    }
                    else{
                        res = "{\"status\": \"mysqlerr\"}";
                    }
                }
                else{
                    //判断邮箱是否为空或者长度不满足
                    if(!phone.equals(realphone)){
                        res = "{\"status\": \"inerr\"}";
                    }
                    else {
                        res = "{\"status\": \"invalid\"}";
                    }
                }
            }
            else{
                    res = "{\"status\": \"invalid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
