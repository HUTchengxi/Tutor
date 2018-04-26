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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yinjimin
 * @Description: 家教登录之后才能进的所有api
 * @date 2018年04月25日
 */
@Controller
@RequestMapping("/tutorpage_con")
public class ForwardTutorPageController {

    /**
     * 进入管理后台
     * @param request
     * @return
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("/gosysconfig")
    public String goSysconfig(){
        return "/sysconfig/tutor/index";
    }

    /**
     *
     * @Description 进入家教后台首页
     * @param
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/3/28
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("/gosystutormain")
    public String goSysTutorMain(){

        return "/sysconfig/tutor/sysmain";
    }


    /**
     *
     * @Description 进入家教的个人中心
     * @param
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/3/30
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("/gosystutorpersonal")
    public String goSysTutorPersonal(){

        return "/sysconfig/tutor/personal";
    }

    /**
     *
     * @Description 进入我的课程发布界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/14
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("/gosystutorpublish")
    public String goSystutorPublish(){

        return "/sysconfig/tutor/mypublish";
    }

    /**
     *
     * @Description 进入发布新的课程界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/14
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("gosystutorpublishnew")
    public String goSystutorPublishNew(){

        return "sysconfig/tutor/publishnew";
    }

    /**
     *
     * @Description 进入课程评论管理界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/18
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("/gosystutorcommand")
    public String goSystutorCommand(){

        return "sysconfig/tutor/commandlist";
    }

    /**
     *
     * @Description 进入课程订单管理界面
     * @param []
     * @return java.lang.String
     * @author yinjimin
     * @date 2018/4/18
     */
    @RequireAuth(ident = "tutor")
    @RequestMapping("/gosystutororder")
    public String goSystutorOrder(){

        return "sysconfig/tutor/orderlist";
    }
}
