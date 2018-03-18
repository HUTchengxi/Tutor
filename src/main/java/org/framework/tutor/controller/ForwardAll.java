package org.framework.tutor.controller;

import org.framework.tutor.domain.UserVali;
import org.framework.tutor.service.UserMService;
import org.framework.tutor.service.UserVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理一切页面跳转请求
 * @author chengxi
 */
@Controller
@RequestMapping("/forward_con")
public class ForwardAll {

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
     * 进入七嘴八舌界面
     * @return
     */
    @RequestMapping("/gabble")
    public String Gabble(){

        return "/home/gabble";
    }

    /**
     * 进入个人中心界面
     * @return
     */
    @RequestMapping("/personal")
    public String Personal(){

        return "/home/personal";
    }

    /**
     * 进入我的课程界面
     * @return
     */
    @RequestMapping("/gomycourse")
    public String goMyCourse(){

        return "/home/mycourse";
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
     * 进入我的订单中心
     * @return
     */
    @RequestMapping("/gomyorder")
    public String goMyOrder(){

        return "/home/order/myorder";
    }

    /**
     * 进入我的购物车
     * @return
     */
    @RequestMapping("/gomycart")
    public String goMyCart(){

        return "/home/order/mycart";
    }

    /**
     * 进入我的通知界面
     * @return
     */
    @RequestMapping("/gomessage")
    public String goMessage(){

        return "/home/mymessage";
    }

    /**
     * 进入设置中心
     */
    @RequestMapping("/gosetting")
    public String goSetting(){

        return "/home/mysetting";
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
     * 进入管理后台
     * @param request
     * @return
     */
    @RequestMapping("/gosysconfig")
    public String goSysconfig(HttpServletRequest request){

        HttpSession session = request.getSession();
        Integer ident = (Integer) session.getAttribute("identity");

        if(ident != null){
            //家教老师管理后台
            if(ident == 1){
                return "/sysconfig/tutor/index";
            }
            //管理员管理后台
            else{
                return "/sysconfig/admin/index";
            }
        }
        else{
            return "/login/index";
        }
    }

}
