package org.framework.tutor.api;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.UserSign;
import org.framework.tutor.service.UserSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public interface UserSignApi {

    /**
     * 获取用户的签到数据
     * @param request
     * @param response
     * @throws IOException
     */
    public void getMySign(HttpServletRequest request, HttpServletResponse response) throws IOException;


    public void addUsersign(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
