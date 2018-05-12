package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.framework.tutor.api.UserMainApi;
import org.framework.tutor.service.UserMainService;
import org.framework.tutor.service.UserSecretService;
import org.framework.tutor.util.CommonUtil;
import org.framework.tutor.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserMainApiImpl implements UserMainApi {

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private UserSecretService userSecretService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String getImgsrc() throws IOException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        String username = (String) session.getAttribute("username");
        //服务层获取数据
        org.framework.tutor.domain.UserMain userMain = userMainService.getByUser(username);
        resultMap.put("status", "valid");
        resultMap.put("imgsrc", userMain.getImgsrc());

        return gson.toJson(resultMap);
    }


    @Override
    public String getUserinfo() throws IOException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(16);

        String username = (String) session.getAttribute("username");
        //服务层获取数据
        org.framework.tutor.domain.UserMain userMain = userMainService.getByUser(username);
        resultMap.put("status", "valid");
        resultMap.put("username", userMain.getUsername());
        resultMap.put("nickname", userMain.getNickname());
        resultMap.put("sex", userMain.getSex() == 1 ? "男" : "女");
        resultMap.put("age", userMain.getAge());
        resultMap.put("imgsrc", userMain.getImgsrc());
        resultMap.put("info", userMain.getInfo());

        return gson.toJson(resultMap);
    }


    @Override
    public String modImgsrc(String imgsrc) throws IOException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        String username = (String) session.getAttribute("username");
        //服务层实现我的头像的修改
        if (userMainService.modImgsrcByUser(username, imgsrc)) {
            resultMap.put("status", "modok");
            resultMap.put("imgsrc", imgsrc);
        } else {
            resultMap.put("status", "mysqlerr");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String modImgfile(MultipartFile imgfile, String oimgsrc) throws IOException {

        HttpSession session = request.getSession();
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
        if (userMainService.modImgsrcByUser(username, imgsrc)) {

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

        return gson.toJson(resultMap);
    }


    @Override
    public String modUserinfo(String username, String nickname, Integer sex, Integer age, String info) throws IOException {

        HttpSession session = request.getSession();
        String rusername = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (rusername == null || !(rusername.equals(username))) {
            resultMap.put("status", "invalid");
            resultMap.put("url", "/forward_con/welcome");
        } else {
            if (!userMainService.modUserinfo(username, nickname, sex, age, info)) {
                resultMap.put("status", "mysqlerr");
                resultMap.put("msg", "I'm sorry");
            } else {
                resultMap.put("status", "valid");
                session.setAttribute("nickname", nickname);
            }
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getBindInfo() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        org.framework.tutor.domain.UserMain userMain = userMainService.getByUser(username);
        if (userMain == null) {
            resultMap.put("status", "invalid");
        } else {
            resultMap.put("tel", userMain.getTelephone());
            resultMap.put("ema", userMain.getEmail());
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String sendMail(String email, String username) throws IOException, MessagingException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            username = (String) request.getSession().getAttribute("username");
            //判断用户名和邮箱是否对应
            org.framework.tutor.domain.UserMain userMain = userMainService.getByUserAndEmail(username, email);
            if (userMain == null) {
                resultMap.put("status", "invalid");
                return gson.toJson(resultMap);
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


        return gson.toJson(resultMap);
    }


    @Override
    public String modPass(String username, String email, String phone, String valicode, String newpass, String repass) throws IOException, NoSuchAlgorithmException {

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
                    org.framework.tutor.domain.UserMain userMain = userMainService.getByUserAndEmail(username, email);
                    if (userMain == null) {
                        resultMap.put("status", "inerr");
                    } else {
                        //可以修改密码
                        Integer salt = userMainService.getByUser(username).getSalt();
                        newpass = CommonUtil.getMd5Pass(newpass, salt);
                        Integer row = userMainService.modPassword(username, newpass);
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
                    org.framework.tutor.domain.UserMain userMain = userMainService.getByUserAndPhone(username, phone);
                    if (userMain == null) {
                        resultMap.put("status", "inerr");
                    } else {
                        //可以修改密码
                        Integer salt = userMainService.getByUser(username).getSalt();
                        newpass = CommonUtil.getMd5Pass(newpass, salt);
                        Integer row = userMainService.modPassword(username, newpass);
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

        return gson.toJson(resultMap);
    }


    @Override
    public String modPassBySecret(String queone, String ansone, String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username) throws IOException, NoSuchAlgorithmException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //校验密保答案
        if (userSecretService.checkSecret(username, queone, ansone)) {
            if (quetwo != null) {
                if (userSecretService.checkSecret(username, quetwo, anstwo)) {
                    if (quethree != null) {
                        if (userSecretService.checkSecret(username, quethree, ansthree)) {
                            //修改密码
                            userMainService.modPassword(username, password);
                            resultMap.put("status", "ok");
                        } else {
                            resultMap.put("status", "err-mb3");
                        }
                    } else {
                        //修改密码
                        Integer salt = userMainService.getByUser(username).getSalt();
                        password = CommonUtil.getMd5Pass(password, salt);
                        userMainService.modPassword(username, password);
                        resultMap.put("status", "ok");
                    }
                } else {
                    resultMap.put("status", "err-mb2");
                }
            } else {
                //修改密码
                Integer salt = userMainService.getByUser(username).getSalt();
                password = CommonUtil.getMd5Pass(password, salt);
                userMainService.modPassword(username, password);
                resultMap.put("status", "ok");
            }
        } else {
            resultMap.put("status", "err-mb1");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String userUnbind(String type, String valicode) throws IOException {

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
                row = userMainService.unbindEmail(username);
            } else if (type != null && type.equals("phone")) {
                row = userMainService.unbindPhone(username);
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

        return gson.toJson(resultMap);
    }


    @Override
    public String sendBindCode(String type, String email, String phone) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //发送邮件验证码
        if (type.equals("email")) {
            //判断邮箱是否存在
            Boolean isExist = userMainService.emailExist(email);
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
            Boolean isexist = userMainService.phoneExist(phone);
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

        return gson.toJson(resultMap);
    }


    @Override
    public String userBind(String type, String email, String valicode) throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

            //判断验证码和邮箱/手机号码是否都正确
            String realemail = (String) session.getAttribute("bindemail");
            String realcode = (String) session.getAttribute("bindcode");
            if (realcode != null && realcode.equals(valicode) && realemail != null && realemail.equals(email)) {
                if (type.equals("email")) {
                    userMainService.bindEmail(username, email);
                } else {
                    userMainService.bindPhone(username, email);
                }
                resultMap.put("status", "valid");
            } else {
                resultMap.put("status", "codeerr");
            }

        return gson.toJson(resultMap);
    }

    @Override
    public String sendUnbindPhone(String phone, String username) throws IOException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        if (username == null) {
            username = (String) session.getAttribute("username");
        }
        //判断用户名和手机号码是否对应
        org.framework.tutor.domain.UserMain userMain = userMainService.getByUserAndPhone(username, phone);
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

        return gson.toJson(resultMap);
    }


    @Override
    public String sendRegisterBindCode(String phone) throws IOException {

        HttpSession session = request.getSession();
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //判断手机号码是否已经被注册
        Boolean isexist = userMainService.phoneExist(phone);
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

        return gson.toJson(resultMap);
    }
}
