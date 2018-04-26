package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.TutorBtnsApi;
import org.framework.tutor.domain.TutorBtns;
import org.framework.tutor.domain.TutorsysBtns;
import org.framework.tutor.service.TutorBtnsService;
import org.framework.tutor.service.TutorsysBtnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 指定家教常用链接控制器
 * @author chengxi
 */
@RestController
@RequestMapping("/tutorbtns_con")
public class TutorBtnsController {

    @Autowired
    private TutorBtnsApi tutorBtnsApi;

    /**
     * 获取当前家教的所有常用链接数据
     * @param request
     * @param response
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getbtnslist")
    public void getBtnsList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        tutorBtnsApi.getBtnsList(request, response);
    }
}
