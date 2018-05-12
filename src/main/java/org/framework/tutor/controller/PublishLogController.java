package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.annotation.RequireAuth;
import org.framework.tutor.api.PublishLogApi;
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
    private PublishLogApi publishLogApi;

    /**
     * 加载最新的版本更新记录
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getlognew")
    public String getLogNew() throws IOException {

       return publishLogApi.getLogNew();
    }


    /**
     * @Description 获取所有的版本更新记录
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getlogall")
    public String getLogAll() throws IOException {

        return publishLogApi.getLogAll();
    }
}
