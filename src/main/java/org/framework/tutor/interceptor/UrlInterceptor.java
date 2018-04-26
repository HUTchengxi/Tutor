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
package org.framework.tutor.interceptor;

import com.google.gson.Gson;
import org.framework.tutor.annotation.RequireAuth;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinjimin
 * @Description: url拦截器
 * @date 2018年04月26日
 */
public class UrlInterceptor extends AbstractHandlerInterceptor {

    /**
     * @param [httpServletRequest, httpServletResponse, o]
     * @return boolean
     * @Description 进入Controller之前时调用
     * @author yinjimin
     * @date 2018/4/26
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //目前该拦截器暂时只支持拦截方法级别
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            System.out.println("INFO urlInterceptor : 暂时不支持方法级别的拦截");
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireAuth requireAuth = handlerMethod.getMethod().getAnnotation(RequireAuth.class);
        if (requireAuth == null) {
            System.out.println("INFO urlInterceptor : 无需校验权限的方法->" + handlerMethod.getMethod().getName());
            return true;
        }

        String ident = requireAuth.ident();
        String type = requireAuth.type();
        HttpSession session = request.getSession();
        Integer identity = (Integer) session.getAttribute("identity");
        if (ident == null) {
            return true;
        }
        //url拦截
        if (type.equals("url")) {
            if (ident.equals("user") && identity != null) {
                return true;
            }
            if (ident.equals("admin") && identity == -1) {
                return true;
            }
            if (ident.equals("tutor") && (identity == 1 || identity == -1)) {
                return true;
            }
            //管理接口盗用时模拟404页面不存在
            if (ident.equals("admin") && identity != -1) {
                request.getRequestDispatcher("/forward_con/zouyi").forward(request, response);
                return false;
            }
            request.getRequestDispatcher("/forward_con/gologin").forward(request, response);
        }
        //api接口拦截
        else {
            Gson gson = new Gson();
            PrintWriter writer = response.getWriter();
            Map<String, Object> resultMap = new HashMap<>(1);
            if (ident.equals("user") && identity != null) {
                return true;
            }
            if (ident.equals("admin") && identity == -1) {
                return true;
            }
            if (ident.equals("tutor") && (identity == 1 || identity == -1)) {
                return true;
            }
            //管理接口盗用时模拟404页面不存在
            if (ident.equals("admin") && identity != -1) {
                resultMap.put("status", "notfound");
                writer.print(gson.toJson(resultMap));
                writer.flush();
                writer.close();
                return false;
            }
            resultMap.put("status", "invalid");
            writer.print(gson.toJson(resultMap));
            writer.flush();
            writer.close();
        }
        return false;
    }
}
