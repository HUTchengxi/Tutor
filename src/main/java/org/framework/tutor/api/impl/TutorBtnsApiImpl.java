package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.TutorBtnsApi;
import org.framework.tutor.domain.TutorBtns;
import org.framework.tutor.domain.TutorsysBtns;
import org.framework.tutor.service.TutorBtnsService;
import org.framework.tutor.service.TutorsysBtnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TutorBtnsApiImpl implements TutorBtnsApi {

    @Autowired
    private TutorBtnsService tutorBtnsService;

    @Autowired
    private TutorsysBtnsService tutorSysBtnsService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String getBtnsList() throws IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<TutorBtns> tutorBtnsList = tutorBtnsService.getBtnsList(username);

        if(tutorBtnsList.size() == 0){
            resultMap.put("count", 0);
        }
        else{
            for (TutorBtns tutorBtns: tutorBtnsList) {
                //根据id获取对应的链接数据
                TutorsysBtns tutorsysBtns = tutorSysBtnsService.getById(tutorBtns.getBid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("id", tutorBtns.getId());
                rowMap.put("name", tutorsysBtns.getName());
                rowMap.put("url", tutorsysBtns.getUrl());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }
}
