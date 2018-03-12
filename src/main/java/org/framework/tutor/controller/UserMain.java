package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.service.UserMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
