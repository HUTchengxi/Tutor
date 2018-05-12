package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseDeleteRespApi;
import org.framework.tutor.domain.CourseDeleteResp;
import org.framework.tutor.service.CourseDeleteRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class CourseDeleteRespApiImpl implements CourseDeleteRespApi {

    @Autowired
    private CourseDeleteRespService courseDeleteRespService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String modReqStatus(Integer id, Integer status, String respDesc) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        CourseDeleteResp courseDeleteResp = courseDeleteRespService.getByRid(id);
        if(courseDeleteResp == null){
            courseDeleteRespService.insertResp(id, status, respDesc);
        }else{
            courseDeleteRespService.updateResp(id, status, respDesc);
        }
        resultMap.put("status", "valid");

        return gson.toJson(resultMap);
    }
}
