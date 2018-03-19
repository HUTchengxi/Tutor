package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserSCService;
import org.framework.tutor.util.CommonUtil;
import org.framework.tutor.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户个人信息控制类
 *
 * @author chengxi
 */
@RestController
@RequestMapping("/usermain_con")
public class UserMain {

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
    @RequestMapping("/getimgsrc")
    public void getImgsrc(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;

        String username = (String) session.getAttribute("username");
        if (username == null) {
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        } else {
            //服务层获取数据
            org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
            res = "{\"status\": \"valid\", \"imgsrc\": \"" + userMain.getImgsrc() + "\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/getuserinfo")
    public void getUserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String res = null;

        String username = (String) session.getAttribute("username");
        if (username == null) {
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        } else {
            //服务层获取数据
            org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
            res = "{\"status\": \"valid\", \"username\": \"" + userMain.getUsername() + "\"" +
                    ", \"nickname\": \"" + userMain.getNickname() + "\"" +
                    ", \"sex\": \"" + (userMain.getSex() == 1 ? "男" : "女") + "\"" +
                    ", \"age\": \"" + userMain.getAge() + "\"" +
                    ", \"info\": \"" + userMain.getInfo() + "\" }";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/modimgsrc")
    public void modImgsrc(HttpServletRequest request, HttpServletResponse response, String imgsrc) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String res = null;
        PrintWriter writer = response.getWriter();

        String username = (String) session.getAttribute("username");
        if (username == null) {
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        } else {
            //服务层实现我的头像的修改
            if (userMService.modImgsrcByUser(username, imgsrc)) {
                res = "{\"status\": \"modok\", \"imgsrc\": \"" + imgsrc + "\"}";
            } else {
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/modimgfile")
    public void modImgfile(HttpServletRequest request, HttpServletResponse response,
                           MultipartFile imgfile, String oimgsrc) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String res = null;
        PrintWriter writer = response.getWriter();

        String username = (String) session.getAttribute("username");
        if (username == null) {
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        } else {
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
                    res = "{\"status\": \"modok\", \"imgsrc\": \"" + imgsrc + "\", \"others\": \"bad\"}";
                } else {
                    res = "{\"status\": \"modok\", \"imgsrc\": \"" + imgsrc + "\", \"others\": \"good\"}";
                }
            } else {
                res = "{\"status\": \"mysqlerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/moduserinfo")
    public void modUserinfo(String username, String nickname, Integer sex, Integer age, String info,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String rusername = (String) session.getAttribute("username");
        String res = null;

        if (rusername == null || !(rusername.equals(username))) {
            res = "{\"status\": \"invalid\", \"url\": \"/forward_con/welcome\"}";
        } else {
            if (!userMService.modUserinfo(username, nickname, sex, age, info)) {
                res = "{\"status\": \"mysqlerr\", \"msg\": \"I'm sorry\"}";
            } else {
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
     *
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

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            org.framework.tutor.domain.UserMain userMain = userMService.getByUser(username);
            if (userMain == null) {
                res = "{\"status\": \"invalid\"}";
            } else {
                res = "{\"tel\": \"" + userMain.getTelephone() + "\", \"ema\": \"" + userMain.getEmail() + "\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/forget_sendmail")
    public void sendMail(String email, String username, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        if (username == null) {
            username = (String) request.getSession().getAttribute("username");
            if (username == null) {
                res = "{\"status\": \"invalid\"}";
            } else {

                //判断用户名和邮箱是否对应
                org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndEmail(username, email);
                if (userMain == null) {
                    res = "{\"status\": \"invalid\"}";
                } else {
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
                    res = "{\"status\": \"ok\"}";
                }
            }
        }


        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/forget_modpass")
    public void modPass(String username, String email, String phone, String valicode, String newpass, String repass,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String realvalicode = (String) session.getAttribute("valicode");
        String res = null;

        //进行密码的验证性
        if (!(newpass != null && newpass.length() >= 6 && newpass.length() <= 12 && newpass.equals(repass))) {
            res = "{\"status\": \"invalid\"}";
        } else {
            //邮箱的方式进行密码找回
            if (email != null) {
                //判断验证断码是否正确
                String realemail = (String) session.getAttribute("valiemail");
                if (email.equals(realemail) && valicode != null && valicode.equals(realvalicode)) {

                    //判断邮箱和用户名是否对应
                    org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndEmail(username, email);
                    if (userMain == null) {
                        res = "{\"status\": \"inerr\"}";
                    } else {
                        //可以修改密码
                        Integer row = userMService.modPassword(username, newpass);
                        if (row == 1) {
                            res = "{\"status\": \"valid\"}";
                        } else {
                            res = "{\"status\": \"mysqlerr\"}";
                        }
                    }
                } else {
                    //判断邮箱是否为空或者长度不满足
                    if (!email.equals(realemail)) {
                        res = "{\"status\": \"inerr\"}";
                    }
                    res = "{\"status\": \"invalid\"}";
                }
            } else if (phone != null) {
                //判断验证断码是否正确
                String realphone = (String) session.getAttribute("valiemail");
                if (phone.equals(realphone) && valicode != null && valicode.equals(realvalicode)) {

                    //判断手机号码和用户名是否对应
                    org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndPhone(username, phone);
                    if (userMain == null) {
                        res = "{\"status\": \"inerr\"}";
                    } else {
                        //可以修改密码
                        Integer row = userMService.modPassword(username, newpass);
                        if (row == 1) {
                            res = "{\"status\": \"valid\"}";
                        } else {
                            res = "{\"status\": \"mysqlerr\"}";
                        }
                    }
                } else {
                    //判断邮箱是否为空或者长度不满足
                    if (!phone.equals(realphone)) {
                        res = "{\"status\": \"inerr\"}";
                    } else {
                        res = "{\"status\": \"invalid\"}";
                    }
                }
            } else {
                res = "{\"status\": \"invalid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/forget_modpassbysecret")
    public void modPassBySecret(@RequestParam("queone") String queone, @RequestParam("ansone") String ansone,
                                String quetwo, String anstwo, String quethree, String ansthree, String password,
                                String username, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String res = null;

        //校验密保答案
        if (userSCService.checkSecret(username, queone, ansone)) {
            if (quetwo != null) {
                if (userSCService.checkSecret(username, quetwo, anstwo)) {
                    if (quethree != null) {
                        if (userSCService.checkSecret(username, quethree, ansthree)) {
                            //修改密码
                            userMService.modPassword(username, password);
                            res = "{\"status\": \"ok\"}";
                        } else {
                            res = "{\"status\": \"err-mb3\"}";
                        }
                    } else {
                        //修改密码
                        userMService.modPassword(username, password);
                        res = "{\"status\": \"ok\"}";
                    }
                } else {
                    res = "{\"status\": \"err-mb2\"}";
                }
            } else {
                //修改密码
                userMService.modPassword(username, password);
                res = "{\"status\": \"ok\"}";
            }
        } else {
            res = "{\"status\": \"err-mb1\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/unbind_valicode")
    public void userUnbind(String type, String valicode, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
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
                    res = "{\"status\": \"invalid\"}";
                }
                if (row == 1) {
                    res = "{\"status\": \"valid\"}";
                } else {
                    res = "{\"status\": \"mysqlerr\"}";
                }
            } else {
                res = "{\"status\": \"codeerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/sendbindcode")
    public void sendBindCode(@RequestParam("type") String type, String email, String phone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
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
                    res = "{\"status\": \"sendok\"}";
                } else {
                    res = "{\"status\": \"exist\"}";
                }
            } else if (type.equals("phone")) {
                //判断手机号码是否已经被注册
                Boolean isexist = userMService.phoneExist(phone);
                if (isexist) {
                    res = "{\"status\": \"exist\"}";
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
                        System.out.println("test:" + Response.getEntity().getContent());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //session保存语音验证短码
                    session.setAttribute("bindcode", uuid);
                    session.setAttribute("bindemail", phone);
                    res = "{\"status\": \"sendok\"}";
                }
            } else {
                res = "{\"status\": \"invalid\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/userbind")
    public void userBind(@RequestParam("type") String type, String email, String valicode, HttpServletResponse response, HttpServletRequest request) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        if (username == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
            //判断验证码和邮箱/手机号码是否都正确
            String realemail = (String) session.getAttribute("bindemail");
            String realcode = (String) session.getAttribute("bindcode");
            if (realcode != null && realcode.equals(valicode) && realemail != null && realemail.equals(email)) {
                if (type.equals("email")) {
                    userMService.bindEmail(username, email);
                } else {
                    userMService.bindPhone(username, email);
                }
                res = "{\"status\": \"valid\"}";
            } else {
                res = "{\"status\": \"codeerr\"}";
            }
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/sendunbindphone")
    public void sendUnbindPhone(@RequestParam("phone") String phone, String username, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;

        if (username == null) {
            username = (String) session.getAttribute("username");
            if (username == null) {
                res = "{\"status\": \"invalid\"}";
                writer.print(new JsonParser().parse(res).getAsJsonObject());
                writer.flush();
                writer.close();
                return;
            }
        }
        //判断用户名和手机号码是否对应
        org.framework.tutor.domain.UserMain userMain = userMService.getByUserAndPhone(username, phone);
        if (userMain == null) {
            res = "{\"status\": \"invalid\"}";
        } else {
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
                System.out.println(phone + "  " + uuid);
                HttpResponse Response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
                System.out.println("test:" + Response.getEntity().getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }

            //session保存语音验证短码
            session.setAttribute("valicode", uuid);
            session.setAttribute("valiemail", phone);
            res = "{\"status\": \"ok\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
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
    @RequestMapping("/register_sendbindcode")
    public void sendRegisterBindCode(@RequestParam("phone") String phone, HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String res = null;

        //判断手机号码是否已经被注册
        Boolean isexist = userMService.phoneExist(phone);
        if (isexist) {
            res = "{\"status\": \"exist\"}";
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
            res = "{\"status\": \"sendok\"}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
