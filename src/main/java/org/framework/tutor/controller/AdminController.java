package org.framework.tutor.controller;

import org.framework.tutor.api.AdminApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


/**
 * @author yinjimin
 * @Description: 管理控制类
 * @date 2018年04月19日
 */
@RestController
@RequestMapping("/admin_con")
public class AdminController {

    @Autowired
    private AdminApi adminApi;

    /**
     *
     * @Description 管理员登录
     * @param [username, password, remember, request]
     * @return void
     * @author yinjimin
     * @date 2018/4/19
     */
    @PostMapping("login.json")
    public String Login(@RequestParam String username, @RequestParam String password) throws IOException, NoSuchAlgorithmException {

        return adminApi.Login(username, password);
    }
}
