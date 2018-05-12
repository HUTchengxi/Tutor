package org.framework.tutor.interceptor;

import com.google.gson.Gson;
import org.framework.tutor.annotation.OneLogin;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.entity.OneLoginSingle;
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

        response.setCharacterEncoding("utf-8");
        String url = request.getRequestURI();
        System.out.print("url: " + url + "\t");
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String sessionId = session.getId();
        OneLogin oneLogin = handlerMethod.getMethod().getAnnotation(OneLogin.class);
        if (username == null) {
            username = request.getParameter("username");
        }
        String loginId = OneLoginSingle.getV(username);
        if (loginId == null) {
            OneLoginSingle.addKAndV(username, sessionId);
        }
        if (oneLogin != null) {
            if (!sessionId.equals(loginId)) {
                OneLoginSingle.addKAndV(username, sessionId);
            }
        } else {
            //判断sessionId和最近登录Id是否对应，不对应就挤出
            if (!sessionId.equals(loginId) && loginId != null) {
//                session.invalidate();
                session.removeAttribute("username");
                session.removeAttribute("nickname");
                session.removeAttribute("identity");
            }
        }

        //目前该拦截器暂时只支持拦截方法级别
        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            System.out.println("INFO urlInterceptor : 暂时不支持方法级别的拦截");
            return true;
        }
        RequireAuth requireAuth = handlerMethod.getMethod().getAnnotation(RequireAuth.class);
        if (requireAuth == null) {
            System.out.println("未设置注解，可以访问");
            return true;
        }

        String ident = requireAuth.ident();
        String type = requireAuth.type();
        Integer identity = (Integer) session.getAttribute("identity");
        if (ident == null) {
            System.out.println("未设置角色，可以访问");
            return true;
        }
        //url拦截
        if (type.equals("url")) {
            if (ident.equals("user") && identity != null) {
                System.out.println("普通用户级别已登录，可以访问");
                return true;
            }
            if (ident.equals("admin") && identity != null && identity == -1) {
                System.out.println("管理员级别已登录，可以访问");
                return true;
            }
            if (ident.equals("tutor") && identity != null && (identity == 1 || identity == -1)) {
                System.out.println("家教级别已登录，可以访问");
                return true;
            }
            //管理接口盗用时模拟404页面不存在
            if (ident.equals("admin") && identity != null && identity != -1) {
                request.getRequestDispatcher("/forward_con/zouyi").forward(request, response);
                System.out.println("管理员级别未登录，拦截");
                return false;
            }
            request.getRequestDispatcher("/forward_con/gologin").forward(request, response);
            System.out.println("无相应权限，拦截");
        }
        //api接口拦截
        else {
            Gson gson = new Gson();
            Map<String, Object> resultMap = new HashMap<>(1);
            if (ident.equals("user") && identity != null) {
                System.out.println("普通用户级别已登录，可以访问");
                return true;
            }
            if (ident.equals("admin") && identity != null && identity == -1) {
                System.out.println("管理员级别已登录，可以访问");
                return true;
            }
            if (ident.equals("tutor") && identity != null && (identity == 1 || identity == -1)) {
                System.out.println("家教级别已登录，可以访问");
                return true;
            }
            //管理接口盗用时模拟404页面不存在
            PrintWriter writer = response.getWriter();
            if (ident.equals("admin") && identity != null && identity != -1) {
                System.out.println("管理员级别未登录，拦截");
                resultMap.put("status", "notfound");
                writer.print(gson.toJson(resultMap));
                writer.flush();
                writer.close();
                return false;
            }
            System.out.println("无相应权限或未登录，拦截");
            resultMap.put("status", "invalid");
            writer.print(gson.toJson(resultMap));
            writer.flush();
            writer.close();
        }
        return false;
    }
}
