package org.framework.tutor.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.framework.tutor.api.PublishLogApi;
import org.framework.tutor.domain.PublishLog;
import org.framework.tutor.domain.PublishType;
import org.framework.tutor.service.PublishLogService;
import org.framework.tutor.service.PublishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PublishLogApiImpl implements PublishLogApi {

    @Autowired
    private PublishLogService publishLogService;

    @Autowired
    private PublishTypeService publishTypeService;


    @Override
    public String getLogNew() throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        //获取最新记录数据
        List<PublishLog> publishLogList = publishLogService.getLogNew();

        if(publishLogList.size() == 0){
            resultMap.put("count", 0);
        }
        else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (PublishLog publishLog: publishLogList) {
                //获取版本类型
                PublishType publishType = publishTypeService.getById(publishLog.getTypeid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("ptype", publishType.getName());
                rowMap.put("pversion", publishLog.getPversion());
                rowMap.put("ptime", simpleDateFormat.format(publishLog.getPtime()));
                rowMap.put("descript", publishLog.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }


    @Override
    public String getLogAll() throws IOException {

        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Object> rowList = new ArrayList<>();

        List<PublishLog> publishLogs = publishLogService.getLogAll();

        if(publishLogs.size() == 0){
            resultMap.put("count", 0);
        }
        else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (PublishLog publishLog: publishLogs) {
                //获取版本类型
                PublishType publishType = publishTypeService.getById(publishLog.getTypeid());
                Map<String, Object> rowMap = new HashMap<>(8);
                rowMap.put("ptype", publishType.getName());
                rowMap.put("pversion", publishLog.getPversion());
                rowMap.put("ptime", simpleDateFormat.format(publishLog.getPtime()));
                rowMap.put("descript", publishLog.getDescript());
                rowList.add(rowMap);
            }
            resultMap.put("list", rowList);
        }

        return gson.toJson(resultMap);
    }
}
