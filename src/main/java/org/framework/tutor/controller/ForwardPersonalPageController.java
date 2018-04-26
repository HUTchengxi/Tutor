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
package org.framework.tutor.controller;

import org.framework.tutor.annotation.RequireAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinjimin
 * @Description: 用户登录之后才能进入的所有api
 * @date 2018年04月25日
 */
@Controller
@RequestMapping("/personalpage_con")
public class ForwardPersonalPageController {

    /**
     * 进入个人中心界面
     * @return
     */
    @RequireAuth(ident = "user")
    @RequestMapping("/personal")
    public String Personal(){

        return "/home/personal";
    }

    /**
     * 进入我的课程界面
     * @return
     */
    @RequireAuth(ident = "user")
    @RequestMapping("/gomycourse")
    public String goMyCourse(){

        return "/home/mycourse";
    }

    /**
     * 进入我的订单中心
     * @return
     */
    @RequireAuth(ident = "user")
    @RequestMapping("/gomyorder")
    public String goMyOrder(){

        return "/home/order/myorder";
    }

    /**
     * 进入我的购物车
     * @return
     */
    @RequireAuth(ident = "user")
    @RequestMapping("/gomycart")
    public String goMyCart(){

        return "/home/order/mycart";
    }

    /**
     * 进入我的通知界面
     * @return
     */
    @RequireAuth(ident = "user")
    @RequestMapping("/gomessage")
    public String goMessage(){

        return "/home/mymessage";
    }

    /**
     * 进入设置中心
     */
    @RequireAuth(ident = "user")
    @RequestMapping("/gosetting")
    public String goSetting(){

        return "/home/mysetting";
    }

    /**
     *
     * @Description 进入我的论坛中心的个人中心
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/12
     */
    @RequireAuth(ident = "user")
    @GetMapping("/gomybbsinfo")
    public String goMyBbsInfo(){

        return "/home/mybbs";
    }
}
