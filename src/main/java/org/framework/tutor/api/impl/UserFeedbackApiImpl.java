package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.UserFeedbackApi;
import org.framework.tutor.domain.UserFeedback;
import org.framework.tutor.entity.ParamMap;
import org.framework.tutor.service.UserFeedbackService;
import org.framework.tutor.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserFeedbackApiImpl implements UserFeedbackApi {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String getMyFeedback() {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = null;

        List<UserFeedback> userFeedbacks = userFeedbackService.getMyFeedback(username);
        if(userFeedbacks.size() > 0){
            rowList = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(UserFeedback userFeedback: userFeedbacks){
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("id", userFeedback.getId());
                rowMap.put("info", userFeedback.getInfo());
                rowMap.put("ptime", simpleDateFormat.format(userFeedback.getPtime()));
                rowList.add(rowMap);
            }
        }
        resultMap.put("rows", rowList);
        return gson.toJson(resultMap);
    }

    @Override
    public String removeMyFeedback(Integer id) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        //首先判断是否是该该用户的
        UserFeedback userFeedback = userFeedbackService.getByUserAndId(id, username);
        if(userFeedback == null){
            resultMap.put("status", "invalid");
        }else{
            //设置status
            Integer status = -1;
            Integer row = userFeedbackService.modMyFeedbackStatus(id, status);
            if(row == 1){
                resultMap.put("status", "valid");
            }else{
                resultMap.put("status", "sqlerr");
                status = userFeedback.getStatus();
                userFeedbackService.modMyFeedbackStatus(id, status);
            }
        }
        return gson.toJson(resultMap);
    }

    @Override
    public String saveMyFeedback(String info) {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userFeedbackService.saveMyFeedback(username, info);
        if(row == 1){
            resultMap.put("status", "valid");

            //发送通知
            Integer identity = 1;
            String suser= "chengxi";
            String title = "反馈成功通知";
            String message = "谢谢您的反馈，稍后我们会第一时间通知您反馈进度";
            userMessageService.seneMessage(identity, suser, username, title, message);
        }else{
            resultMap.put("status", "sqlerr");
        }
        return gson.toJson(resultMap);
    }

    @Override
    public String getUserFeedback(ParamMap paramMap) {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        String username = paramMap.getCourseName();
        if(username == null){
            username = "";
        }
        Integer status = paramMap.getStatus();
        Integer pageNo = paramMap.getPageNo();
        Integer pageSize = paramMap.getPageSize();
        Integer offset = pageNo * pageSize;

        List<UserFeedback> userFeedbacks = null;
        Integer total = 0;
        if(status != null) {
             userFeedbacks = userFeedbackService.getUserFeedback(username, status, offset, pageSize);
             total = userFeedbackService.getUserFeedbackCount(username, status);
        }else{
            userFeedbacks = userFeedbackService.getUserFeedback(username, offset, pageSize);
            total = userFeedbackService.getUserFeedbackCount(username);
        }
        if(userFeedbacks.size() > 0){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(UserFeedback userFeedback: userFeedbacks){
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("id", userFeedback.getId());
                rowMap.put("username", userFeedback.getUsername());
                rowMap.put("info", userFeedback.getInfo());
                rowMap.put("ptime", simpleDateFormat.format(userFeedback.getPtime()));
                rowMap.put("status", userFeedback.getStatus());
                rowList.add(rowMap);
            }
        }
        resultMap.put("rows", rowList);
        resultMap.put("total", total);
        return gson.toJson(resultMap);
    }

    @Override
    public String removeUserFeedback(Integer id) {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer row = userFeedbackService.removeUserFeedback(id);
        if(row != 1){
            resultMap.put("status", "sqlerr");
        }else{
            resultMap.put("status", "valid");
        }
        return gson.toJson(resultMap);
    }

    @Override
    public String modUserFeedbackStatus(ParamMap paramMap) {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);

        Integer id = paramMap.getPageNo();
        Integer status = paramMap.getStatus();

        Integer row = userFeedbackService.modMyFeedbackStatus(id, status);
        if(row == 1){
            resultMap.put("status", "valid");
        }else{
            resultMap.put("status", "sqlerr");
        }
        return gson.toJson(resultMap);
    }
}
