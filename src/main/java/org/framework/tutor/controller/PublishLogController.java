package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.domain.PublishLog;
import org.framework.tutor.domain.PublishType;
import org.framework.tutor.service.PublishLogService;
import org.framework.tutor.service.PublishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 版本更新记录控制器
 * @author chengxi
 */
@RestController
@RequestMapping("/publishlog_con")
public class PublishLogController {

    @Autowired
    private PublishLogService publishLogService;

    @Autowired
    private PublishTypeService publishTypeService;

    /**
     * 加载最新的版本更新记录
     * @param response
     * @return
     */
    @RequestMapping("/getlognew")
    public void getLogNew(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        //获取最新记录数据
        List<PublishLog> publishLogList = publishLogService.getLogNew();

        if(publishLogList.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            res = "{";
            int i = 1;
            for (PublishLog publishLog: publishLogList) {
                //获取版本类型
                PublishType publishType = publishTypeService.getById(publishLog.getTypeid());
                res += "\""+i+"\": ";
                String temp = "{\"ptype\": \""+publishType.getName()+"\", " +
                        "\"pversion\": \""+publishLog.getPversion()+"\", "+
                        "\"ptime\": \""+simpleDateFormat.format(publishLog.getPtime())+"\", "+
                        "\"descript\": \""+publishLog.getDescript()+"\"}, ";
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


    /**  
     *    
     * @Description 获取所有的版本更新记录
     * @param [response]    
     * @return void
     * @author yinjimin  
     * @date 2018/3/31
     */  
    @RequestMapping("/getlogall")
    public void getLogAll(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String res = null;

        List<PublishLog> publishLogs = publishLogService.getLogAll();

        if(publishLogs.size() == 0){
            res = "{\"count\": \"0\"}";
        }
        else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            res = "{";
            int i = 1;
            for (PublishLog publishLog: publishLogs) {
                //获取版本类型
                PublishType publishType = publishTypeService.getById(publishLog.getTypeid());
                res += "\""+i+"\": ";
                String temp = "{\"ptype\": \""+publishType.getName()+"\", " +
                        "\"pversion\": \""+publishLog.getPversion()+"\", "+
                        "\"ptime\": \""+simpleDateFormat.format(publishLog.getPtime())+"\", "+
                        "\"descript\": \""+publishLog.getDescript()+"\"}, ";
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
