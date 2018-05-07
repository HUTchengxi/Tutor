package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserVali;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理一切页面跳转请求
 * @author chengxi
 */
@Controller
@RequestMapping("/forward_con")
public class ForwardAllController {

    @Autowired
    private UserVService userVService;

    @Autowired
    private UserMService userMService;

    /**
     * 进入登录界面
     * @return
     */
    @RequestMapping("/gologin")
    public String goLogin(){

        return "/login/index";
    }

    /**
     * 进入注册界面
     * @return
     */
    @RequestMapping("/goregister")
    public String goRegister(){

        return "/register/index";
    }

    /**
     * 进入首页
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome(){

        return "/home/welcome";
    }

    /**
     * 进入联系我们界面
     * @return
     */
    @RequestMapping("/contact")
    public String Contact(){

        return "/home/contact";
    }

    /**
     * 进入勤成榜界面
     * @return
     */
    @RequestMapping("/gorank")
    public String Rank(){

        return "/home/rank";
    }

    /**
     * 进入分类界面
     * @return
     */
    @RequestMapping("/classify")
    public String Classify(){

        return "/home/classify";
    }

    /**
     * 进入论坛中心界面
     * @return
     */
    @RequestMapping("/gabble")
    public String Gabble(){

        return "/home/gabble";
    }

    /**
     * 进入404页面
     * @return
     */
    @RequestMapping("/error_404")
    public String NotFound(){
        return "/error/404";
    }

    /**
     * 进入指定课程界面
     * @return
     */
    @RequestMapping("/showcourse")
    public String showcourse(){

        return "home/showcourse";
    }

    /**
     * 进入忘记密码界面
     * @return
     */
    @RequestMapping("/goforget")
    public String goForget(){

        return "/login/forget";
    }

    /**
     * 邮件注册后的提示页面
     * @param request
     * @return
     */
    @RequestMapping("/register_info")
    public String goRegisterInfo(HttpServletRequest request){

        String email = (String) request.getSession().getAttribute("email");
        if(email == null){
            return "home/welcome";
        }
        else{
            //判断邮箱验证状态
            UserVali userVali = userVService.checkEmailStatus(email.split(" ")[1]);
            //验证成功
            if(userVali == null){
                return "/home/welcome";
            }
        }
        return "/register/check";
    }

    /**
     * 校验邮箱注册码
     * @param username
     * @param valicode
     * @return
     */
    @RequestMapping("/register_check")
    public String register_check(String username, String valicode){

        //清空不是当天发送的验证码(留存23：59-24：00的bug)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = simpleDateFormat.format(new Date());
        userVService.checkAll(now);

        //获取正确的邮箱验证码
        String realcode = userVService.getCodeByUsername(username);

        if(realcode != null && realcode.equals(valicode)){
            Integer identity = 0;
            //设置当前用户的id为以验证普通用户
            userMService.setIdentity(username, identity);
            //清除未验证状态
            userVService.delStatus(username);
            return "/login/index";
        }
        else{
            return "/home/welcome";
        }
    }

    /**  
     *    
     * @Description 查询帖子
     * @return java.lang.String
     * @author yinjimin  
     * @date 2018/4/2
     */  
    @RequestMapping("/showcardsearch")
    public String showCardSearch(){

        return "/home/showcard";
    }


    /**  
     *    
     * @Description 帖子详情
     * @return java.lang.String
     * @author yinjimin  
     * @date 2018/4/4
     */  
    @RequestMapping("/showcarddetail")
    public String showCardDetail(){

        return "/home/carddetail";
    }

    /**
     *
     * @Description 被挤下线异常退出
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/5/1
     */
    @RequestMapping("/err_logout")
    public String errLogout(){
        return "/error/err_logout";
    }
}
