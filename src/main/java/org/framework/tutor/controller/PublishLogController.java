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
     * @param response
     * @return
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getlognew")
    public void getLogNew(HttpServletResponse response) throws IOException {

        publishLogApi.getLogNew(response);
    }


    /**
     *
     * @Description 获取所有的版本更新记录
     * @param [response]
     * @return void
     * @author yinjimin
     * @date 2018/3/31
     */
    @RequireAuth(ident = "tutor", type = "api")
    @RequestMapping("/getlogall")
    public void getLogAll(HttpServletResponse response) throws IOException {
        publishLogApi.getLogAll(response);
    }
}
