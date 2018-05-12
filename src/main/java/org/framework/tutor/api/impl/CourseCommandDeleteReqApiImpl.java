package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.CourseCommandDeleteReqApi;
import org.framework.tutor.domain.CourseCommand;
import org.framework.tutor.service.CourseCommandService;
import org.framework.tutor.service.CourseCommandDeleteReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class CourseCommandDeleteReqApiImpl implements CourseCommandDeleteReqApi {

    @Autowired
    private CourseCommandDeleteReqService courseCommandDeleteReqService;

    @Autowired
    private CourseCommandService courseCommandService;

    @Autowired
    private HttpServletRequest request;


    @Override
    @Transactional
    public String addCommandDeleteReq(Integer cid, String info) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        //判断评论的课程username是否所属相同
        CourseCommand courseCommand = courseCommandService.getCommandById(cid);
        if(courseCommand == null || !courseCommand.getUsername().equals(username)){
            resultMap.put("status", "invalid");
        }else{
            //提交申请，不管有没有都是添加，不会update
            courseCommandDeleteReqService.addCommandDeleteReq(username, cid, info);

            //对应的评论状态更新
            Integer status = 1;
            courseCommandService.updateCommandStatus(cid,status);
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }
}
