package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import org.framework.tutor.api.SysEmailManagerApi;
import org.framework.tutor.domain.SysEmailManage;
import org.framework.tutor.domain.UserMain;
import org.framework.tutor.entity.EmailParam;
import org.framework.tutor.service.SysEmailManageService;
import org.framework.tutor.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SysEmailManagerApiImpl implements SysEmailManagerApi {

    @Autowired
    private SysEmailManageService sysEmailManageService;

    @Autowired
    private UserMainService userMainService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private HttpServletRequest request;


    @Override
    public String sendEmail(EmailParam emailParam) throws IOException, MessagingException {

        Gson gson = new Gson();

        //获取对应用户的address
        UserMain userMain = userMainService.getByUser(emailParam.getSend());
        Map<String, Object> resultMap = new HashMap<>(1);
        if (userMain == null) {
            resultMap.put("status", "noaddress");
        } else {
            Integer status = 1;
            String address = userMain.getEmail();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            messageHelper.setFrom(from);
            messageHelper.setTo(address);
            messageHelper.setSubject(emailParam.getTheme());
            messageHelper.setText(emailParam.getFormatEmail(), true);
            javaMailSender.send(mimeMessage);
            sysEmailManageService.sendEmail(emailParam.getSend(), address, emailParam.getTheme(), emailParam.getEmail(), status);
            resultMap.put("status", "sendok");
        }

        return gson.toJson(resultMap);
    }


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String saveEmail(EmailParam emailParam) throws IOException, MessagingException {

        Gson gson = new Gson();
        //获取对应用户的address
        UserMain userMain = userMainService.getByUser(emailParam.getSend());
        Map<String, Object> resultMap = new HashMap<>(1);
        if (userMain == null) {
            resultMap.put("status", "noaddress");
        } else {
            //判断是否存在id
            Integer emailId = emailParam.getId();
            SysEmailManage sysEmailManageById = null;
            if (emailId != null) {
                sysEmailManageById = sysEmailManageService.getById(emailId);
            }
            //新增
            if (sysEmailManageById == null) {
                Integer status = 0;
                String address = userMain.getEmail();
                //获取最新保存的id
                sysEmailManageService.sendEmail(emailParam.getSend(), address, emailParam.getTheme(), emailParam.getEmail(), status);
                Integer lastId = sysEmailManageService.getLastId();
                resultMap.put("emailId",lastId);
            }
            //更新
            else{
                sysEmailManageService.updateEmail(emailId, emailParam.getTheme(), emailParam.getEmail());
            }
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getEmailList(EmailParam emailParam) throws IOException {

        Gson gson = new Gson();

        Integer emailStatus = emailParam.getEmailStatus();
        Integer pageNo = emailParam.getPageNo();
        Integer pageSize = emailParam.getPageSize();
        Integer offset = pageNo * pageSize;
        List<Object> rowList = new ArrayList<>(pageSize);
        Map<String, Object> resultMap = new HashMap<>(2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取所有的email
        List<SysEmailManage> sysEmailManageList = null;
        Integer total = 0;
        if(emailStatus == -1){
            sysEmailManageList = sysEmailManageService.getAllEmailListLimit(offset, pageSize);
            total = sysEmailManageService.getAllEmailCount();
        }else{
            sysEmailManageList = sysEmailManageService.getEmailListByStatusLimit(emailStatus, offset, pageSize);
            total = sysEmailManageService.getEmailCountByStatus(emailStatus);
        }
        if(sysEmailManageList == null || sysEmailManageList.size() == 0){
            resultMap.put("rows", rowList);
            resultMap.put("total", 0);
        }else{
            for(SysEmailManage sysEmailManage: sysEmailManageList){
                Map<String, Object> rowMap = new HashMap<>(4);
                rowMap.put("emailTheme", sysEmailManage.getTheme());
                rowMap.put("emailStatus", sysEmailManage.getStatus());
                rowMap.put("updateTime", simpleDateFormat.format(sysEmailManage.getSendtime()));
                rowMap.put("emailId", sysEmailManage.getId());
                rowList.add(rowMap);
            }
            resultMap.put("rows", rowList);
            resultMap.put("total", total);
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getEmailDetail(Integer id) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        SysEmailManage sysEmailManage = sysEmailManageService.getById(id);
        if(sysEmailManage == null){
            resultMap.put("status", "none");
        }else{
            String status = sysEmailManage.getStatus() == 1?"已发送":"草稿";
            resultMap.put("sendto", sysEmailManage.getSendto());
            resultMap.put("theme", sysEmailManage.getTheme());
            resultMap.put("email", sysEmailManage.getEmail());
            resultMap.put("status", status);
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String deleteEmail(Integer id) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(1);

        SysEmailManage sysEmailManage = sysEmailManageService.getById(id);
        if(sysEmailManage == null){
            resultMap.put("status", "invalid");
        }else {
            sysEmailManageService.deleteEmial(id);
            resultMap.put("status", "valid");
        }

        return gson.toJson(resultMap);
    }

    @Override
    public String getModinfoById(Integer id) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(4);

        SysEmailManage sysEmailManage = sysEmailManageService.getById(id);
        if(sysEmailManage == null){
            resultMap.put("status", "invalid");
        }else{
            resultMap.put("status", "valid");
            resultMap.put("username", sysEmailManage.getSendto());
            resultMap.put("theme", sysEmailManage.getTheme());
            resultMap.put("email", sysEmailManage.getEmail());
        }

        return gson.toJson(resultMap);
    }
}
