package org.framework.tutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理一切页面跳转请求
 * @author chengxi
 */
@Controller
@RequestMapping("/forward_con")
public class ForwardAll {

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
     * 进入忘记密码界面
     * @return
     */
    @RequestMapping("/goforget")
    public String goForget(){

        return "/forget/index";
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
}
