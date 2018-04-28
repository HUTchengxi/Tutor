/*
 * Copyright (C) 2011-2013 ShenZhen iBoxpay Information Technology Co. Ltd.
 *
 * All right reserved.
 *
 * This software is the confidential and proprietary information of iBoxPay Company of China.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the contract agreement you entered into with iBoxpay inc.
 *
 *
 */
package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.framework.tutor.api.UserMainApi;
import org.framework.tutor.service.UserMSService;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserSCService;
import org.framework.tutor.util.CommonUtil;
import org.framework.tutor.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinjimin
 * @Description:
 * @date 2018年04月25日
 */
@Component
public class UserMainApiImpl implements UserMainApi {

    @Autowired
    private UserMService userMService;

    @Autowired
    private UserSCService userSCService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 获取我的个人头像
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getImgsrc(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        String username = (String) session.getAttribute("username");
        //服务层获取数据
        org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
        resultMap.put("status", "valid");
        resultMap.put("imgsrc", userMain.getImgsrc());

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取我的个人信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(16);

        String username = (String) session.getAttribute("username");
        //服务层获取数据
        org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
        resultMap.put("status", "valid");
        resultMap.put("username", userMain.getUsername());
        resultMap.put("sex", userMain.getSex() == 1 ? "男" : "女");
        resultMap.put("age", userMain.getAge());
        resultMap.put("imgsrc", userMain.getImgsrc());
        resultMap.put("info", userMain.getInfo());

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 我帮你换修改我的头像
     *
     * @param request
     * @param response
     * @param imgsrc
     */
    @Override
    public void modImgsrc(HttpServletRequest request, HttpServletResponse response, String imgsrc) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        String username = (String) session.getAttribute("username");
        //服务层实现我的头像的修改
        if (userMService.modImgsrcByUser(username, imgsrc)) {
            resultMap.put("status", "modok");
            resultMap.put("imgsrc", imgsrc);
        } else {
            resultMap.put("status", "mysqlerr");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 手动上传修改我的头像
     *
     * @param request
     * @param response
     * @param imgfile
     * @param oimgsrc
     */
    @Override
    public void modImgfile(HttpServletRequest request, HttpServletResponse response,
                           MultipartFile imgfile, String oimgsrc) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(8);

        String username = (String) session.getAttribute("username");
        String imgsrc = "/images/user/face/" + imgfile.getOriginalFilename();
        //上传头像到/images/user/face里
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                new File("src/main/resources/static" + imgsrc)
        ));
        bos.write(imgfile.getBytes());
        bos.flush();
        bos.close();

