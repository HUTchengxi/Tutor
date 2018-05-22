package org.framework.tutor.controller;

import com.google.gson.JsonParser;
import org.framework.tutor.api.CommonImgsrcApi;
import org.framework.tutor.domain.CommonImgsrc;
import org.framework.tutor.service.CommonImgsrcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yinjimin
 * @Description: 通用图片数据控制类
 * @date 2018年04月01日
 */
@RestController
@RequestMapping("/commonimgsrc_con")
public class CommonImgsrcController {

    @Autowired
    private CommonImgsrcApi commonImgsrcApi;


    /**
     * @Description 获取所有图片数据
     */
    @RequestMapping("/getAll.json")
    public String getAll() throws IOException {

        return commonImgsrcApi.getAll();
    }
}
