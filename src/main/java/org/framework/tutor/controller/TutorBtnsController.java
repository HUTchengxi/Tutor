package org.framework.tutor.controller;

import com.google.gson.JsonParser;
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
    private TutorBtnsService tutorBtnsService;

    @Autowired
    private TutorsysBtnsService tutorSysBtnsService;

    /**
     * 获取当前家教的所有常用链接数据
     * @param request
     * @param response
     */
    @RequestMapping("/getbtnslist")
    public void getBtnsList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String res = null;

        List<TutorBtns> tutorBtnsList = tutorBtnsService.getBtnsList(username);

        if(tutorBtnsList.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else{
            res = "{";
            int i = 1;
            for (TutorBtns tutorBtns: tutorBtnsList) {
                //根据id获取对应的链接数据
                TutorsysBtns tutorsysBtns = tutorSysBtnsService.getById(tutorBtns.getBid());
                res += "\""+i+"\": ";
                String temp = "{\"id\": \""+tutorBtns.getId()+"\", " +
                        "\"name\": \""+tutorsysBtns.getName()+"\", "+
                        "\"url\": \""+tutorsysBtns.getUrl()+"\"}, ";
                res += temp;
                i++;
            }
            res = res.substring(0, res.length()-2);
            res += "}";
        }

        writer.print(new JsonParser().parse(res).getAsJsonObject());
        writer.flush();
        writer.close();
    }
}