        //服务层实现我的头像的修改
        if (userMService.modImgsrcByUser(username, imgsrc)) {

            //然后删除原来的那张图片
            String oldimgsrc = "src/main/resources/static" + oimgsrc;
            File oldFile = new File(oldimgsrc);
            if (!oldFile.delete()) {
                resultMap.put("status", "modok");
                resultMap.put("imgsrc", imgsrc);
                resultMap.put("others", "bad");
            } else {
                resultMap.put("status", "modok");
                resultMap.put("imgsrc", imgsrc);
                resultMap.put("others", "good");
            }
        } else {
            resultMap.put("status", "mysqlerr");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 修改我的个人信息
     *
     * @param username
     * @param nickname
     * @param sex
     * @param age
     * @param info
     * @param request
     * @param response
     */
    @Override
    public void modUserinfo(String username, String nickname, Integer sex, Integer age, String info,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String rusername = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (rusername == null || !(rusername.equals(username))) {
            resultMap.put("status", "invalid");
            resultMap.put("url", "/forward_con/welcome");
        } else {
            if (!userMService.modUserinfo(username, nickname, sex, age, info)) {
                resultMap.put("status", "mysqlerr");
                resultMap.put("msg", "I'm sorry");
            } else {
                resultMap.put("status", "valid");
                session.setAttribute("nickname", nickname);
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 获取当前用户的绑定数据
     *
     * @param request
     * @param response
     */
    @Override
    public void getBindInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
        if (userMain == null) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("tel", userMain.getTelephone());
            resultMap.put("ema", userMain.getEmail());
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 通过发送邮件找回密码
     *
     * @param email
     * @param username
     * @param response
     * @throws IOException
     */
    @Override
    public void sendMail(String email, String username, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            username = (String) request.getSession().getAttribute("username");
            //判断用户名和邮箱是否对应
            org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndEmail(username, email);
            if (userMain == null) {
                resultMap.put("status", "invalid");
                writer.print(gson.toJson(resultMap));
                writer.flush();
                writer.close();
            }
        }else {
                //发送校验断码邮件
                String uuid = CommonUtil.getUUID().substring(0, 4);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(email);
                message.setSubject("勤成家教网----邮件找回密码验证钥匙");
                message.setText("您的验证短码是：" + uuid);
                javaMailSender.send(message);

                //session保存短码
                request.getSession().setAttribute("valiemail", email);
                request.getSession().setAttribute("valicode", uuid);
                resultMap.put("status", "ok");
            }


        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 根据手机号/邮箱来重设密码
     *
     * @param username
     * @param email
     * @param phone
     * @param valicode
     * @param newpass
     * @param repass
     * @param request
     * @param response
     */
    @Override
    public void modPass(String username, String email, String phone, String valicode, String newpass, String repass,
                        HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String realvalicode = (String) session.getAttribute("valicode");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //进行密码的验证性
        if (!(newpass != null && newpass.length() >= 6 && newpass.length() <= 12 && newpass.equals(repass))) {
            resultMap.put("status", "invalid");
        } else {
            //邮箱的方式进行密码找回
            if (email != null) {
                //判断验证断码是否正确
                String realemail = (String) session.getAttribute("valiemail");
                if (email.equals(realemail) && valicode != null && valicode.equals(realvalicode)) {

                    //判断邮箱和用户名是否对应
                    org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndEmail(username, email);
                    if (userMain == null) {
                        resultMap.put("status", "inerr");
                    } else {
                        //可以修改密码
                        Integer salt = userMService.getByUser(username).getSalt();
                        newpass = CommonUtil.getMd5Pass(newpass, salt);
                        Integer row = userMService.modPassword(username, newpass);
                        if (row == 1) {
                            resultMap.put("status", "valid");
                        } else {
                            resultMap.put("status", "mysqlerr");
                        }
                    }
                } else {
                    //判断邮箱是否为空或者长度不满足
                    if (!email.equals(realemail)) {
                        resultMap.put("status", "inerr");
                    }
                    resultMap.put("status", "invalid");
                }
            } else if (phone != null) {
                //判断验证断码是否正确
                String realphone = (String) session.getAttribute("valiemail");
                if (phone.equals(realphone) && valicode != null && valicode.equals(realvalicode)) {

                    //判断手机号码和用户名是否对应
                    org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndPhone(username, phone);
                    if (userMain == null) {
                        resultMap.put("status", "inerr");
                    } else {
                        //可以修改密码
                        Integer salt = userMService.getByUser(username).getSalt();
                        newpass = CommonUtil.getMd5Pass(newpass, salt);
                        Integer row = userMService.modPassword(username, newpass);
                        if (row == 1) {
                            resultMap.put("status", "valid");
                        } else {
                            resultMap.put("status", "mysqlerr");
                        }
                    }
                } else {
                    //判断邮箱是否为空或者长度不满足
                    if (!phone.equals(realphone)) {
                        resultMap.put("status", "inerr");
                    } else {
                        resultMap.put("status", "invalid");
                    }
                }
            } else {
                resultMap.put("status", "invalid");
            }
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 通过密保的方式进行找回密码
     *
     * @param queone
     * @param ansone
     * @param quetwo
     * @param anstwo
     * @param quethree
     * @param ansthree
     * @param password
     * @param username
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void modPassBySecret(String queone, String ansone, String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username, HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {

        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //校验密保答案
        if (userSCService.checkSecret(username, queone, ansone)) {
            if (quetwo != null) {
                if (userSCService.checkSecret(username, quetwo, anstwo)) {
                    if (quethree != null) {
                        if (userSCService.checkSecret(username, quethree, ansthree)) {
                            //修改密码
                            userMService.modPassword(username, password);
                            resultMap.put("status", "ok");
                        } else {
                            resultMap.put("status", "err-mb3");
                        }
                    } else {
                        //修改密码
                        Integer salt = userMService.getByUser(username).getSalt();
                        password = CommonUtil.getMd5Pass(password, salt);
                        userMService.modPassword(username, password);
                        resultMap.put("status", "ok");
                    }
                } else {
                    resultMap.put("status", "err-mb2");
                }
            } else {
                //修改密码
                Integer salt = userMService.getByUser(username).getSalt();
                password = CommonUtil.getMd5Pass(password, salt);
                userMService.modPassword(username, password);
                resultMap.put("status", "ok");
            }
        } else {
            resultMap.put("status", "err-mb1");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 解除绑定
     *
     * @param type
     * @param valicode
     * @param request
     * @param response
     */
    @Override
    public void userUnbind(String type, String valicode, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //验证码进行判断
        String realvalicode = (String) session.getAttribute("valicode");
        if (realvalicode != null && realvalicode.equals(valicode)) {
            //进行邮箱解除绑定
            Integer row = null;
            if (type != null && type.equals("email")) {
                row = userMService.unbindEmail(username);
            } else if (type != null && type.equals("phone")) {
                row = userMService.unbindPhone(username);
            } else {
                resultMap.put("status", "invalid");
            }
            if (row == 1) {
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status", "mysqlerr");
            }
        } else {
            resultMap.put("status", "codeerr");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 发送绑定的验证短码
     *
     * @param type
     * @param email
     * @param phone
     * @param request
     * @param response
     */
    @Override
    public void sendBindCode(String type, String email, String phone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //发送邮件验证码
        if (type.equals("email")) {
            //判断邮箱是否存在
            Boolean isExist = userMService.emailExist(email);
            if (!isExist) {
                //发送邮件验证码
                String uuid = CommonUtil.getUUID().substring(0, 4);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(email);
                message.setSubject("勤成家教网----邮件数据绑定验证钥匙");
                message.setText("您的验证短码是：" + uuid);
                javaMailSender.send(message);

                session.setAttribute("bindcode", uuid);
                session.setAttribute("bindemail", email);
                resultMap.put("status", "sendok");
            } else {
                resultMap.put("status", "exist");
            }
        } else if (type.equals("phone")) {
            //判断手机号码是否已经被注册
            Boolean isexist = userMService.phoneExist(phone);
            if (isexist) {
                resultMap.put("status", "exist");
            } else {
                //发送手机语音验证短码
                String host = "http://yuyin.market.alicloudapi.com";
                String path = "/yzx/voiceSend";
                String method = "POST";
                String appcode = "4a97cdc9fdf94a0898a8a265fbc9ab20";
                //随机生成四位数code
                String uuid = CommonUtil.getUUIDInt().substring(0, 4);
                Map<String, String> headers = new HashMap<String, String>();
                //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
                headers.put("Authorization", "APPCODE " + appcode);
                Map<String, String> querys = new HashMap<String, String>();
                querys.put("mobile", phone);
                querys.put("param", "code:" + uuid);
                Map<String, String> bodys = new HashMap<String, String>();
                try {
                    HttpResponse Response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //session保存语音验证短码
                session.setAttribute("bindcode", uuid);
                session.setAttribute("bindemail", phone);
                resultMap.put("status", "sendok");
            }
        } else {
            resultMap.put("status", "invalid");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 进行邮箱/手机号码的绑定
     *
     * @param type
     * @param email
     * @param valicode
     * @param response
     * @param request
     */
    @Override
    public void userBind(String type, String email, String valicode, HttpServletResponse response, HttpServletRequest request) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

            //判断验证码和邮箱/手机号码是否都正确
            String realemail = (String) session.getAttribute("bindemail");
            String realcode = (String) session.getAttribute("bindcode");
            if (realcode != null && realcode.equals(valicode) && realemail != null && realemail.equals(email)) {
                if (type.equals("email")) {
                    userMService.bindEmail(username, email);
                } else {
                    userMService.bindPhone(username, email);
                }
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status", "codeerr");
            }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 发送解除手机绑定的验证码
     *
     * @param phone
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void sendUnbindPhone(String phone, String username, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            username = (String) session.getAttribute("username");
        }
        //判断用户名和手机号码是否对应
        org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndPhone(username, phone);
            //发送手机验证码并保存到session中
            //发送手机语音验证短码
            String host = "http://yuyin.market.alicloudapi.com";
            String path = "/yzx/voiceSend";
            String method = "POST";
            String appcode = "4a97cdc9fdf94a0898a8a265fbc9ab20";
            //随机生成四位数code
            String uuid = CommonUtil.getUUIDInt().substring(0, 4);
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("mobile", phone);
            querys.put("param", "code:" + uuid);
            Map<String, String> bodys = new HashMap<String, String>();
            try {
                HttpResponse Response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //session保存语音验证短码
            session.setAttribute("valicode", uuid);
            session.setAttribute("valiemail", phone);
            resultMap.put("status", "ok");

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }

    /**
     * 发送手机注册验证码
     *
     * @param phone
     * @param request
     * @param response
     */
    @Override
    public void sendRegisterBindCode(String phone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断手机号码是否已经被注册
        Boolean isexist = userMService.phoneExist(phone);
        if (isexist) {
            resultMap.put("status", "exist");
        } else {
            //发送手机语音验证短码
            String host = "http://yuyin.market.alicloudapi.com";
            String path = "/yzx/voiceSend";
            String method = "POST";
            String appcode = "4a97cdc9fdf94a0898a8a265fbc9ab20";
            //随机生成四位数code
            String uuid = CommonUtil.getUUIDInt().substring(0, 4);
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("mobile", phone);
            querys.put("param", "code:" + uuid);
            Map<String, String> bodys = new HashMap<String, String>();
            try {
                HttpResponse Response = HttpUtils.doPost(host, path, method, headers, querys, bodys);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //session保存语音验证短码
            session.setAttribute("bindcode", uuid);
            session.setAttribute("bindphone", phone);
            resultMap.put("status", "sendok");
        }

        writer.print(gson.toJson(resultMap));
        writer.flush();
        writer.close();
    }
}
